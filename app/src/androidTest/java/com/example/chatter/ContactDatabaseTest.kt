package com.example.chatter

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.chatter.database.ContactDatabase
import com.example.chatter.database.ContactModel
import com.example.chatter.database.ContactsDatabaseDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ContactDatabaseTest {

    private lateinit var contactDao: ContactsDatabaseDao
    private lateinit var db: ContactDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        contactDao = db.contactDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetContact() {
        val contact = ContactModel(19457566746, "1234567890", "Kevin", "Dannenberg")
        contactDao.insertContacts(contact)
        val myContact = contactDao.getContact(19457566746)
        assertEquals(myContact?.firstName, "Kevin")
    }

}