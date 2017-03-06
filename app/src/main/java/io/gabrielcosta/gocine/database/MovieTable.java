package io.gabrielcosta.gocine.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by gabriel on 3/6/17.
 */

class MovieTable implements Table {

  public static final String TABLE_NAME = "MOVIES";
  public static final String COLUMN_ID = "id";
  public static final String COLUMN_TITLE = "title";
  public static final String COLUMN_POSTER_PATH = "poster_path";


  private static final String CREATE_MOVIE_TABLE_QUERY = String.format(
      "CREATE TABLE IF NOT EXISTS %s (%s integer primary key autoincrement, %s text not null, %s text not null);",
      TABLE_NAME, COLUMN_ID, COLUMN_TITLE, COLUMN_POSTER_PATH);

  public void onCreate(final SQLiteDatabase sqLiteDatabase, final int version) {
    sqLiteDatabase.execSQL(CREATE_MOVIE_TABLE_QUERY);
  }

  @Override
  public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int oldVersion,
      final int newVersion) {
    sqLiteDatabase.execSQL(String.format("Drop table if exists %s", TABLE_NAME));
    onCreate(sqLiteDatabase, newVersion);
  }

}
