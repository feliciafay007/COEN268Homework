package project.coen268.scu.hw4mapmarker;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class LocationsContentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "coen268.homework4.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/positions";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String _ID = "_id";
    static final String LATITUDE = "latitude";
    static final String LONGITUTE = "longitude";
    static final String ZOOMLEVEL = "zoomlevel";
    static final int POSITIONS = 1;
    static final int POSITION_ID = 2;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "positions", POSITIONS);
        uriMatcher.addURI(PROVIDER_NAME, "positions/#", POSITION_ID);
    }

    /**
     * Database specific constant declarations */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "MapPositions";
    static final String TABLE_NAME = "positions";
    static final int DATABASE_VERSION = 1;

    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + TABLE_NAME  +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " latitude TEXT NOT NULL, " +
                    " longitude TEXT NOT NULL, " +
                    " zoomlevel TEXT NOT NULL);";
    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     **/
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        //@Felicia 为什么static class还可以被new出来?
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        /**
         * Create a write able database which will trigger its * creation if it doesn't already exist.
         **/
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        /**
         * Add a new student record
         */
        System.out.println("FF" +  "insert success1!");
        Log.i("FF", "insert success1!");

        long rowID = db.insert( TABLE_NAME , "", values);

        System.out.println("FF" + "insert success2!");
        Log.i("FF", "insert success2!");
         /* *
         *If record is added successfully
         */
        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            System.out.println("FF" + "insert success3!");
            Log.i("FF", "insert success3!");
            return _uri;
        }
        System.out.println("FF" + "insert fail1!");
        Log.i("FF", "insert fail1!");
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case POSITIONS:
                //qb.setProjectionMap(POSITION_PROJECTION_MAP);
                break;
            case POSITION_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri); }
        if (sortOrder == null || sortOrder == ""){
            //todo: required to sort on different keys, lantitude, longitute, zoomleve.
            sortOrder = LATITUDE;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // todo: change this function
        int count = 0;
        switch (uriMatcher.match(uri)){
            case POSITIONS:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case POSITION_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( TABLE_NAME, _ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // todo: change this function
        int count = 0;
        switch (uriMatcher.match(uri)){
            case POSITIONS:
                count = db.update(TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case POSITION_ID:
                count = db.update(TABLE_NAME, values, _ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all student records
             */
            case POSITIONS:
                //todo: 这里不知道用什么来改？
                return "vnd.android.cursor.dir/vnd.example.students"; /**
             * Get a particular student
             */
            case POSITION_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri); }
    }
}
