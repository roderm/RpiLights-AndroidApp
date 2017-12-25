package odermatt.com.rpilight.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Handler;

import odermatt.com.rpilight.models.RpiLight;

/**
 * Created by roman on 24.12.17.
 */

public class Storage extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RpiLight.db";

    public Storage(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + SQLLightDefinition.LightDefinition.TABLE_NAME + " (" +
                SQLLightDefinition.LightDefinition._ID + " INTEGER PRIMARY KEY," +
                SQLLightDefinition.LightDefinition.HOST + " TEXT," +
                SQLLightDefinition.LightDefinition.IP + " TEXT," +
                SQLLightDefinition.LightDefinition.PORT + " INT)";
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("SQL", "Not upgrade defined");
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("SQL", "Not downgrade defined");
    }
    public long Put(RpiLight d){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLLightDefinition.LightDefinition.HOST, d.Hostname);
        values.put(SQLLightDefinition.LightDefinition.IP, d.IP.toString());
        values.put(SQLLightDefinition.LightDefinition.PORT, d.Port);

        return db.insert(SQLLightDefinition.LightDefinition.TABLE_NAME, null, values);
    }

    public ArrayList<RpiLight> Get(){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                SQLLightDefinition.LightDefinition._ID,
                SQLLightDefinition.LightDefinition.HOST,
                SQLLightDefinition.LightDefinition.IP,
                SQLLightDefinition.LightDefinition.PORT,
        };

        Cursor cursor = db.query(
                SQLLightDefinition.LightDefinition.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList items = new ArrayList<RpiLight>();
        while(cursor.moveToNext()) {
            RpiLight l = new RpiLight();
            l.sql_id = cursor.getLong(cursor.getColumnIndexOrThrow(SQLLightDefinition.LightDefinition._ID));
            l.Hostname = cursor.getString(cursor.getColumnIndexOrThrow(SQLLightDefinition.LightDefinition.HOST));
            l.Port = cursor.getInt(cursor.getColumnIndexOrThrow(SQLLightDefinition.LightDefinition.PORT));
            l.stored = true;
            l.IP = StringToInetAdresse(cursor.getString(cursor.getColumnIndexOrThrow(SQLLightDefinition.LightDefinition.IP)));


            items.add(l);
        }
        cursor.close();
        return items;
    }

    public boolean Delete(RpiLight d){
        SQLiteDatabase db = getWritableDatabase();
        String selection = SQLLightDefinition.LightDefinition._ID + " = ?";
        String[] selectionArgs = { Long.toString(d.sql_id) };
        int rows = db.delete(SQLLightDefinition.LightDefinition.TABLE_NAME, selection, selectionArgs);
        return (rows >= 0);
    }



    public Inet4Address StringToInetAdresse(String addr){
        class RetInet4Address implements Callable<Inet4Address>{
            String mAddr;
            public RetInet4Address(String addr){
                mAddr = addr.replace("/", "");
            }
            @Override
            public Inet4Address call() {
                Inet4Address ret;
                try{
                    ret = (Inet4Address) InetAddress.getByName(mAddr);
                }catch (UnknownHostException e){
                    Log.e("UnknownHostException", e.getMessage());
                    ret = null;
                }
                return ret;
            }
        }
        Inet4Address result = null;

        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Inet4Address> retInet = service.submit(new RetInet4Address(addr));
        try {
            result = retInet.get();
        }catch (InterruptedException e){
            Log.e("InterruptedException", e.getMessage());
        }catch (ExecutionException e){
            Log.e("ExecutionException", e.getMessage());
        }
        service.shutdown();
        return result;
    }
}
