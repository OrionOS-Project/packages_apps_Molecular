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
import android.hardware.fingerprint.FingerprintManager;
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

import com.android.internal.util.orion.Utils;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.SettingsPreferenceFragment;
import android.content.res.Resources;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import com.orion.support.utils.DeviceUtils;

public class Themes extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String KEY_ICONS_CATEGORY = "themes_icons_category";
    private static final String KEY_SIGNAL_ICON = "android.theme.customization.signal_icon";
    private static final String KEY_ANIMATIONS_CATEGORY = "themes_animations_category";
    private static final String KEY_UDFPS_ANIMATION = "udfps_animation";

    private PreferenceCategory mIconsCategory;
    private Preference mSignalIcon;
    private PreferenceCategory mAnimationsCategory;
    private Preference mUdfpsAnimation;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        final Context context = getContext();
        final Resources resources = context.getResources();
        addPreferencesFromResource(R.xml.themes_section);
        final ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefScreen = getPreferenceScreen();

        mIconsCategory = (PreferenceCategory) findPreference(KEY_ICONS_CATEGORY);
        mSignalIcon = (Preference) findPreference(KEY_SIGNAL_ICON);
        mAnimationsCategory = (PreferenceCategory) findPreference(KEY_ANIMATIONS_CATEGORY);
        mUdfpsAnimation = (Preference) findPreference(KEY_UDFPS_ANIMATION);

        if (!DeviceUtils.deviceSupportsMobileData(context)) {
            mIconsCategory.removePreference(mSignalIcon);
        }

        FingerprintManager fingerprintManager = (FingerprintManager)
                getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
            mAnimationsCategory.removePreference(mUdfpsAnimation);
        } else {
            if (!Utils.isPackageInstalled(context, "com.orion.udfps.animations")) {
                mAnimationsCategory.removePreference(mUdfpsAnimation);
            }
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
        new BaseSearchIndexProvider(R.xml.themes_section) {

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                final Resources resources = context.getResources();
                if (!DeviceUtils.deviceSupportsMobileData(context)) {
                    keys.add(KEY_SIGNAL_ICON);
                }

                if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
                    keys.add(KEY_UDFPS_ANIMATION);
                } else {
                    if (!Utils.isPackageInstalled(context, "com.orion.udfps.animations")) {
                        keys.add(KEY_UDFPS_ANIMATION);
                    }
                }
                return keys;
            }
};
