/*
 * Copyright (C) 2025 OrionOS Project
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

package com.orion.support;

import com.android.internal.logging.nano.MetricsProto;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.widget.LinearLayout;
import android.preference.Preference;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.settings.R;

import com.android.settings.SettingsPreferenceFragment;

import com.android.settingslib.widget.LayoutPreference;
import com.google.android.material.card.MaterialCardView;

import com.orion.support.fragments.Lockscreen;
import com.orion.support.fragments.About;
import com.orion.support.fragments.Quicksettings;

public class Molecular extends SettingsPreferenceFragment implements View.OnClickListener {
    private LayoutPreference mTopLayout;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.molecular_menu);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        mTopLayout = findPreference("interface_menu_top");
        if (mTopLayout != null) {
            View root = mTopLayout.findViewById(R.id.interface_menu_top);
            MaterialCardView lockscreenSection = root.findViewById(R.id.lockscreen_section);
            LinearLayout aboutSection = root.findViewById(R.id.about_section);
            LinearLayout quickSettingsSection = root.findViewById(R.id.quicksettings_section);

            if (lockscreenSection != null) lockscreenSection.setOnClickListener(this);
            if (aboutSection != null) aboutSection.setOnClickListener(this);
            if (quickSettingsSection != null) quickSettingsSection.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Fragment selectedFragment = null;
        int id = view.getId();

        if (id == R.id.lockscreen_section) {
            selectedFragment = new Lockscreen();
        } else if (id == R.id.about_section) {
            selectedFragment = new About();
        } else if (id == R.id.quicksettings_section) {
            selectedFragment = new Quicksettings();
        }

        if (selectedFragment != null && getActivity() != null) {
            FragmentManager parentManager = getActivity().getSupportFragmentManager();

            parentManager.beginTransaction()
                    .replace(R.id.main_content, selectedFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ORION;
    }
}
