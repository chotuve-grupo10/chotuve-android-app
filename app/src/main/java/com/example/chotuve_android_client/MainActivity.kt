package com.example.chotuve_android_client

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chotuve_android_client.tools.TokenHolder
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "MainActivity"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val messagingService = MyFirebaseMessagingService()

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_notifications,
                R.id.navigation_upload_video))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Log.d(TAG, "App server token is ${TokenHolder.appServerToken}")

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
            this@MainActivity,
            OnSuccessListener<InstanceIdResult> { instanceIdResult ->
                val token = instanceIdResult.token
                Log.d(TAG, "El token en ${TAG} es ${token}")
                messagingService.addTokenToUser(token)
            })

    }

}
