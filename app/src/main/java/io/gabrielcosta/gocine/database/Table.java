package io.gabrielcosta.gocine.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by gabriel on 3/6/17.
 */

interface Table {

  void onCreate(final SQLiteDatabase sqLiteDatabase, final int version);

  void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int oldVersion, final int newVersion);

}
