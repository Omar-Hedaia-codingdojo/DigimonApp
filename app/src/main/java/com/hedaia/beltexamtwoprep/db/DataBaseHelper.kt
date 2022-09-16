package com.hedaia.beltexamtwoprep.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hedaia.beltexamtwoprep.models.DigimonModel

class DataBaseHelper(context:Context): SQLiteOpenHelper(context,"digimon_database",null,1) {
    private val sqliteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table digimon(pk Integer Primary Key autoincrement, Name text, Img text, Level text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table if exists digimon")
        onCreate(db)
    }

    fun saveData(name:String,img:String,level:String){
        val contentValues = ContentValues()
        contentValues.put("Name",name)
        contentValues.put("Img",img)
        contentValues.put("Level",level)
        sqliteDatabase.insert("digimon",null,contentValues)

    }

    fun readData():ArrayList<DigimonModel>
    {
        val digimons = arrayListOf<DigimonModel>()
        val cursor = sqliteDatabase.rawQuery("SELECT * FROM digimon",null)

        if(cursor.count<1){
            println("No data found")
        }else{
            while (cursor.moveToNext()){
                val pk = cursor.getInt(0)
                val name = cursor.getString(1)
                val img = cursor.getString(2)
                val level = cursor.getString(3)

                digimons.add(DigimonModel(pk,img,level,name))
            }
        }
        cursor.close()
        return digimons

    }

    fun deleteData(digimonModel: DigimonModel){
        sqliteDatabase.delete("digimon","pk = ${digimonModel.pk}",null)
    }

}