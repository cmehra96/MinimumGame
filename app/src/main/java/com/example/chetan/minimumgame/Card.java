package com.example.chetan.minimumgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import icepick.Bundler;

public class Card implements Comparable {
    private int current_X;
    private int current_Y;
    private boolean showcardface;
    private Bitmap Image;
    private Rank CardValue;
    private Suit suit;

    Card(Rank cardValue, Suit suit) {
        CardValue = cardValue;
        this.suit = suit;
        this.showcardface = false;
        this.current_X = 0;
        this.current_Y = 0;
        this.Image = null;

    }

    Card(Rank value, Suit suit, boolean showcardface, int current_X, int current_Y, Bitmap image) {
        this.CardValue = value;
        this.suit = suit;
        this.showcardface = showcardface;
        this.current_X = current_X;
        this.current_Y = current_Y;
        this.Image = image;

    }

    Card(Rank value, Suit suit, boolean showcardface, int current_X, int current_Y) {
        this.CardValue = value;
        this.suit = suit;
        this.showcardface = showcardface;
        this.current_X = current_X;
        this.current_Y = current_Y;

    }

    public Card() {

    }


    public Bitmap getImage() {

        return Image;
    }

    public void setImage(Bitmap image) {
        this.Image = image;
    }

    public Bitmap getImage(Context currentcontext, int card_width, int card_height) {

        new BitmapLoader(currentcontext, card_width, card_height).execute();
        return this.Image;
    }

    public int getCurrent_X() {
        return current_X;
    }

    public void setCurrent_X(int current_X) {
        this.current_X = current_X;
    }

    public int getCurrent_Y() {
        return current_Y;
    }

    public void setCurrent_Y(int current_Y) {
        this.current_Y = current_Y;
    }

    public void setShowcardface(boolean showcardface) {
        this.showcardface = showcardface;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getCardRank() {
        return CardValue;
    }

    public int cardRank() {
        return this.CardValue.getRank();
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }
        Card c = (Card) obj;
        return (this.suit == c.suit && this.CardValue == c.CardValue);

    }

    @Override
    public int hashCode() {
        return suit.getValue() * 13 + CardValue.getRank();
    }


    @Override
    public int compareTo(Object o) {
        return ((Integer) hashCode()).compareTo(o.hashCode());
    }

    private class BitmapLoader extends AsyncTask<Object, Void, Bitmap> {
        Context context;
        int card_width;
        int card_height;

        public BitmapLoader(Context context, int card_width, int card_height) {
            this.context = context;
            this.card_width = card_width;
            this.card_height = card_height;
        }

