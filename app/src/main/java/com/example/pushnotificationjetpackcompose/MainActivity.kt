package com.example.pushnotificationjetpackcompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pushnotificationjetpackcompose.ui.theme.Purple80
import com.example.pushnotificationjetpackcompose.ui.theme.PushNotificationJetpackComposeTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PushNotificationJetpackComposeTheme {
                FirebaseMessaging.getInstance().token // cihazın mevcut FCM token'ını asenkron olarak döndürür.
                    .addOnCompleteListener(OnCompleteListener {task -> //Bu dinleyici, token alım işlemi tamamlandığında tetiklenir.
                        if(!task.isSuccessful){
                            Log.d("FCM Notify","Fetching FCM registration token failed",task.exception)
                            return@OnCompleteListener
                        }
                        val token : String? = task.result
                        Log.d("FCM Token", token,task.exception)
                        Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
                    })
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Compose FCM Notificaiton",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth()
                                .background(Purple80)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "",
                            modifier = Modifier.height(200.dp).padding(15.dp).clip(
                                RoundedCornerShape(20.dp)
                            ))
                    }

                }
            }
        }
    }
}