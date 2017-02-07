package temperatureconverter.android.vogella.com.accdemo;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;

    TextView xValueLabel;
    TextView yValueLabel;
    TextView zValueLabel;
    ImageView directionImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xValueLabel = (TextView)findViewById(R.id.xValue);
        yValueLabel = (TextView)findViewById(R.id.yValue);
        zValueLabel = (TextView)findViewById(R.id.zValue);
        directionImg = (ImageView)findViewById(R.id.imageView);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float aThreshold = 2;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // assign directions
            float x = event.values[0];
            float y = event.values[1];
            float z = (float) (event.values[2] - 9.8);
            xValueLabel.setText("" + x);
            yValueLabel.setText("" + y);
            zValueLabel.setText("" + z);

            if (x < aThreshold && y < aThreshold && z < aThreshold) {
                return;
            }
            if (x > 0){
                directionImg.setImageResource(R.drawable.arrow_left);
            }else{
                directionImg.setImageResource(R.drawable.arrow_right);
            }

            if (x > y && x > z) {
                xValueLabel.setBackgroundColor(Color.GREEN);
                yValueLabel.setBackgroundColor(Color.WHITE);
                zValueLabel.setBackgroundColor(Color.WHITE);
            } else if (y > x && y > z) {
                yValueLabel.setBackgroundColor(Color.GREEN);
                xValueLabel.setBackgroundColor(Color.WHITE);
                zValueLabel.setBackgroundColor(Color.WHITE);
            } else {
                zValueLabel.setBackgroundColor(Color.GREEN);
                xValueLabel.setBackgroundColor(Color.WHITE);
                yValueLabel.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do something here if sensor accuracy changes.
    }

}

