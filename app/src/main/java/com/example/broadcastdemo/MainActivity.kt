package com.example.broadcastdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.AirplanemodeInactive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.broadcastdemo.ui.theme.BroadcastDemoTheme

class MainActivity : ComponentActivity() {

    private lateinit var powerConnectedReceiver: BroadcastReceiver
    private lateinit var airplaneModeReceiver: BroadcastReceiver

    private val airplaneModeState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        airplaneModeState.value = isAirplaneModeOn(this)

        powerConnectedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_POWER_CONNECTED) {
                    Toast.makeText(context, "Зарядка підключена", Toast.LENGTH_SHORT).show()
                }
            }
        }

        airplaneModeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isOn = intent.getBooleanExtra("state", false)
                    airplaneModeState.value = isOn
                }
            }
        }

        setContent {
            BroadcastDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(airplaneModeState.value)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(powerConnectedReceiver, IntentFilter(Intent.ACTION_POWER_CONNECTED))
        registerReceiver(airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(powerConnectedReceiver)
        unregisterReceiver(airplaneModeReceiver)
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }
}

@Composable
fun MainScreen(isAirplaneModeOn: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Стан авіарежиму",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Іконка авіарежиму
        val icon = if (isAirplaneModeOn) {
            Icons.Filled.AirplanemodeActive
        } else {
            Icons.Filled.AirplanemodeInactive
        }

        val iconTint = if (isAirplaneModeOn) Color.Red else Color.Gray

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(iconTint.copy(alpha = 0.1f))
                .padding(16.dp),
            tint = iconTint
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (isAirplaneModeOn) "Авіарежим ВКЛ" else "Авіарежим ВИКЛ",
            style = MaterialTheme.typography.headlineSmall,
            color = if (isAirplaneModeOn) Color.Red else Color.Gray
        )
    }
}
