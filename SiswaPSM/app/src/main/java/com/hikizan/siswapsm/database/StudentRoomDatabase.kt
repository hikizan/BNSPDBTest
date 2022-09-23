package com.hikizan.siswapsm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentRoomDatabase : RoomDatabase() {
    abstract fun studentDao() : StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StudentRoomDatabase {
            if (INSTANCE == null) {
                synchronized(StudentRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StudentRoomDatabase::class.java,
                        "student_database"
                    ).build()
                }
            }
            return INSTANCE as StudentRoomDatabase
        }
    }
}