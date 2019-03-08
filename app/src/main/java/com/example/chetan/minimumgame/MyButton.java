package com.example.chetan.minimumgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * Class to create custome button and draw them
 * on the UI
 */
public class MyButton {
    private Matrix btn_matrix = new Matrix();
    private RectF btn_rect;
    private float width;
    private float height;
    private Bitmap btn_image;
    float rotationangle;

    public MyButton(int starting_X, int starting_Y, float width, float height, Bitmap btn_image) {
        this.width = width;
        this.height = height;
        this.btn_image = btn_image;
        btn_rect = new RectF(starting_X - width, starting_Y - height, starting_X, starting_Y);
    }

    public MyButton(float left, float top, float right, float bottom, Bitmap btn_image) {

    }

    public void setPostion(float x, float y) {
        btn_matrix.setTranslate(x, y);
        btn_matrix.mapRect(btn_rect);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(btn_image, null, btn_rect, null);
    }

    public Bitmap getBtn_image() {
        return btn_image;
    }

    public RectF getBtn_rect() {
        return btn_rect;
    }
}
