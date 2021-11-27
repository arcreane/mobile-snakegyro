package com.example.snakegiro;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    protected SensorManager sensorManager;
    protected Sensor gyroscopeSensor;
    protected SensorEventListener gyroscopeEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyroscopeSensor == null){
            Toast.makeText(this, "Pas de Gyro", Toast.LENGTH_SHORT).show();
            finish();
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }
}