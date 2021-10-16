/*
 * SPDX-FileCopyrightText: Project Fluid
 * SPDX-FileCopyrightText: DerpFest AOSP
 * SPDX-FileCopyrightText: OrionOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.orion.support.fragment

import com.android.internal.logging.nano.MetricsProto.MetricsEvent

import android.os.Bundle

import androidx.preference.Preference

import com.android.settings.R
import com.android.settings.SettingsPreferenceFragment

class Lockscreen : SettingsPreferenceFragment(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.lockscreen)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        return true
    }

    override fun getMetricsCategory(): Int = MetricsEvent.ORION

    companion object {
        const val TAG = "Molecular"
    }
}
