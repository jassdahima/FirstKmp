package com.example.firstkmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.firstkmp.data.Features.datastore.DATASTORE_FILE_NAME
import com.example.firstkmp.data.Features.datastore.createDataStore



class MainActivity : ComponentActivity() {
    private val dataStore : DataStore<Preferences> by lazy {
        createDataStore(producePath = {
            applicationContext.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(dataStore = dataStore)
        }
    }
}
