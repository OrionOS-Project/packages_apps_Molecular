/*
 * SPDX-FileCopyrightText: Project Fluid
 * SPDX-FileCopyrightText: DerpFest AOSP
 * SPDX-FileCopyrightText: OrionOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.orion.support.preference;

import android.app.UiModeManager
import android.content.Context
import android.util.AttributeSet

import com.android.settings.R

import com.orion.ui.preference.OrionCardDarkModePreferenceBase

class DarkModeCardPreference(context: Context, attrs: AttributeSet) : OrionCardDarkModePreferenceBase(context, attrs) {

    private val uiModeManager: UiModeManager = context.getSystemService(UiModeManager::class.java)

    override fun setNightModeActivated(checked: Boolean) {
        uiModeManager.nightMode = when (checked) {
            true -> UiModeManager.MODE_NIGHT_YES
            false -> UiModeManager.MODE_NIGHT_NO
        }
    }

    override val defaultSummary = context.resources.getString(R.string.dark_mode_summary_default)
    override val powerSaveSummary = context.resources.getString(R.string.dark_mode_summary_powersave)
}
