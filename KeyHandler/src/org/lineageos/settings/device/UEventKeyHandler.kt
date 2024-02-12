/*
 * Copyright (C) 2021-2022 The LineageOS Project
 * Copyright (C) 2024 StatiXOS
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.device;

import android.content.Context;
import android.media.AudioManager;
import android.os.UEventObserver;
import android.util.Log;

import java.util.HashMap;

/**
 * UEventObserver-based KeyHandler for tri-state key functionality.
 */
public class UEventKeyHandler {

    private static final String TAG = "UEventKeyHandler";

    private static final String UEVENT_PATH = "/proc/tristatekey/tri_state";

    private final Context mContext;
    private final AudioManager mAudioManager;
    private final HashMap<Integer, Integer> mTriStateToAudioModeMap;
    private final UEventObserver mUEventObserver;

    public UEventKeyHandler(Context context) {
        mContext = context;
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mTriStateToAudioModeMap = new HashMap<>();
        mTriStateToAudioModeMap.put(1, AudioManager.RINGER_MODE_NORMAL);
        mTriStateToAudioModeMap.put(2, AudioManager.RINGER_MODE_VIBRATE);
        mTriStateToAudioModeMap.put(3, AudioManager.RINGER_MODE_SILENT);

        mUEventObserver = new UEventObserver(UEVENT_PATH) {
            @Override
            public void onUEvent(UEvent event) {
                try {
                    int triStateValue = Integer.parseInt(event.get("value"));
                    int audioMode = mTriStateToAudioModeMap.get(triStateValue);
                    if (audioMode != null) {
                        mAudioManager.setRingerMode(audioMode);
                    } else {
                        Log.w(TAG, "Unknown tri-state value: " + triStateValue);
                    }
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Failed to parse tri-state value: " + event.get("value"), e);
                }
            }
        };
        mUEventObserver.startObserving();
    }

    public void release() {
        mUEventObserver.stopObserving();
    }
}