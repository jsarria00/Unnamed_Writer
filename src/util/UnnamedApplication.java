package util;

import javafx.scene.media.AudioSpectrumListener;
import javafx.util.Duration;

import static java.lang.Thread.sleep;

/**
 * A class responsible for initiating the Application Thread of util.UnnamedMediaPlayerHolder
 * The Class is responsible as a communication interface for the MediaQueuer, VisualizerComponent with some of its children, and the util.UnnamedMediaPlayerHolder
 */
public class UnnamedApplication implements Runnable {
    private UnnamedMediaPlayerHolder player;
    private boolean isPlaying = false;
    private boolean debug;


    /**
     * Default constructor, which requires a boolean to check if it will start the util.UnnamedApplication in debug mode or not
     *
     * @param debug boolean value that determine if debug mode turned on.
     */
    public UnnamedApplication(boolean debug) {
        this.debug = debug;
        player = new UnnamedMediaPlayerHolder(debug);
    }

    /**
     * gets the current of the util.UnnamedMediaPlayerHolder
     * @return int
     */
    public int getVolume()
    {
        return player.getVolume();
    }

    /**
     * gets the total duration of the current load player in util.UnnamedMediaPlayerHolder as a double
     * @return double
     */
    public double getEndTime()
    {
        return player.getEndTime();
    }

    /**
     * gets the current duration as a double of the util.UnnamedMediaPlayerHolder MediaPlayer
     * @return double
     */
    public double getCurrentTime()
    {
        return player.getCurrentTime();
    }

    /**
     * Transfers a requested Duration to the util.UnnamedMediaPlayerHolder
     * @param requestedTime Duration requested from a time interval
     */
    public void setSeek(Duration requestedTime)
    {
        player.setSeek(requestedTime);
    }

    /**
     * Prepares a Runnable class to function with the Java util.UnnamedApplication platform in threading
     * @param r Class that implements the Runnable interface
     */
    private static void startup(Runnable r)
    {
        com.sun.javafx.application.PlatformImpl.startup(r);
    }

    /**
     * Changes the play state of the MediaPlayerHolder to its opposite state, and then updates the util.UnnamedApplication with the MediaPlayer's play state.
     */
    public void togglePlayState()
    {
        if(isPlaying)
        {
            player.pause();
        }else
        {
            player.play();
        }//Only request once, so that it does not get hogged by the J.AWT thread
       isPlaying = player.isPlaying();
    }


    /**
     * Endlessly feeds the Application thread to get a new set of instructions via String input.
     */
    private void start()
    {
        while(true) {
            startup(player);

            //Wait for player to change to inUse
            try {
                sleep(200);
            } catch(InterruptedException e) {
                System.out.println("Main thread sleep was interrupted");
            }
            while(inUse() || isLoading()) {

                try {
                    sleep(200);
                    isPlaying = player.isPlaying();
                    //System.out.println("Waiting till free");
                } catch (InterruptedException e) {
                    System.out.println("Main thread sleep was interrupted");
                }
            }
        }
    }

    /**
     * checks if the MediaPlayerHolder is busy with a loading task
     * @return boolean
     */
    public boolean isLoading() {
        return player.isLoading();
    }

    /**
     * checks if the MediaPlayerHolder is busy with a task via terminal input( -debug argument)
     * @return boolean
     */
    public boolean inUse() {
        return player.inUse();
    }

    /**
     * Returns the playing status
     * @return Boolean which determines if the util.UnnamedMediaPlayerHolder is Playing
     */
    public boolean isPlaying()
    {
        return isPlaying;
    }

    /**
     * Requests the util.UnnamedMediaPlayerHolder to load a file
     * @param path string containing the Absolute directory and file name
     */
    public void load(String path)
    {
        player.load(path);
        isPlaying = player.isPlaying();
    }

    /**
     * returns a reference to the Universal songLog Object in MediaPlayerHolder and util.UnnamedApplication.
     * @return a SongLog object
     */

    public void setAudioSpectrumListener(AudioSpectrumListener audioSpectrumListener)
    {
        player.setAudioSpectrumListener(audioSpectrumListener);
    }


    /**
     * Overridden method for Runnable interface via threading.
     * If in debug mode util.UnnamedApplication will request string input until quit.
     * Otherwise Java Application thread will be fed, and util.UnnamedApplication will consistently request and save the playing status of util.UnnamedMediaPlayerHolder
     */
    @Override
    public void run() {
        if(debug) {
            start();
        }
        else
        {
            startup(player);
            while (true) {
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("Main thread sleep was interrupted");
                }
                isPlaying = player.isPlaying();
            }
        }
    }
}
