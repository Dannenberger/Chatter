package com.example.chatter.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactsDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertContact(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Query("SELECT * from contact_list WHERE id = :key")
    fun getContactById(key: Long): Contact?

    @Query("SELECT * from contact_list ORDER BY name DESC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * from contact_list ORDER BY RANDOM() LIMIT 1")
    fun getRandomContact(): Contact

    @Query("DELETE FROM contact_list")
    fun clearContacts()

}