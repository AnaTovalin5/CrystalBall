package android.tovalina.crystalball;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;


public class CrystalBall extends Activity {

    private TextView answerText;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float acceleration;
    private float currentAcceleration;
    private float previousAcceleration;

    AnimationDrawable ballRoll;

    long previousTime;
    long currentTime;
    long delay;
    long elapsed;

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            previousAcceleration = currentAcceleration;  //sets acceleration
            currentAcceleration = FloatMath.sqrt(x * x + y * y + z * z);
            float delta = currentAcceleration - previousAcceleration;
            acceleration = acceleration * 0.9f + delta;

            previousTime = currentTime;  //sets delay time to correct amount of time
            currentTime = System.currentTimeMillis();
            elapsed = currentTime - previousTime;
            delay += elapsed;

            if(acceleration > 15 && delay >= 4000) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.crystal_ball); //plays sound after shaken
                mediaPlayer.start();

                answerText = (TextView) findViewById(R.id.answerText); //displays text
                answerText.setText(Predictions.get().getPrediction());

                answerText.startAnimation(AnimationUtils.loadAnimation(CrystalBall.this, android.R.anim.slide_in_left));//adds animation to text

                ImageView background = (ImageView) findViewById(R.id.animation); //sets up animation code
                background.setBackgroundResource(R.drawable.ball_images);
                AnimationDrawable ballRoll = (AnimationDrawable) background.getBackground();

                if(ballRoll.isRunning()) { //checks if animation is already running
                    ballRoll.stop();
                    ballRoll.start();
                } else {
                    ballRoll.start();
                }

                delay = 0;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crystal_ball);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        acceleration = 0.0f;  //measures acceleration
        currentAcceleration = sensorManager.GRAVITY_EARTH;
        previousAcceleration = sensorManager.GRAVITY_EARTH;

        ImageView background = (ImageView) findViewById(R.id.animation); //part of animation for dragon
        background.setBackgroundResource(R.drawable.fly8);

        previousTime = System.currentTimeMillis(); //sets times
        currentTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }
}