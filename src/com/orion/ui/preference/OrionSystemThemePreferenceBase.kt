/*
 * SPDX-FileCopyrightText: OrionOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.orion.ui.preference

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.android.settings.R

abstract class OrionSystemThemePreferenceBase : Preference, View.OnClickListener {
    
    companion object {
        private const val ANIM_FRACTION_PREVIEW = 0.96f
    }
    
    lateinit var orionLogo: ImageView
    lateinit var buttonBelow: CardView
    lateinit var buttonBelowText: TextView
    lateinit var buttonBelowIcon: ImageView
    
    private var holder: PreferenceViewHolder? = null
    
    constructor(context: Context) : super(context) {
        initialize()
    }
    
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }
    
    private fun initialize() {
        CoroutineScope(Dispatchers.Main).launch {
            // Original had a Core.init() call here that we can skip
        }
        
        layoutResource = R.layout.orion_system_theme_preference
        isSelectable = false
    }
    
    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        
        orionLogo = holder.findViewById(R.id.orion_logo) as ImageView
        buttonBelow = holder.findViewById(R.id.button_below) as CardView
        buttonBelowText = holder.findViewById(R.id.button_below_text) as TextView
        buttonBelowIcon = holder.findViewById(R.id.button_below_icon) as ImageView
        
        this.holder = holder
        
        buttonBelow.setOnClickListener(this)
        
        buttonBelowIcon.drawable.setTint(context.getColor(R.color.colorSecondary))
        
        // Update button text based on whether Monet is enabled
        updateButtonText()
    }
    
    private fun updateButtonText() {
        val isMonet = isMonetEnabled()
        buttonBelowText.text = context.getString(R.string.monet_settings)
    }
    
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_below -> openThemeSettings()
        }
    }
    
    val monetEnabled: Boolean
        get() = isMonetEnabled()
        
    fun setMonetEnabled(value: Boolean) {
        toggleMonet(value)
        updateButtonText()
    }
    
    // Abstract methods that subclasses need to implement
    abstract fun openThemeSettings()
    abstract fun isMonetEnabled(): Boolean
    abstract fun toggleMonet(activated: Boolean?)
} 
