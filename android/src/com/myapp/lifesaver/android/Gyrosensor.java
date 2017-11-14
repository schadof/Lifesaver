package com.myapp.lifesaver.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import com.badlogic.gdx.math.Vector2;


public abstract class Gyrosensor extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    // Create a constant to convert nanoseconds to seconds.
    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp;

    public Gyrosensor(){
        // ask for sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // get the default gyro
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void onSensorChanged(SensorEvent event) {
        Vector2 gyro = new Vector2(event.values[0], event.values[1]);
    }
}
