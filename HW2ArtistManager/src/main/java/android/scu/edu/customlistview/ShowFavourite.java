package android.scu.edu.customlistview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Iterator;


public class ShowFavourite extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favourite);
        TextView favourites = (TextView) findViewById(R.id.textView5);
        StringBuilder show = new StringBuilder("A List of Favourite Artist:\n\n\n");
        Iterator<String> iterator = Person.favLinkedHashSet.iterator();
        while (iterator.hasNext()) {
            show = show.append(iterator.next());
            show = show.append("\n\n");
        }
        favourites.setText(show);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_favourite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information4) {
            Intent showConcertIntent = new Intent(ShowFavourite.this, ConcertActivity.class);
            startActivity(showConcertIntent );
            return true;
        }else if (id == R.id.action_favourite4) {
            return true;
        } else if (id == R.id.action_uninstall4) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + this.getPackageName()));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
