package com.example.chatter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.work.*
import com.example.chatter.database.Contact
import com.example.chatter.database.ContactDatabase
import com.example.chatter.databinding.ActivityMainBinding
import com.example.chatter.workmanager.NotificationWorker
import java.sql.Time
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        // Sending notification to user
        val task = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            7, TimeUnit.DAYS,
            1, TimeUnit.HOURS
        ).build()
        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(task)

//        // This is for testing one-off notifications
//        val task = OneTimeWorkRequest.Builder(NotificationWorker::class.java).build()
//        val workManager = WorkManager.getInstance(this)
//        workManager.enqueue(task)

    }

    /**
     * Handles the up button functionality in the action bar
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }




}