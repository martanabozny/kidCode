package com.example.kidcode2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.*;
import java.sql.SQLException;

/**
 * Created by marta on 25.04.14.
 */
public final class DataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "strips.db";
    private static String DB_PATH = "";
    private final Context mContext;
    private SQLiteDatabase mDataBase;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                copyDataBase();

            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES2);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "strips";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_JSON = "json";
        public static final String COLUMN_NAME_ID = "id";
    }

    public static abstract class FeedEntry2 implements BaseColumns {
        public static final String TABLE_NAME = "files";
        public static final String COLUMN_FILE_NAME = "name";
        public static final String COLUMN_NAME_JSON = "json";
        public static final String COLUMN_NAME_ID = "id";
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                                                     FeedEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                                                     FeedEntry.COLUMN_NAME_JSON + " TEXT," +
                                                     FeedEntry.COLUMN_NAME_NAME + " TEXT" +
                                                     ")";
    private static final String SQL_CREATE_ENTRIES2 = "CREATE TABLE " + FeedEntry2.TABLE_NAME + " (" +
                                                    FeedEntry2.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                                                    FeedEntry2.COLUMN_NAME_JSON + " TEXT," +
                                                    FeedEntry2.COLUMN_FILE_NAME + " TEXT" +
                                                    ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + FeedEntry2.TABLE_NAME;

    public void createDataBase() {


    }

    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }


}
