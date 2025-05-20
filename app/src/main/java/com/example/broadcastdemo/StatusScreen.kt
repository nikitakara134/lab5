package com.example.broadcastdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusScreen(
    bootCompleted: Boolean,
    powerConnected: Boolean,
    airplaneModeOn: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        StatusItem(
            icon = Icons.Default.CheckCircle,
            iconColor = if (bootCompleted) Color(0xFF4CAF50) else Color.Gray,
            title = "Завантаження пристрою",
            status = if (bootCompleted) "Пристрій завантажений" else "Поки що не завантажено"
        )

        StatusItem(
            icon = Icons.Default.BatteryChargingFull,
            iconColor = if (powerConnected) Color(0xFF2196F3) else Color.Gray,
            title = "Зарядка",
            status = if (powerConnected) "Зарядка підключена" else "Зарядка відключена"
        )

        StatusItem(
            icon = Icons.Default.AirplanemodeActive,
            iconColor = if (airplaneModeOn) Color(0xFFFF5722) else Color.Gray,
            title = "Авіарежим",
            status = if (airplaneModeOn) "Авіарежим ВКЛ" else "Авіарежим ВИКЛ"
        )
    }
}

@Composable
fun StatusItem(icon: androidx.compose.ui.graphics.vector.ImageVector,
               iconColor: Color,
               title: String,
               status: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(48.dp)
                .background(iconColor.copy(alpha = 0.1f), shape = CircleShape)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = status, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
        }
    }
}
