package com.example.chatter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.chatter.database.ContactsDatabaseDao

class ContactListViewModel(
    val database: ContactsDatabaseDao,
    application: Application) : AndroidViewModel(application) {



}