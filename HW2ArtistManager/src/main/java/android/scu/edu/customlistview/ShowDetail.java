package android.scu.edu.customlistview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowDetail extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        //NOTE:
        // 1. give response to the Intent, initiated by the parent activity.
        // 2. convert serializable data back to Class Type Person.
        Intent intent = getIntent();
        Person thisPerson = (Person) intent.getExtras().getSerializable(MainActivity.ARTISTINDEX);
        if ( null != thisPerson) {
            TextView nameView =(TextView) findViewById(R.id.textView);
            nameView.setText(thisPerson.getName());
            TextView detailView = (TextView) findViewById(R.id.textView2);
            detailView.setText(thisPerson.getDetail());
            ImageView artistImageView = (ImageView) findViewById(R.id.imageView);
            artistImageView.setImageResource(thisPerson.getResIDlargeImage());
            detailView.setMovementMethod(new ScrollingMovementMethod());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information3) {
            Intent showConcertIntent = new Intent(ShowDetail.this, ConcertActivity.class);
            startActivity(showConcertIntent);
            return true;
        }else if (id == R.id.action_favourite3) {
            Intent showFavourite = new Intent(ShowDetail.this, ShowFavourite.class);
            startActivity(showFavourite);
            return true;
        } else if (id == R.id.action_uninstall3) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
