package com.clem.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.clem.spider.MyFav;

public class MyFavDao {
	private MyFavDBHelper dbHelper;

	public MyFavDao(Context context) {
		dbHelper = new MyFavDBHelper(context);
	}

	public void add(MyFav myFav) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			String sql = "INSERT INTO tb_myFav(title, link, author) values (?,?,?);";
			db.execSQL(sql,
					new Object[] { myFav.getFavTitle(), myFav.getFavLink(), myFav.getFavAuthor() });
		} catch (Exception e) {
			return;
		} finally {
			db.close();
		}
	}

	public void delete(MyFav myFav) {
		String sql = "delete from tb_myFav where title = ?";
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL(sql, new Object[] { myFav.getFavTitle() });
		db.close();
	}

	// public List<MyFav> getAll(){
	// String sql = "select * from tb_myFav";
	// SQLiteDatabase db = dbHelper.getWritableDatabase();
	// db.execSQL(sql);
	//
	//
	// db.close();
	// }

	public List<MyFav> listFav(int currentPage) {
		List<MyFav> myFavs = new ArrayList<MyFav>();

		try {
			// int offset = 10 * (currentPage - 1);

			// 0 -9 , 10 - 19 ,
			String sql = "select title, link, author from tb_myFav";
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery(sql, null);

//			c.moveToFirst();

			MyFav myFav = null;

			while (c.moveToNext()) {
				myFav = new MyFav();

				String title = c.getString(0);
				String link = c.getString(1);
				String author = c.getString(2);
				
				myFav.setFavTitle(title);
				myFav.setFavLink(link);
				myFav.setFavAuthor(author);
				
				myFavs.add(myFav);
			}

			c.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return myFavs;

	}

}
