package com.myapp.lifesaver.android;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Message;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.badlogic.gdx.math.Vector2;

import java.io.File;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Lifesaver extends AppCompatActivity {
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			GameArea.HenryMsg henryMsg = (GameArea.HenryMsg)msg.obj;
			renderHenry(henryMsg.pos);
		}
	};

	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * Some older devices needs a small delay between UI widget updates
	 * and a change of the status and navigation bar.
	 */
	private static final int UI_ANIMATION_DELAY = 300;

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */

	//variables
    Canvas henrycanvas;
    Resources res;
    Bitmap henry;
    Paint henrypaint;
    Bitmap tempBitmap;
    Canvas tempCanvas;
    ImageView henryView;
    GameArea gameArea;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        gameArea = new GameArea(this, handler);
        gameArea.start();
		setContentView(R.layout.activity_lifesaver);
        henryView = findViewById(R.id.HenryArea);
        hide();


        Bitmap b = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        b.eraseColor(Color.GRAY);
        henryView.setImageBitmap(b);


        //renderTime();
        renderHenry(new Vector2(0,0));
        //renderSpike();

        //render henry does not work yet
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.

	}

	private void hide() {
		// Hide UI first
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.hide();
		}/*
		mControlsView.setVisibility(View.GONE);
		mVisible = false;

		// Schedule a runnable to remove the status and navigation bar after a delay
		mHideHandler.removeCallbacks(mShowPart2Runnable);
		mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);*/
	}


	public void renderHenry(Vector2 pos){
        setContentView(R.layout.activity_lifesaver);
        res = getResources();
        henry = BitmapFactory.decodeResource(res,R.drawable.henrybmp);
        //henrypaint = new Paint();

        tempBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        tempBitmap.eraseColor(Color.WHITE);
        tempCanvas = new Canvas(tempBitmap);

        Matrix rotationMatrix = new Matrix();
        int henryBaseX=250;
        int henryBaseY=250;
        int henryX = (int) (henryBaseX - pos.x * 100 - henry.getWidth()/2);
        int henryY = (int) (henryBaseY - pos.y * 100 - henry.getHeight()/2);
        //rotationMatrix.setRotate(gameArea.henry.getPosition().x);
        tempCanvas.drawBitmap(henry, henryX, henryY, null);
        //henrycanvas = new Canvas(henry);
        //henrycanvas.drawBitmap(henry, henrycanvas.getWidth()/2,henrycanvas.getHeight()/2, null);
		//henryView.draw(tempCanvas);


        ImageView henV = findViewById(R.id.HenryArea);
		//henryView.setImageDrawable(res.getDrawable(R.drawable.henrybmp));
        henV.setImageBitmap(tempBitmap);
		//henryView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
    }

	//solved in XML
	/*
	public void renderSpike(){
		setContentView(R.layout.activity_lifesaver);

		Bitmap b = Bitmap.createBitmap(900, 100, Bitmap.Config.ARGB_8888);
		ImageView v = (ImageView)findViewById(R.id.SpikeArea);
		v.setImageBitmap(b);

	}*/



	public void renderTime(){
		setContentView(R.layout.activity_lifesaver);
		Bitmap b;
		b = Bitmap.createBitmap(500, 50, Bitmap.Config.ARGB_8888);
        ImageView v = (ImageView)findViewById(R.id.timeArea);
        b.eraseColor(Color.GRAY);

		v.setImageBitmap(b);

	}


}
