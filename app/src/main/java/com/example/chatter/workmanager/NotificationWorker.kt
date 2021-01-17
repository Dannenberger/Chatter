package com.example.chatter.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.chatter.*
import com.example.chatter.database.ContactDatabase

class NotificationWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    private val CHANNEL_ID = "channel_id"
    private lateinit var channel: NotificationChannel
    private val notificationId = 882
    private var context: Context = applicationContext

    /**
     * The worker that creates the notification channel and send the notification
     */
    override fun doWork(): Result {
        createNotificationChannel()
        sendNotification()
        return Result.success()
    }

    /**
     * Creates the notification channel that allows notifications to be sent
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Title"
            val descriptionText = "Hello, world!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager? = getSystemService(context, NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    /**
     * Handles sending the actual notification to the device
     */
    private fun sendNotification() {
        val randomContact = ContactDatabase.getInstance(applicationContext).contactsDao.getRandomContact()
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Chatter here!")
            .setContentText("Say hi to " + randomContact.name + " today")
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

    /**
     * TODO: Opens the SMS app on the user's device when tapping the notification
     */
    private fun composeSmsMessage() {
        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.addCategory(Intent.CATEGORY_APP_MESSAGING)
        startActivity(context, smsIntent, bundleOf())
    }

}