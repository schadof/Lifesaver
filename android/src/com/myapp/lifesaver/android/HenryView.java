package com.myapp.lifesaver.android;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * Created by Sumpf on 19-Nov-17.
 */

public class HenryView extends View {
    public HenryView(Context context) {
        super(context);
    }


    BitmapFactory bitMapFac = null;
    public void setBitmapFactory(BitmapFactory bitMapFac)
    {
        this.bitMapFac = bitMapFac;
    }

}
