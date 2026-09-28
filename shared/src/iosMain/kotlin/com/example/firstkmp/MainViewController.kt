package com.example.firstkmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.example.firstkmp.data.Features.datastore.DATASTORE_FILE_NAME
import com.example.firstkmp.data.Features.datastore.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController {
    val dataStore = remember {
        createDataStore(producePath = {
            val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            requireNotNull(documentDirectory).path + "/$DATASTORE_FILE_NAME"
    }
        )
    }
    App(dataStore)
}