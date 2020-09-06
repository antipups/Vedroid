package com.example.myapplication
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.widget.Toast
import androidx.core.content.contentValuesOf


object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    const val DB_NAME = "MyDB.db"
    object FeedEntry : BaseColumns {
        const val TABLE_NAME  = "settings"
        const val TITLE_SETTING  = "title"
        const val COL_WIFI  = "wifi"
        const val COL_BLUETOOTH  = "bluetooth"
        const val COL_BRIGHTNESS  = "brightness"
    }

    private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${FeedEntry.TITLE_SETTING} TEXT, " +
                "${FeedEntry.COL_WIFI} TEXT, " +
                "${FeedEntry.COL_BLUETOOTH} TEXT, " +
                "${FeedEntry.COL_BRIGHTNESS} INT)"

    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"

    class FeedReaderDbHelper(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1){

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
            db?.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        fun insertData(setting: Setting){
            val db = this.writableDatabase
            val values = ContentValues().apply {
                put(FeedEntry.TITLE_SETTING, setting.title)
                put(FeedEntry.COL_WIFI, setting.wifi)
                put(FeedEntry.COL_BLUETOOTH, setting.bluetooth)
                put(FeedEntry.COL_BRIGHTNESS, setting.brightness)
            }
        if (db.insert(FeedEntry.TABLE_NAME, null, values) != (-1).toLong())
            Toast.makeText(context, "Мод добавлен", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_LONG).show()
        }

        fun getData(): MutableList<Setting>{
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM ${FeedEntry.TABLE_NAME}", null)
            val rows: MutableList<Setting> = ArrayList()
            with(cursor) {
                while (moveToNext()) {
                    rows.add(Setting(
                        cursor.getString(1).toString(),
                        cursor.getString(2).toInt(),
                        cursor.getString(3).toInt(),
                        cursor.getString(4).toInt()
                    ))
                }
//                println(rows)
            }
            return rows
        }

        fun deleteData(title: String){
            val db = this.writableDatabase
//            val cursor = db.rawQuery("SELECT * FROM ${FeedEntry.TABLE_NAME}", null)
            db.delete(FeedEntry.TABLE_NAME, "${FeedEntry.TITLE_SETTING} = ?", arrayOf(title))
        }

        fun updateData(setting: Setting){
            val db = this.writableDatabase
//            val cursor = db.rawQuery("SELECT * FROM ${FeedEntry.TABLE_NAME}", null)
            val values = ContentValues().apply {
                put(FeedEntry.TITLE_SETTING, setting.title)
                put(FeedEntry.COL_WIFI, setting.wifi)
                put(FeedEntry.COL_BLUETOOTH, setting.bluetooth)
                put(FeedEntry.COL_BRIGHTNESS, setting.brightness)
            }
            db.update(FeedEntry.TABLE_NAME, values,"${FeedEntry.TITLE_SETTING} = ?", arrayOf(setting.title))
        }
    }
}