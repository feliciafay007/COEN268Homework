package project.coen268.scu.hw4mapmarker2;

/**
 * Created by feliciafay on 5/25/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationsDB extends SQLiteOpenHelper{
    private static String DBNAME = "markermapsql";
    private static int VERSION = 1;
    public static final String _ID = "_id";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lng";
    public static final String ZOOMLEVEL = "zom";
    private static final String DATABASE_TABLE = "marker_map_db";
    private SQLiteDatabase mDB;

    public LocationsDB(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
    }

    /** This is a callback method,
     * invoked when the method getReadableDatabase()
     * or getWritableDatabase() is called
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =     "create table " + DATABASE_TABLE + " ( " +
                _ID + " integer primary key autoincrement , " +
                LONGITUDE + " double , " +
                LATITUDE + " double , " +
                ZOOMLEVEL + " text " +
                " ) ";

        db.execSQL(sql);
    }

    public long insert(ContentValues contentValues){
        long rowID = mDB.insert(DATABASE_TABLE, null, contentValues);
        return rowID;
    }

    public int delete(){
        int cnt = mDB.delete(DATABASE_TABLE, null , null);
        return cnt;
    }

    public Cursor getAllLocations(){
        return mDB.query(DATABASE_TABLE, new String[] { _ID,  LATITUDE , LONGITUDE, ZOOMLEVEL } , null, null, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}