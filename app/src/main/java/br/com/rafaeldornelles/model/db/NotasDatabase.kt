package br.com.rafaeldornelles.model.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.rafaeldornelles.model.Nota

@Database(entities = [Nota::class], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class NotasDatabase : RoomDatabase() {
    abstract fun notaDao(): NotaDao
    companion object{
        private const val DATABASE_NAME = "NotasDatabase"
        private lateinit var db: NotasDatabase

        fun getDatabase(application: Application): NotasDatabase{
            return if(::db.isInitialized)  db
                else
                    Room.databaseBuilder(application.applicationContext, NotasDatabase::class.java, DATABASE_NAME)
                    .build().also { db = it }
        }
    }
}