/*
 * Copyright (C) 2025 OrionOS
 * SPDX-License-Identifier: Apache-2.0
 */

package com.orion.support

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.internal.logging.nano.MetricsProto
import com.android.settings.R
import com.android.settings.dashboard.DashboardFragment
import com.android.settings.search.BaseSearchIndexProvider
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.core.lifecycle.Lifecycle
import com.android.settingslib.search.SearchIndexable

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.UserManager
import com.android.settingslib.drawable.CircleFramedDrawable
import com.android.internal.util.UserIcons
import android.os.UserHandle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

class Molecular : DashboardFragment() {

    companion object {
        const val CATEGORY_KEY = "com.android.settings.category.ia.molecular"
        private const val LOG_TAG = "Molecular"

        val SEARCH_INDEX_DATA_PROVIDER: BaseSearchIndexProvider = object : BaseSearchIndexProvider(R.xml.molecular) {
            override fun createPreferenceControllers(context: Context): List<AbstractPreferenceController> {
                return buildPreferenceControllers(context, null, null)
            }
        }

        private fun buildPreferenceControllers(context: Context, fragment: Molecular?, lifecycle: Lifecycle?): List<AbstractPreferenceController> {
            val controllers = mutableListOf<AbstractPreferenceController>()
            controllers.add(MolecularController(context))
            return controllers
        }
    }

    override fun getPreferenceScreenResId(): Int {
        return R.xml.molecular
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homePref = findPreference<com.android.settingslib.widget.LayoutPreference>("molecular_homepage")
        val greetingText = homePref?.findViewById<TextView>(R.id.greetings_text)
        val avatarView = homePref?.findViewById<androidx.preference.internal.PreferenceImageView>(R.id.avatar_profile)

        // User Name
        val userManager = requireContext().getSystemService(UserManager::class.java)
        val userInfo = com.android.settings.Utils.getExistingUser(
            userManager,
            android.os.Process.myUserHandle()
        )
        val ownerName = userInfo?.name ?: "User"

        greetingText?.text = getString(R.string.greetings, ownerName)

        // User avatar profile
        if (avatarView != null) {
            avatarView.setImageDrawable(getCircularUserIcon())
            
            avatarView.setOnClickListener {
                navigateToUserSettings()
            }
            
            avatarView.contentDescription = getString(R.string.accessibility_user_profile)
            avatarView.isClickable = true
        }
    }

    private fun navigateToUserSettings() {
        val intent = Intent().apply {
            setClassName(
                "com.android.settings",
                "com.android.settings.Settings\$UserSettingsActivity"
            )
        }
        
        try {
            startActivity(intent)
        } catch (e: Exception) {
            try {
                val fallbackIntent = Intent().apply {
                    action = "android.settings.USER_SETTINGS"
                }
                startActivity(fallbackIntent)
            } catch (e2: Exception) {
                android.widget.Toast.makeText(
                    requireContext(),
                    R.string.user_settings_unavailable,
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getCircularUserIcon(): Drawable {
        val userManager = requireContext().getSystemService(UserManager::class.java)

        // Get avatar from system
        var bitmapUserIcon: Bitmap? = userManager.getUserIcon(UserHandle.myUserId())

        if (bitmapUserIcon == null) {
            // default avatar
            val defaultDrawable = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.molecular_orion_logo
            )!!

            bitmapUserIcon = UserIcons.convertToBitmap(defaultDrawable)
        }

        return CircleFramedDrawable(
            bitmapUserIcon,
            resources.getDimension(com.android.internal.R.dimen.user_icon_size).toInt()
        )
    }

    override fun getMetricsCategory(): Int {
        return MetricsProto.MetricsEvent.ORION
    }

    override fun onStart() {
        super.onStart()
    }

    override fun getLogTag(): String {
        return LOG_TAG
    }

    override fun createPreferenceControllers(context: Context): List<AbstractPreferenceController> {
        return buildPreferenceControllers(context, this, getSettingsLifecycle())
    }
}
