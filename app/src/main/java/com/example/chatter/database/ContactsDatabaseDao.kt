package com.example.chatter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.chatter.database.ContactModel

@Dao
interface ContactsDatabaseDao {

    @Insert
    fun insertContacts(contact: ContactModel)

    @Update
    fun updateContact(contact: ContactModel)

    @Query("SELECT * from contact_list WHERE id = :key")
    fun getContact(key: Long): ContactModel?

    @Query("SELECT * from contact_list ORDER BY last_name DESC")
    fun getAllContacts(): LiveData<List<ContactModel>>

    @Query("DELETE FROM contact_list")
    fun clearContacts()

}