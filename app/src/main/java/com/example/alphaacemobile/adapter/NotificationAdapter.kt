package com.example.alphaacemobile.adapter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.alphaacemobile.R
import com.example.alphaacemobile.databinding.NotificationItemBinding
import com.example.alphaacemobile.model.NotificationModel

class NotificationAdapter(
    private val notificationList: ArrayList<NotificationModel>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(notificationList[position])
        holder.listItemBinding.sNotificationSwitch.setOnClickListener {
            if (holder.listItemBinding.sNotificationSwitch.isChecked){
                setupNotification(holder.listItemBinding.tvNotificationName.text as String)
                //Toast.makeText(holder.itemView.context, "${holder.listItemBinding.tvNotificationName.text}", Toast.LENGTH_SHORT).show()
                Toast.makeText(holder.itemView.context, "TRUE", Toast.LENGTH_SHORT).show()

                val channelID = "1000"
                val notificationManager = holder.itemView.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                val builder = NotificationCompat.Builder(holder.itemView.context, channelID)
                    .setSmallIcon(R.drawable.ic_coin)
                    .setContentTitle("${holder.listItemBinding.tvNotificationName.text}")
                    .setContentText("Sample Description")

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(channelID, "Custom Notification", NotificationManager.IMPORTANCE_DEFAULT)

                    channel.enableLights(true)
                    channel.enableVibration(true)
                    notificationManager.createNotificationChannel(channel)
                    builder.setChannelId(channelID)
                }
                val notification = builder.build()

                notificationManager.notify(1000, notification)


//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    val CHANNEL_ID = "channelID"
//                    val name = "${holder.listItemBinding.tvNotificationName.text}"
//                    val id = 0
//                    val descriptionText = "ram"
//                    val importance = NotificationManager.IMPORTANCE_DEFAULT
//                    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                        lightColor=Color.BLUE
//                        enableLights(true)
//                        description = descriptionText
//                    }
//                    // Register the channel with the system
//                    val notificationManager: NotificationManager =
//                        holder.itemView.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//                    notificationManager.createNotificationChannel(channel)
//
//                    val snoozeIntent = Intent(holder.itemView.context, NotificationAdapter::class.java)
//                    val pendingIntent = TaskStackBuilder.create(holder.itemView.context).run {
//                        addNextIntentWithParentStack(snoozeIntent)
//                        getPendingIntent(0, Pend)
//                    }
//                }
            } else {
                Toast.makeText(holder.itemView.context, "FALSE", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupNotification(text: String) {
        Log.d("HIHI", text)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    class ViewHolder(var listItemBinding: NotificationItemBinding) :
        RecyclerView.ViewHolder(listItemBinding.root) {
        fun bindItem(notification: NotificationModel) {
            listItemBinding.ivNotificationImage.setImageResource(notification.image)
            listItemBinding.tvNotificationName.text = notification.name
            listItemBinding.ivNotificationStatus.setImageResource(notification.status)
            listItemBinding.sNotificationSwitch.isChecked = notification.notify
        }
    }

}