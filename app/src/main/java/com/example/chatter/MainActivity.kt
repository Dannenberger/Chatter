package com.example.chatter

import android.content.Context
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.chatter.database.Contact
import com.example.chatter.database.ContactDatabase
import com.example.chatter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    /**
     * Handles the up button functionality in the action bar
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

    /**
     * Imports contacts from a user's contact list on their device
     */
//    fun importContacts() {
//        val context: Context? = baseContext
//        val db = context?.let { ContactDatabase.getInstance(it) }
//        val contentResolver = contentResolver
//        val cursor: Cursor? = contentResolver.query(
//            ContactsContract.Contacts.CONTENT_URI,
//            null,
//            null,
//            null,
//            null
//        )
//        while (cursor != null && cursor.moveToNext()) {
//            if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
//                val phoneNumber: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                if (!checkForValidNumber(phoneNumber)) continue
//                val id: Long = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
//                val firstName: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME))
//                val lastName: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME))
//                val contact = Contact(
//                    id = id,
//                    firstName = firstName,
//                    lastName = lastName,
//                    phoneNumber = phoneNumber,
//                )
//                if (db != null) {
//                    db.contactsDao.insertContact(contact)
//                }
//            }
//        }
//    }

    /**
     * Checks to make sure a number received should be imported to the list of contacts in the app.
     */
    private fun checkForValidNumber(phoneNumber: String): Boolean {
        if (phoneNumber.length < 10 || phoneNumber.startsWith("1800")) {
            return false
        }
        return true
    }


}