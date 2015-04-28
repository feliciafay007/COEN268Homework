package android.scu.edu.customlistview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


// NOTE
// 1. this activity is almost the same as MainActity.
// 2. both have to initiate a listview and to write cutomer adpater to populate the listview.
public class ConcertActivity extends ActionBarActivity {
    private List<ConcertInfo> concertList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("Wenyi", "TEST_ConcertActivity");
        setContentView(R.layout.activity_concert);
        ListView concertInfoListView = (ListView) findViewById(R.id.listView2);
        String[] artistDetailArray = getResources().getStringArray(R.array.concert_details);
        populateList(artistDetailArray, concertList);
        concertInfoListView.setAdapter(new ConcertInfoAdapter(this, R.layout.concert_row, concertList));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_concert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information2) {
            return true;
        }else if (id == R.id.action_favourite2) {
            Intent showFavourite = new Intent(ConcertActivity.this, ShowFavourite.class);
            startActivity(showFavourite);
            return true;
        } else if (id == R.id.action_uninstall2) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateList(String[] concertInfoInput, List<ConcertInfo> concertList){
           for (int i = 0; i < concertInfoInput.length; ++i) {
               try {
                   ConcertInfo concertInfo = new ConcertInfo(concertInfoInput[i]);
                   concertList.add(concertInfo);
               } catch(Exception e) {
                   Log.e("Wenyi", "Concert Activity, populate().", e);
               }
           }
    }
}
