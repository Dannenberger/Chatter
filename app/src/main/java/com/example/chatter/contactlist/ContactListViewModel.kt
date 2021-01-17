package com.example.chatter.contactlist

import android.app.Application
import android.database.Cursor
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import com.example.chatter.database.Contact
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
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    var id: Long = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    var name: String = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    var phoneNumber = ""
                    val phoneCursor: Cursor? = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null,
                        null)
                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            var type = phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                            if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                                break
                            }
                        }
                    }
                    if (!checkForValidNumber(phoneNumber)) {
                        continue
                    }
                    val contact = Contact(
                        id = id,
                        name = name,
                        phoneNumber = phoneNumber,
                    )
                    insert(contact)
                }
            }
        }
    }

//    /**
//     * TODO: Returns a list of all phone numbers associated with a contact, and their type
//     */
//    private fun getPhoneNumbers(phoneCursor: Cursor?): String {
//    }

    private fun checkForValidNumber(phoneNumber: String): Boolean {
        if (phoneNumber.startsWith("+1800")    ||
            phoneNumber.startsWith("+1 (800)") ||
            phoneNumber.startsWith("1 (800)")  ||
            phoneNumber.startsWith("1(800)")
        ) return false
        return phoneNumber.length >= 10
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