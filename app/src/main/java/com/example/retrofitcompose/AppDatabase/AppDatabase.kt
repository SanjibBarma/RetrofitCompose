package com.example.retrofitcompose.AppDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.retrofitcompose.Model.PostEntity
import com.example.retrofitcompose.Model.UserEntity

@Database(entities = [PostEntity::class, UserEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_3_5 = object : Migration(3, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS `user_table` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `username` TEXT NOT NULL,
                `password` TEXT NOT NULL DEFAULT 'undefined'
            )
        """)
            }
        }


        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_3_5)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
