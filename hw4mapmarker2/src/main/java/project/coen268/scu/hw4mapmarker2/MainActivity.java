package project.coen268.scu.hw4mapmarker2;
//android key:  AIzaSyCyBKhczHgzEfL2UEGxvKMp3S7b6AyvUB0

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {
    private  final LatLng LOCATION_UNIV = new LatLng(37.349642, -121.938987);
    private  final LatLng LOCATION_BUILDING = new LatLng(37.348190, -121.937975);
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_BUILDING, 13));
        getSupportLoaderManager().initLoader(0, null, this);
        googleMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                drawMarker(point);
                ContentValues contentValues = new ContentValues();
                contentValues.put(LocationsDB.LATITUDE, point.latitude );
                contentValues.put(LocationsDB.LONGITUDE, point.longitude);
                contentValues.put(LocationsDB.ZOOMLEVEL, googleMap.getCameraPosition().zoom);
                LocationInsertTask insertTask = new LocationInsertTask();
                insertTask.execute(contentValues);
                Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();
            }
        });

        googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                googleMap.clear();
                LocationDeleteTask deleteTask = new LocationDeleteTask();
                deleteTask.execute();
                Toast.makeText(getBaseContext(), "All markers are removed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void drawMarker(LatLng point){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        googleMap.addMarker(markerOptions);
    }

    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void>{
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }

    private class LocationDeleteTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            getContentResolver().delete(LocationsContentProvider.CONTENT_URI, null, null);
            return null;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri uri = LocationsContentProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        int locationCount = 0;
        double lat=0;
        double lng=0;
        float zoom=0;

        locationCount = arg1.getCount();
        arg1.moveToFirst();

        for(int i=0;i<locationCount;i++){
            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.LATITUDE));
            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.LONGITUDE));
            zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.ZOOMLEVEL));
            LatLng location = new LatLng(lat, lng);
            drawMarker(location);
            arg1.moveToNext();
        }

        if(locationCount>0){
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        return;
    }

    public void onClick_City(View v) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 10);
        googleMap.animateCamera(update);
    }

    public void onClick_University(View v) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 13);
        googleMap.animateCamera(update);
    }

    public void onClick_Building(View v) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_BUILDING, 17);
        googleMap.animateCamera(update);
    }
}
