package com.example.mcmorris.imtilted;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by Josh on 2/13/2018.
 * Class to override base Java Thread
 * TODO: Handle frame rate stuff
 */

public class MainThread extends Thread {
    public static final int FPS = 30;
    private final SurfaceHolder surfaceHolder;
    private GameContent gameContent;
    private boolean running;
    private static Canvas canvas;

    // This variable represents the listener passed in by the owning object
    // The listener must implement the events interface and passes messages up to the parent.



    MainThread(SurfaceHolder surfaceHolder, GameContent gameContent) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameContent = gameContent;
        // set null or default listener or accept as argument to constructor

    }

    void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime;
        while(running) {
            //startTime = System.nanoTime();

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameContent.update();
                    this.gameContent.draw(canvas);
                }
            } catch(Exception e) { e.printStackTrace(); }
            finally {
                if(canvas != null)
                {
                      try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch(Exception e) { e.printStackTrace(); }
                }
            }

        }


    }





}
