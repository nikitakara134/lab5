package com.example.broadcastdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.i("BootCompletedReceiver", "Пристрій завершив завантаження (BOOT_COMPLETED)")
            context?.let {
                Toast.makeText(it, "Пристрій завершив завантаження", Toast.LENGTH_LONG).show()
            }
        }
    }
}
