package com.example.mcmorris.imtilted;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Josh on 2/27/2018.
 * Class regarding the player object's rendering, movement, etc.
 * TODO: Better texture, animations, maybe able to jump (long term)
 */

public class Player implements GameObject {

    private Point playerPoint;
    public Point getPlayerPoint() {
        float radius = bitmap.getWidth() / 2;
        return new Point((int)(playerPoint.x + radius), (int)(playerPoint.y + radius));
    }

    private TiltManager tiltManager;
    private long frameTime;
    private Bitmap bitmap;
    //Arbitrary value
    //TODO: Allow the user to set this in settings??
    private float playerSpeed = 3.4f;
    float radius;
    int originalPosX;

    Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    private Boolean alive = false;

    Player(Point playerStart, TiltManager tiltManager) {
        this.playerPoint = playerStart;
        this.tiltManager = tiltManager;
        frameTime = -1;
        bitmap = BitmapFactory.decodeResource(Constants.resources, R.drawable.ball);
        radius = bitmap.getHeight() / 2;
        playerPoint.x -= bitmap.getWidth() / 2;
        originalPosX = playerPoint.x;
    }

    //THIS NEEDS TO BE CALLED ON RESUME OR RIPPP PLAYER
    void reset() {
        setAlive(true);
        frameTime = -1;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!alive) {
            /*bitmap.setHeight((int)radius);
            bitmap.setWidth((int)radius);
            radius -= .00005f;*/
        }
        canvas.drawBitmap(bitmap, playerPoint.x, playerPoint.y, null);
    }

    @Override
    public void update() {
        if (frameTime == -1)
            frameTime = System.currentTimeMillis();
        else if (alive) {
            long elapsedTime = System.currentTimeMillis() - frameTime;
            frameTime = System.currentTimeMillis();
            if (tiltManager.getTilt() != null) {
                //float roll = tiltManager.getTilt()[2] - tiltManager.getInitialTilt()[2];
                float roll = tiltManager.getTilt()[2];

                float movement = roll * playerSpeed;
                playerPoint.x += movement * elapsedTime;
                //These values shouldn't be possible anyway
                if (playerPoint.x < 0)
                    playerPoint.x = 0;
                if (playerPoint.x > Constants.screenWidth - bitmap.getWidth())
                    playerPoint.x = Constants.screenWidth - bitmap.getWidth();


            }
        }
    }

    public void resetPos() {
        playerPoint.x = originalPosX;
    }




}
