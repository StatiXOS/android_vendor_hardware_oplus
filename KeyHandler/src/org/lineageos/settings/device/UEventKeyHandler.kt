/*
 * Copyright (C) 2021-2022 The LineageOS Project
 * Copyright (C) 2024 StatiXOS
 * SPDX-License-Identifier: Apache-2.0
 */

 package org.lineageos.settings.device

 import android.content.Context
 import android.media.AudioManager
 import android.os.UEventObserver
 import android.util.Log
 import java.util.HashMap
 
 class UEventKeyHandler(private val context: Context) {

    companion object {
       private val TAG = "UEventKeyHandler"
       private val UEVENT_PATH = "/proc/tristatekey/tri_state"
    }

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val triStateToAudioModeMap = mutableMapOf(
        1 to AudioManager.RINGER_MODE_NORMAL,
        2 to AudioManager.RINGER_MODE_VIBRATE,
        3 to AudioManager.RINGER_MODE_SILENT
    )
 
    private val uEventObserver = UEventObserver(UEVENT_PATH) { event ->
        try {
           val triStateValue = event.getString("value")?.toInt() ?: return@UEventObserver
           val audioMode = triStateToAudioModeMap[triStateValue]
           if (audioMode != null) {
              audioManager.setRingerMode(audioMode)
           } else {
              Log.w(TAG, "Unknown tri-state value: $triStateValue")
           }
        } catch (e: NumberFormatException) {
           Log.w(TAG, "Failed to parse tri-state value: ${event.getString("value")}", e)
        }
    }
 
    init {
        uEventObserver.startObserving()
    }
 
    fun release() {
        uEventObserver.stopObserving()
    }
 }