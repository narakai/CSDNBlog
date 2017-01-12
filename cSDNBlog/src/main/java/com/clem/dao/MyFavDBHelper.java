package com.clem.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyFavDBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "my_fav";

	public MyFavDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	// unique
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			String sql = "create table tb_myFav(_id integer primary key autoincrement, "
					+ " title text not null unique, link text, author text);";
			db.execSQL(sql);
		} catch (Exception e) {
			return;
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
