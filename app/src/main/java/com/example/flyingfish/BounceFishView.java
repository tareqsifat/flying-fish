package com.example.flyingfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BounceFishView extends View
{
    private Bitmap fish[]= new Bitmap[2];

    private int fishX = 10;
    private int fishY;
    private int fishspeed;

    private int canvasWidth, canvasHight;

    private int yellowX, yellowY, yellowSpeed = 11;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 13;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 5;
    private Paint redPaint = new Paint();

    private int score, lifeCounterofFish;

    private boolean touch = false;

    private Bitmap backgroundimage;
    private Paint scorepaint = new Paint();
    private Bitmap heart[] = new Bitmap[2];


    public BounceFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        backgroundimage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(50);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);
        heart[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        heart[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounterofFish = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = getWidth();
        canvasHight = getHeight();


        canvas.drawBitmap(backgroundimage, 0, 0, null);
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHight - fish[0].getHeight() * 3;
        fishY = fishY + fishspeed;

        if (fishY < minFishY) {
            fishY = minFishY;
        }
        if (fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishspeed = fishspeed + 2;

        if (touch)
        {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;

        }
        else
        {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }


        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY))
        {
            score = score + 10;
            yellowX = -100;
        }

        if (yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(yellowX, yellowY, 10, yellowPaint);

        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX, greenY))
        {
            score = score + 20;
            greenX = -100;
        }

        if (greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(greenX, greenY, 18, greenPaint);

        redX = redX - redSpeed;

        if (hitBallChecker(redX, redY))
        {
            redX = -100;
            lifeCounterofFish--;

            if (lifeCounterofFish == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);

                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX < 0)
        {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(redX, redY, 26, redPaint);


        canvas.drawText("score :"+ score, 20, 60, scorepaint);
        for (int i=0; i<3; i++)
        {
            int x = (int) (300 + heart[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i<lifeCounterofFish)
            {
                canvas.drawBitmap(heart[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(heart[1], x, y, null);
            }
        }



    }

    public boolean hitBallChecker (int x, int y)
    {
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;
            fishspeed = -22;
        }
        return true;
    }
}
