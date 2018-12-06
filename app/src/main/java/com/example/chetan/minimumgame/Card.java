package com.example.chetan.minimumgame;

import android.content.Context;
import android.graphics.Bitmap;

public class Card {
    private int current_X;
    private int current_Y;
    private boolean showcardface;
    private Bitmap Image;
    private  Rank CardValue;
    private Suit suit;



    Card(Rank value, Suit suit,boolean showcardface, int current_X,int current_Y, Bitmap image)
    {
        this.CardValue=value;
        this.suit=suit;
        this.showcardface=showcardface;
        this.current_X=current_X;
        this.current_Y=current_Y;
        this.Image= image;

    }

    public  int GetImageId(Context currentcontext)
    {
        int cardimageid;
        int imagenumber;
        String imagename;
        if(showcardface==false)
        {
            imagename="blueback";
        }
        else
        {
           imagename= suit.getName()+ CardValue.getImageName();

        }
        cardimageid= currentcontext.getResources().getIdentifier(imagename,"drawable",currentcontext.getPackageName());
        return cardimageid;
    }

    public static  int GetBlueBackCardImageId(Context currentcontext){
        return  currentcontext.getResources().getIdentifier("blueback","drawable",currentcontext.getPackageName());
    }

    public Bitmap getImage() {
        return Image;
    }

    public int getCurrent_X() {
        return current_X;
    }

    public int getCurrent_Y() {
        return current_Y;
    }

    public  boolean getShowCardFaced()
    {
        return  showcardface;
    }

    public void setShowcardface(boolean showcardface) {
        this.showcardface = showcardface;
    }

    public void setImage(Bitmap image)
    {
        this.Image=image;
    }

    public void setCurrent_X(int current_X) {
        this.current_X = current_X;
    }

    public void setCurrent_Y(int current_Y) {
        this.current_Y = current_Y;
    }

}
