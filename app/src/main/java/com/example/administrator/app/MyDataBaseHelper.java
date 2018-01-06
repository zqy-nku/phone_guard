package com.example.administrator.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017-11-24.
 * A tool class to use the database
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

		final  String CREAT_TABLE_SQL="create table phone(number)";

		final String CREATE_TABLE_RECORD= "create table record(number,time)";

		public MyDataBaseHelper(Context context, String name, int version) {
				super(context, name, null, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
				db.execSQL(CREATE_TABLE_RECORD);
				db.execSQL(CREAT_TABLE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				Log.v(getClass().getName(),"Update Database Version:"+newVersion);
		}
}
