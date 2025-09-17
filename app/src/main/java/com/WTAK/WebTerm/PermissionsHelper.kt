package com.WTAK.WebTerm

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager

fun checkStoragePermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        val readPerm = ContextCompat.checkSelfPermission(
            App.instance, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val writePerm = ContextCompat.checkSelfPermission(
            App.instance, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        readPerm == PackageManager.PERMISSION_GRANTED &&
                writePerm == PackageManager.PERMISSION_GRANTED
    }
}

fun requestStoragePermission() {
    val context = App.instance
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // Android 11+
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.data = Uri.parse("package:${context.packageName}")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } else {
        // Android 10 and below → runtime permissions
        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1001
            )
        }
    }
}