        @Override
        protected Bitmap doInBackground(Object... objects) {
            try {
                return getBitmap();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private Bitmap getBitmap() throws Exception {

            Image = DecodeSampleBitmapFromResource(context.getResources(), GetImageId(context), card_width, card_height);
            return Image;
        }


        private Bitmap DecodeSampleBitmapFromResource(Resources res, int resId,
                                                      int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeResource(res, resId, options);
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {


                int heightratio = (int) Math.round((double) height / reqHeight);
                int widthratio = (int) Math.round((double) width / reqWidth);
                inSampleSize = heightratio < widthratio ? widthratio : heightratio;
            }

            return inSampleSize;
        }

        public int GetImageId(Context currentcontext) {
            int cardimageid;
            int imagenumber;
            String imagename;
            if (showcardface == false) {
                imagename = "blueback";
            } else {
                imagename = suit.getName() + CardValue.getImageName();

            }
            cardimageid = currentcontext.getResources().getIdentifier(imagename, "drawable", currentcontext.getPackageName());
            return cardimageid;
        }
    }

}


/*
public class CardBundler implements Bundler<Card>
{

    @Override
    public void put(String s, Card card, Bundle bundle) {
        bundle.putParcelable(s,Parcels);
    }

    @Override
    public Card get(String s, Bundle bundle) {
        return null;
    }
}
*/
/*
public class Card implements Comparable, Parcelable {
    private int current_X;
    private int current_Y;
    private boolean showcardface;
    private Bitmap Image;
    private Rank CardValue;
    private Suit suit;

    Card(Rank cardValue, Suit suit) {
        CardValue = cardValue;
        this.suit = suit;
        this.showcardface = false;
        this.current_X = 0;
        this.current_Y = 0;
        this.Image = null;

    }

    Card(Rank value, Suit suit, boolean showcardface, int current_X, int current_Y, Bitmap image) {
        this.CardValue = value;
        this.suit = suit;
        this.showcardface = showcardface;
        this.current_X = current_X;
        this.current_Y = current_Y;
        this.Image = image;

    }

    Card(Rank value, Suit suit, boolean showcardface, int current_X, int current_Y) {
        this.CardValue = value;
        this.suit = suit;
        this.showcardface = showcardface;
        this.current_X = current_X;
        this.current_Y = current_Y;

    }

    public Card() {

    }


    protected Card(Parcel in) {
        current_X = in.readInt();
        current_Y = in.readInt();
        showcardface = in.readByte() != 0;
        Image = in.readParcelable(Bitmap.class.getClassLoader());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(current_X);
        dest.writeInt(current_Y);
        dest.writeByte((byte) (showcardface ? 1 : 0));
        dest.writeParcelable(Image, flags);
    }


    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public Bitmap getImage() {

        return Image;
    }

    public void setImage(Bitmap image) {
        this.Image = image;
    }

    public Bitmap getImage(Context currentcontext, int card_width, int card_height) {

        new BitmapLoader(currentcontext, card_width, card_height).execute();
        return this.Image;
    }

    public int getCurrent_X() {
        return current_X;
    }

    public void setCurrent_X(int current_X) {
        this.current_X = current_X;
    }

    public int getCurrent_Y() {
        return current_Y;
    }

    public void setCurrent_Y(int current_Y) {
        this.current_Y = current_Y;
    }

    public void setShowcardface(boolean showcardface) {
        this.showcardface = showcardface;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getCardRank() {
        return CardValue;
    }

    public int cardRank() {
        return this.CardValue.getRank();
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }
        Card c = (Card) obj;
        return (this.suit == c.suit && this.CardValue == c.CardValue);

    }

    @Override
    public int hashCode() {
        return suit.getValue() * 13 + CardValue.getRank();
    }


    @Override
    public int compareTo(Object o) {
        return ((Integer) hashCode()).compareTo(o.hashCode());
    }

    private class BitmapLoader extends AsyncTask<Object, Void, Bitmap> {
        Context context;
        int card_width;
        int card_height;

        public BitmapLoader(Context context, int card_width, int card_height) {
            this.context = context;
            this.card_width = card_width;
            this.card_height = card_height;
        }

        @Override
        protected Bitmap doInBackground(Object... objects) {
            try {

                return getBitmap();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private Bitmap getBitmap() throws Exception {

            Image = DecodeSampleBitmapFromResource(context.getResources(), GetImageId(context), card_width, card_height);
            return Image;
        }


        private Bitmap DecodeSampleBitmapFromResource(Resources res, int resId,
                                                      int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeResource(res, resId, options);
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {


                int heightratio = (int) Math.round((double) height / reqHeight);
                int widthratio = (int) Math.round((double) width / reqWidth);
                inSampleSize = heightratio < widthratio ? widthratio : heightratio;
            }

            return inSampleSize;
        }

        public int GetImageId(Context currentcontext) {
            int cardimageid;
            int imagenumber;
            String imagename;
            if (showcardface == false) {
                imagename = "blueback";
            } else {
                imagename = suit.getName() + CardValue.getImageName();

            }
            cardimageid = currentcontext.getResources().getIdentifier(imagename, "drawable", currentcontext.getPackageName());
            return cardimageid;
        }
    }

}

*/
