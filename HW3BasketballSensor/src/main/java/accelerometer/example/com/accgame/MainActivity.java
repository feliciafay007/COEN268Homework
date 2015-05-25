package accelerometer.example.com.accgame;

import android.app.Activity;
import android.os.PowerManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
      private static final String TAG = "com.example.accelerometer.MainActivity";
    private PowerManager.WakeLock mWakLock;
    private SimulationView mSimulationView;
    /**
     * huge KENG KENG KENG
     * member varialbe shoudl be put there ,not as a local Variable in the onCreate Method.
     */
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        /**
         * developer.android.com: SCREEN_BRIGHT_WAKE_LOCK is deprecated;
         * FLAG_KEEP_SCREEN_ON is more preferable, but not supoorted in minimum SDK( API level = 8).
         */
        mWakLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TAG);
        mSimulationView = new SimulationView(this.getApplicationContext());
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mLinearLayout.addView(mSimulationView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Acquire WakeLock
        mWakLock.acquire();
        mSimulationView.startSimulation();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Release WakeLock
        mSimulationView.stopSimulation();
        mWakLock.release();
    }
}
