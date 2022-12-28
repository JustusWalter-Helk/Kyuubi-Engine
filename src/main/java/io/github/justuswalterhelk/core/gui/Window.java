package io.github.justuswalterhelk.core.gui;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window
{
    private final int width;
    private final int height;
    private final String title;

    private final boolean fullScreen;

    public long getWindowID() {
        return window;
    }

    private long window;

    public Window(int width, int height, String title, boolean fullScreen)
    {
        this.width = width;
        this.height = height;
        this.title = title;

        this.fullScreen = fullScreen;
    }

    public Window run()
    {
        System.out.println("Running window with id of " + window);
        loop();

        return this;
    }

    public Window initWindow()
    {
        //Print errors to console
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if(!glfwInit())
        {
            //TODO: Add a warning in the engine debugger
            throw new IllegalStateException("Initalizing GLFW failed!");
        }

        //Reset all hints to their default values
        glfwDefaultWindowHints();
        //Set the window properties
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, fullScreen ? 1:0);

        //TODO: Add monitor and share to constructor
        //Create the window and save its id in a long
        window = glfwCreateWindow(this.width,this.height,this.title, NULL,NULL);
        if(window == NULL)
        {
            //TODO: Add a warning in the engine debugger
            throw new IllegalStateException("Failed to create window");
        }

        //WARNING: Needs proper handling when dealing with multiple windows on the same Thread!
        glfwMakeContextCurrent(window);

        //V-Sync
        //glfwSwapInterval(1)

        //Show the window
        glfwShowWindow(window);

        //IMPORTANT
        //Create all the needed opengl objects
        //TODO: Needs proper handling with multi threading
        GL.createCapabilities();

        return this;
    }

    private void loop()
    {
        //Can be called from any thread!
        while(!glfwWindowShouldClose(window))
        {
            //Process all events that are still in the queue
            glfwPollEvents();

            //Clear color
            glClearColor(1.0f,1.0f,1.0f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            //Switches the buffers to display the next frame
            glfwSwapBuffers(window);
        }
    }
}
