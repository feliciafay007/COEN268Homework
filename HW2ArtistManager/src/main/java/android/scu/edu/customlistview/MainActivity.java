package android.scu.edu.customlistview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    public static final String ARTISTINDEX = "index";
    private final List<Person> persons = new ArrayList();
    //private int mPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Routine:
        // step1 (XXXView) findViewByID(R.id.XXX;
        // step2 setAdapter or put data in view
        // step3 setOnItemClickListener()
        ListView listView = (ListView) findViewById(R.id.listView);
        populateList(persons);
        //Note:
        // setAdapter(new XXXAdapter(para1, para2, para3)) : para3 shoudl be a list, to be
        // regarded as a list for listview.
        listView.setAdapter(new CustomAdapter(this, R.layout.custom_row, persons));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // NOTE:
                // 1. If you want to pass the instance of person in the intent.
                // 2. then you should use a Class, that implement the Serializable interface.
                Intent showDetailIntent = new Intent(MainActivity.this, ShowDetail.class);
                showDetailIntent.putExtra(ARTISTINDEX, persons.get(position));
                startActivity(showDetailIntent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            // NOTE:
            // 1. "final int position " is used instead of int "position"
            // because inner class is haveing access to the variable.
            // 2. the return type is boolean, quite different from setOnItemClickListener
            // 3. How to make AlertDialog
            public boolean onItemLongClick(AdapterView<?> parent, View view,  final int position, long id) {
                AlertDialog.Builder addToFavouriteBuilder = new AlertDialog.Builder(MainActivity.this);
                addToFavouriteBuilder.setMessage("Add to the favourite list?").setTitle("Note");
                addToFavouriteBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = persons.get(position ).getName();
                        Person.favLinkedHashSet.add(name);
                    }
                });
                addToFavouriteBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog addToFavouriteAlert = addToFavouriteBuilder.create();
                addToFavouriteAlert.show();
                return true;
            }
        });
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

        //noinspection SimplifiableIfStatement
        int id = item.getItemId();
       if (id == R.id.action_information) {
           // NOTE:
           // 1. routine to start an intent.
           Intent showConcertIntent = new Intent(MainActivity.this, ConcertActivity.class);
           startActivity(showConcertIntent );
           return true;
       }else if (id == R.id.action_favourite) {
           Intent showFavourite = new Intent(MainActivity.this, ShowFavourite.class);
           startActivity(showFavourite);
           return true;
       } else if (id == R.id.action_uninstall) {
           //NOTE:
           // this current in use toast is equivalent to the 3 lines below:
           //           Context context = getApplicationContext();
           //           Toast toast = Toast.makeText(context, "uninstall clicked", Toast.LENGTH_SHORT);
           //           toast.show();
           Toast toast = Toast.makeText(this, "uninstall", Toast.LENGTH_SHORT);
           toast.show();
           // NOTE:
           // intent to give a phone call
           Intent intent = new Intent(Intent.ACTION_DELETE);
           intent.setData(Uri.parse("package:" + this.getPackageName()));
           startActivity(intent);
           return true;
       }
       return super.onOptionsItemSelected(item);
    }

    private void populateList(List<Person> persons) {
        // NOTE
        // 1 put variables in strings.xml items in String [].
        String[] artistNameList = getResources().getStringArray(R.array.artist_names);
        String[] artistDetail = getResources().getStringArray(R.array.artist_details);
        for (int i = 0; i < artistNameList.length; ++i) {
            try {
                Class res = R.drawable.class;
                Field field1 = res.getField(artistNameList[i].toLowerCase());
                int drawableIdThumbnail = field1.getInt(null);
                Field field2 = res.getField(artistNameList[i].toLowerCase()+"_large");
                int drawableIdLargeImage= field2.getInt(null);
                persons.add(new Person(artistNameList[i], drawableIdThumbnail, drawableIdLargeImage, artistDetail[i]));
            }
            catch (Exception e) {
                Log.e("Wenyi", "Main Activity, populate().", e);
            }
        }
    }
}
