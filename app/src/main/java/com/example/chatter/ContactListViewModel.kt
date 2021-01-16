package com.example.chatter

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.chatter.database.Contact
import com.example.chatter.database.ContactDatabase
import com.example.chatter.database.ContactsDao
import kotlinx.coroutines.*

class ContactListViewModel(
    val database: ContactsDao,
    application: Application) : AndroidViewModel(application) {

    val contacts = database.getAllContacts()

    private var viewModelJob = Job()
    private val context = getApplication<Application>().applicationContext
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private suspend fun importContacts() {
        val contentResolver = context?.contentResolver
        val cursor: Cursor? = contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)) > 0) {
                    val phoneNumber: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                    if (!checkForValidNumber(phoneNumber)) continue
                    val id: Long = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
                    val firstName: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val lastName: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val contact = Contact(
                        id = id,
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber,
                    )
                    insert(contact)
                }
            }
        }
    }

    fun onStartImport() {
        uiScope.launch {
            importContacts()
        }
    }

    private suspend fun insert(contact: Contact) {
        withContext(Dispatchers.IO) {
            database.insertContact(contact)
        }
    }


}