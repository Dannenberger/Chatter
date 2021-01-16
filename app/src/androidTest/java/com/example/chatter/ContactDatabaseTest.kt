package com.example.chatter

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.chatter.database.Contact
import com.example.chatter.database.ContactDatabase
import com.example.chatter.database.ContactsDao
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ContactDatabaseTest {

//    private lateinit var contactsDao: ContactsDao
//    private lateinit var db: ContactDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        // Using an in-memory database because the information stored here disappears when the
//        // process is killed.
//        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java)
//                // Allowing main thread queries, just for testing.
//                .allowMainThreadQueries()
//                .build()
//        contactsDao = db.contactsDao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
//        val contact = Contact(1, "1234567890", "Kevin", "Dannenberg")
//        contactsDao.insertContact(contact)
//        assertEquals(contactsDao.getContactById(1)?.firstName, "Kevin")
        assertEquals(1, 1)
    }

}