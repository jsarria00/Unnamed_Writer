package ui;

import util.UnnamedApplication;

import javax.swing.*;
import java.awt.*;

//To play an audio file
//CustomClasses
//import java.awt.image.BufferedImage;
//import java.awt.Point;


/**
 * A class that creates a JFrame, and a util.UnnamedMediaPlayerHolder and prepares them for use.
 * @author Javier Sarria Bastidas
 * @version W2
 */
public class UnnamedWriter {
    private static Thread mediaThread;
    private static UnnamedApplication player;

    /**
     * constructs a new JFrame and sets up additional settings
     * @return Returns a JFrame with a set title "Audio ui.UnnamedWriter" with a starting size of 640x400 pixels.
     */
    private static JFrame createFrame()
    {
        //System.out.println("Creating a frame with XxY size.");//for debug
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(1000, 800));
        frame.setSize(1000,800);
        frame.setTitle("Unnamed");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    /**
     * sets a frame to visible and also adds the visualizer component to the frame. This allows the user to start interacting with the UI
     * @param frame a pre-created frame that will be modified to be visible and hold the visualizer component
     */
    private static void openUI(JFrame frame)
    {

    }


    public static void main(String[] args)
    {
        //System.setProperty("sun.java2d.opengl", "true");
        boolean debug = false;
        //lineArguments bool setter
        for(int i=0; i<args.length; i++)
        {
            debug = args[i].equals("-debug");
        }
        player = new UnnamedApplication(debug);
        mediaThread = new Thread(player);
        mediaThread.start();
        JFrame fr = createFrame();
        openUI(fr);


    }
}
