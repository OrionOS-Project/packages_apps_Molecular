/*
 * SPDX-FileCopyrightText: Project Fluid
 * SPDX-FileCopyrightText: DerpFest AOSP
 * SPDX-FileCopyrightText: OrionOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.orion.support.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.settings.R

class Buttons : Fragment() {

    private var hasLaunched = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.molecular_homepage_layout, container, false)
    }

    override fun onResume() {
        super.onResume()

        if (hasLaunched) return
        hasLaunched = false

        launchLineageOSSettings()
        
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun launchLineageOSSettings() {
        val intent = Intent().apply {
            setClassName(
                "org.lineageos.lineageparts",
                "org.lineageos.lineageparts.input.ButtonSettings"
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            requireContext().startActivity(intent)
        } catch (err: Exception) {
            Toast.makeText(
                requireContext(),
                "Target App not found",
                Toast.LENGTH_LONG
            ).show()
            err.printStackTrace()
        }
    }
}
