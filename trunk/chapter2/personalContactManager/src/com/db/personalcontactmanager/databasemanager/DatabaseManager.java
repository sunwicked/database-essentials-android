package com.db.personalcontactmanager.databasemanager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.db.personalcontactmanager.model.ContactModel;

public class DatabaseManager {

	private SQLiteDatabase db; // a reference to the database manager class.
	private final String DB_NAME = "sms"; // the name of our database
	private final int DB_VERSION = 1; // the version of the database

	private final String TABLE_NAME = "contact_table";// table name

	// the names for our database columns
	private final String TABLE_ROW_ID = "_id";
	private final String TABLE_ROW_NAME = "contact_name";
	private final String TABLE_ROW_PHONENUM = "contact_number";
	private final String TABLE_ROW_EMAIL = "contact_email";
	private final String TABLE_ROW_PHOTOID = "photo_id";
	private Context context;

	public DatabaseManager(Context context) {
		this.context = context;

		// create or open the database
		CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
		this.db = helper.getWritableDatabase();
	}

	// the beginnings our SQLiteOpenHelper class
	private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
		public CustomSQLiteOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// the SQLite query string that will create our column database
			// table.
			String newTableQueryString = "create table " + TABLE_NAME + " ("
					+ TABLE_ROW_ID
					+ " integer primary key autoincrement not null,"
					+ TABLE_ROW_NAME + " text not null," + TABLE_ROW_PHONENUM
					+ " text not null," + TABLE_ROW_EMAIL + " text not null,"
					+ TABLE_ROW_PHOTOID + " text not null" + ");";

			// execute the query string to the database.
			db.execSQL(newTableQueryString);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			// LATER, WE WOULD SPECIFIY HOW TO UPGRADE THE DATABASE
			// FROM OLDER VERSIONS.

		}

	}

	public void addRow(ContactModel contactObj) {
		ContentValues values = prepareData(contactObj);
		// ask the database object to insert the new data
		try {
			db.insert(TABLE_NAME, null, values);
		} catch (Exception e) {
			Log.e("DB ERROR", e.toString()); // prints the error message to
												// the
												// log
			e.printStackTrace(); // prints the stack trace to the log
		}
	}

	private ContentValues prepareData(ContactModel contactObj) {
		ContentValues values = new ContentValues();
		values.put(TABLE_ROW_NAME, contactObj.getName());
		values.put(TABLE_ROW_PHONENUM, contactObj.getContactNo());
		values.put(TABLE_ROW_EMAIL, contactObj.getEmail());
		values.put(TABLE_ROW_PHOTOID, contactObj.getPhoto());
		return values;
	}

	public ContactModel getRowAsObject(int rowID) {
		ContactModel rowContactObj = new ContactModel();
		Cursor cursor;

		try {

			cursor = db.query(TABLE_NAME, new String[] { TABLE_ROW_ID,
					TABLE_ROW_NAME, TABLE_ROW_PHONENUM, TABLE_ROW_EMAIL,
					TABLE_ROW_PHOTOID }, TABLE_ROW_ID + "=" + rowID, null,
					null, null, null, null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				do {
					prepareSendObject(rowContactObj, cursor);
				} while (cursor.moveToNext()); // try to move the cursor's
												// pointer forward one position.
			}
		} catch (SQLException e) {
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}

		return rowContactObj;
	}

	public ArrayList<ContactModel> getAllData() {
		ArrayList<ContactModel> allRowsObj = new ArrayList<ContactModel>();
		Cursor cursor;
		ContactModel rowContactObj;
		try {

			cursor = db.query(TABLE_NAME, new String[] { TABLE_ROW_ID,
					TABLE_ROW_NAME, TABLE_ROW_PHONENUM, TABLE_ROW_EMAIL,
					TABLE_ROW_PHOTOID }, null, null, null, null, null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast()) {
				do {
					rowContactObj = new ContactModel();
					rowContactObj.setId(cursor.getInt(0));
					prepareSendObject(rowContactObj, cursor);
					allRowsObj.add(rowContactObj);

				} while (cursor.moveToNext()); // try to move the cursor's
												// pointer forward one position.
			}
		} catch (SQLException e) {
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}

		return allRowsObj;

	}

	private void prepareSendObject(ContactModel rowObj, Cursor cursor) {
		rowObj.setName(cursor.getString(cursor
				.getColumnIndexOrThrow(TABLE_ROW_NAME)));
		rowObj.setContactNo(cursor.getString(cursor
				.getColumnIndexOrThrow(TABLE_ROW_PHONENUM)));
		rowObj.setEmail(cursor.getString(cursor
				.getColumnIndexOrThrow(TABLE_ROW_EMAIL)));
		rowObj.setPhoto(cursor.getString(5)); // alternate way to pick data if
												// we know the column number
	}

	public void deleteRow(int rowID) {
		// ask the database manager to delete the row of given id
		try {
			db.delete(TABLE_NAME, TABLE_ROW_ID + "=" + rowID, null);
		} catch (Exception e) {
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}

}
