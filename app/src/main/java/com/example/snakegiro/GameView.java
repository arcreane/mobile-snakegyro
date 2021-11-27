package com.example.snakegiro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;
import android.os.Handler;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private Bitmap bmTuile1, bmTuile2, bmSnake;
    public static int sizeOfMap = 75*Constants.SCREEN_WIDTH/1000;
    private int h = 21, w = 12;
    private ArrayList<Tuile> arrTuile = new ArrayList<>();
    private Snake snake;
    private boolean move = false;
    private Handler handler;
    private Runnable runnable;
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //design du jeu
        bmTuile1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        bmTuile1 = Bitmap.createScaledBitmap(bmTuile1, sizeOfMap, sizeOfMap, true);
        bmTuile2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass1);
        bmTuile2 = Bitmap.createScaledBitmap(bmTuile2, sizeOfMap, sizeOfMap, true);
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14*sizeOfMap, sizeOfMap, true);

        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                if((i+j)%2==0){
                    arrTuile.add(new Tuile(bmTuile1, j*sizeOfMap + Constants.SCREEN_WIDTH/2-(w/2)*sizeOfMap, i*sizeOfMap+100*Constants.SCREEN_HEIGHT/1920, sizeOfMap, sizeOfMap));
                }else{
                    arrTuile.add(new Tuile(bmTuile2, j*sizeOfMap + Constants.SCREEN_WIDTH/2-(w/2)*sizeOfMap, i*sizeOfMap+100*Constants.SCREEN_HEIGHT/1920, sizeOfMap, sizeOfMap));
                }
            }
        }
        snake = new Snake(bmSnake, arrTuile.get(126).getX(), arrTuile.get(126).getY(), 4);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        //variable du gyro avec ajouts
        SensorEventListener gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (move == false) {
                    move = true;
                } else { //mouvements dans chaque sens en fonction de son orientation
                    if (event.values[2] > 0.5f && !snake.isMove_right()) {
                        snake.setMove_top(true);
                    } else if (event.values[2] > -0.5f && snake.isMove_right()) {
                        snake.setMove_bottom(true);
                    } else if (event.values[2] > 0.5f && snake.isMove_left()) {
                        snake.setMove_bottom(true);
                    } else if (event.values[2] > -0.5f && snake.isMove_left()) {
                        snake.setMove_top(true);
                    } else if (event.values[2] > 0.5f && snake.isMove_top()) {
                        snake.setMove_left(true);
                    } else if (event.values[2] > -0.5f && snake.isMove_top()) {
                        snake.setMove_right(true);
                    } else if (event.values[2] > 0.5f && snake.isMove_bottom()) {
                        snake.setMove_right(true);
                    } else if (event.values[2] > -0.5f && snake.isMove_bottom()) {
                        snake.setMove_left(true);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    };


    //création de la carte
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0xFFFF0000);
        for (int i = 0; i < arrTuile.size(); i++){
            canvas.drawBitmap(arrTuile.get(i).getBm(), arrTuile.get(i).getX(), arrTuile.get(i).getY(), null);
        }
        //mise à jour carte et snake
        snake.update();
        snake.draw(canvas);
        handler.postDelayed(runnable, 2000);
    }
}

