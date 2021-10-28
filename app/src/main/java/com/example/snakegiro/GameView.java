package com.example.snakegiro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ArrayRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.state.ConstraintReference;

import java.util.ArrayList;

public class GameView extends View {
    private Bitmap bmTuile1, bmTuile2;
    public static int sizeOfMap = 75*Constants.SCREEN_WIDTH/1000;
    private int h = 21, w = 12;
    private ArrayList<Tuile> arrTuile = new ArrayList<>();
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bmTuile1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        bmTuile1 = Bitmap.createScaledBitmap(bmTuile1, sizeOfMap, sizeOfMap, true);
        bmTuile2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass1);
        bmTuile2 = Bitmap.createScaledBitmap(bmTuile2, sizeOfMap, sizeOfMap, true);
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                if((i+j)%2==0){
                    arrTuile.add(new Tuile(bmTuile1, j*sizeOfMap + Constants.SCREEN_WIDTH/2-(w/2)*sizeOfMap, i*sizeOfMap+100*Constants.SCREEN_HEIGHT/1920, sizeOfMap, sizeOfMap));
                }else{
                    arrTuile.add(new Tuile(bmTuile2, j*sizeOfMap + Constants.SCREEN_WIDTH/2-(w/2)*sizeOfMap, i*sizeOfMap+100*Constants.SCREEN_HEIGHT/1920, sizeOfMap, sizeOfMap));
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0xFFFF0000);
        for (int i = 0; i < arrTuile.size(); i++){
            canvas.drawBitmap(arrTuile.get(i).getBm(), arrTuile.get(i).getX(), arrTuile.get(i).getY(), null);
        }
    }
}
