/*
 * Copyright (C) 2025 OrionOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orion.support.fragments;

import android.content.Context;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import com.android.settings.R;
import androidx.annotation.NonNull;
import android.hardware.fingerprint.FingerprintManager;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.SettingsPreferenceFragment;
import android.content.res.Resources;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Lockscreen extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String KEY_FINGERPRINT_CATEGORY = "lock_screen_fingerprint_category";
    private static final String KEY_RIPPLE_EFFECT = "enable_ripple_effect";
    private PreferenceCategory mFingerprintCategory;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        final Context context = getContext();
        final Resources resources = context.getResources();
        addPreferencesFromResource(R.xml.lockscreen_section);
        final ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefScreen = getPreferenceScreen();
        mFingerprintCategory = (PreferenceCategory) findPreference(KEY_FINGERPRINT_CATEGORY);
        FingerprintManager fingerprintManager = (FingerprintManager)
                getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
            prefScreen.removePreference(mFingerprintCategory);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ORION;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider(R.xml.lockscreen_section) {

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                final Resources resources = context.getResources();
                FingerprintManager fingerprintManager = (FingerprintManager)
                    context.getSystemService(Context.FINGERPRINT_SERVICE);
                if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
                    keys.add(KEY_RIPPLE_EFFECT);
                }
                return keys;
            }
};
