package accelerometer.example.com.accgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by feliciafay on 5/10/15.
 */
public class SimulationView extends View implements SensorEventListener{
//public class SimulationView extends View {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Display mDisplay;
    private long mSensorTimeStamp; // is this a long type?
    private Bitmap mField;
    private Bitmap mBasket;
    private Bitmap mBitmap;
    private int screenWidth;
    private int screenHeight;
    private static final int BALL_SIZE = 64;
    private static final int BASKET_SIZE = 80;
    private float mXOrigin;
    private float mYOrigin;
    private float mHorizontalBound;
    private float mVerticalBound;
    private Particle mBall;
    private float mSensorX;
    private float mSensorY;
    private float mSensorZ;

    public SimulationView(Context context) {
        this(context, null);
    }

    public SimulationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        screenWidth = mDisplay.getWidth();
        screenHeight = mDisplay.getHeight();
        mHorizontalBound = screenWidth / 2;
        mVerticalBound = screenHeight / 2;
        mXOrigin = screenWidth / 2;
        mYOrigin = screenHeight / 2;
        //mSensorTimeStamp = new Date().getTime();
        mSensorTimeStamp = System.currentTimeMillis()  * 1000000;
        try {
            Bitmap ball = BitmapFactory.decodeResource(this.getResources(), R.drawable.ball);
            mBitmap = Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE, true);
            Bitmap basket = BitmapFactory.decodeResource(getResources(), R.drawable.basket);
            mBasket = Bitmap.createScaledBitmap(basket, BASKET_SIZE, BASKET_SIZE, true);

            /**
             * RGB_565: the way each pixel is stored :
             * red is stored with 5 bits of precision (32 possible values),
             * green is stored with 6 bits of precision (64 possible values),
             * blue is stored with 5 bits of precision.
             */
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inDither = true;
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap field = BitmapFactory.decodeResource(getResources(), R.drawable.field, opts);
            mField = Bitmap.createScaledBitmap(field, screenWidth, screenHeight, true);
            mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mBall = new Particle();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * onDraw() is called, when:
     * 1. The view is initially drawn
     * 2. Whenever invalidate() is called on the view
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mField != null) {
            canvas.drawBitmap(mField, 0, 0, null);
        }

        if (mBasket != null) {
            canvas.drawBitmap(mBasket,
                    mXOrigin - BASKET_SIZE / 2,
                    mYOrigin - BASKET_SIZE / 2,
                    null);
        }

        if (mBall != null) {
            mBall.updatePosition(mSensorX,
                    mSensorY,
                    mSensorZ,
                    mSensorTimeStamp);
            mBall.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound);
            canvas.drawBitmap(mBitmap,
                    (mXOrigin - BALL_SIZE / 2) + mBall.mPosX,
                    (mYOrigin - BALL_SIZE / 2) - mBall.mPosY,
                    null);
        }
        if (this.isGoalMeet(mBall.mPosX, mBall.mPosY)) {
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setTextSize(150);
            canvas.drawText("Nice!", 0, 100, paint);
        }
        invalidate();
    }


    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        mXOrigin = width * 0.5f;
        mYOrigin = height * 0.5f;
        mHorizontalBound = (width - BALL_SIZE) * 0.5f;
        mVerticalBound = (height - BALL_SIZE) * 0.5f;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }
        mSensorTimeStamp = event.timestamp;
        //mSensorTimeStamp = System.nanoTime();
        mSensorX = event.values[0];
        mSensorY = event.values[1];
        mSensorZ = event.values[2];
        System.out.println("\t\t" + mSensorX + "\t\t" + mSensorY + "\t\t" + mSensorZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startSimulation() {
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                mSensorManager.SENSOR_DELAY_UI); // difference vs others?
    }

    public void stopSimulation() {
        mSensorManager.unregisterListener(this);
    }
    public boolean isGoalMeet(float x, float y) {
        if((x < (50 + BASKET_SIZE) / 2) &&
                (x > -(50 + BASKET_SIZE) / 2) &&
                (y < (50 + BASKET_SIZE) / 2) &&
                (y > - (50 + BASKET_SIZE) / 2))
            return true;
        return false;
    }
}
