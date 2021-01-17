package com.example.chatter.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data mapping for a single entity in the Room database
 */
@Entity(tableName = "contact_list")
data class Contact(
    @PrimaryKey(autoGenerate = false)   val id: Long = 0L,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?,
    @ColumnInfo(name = "name")   val name: String?
)
