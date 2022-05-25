package com.marva.notes.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marva.notes.data.entity.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun notesDao(): NotesDao
    
    companion object {

        // supaya instance database tidak dibuat berulang kali saat aplikasi di runnning

        @Volatile
        private var instance: NotesDatabase? = null
        
        @JvmStatic
        fun getDatabase(context: Context): NotesDatabase{
            if (instance == null){
                synchronized(NotesDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context,
                        NotesDatabase::class.java,
                        "notes.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return instance as NotesDatabase
        }
    }
}