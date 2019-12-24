package com.example.csvexporter

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }
    fun addData(time: String){
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("time", time)
        db.insert("$TABLE_NAME", null, values)
        db.close()

    }
    fun deleteUsers(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.close()
    }
    fun getAllData(): Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        db.close()
    }
    companion object{
        val DATABASE_NAME = "DB"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "DATA"
        val DATA_ID = "ID"
        val DATA_TIME = "TIME"
        val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + DATA_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," + DATA_TIME + " STRING )"
    }
}
