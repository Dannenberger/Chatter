package com.example.chatter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 2, exportSchema = false)
abstract class ContactDatabase: RoomDatabase() {

    abstract val contactsDao: ContactsDao

    companion object{
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        fun getInstance(context: Context) : ContactDatabase {
            synchronized(this) { // makes sure database only get initialized once
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contact_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}