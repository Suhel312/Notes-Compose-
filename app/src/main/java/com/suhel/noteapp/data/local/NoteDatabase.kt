package com.suhel.noteapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        val MIGRATION_1_2 = Migration(1, 2) {
            fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE notes ADD COLUMN isPinned INTEGER NOT NULL DEFAULT 0"
                )
                database.execSQL(
                    "ALTER TABLE notes ADD COLUMN color INTEGER NOT NULL DEFAULT -1"
                )
            }
        }

        fun getDatabase(context: Context): NoteDatabase{
            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}