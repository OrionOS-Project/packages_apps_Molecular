/*
 * Copyright (C) 2025 OrionOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.orion.support

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.preference.PreferenceScreen
import com.android.settings.R
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.widget.LayoutPreference

class MolecularController(
    context: Context
) : AbstractPreferenceController(context) {

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)

        val layout = screen.findPreference<LayoutPreference>(PREF_KEY) ?: return

        bind(layout, R.id.molecular_statusbar, STATUSBAR)
        bind(layout, R.id.molecular_quicksettings, QUICK_SETTINGS)
        bind(layout, R.id.molecular_button, BUTTON)
        bind(layout, R.id.molecular_lockscreen, LOCKSCREEN)
        bind(layout, R.id.molecular_about, ABOUT)
        bind(layout, R.id.molecular_misc, MISC)
        bind(layout, R.id.molecular_spoof, SPOOF)
        bind(layout, R.id.molecular_monet, MONET)
    }

    private fun bind(
        layout: LayoutPreference,
        viewId: Int,
        activity: String
    ) {
        layout.findViewById<View>(viewId)?.setOnClickListener {
            mContext.startActivity(
                Intent().apply {
                    val fullClassName = if (activity.startsWith(SETTINGS_PACKAGE)) activity else "$SETTINGS_PACKAGE.$activity"
                    component = ComponentName(SETTINGS_PACKAGE, fullClassName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }

    override fun isAvailable(): Boolean = true

    override fun getPreferenceKey(): String = PREF_KEY

    companion object {
        private const val PREF_KEY = "molecular_homepage"
        private const val SETTINGS_PACKAGE = "com.android.settings"

        private const val STATUSBAR =
            "Settings\$MolecularStatusbarActivity"
        private const val QUICK_SETTINGS =
            "Settings\$MolecularQuickSettingsActivity"
        private const val BUTTON =
            "Settings\$MolecularButtonActivity"
        private const val LOCKSCREEN =
            "Settings\$MolecularLockScreenActivity"
        private const val ABOUT =
            "Settings\$MolecularAboutActivity"
        private const val MISC =
            "Settings\$MolecularMiscActivity"
        private const val SPOOF =
            "Settings\$MolecularSpoofActivity"
        private const val MONET =
            "Settings\$MolecularMonetActivity"
    }
}
