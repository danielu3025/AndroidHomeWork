package com.example.danielluzgarten.androidhomework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by danielluzgarten on 17/12/2016.
 */

public class drowView extends View {
    Paint paint = new Paint();
    private ShapeDrawable circleFill, circleLine;


    public drowView(Context context) {
        super(context);
        init();
    }


    public drowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public drowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setStrokeWidth(4);
        paint.setColor(0xff_00_00_ff);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {


        //H
        canvas.drawLine(0,0,0,100,paint);
        canvas.drawLine(0,50,50,50,paint);
        canvas.drawLine(50,0,50,100,paint);
        //E
        canvas.drawLine(70,0,70,100,paint);
        canvas.drawLine(70,0,130,0,paint);
        canvas.drawLine(70,50,130,50,paint);
        canvas.drawLine(70,100,130,100,paint);
        //L
        canvas.drawLine(150,0,150,100,paint);
        canvas.drawLine(150,100,200,100,paint);
        //L
        canvas.drawLine(220,0,220,100,paint);
        canvas.drawLine(220,100,270,100,paint);
        //O
        canvas.drawOval(280,0,340,100,paint);
        paint.setColor(0xff_ff_ff_00);
        canvas.drawOval(284,4,336,96,paint);
    }
}
