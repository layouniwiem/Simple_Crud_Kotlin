package esprims.gi2.tp3Wiem.classes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import kotlin.collections.ArrayList

class WordsManager (context: Context){
    private var motHelper : MotDbHelper = MotDbHelper(context)
    lateinit var db: SQLiteDatabase

    fun openReadDB (){
        db = motHelper.readableDatabase
    }
    fun openWriteDB(){
        db = motHelper.writableDatabase
    }
    fun closeDB(){
        db.close()
    }
    fun addWord(mot : Mot): Long {
        val contentValues = ContentValues()
        contentValues.put(MotDbHelper.COL_MOT,mot.leMot)
        contentValues.put(MotDbHelper.COL_TAILLE,mot.taille)
        contentValues.put(MotDbHelper.COL_GENRE,mot.genre)
        contentValues.put(MotDbHelper.COL_TYPE,mot.type)

        return motHelper.writableDatabase.insert(MotDbHelper.THE_TABLE, null , contentValues)
    }
    fun cursorToWord(cursor: Cursor) : List<Mot>{
        val wordsList : ArrayList<Mot> = ArrayList()
        if(cursor.moveToFirst())
            do {
                var leMot = cursor.getString(cursor.getColumnIndex("leMot"))
                var taille = cursor.getInt(cursor.getColumnIndex("taille"))
                var genre = cursor.getString(cursor.getColumnIndex("genre"))
                var type = cursor.getString(cursor.getColumnIndex("type"))
                val mot=Mot(leMot,taille,genre,type)
                wordsList.add(mot)

            }while (cursor.moveToNext())
        return  wordsList
    }
    fun getWords(): List<Mot>{
        val selectQuery= "SELECT * FROM " + MotDbHelper.THE_TABLE
        var cursor: Cursor?
        cursor = db.rawQuery(selectQuery,null)
        return cursorToWord(cursor)
    }
    fun deleteWord(mot : String){
        this.openWriteDB()
        db.delete(MotDbHelper.THE_TABLE,MotDbHelper.COL_MOT+"=?", arrayOf(mot))
        this.closeDB()
    }
    fun updateWord(motNew: Mot,mot : String): Int {
        val contentValues = ContentValues()
        contentValues.put(MotDbHelper.COL_MOT,motNew.leMot)
        contentValues.put(MotDbHelper.COL_TAILLE,motNew.taille)
        contentValues.put(MotDbHelper.COL_GENRE,motNew.genre)
        contentValues.put(MotDbHelper.COL_TYPE,motNew.type)

        return db.update(MotDbHelper.THE_TABLE,contentValues,MotDbHelper.COL_MOT+"=?", arrayOf(mot))
    }
}

