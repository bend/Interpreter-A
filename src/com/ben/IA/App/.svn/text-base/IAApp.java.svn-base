/*
 * IAApp.java
 */

package com.ben.IA.App;

import com.ben.IA.Views.*;
import java.io.File;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class IAApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        initiateFolders();
        show(new IAView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of IAApp
     */
    public static IAApp getApplication() {
        return Application.getInstance(IAApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(IAApp.class, args);
    }

    

    private void initiateFolders() {
        File f= new File("Conf");
        f.mkdir();
    }
}
