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
import android.net.Uri;
import android.os.Bundle;
import com.android.settings.R;
import androidx.annotation.NonNull;
import android.util.Log;

import androidx.cardview.widget.CardView;

import android.view.View;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import com.orion.support.MolecularComposeFragment;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.SettingsPreferenceFragment;

import com.android.settingslib.widget.LayoutPreference;

import java.util.Map;
import java.util.HashMap;

public class About extends SettingsPreferenceFragment implements View.OnClickListener {

    private LayoutPreference mLayoutPreference;
    private Context mContext;
    private Map<Integer, Uri> buttonUriMap;
    private static final Uri GITHUB_URI_DATA = Uri.parse("https://github.com/OrionOS-Project");
    private static final Uri WEBSITE_URI_DATA = Uri.parse("https://orionos.tech/");
    private static final Uri TELEGRAM_URI_DATA = Uri.parse("https://t.me/OrionOS_prjkt");
    private static final Uri FOUNDER_URI_DATA = Uri.parse("https://orionos.tech/team/hirokixd");
    private static final Uri CO_FOUNDER_URI_DATA = Uri.parse("https://orionos.tech/team/romi.yusna");
    private static final Uri CORE_DEV_URI_DATA = Uri.parse("https://orionos.tech/team/onle");
    private static final Uri UI_UX_URI_DATA = Uri.parse("https://orionos.tech/team/iverz");
    private static final Uri SUPPORTING_URI_DATA = Uri.parse("https://orionos.tech/team/smokey");
    private static final Uri CONTRIBUTOR_URI_DATA = Uri.parse("https://orionos.tech/team/drenzzz");

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mContext = getContext();
        addPreferencesFromResource(R.xml.about_section);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeButtonUriMap();
        setupLayoutPreference();
        setupBackPressHandler();
    }

    private void initializeButtonUriMap() {
        buttonUriMap = new HashMap<>();
        buttonUriMap.put(R.id.img_github, GITHUB_URI_DATA);
        buttonUriMap.put(R.id.img_web, WEBSITE_URI_DATA);
        buttonUriMap.put(R.id.img_tele, TELEGRAM_URI_DATA);
        buttonUriMap.put(R.id.click_founder, FOUNDER_URI_DATA);
        buttonUriMap.put(R.id.click_co_founder, CO_FOUNDER_URI_DATA);
        buttonUriMap.put(R.id.click_core_dev, CORE_DEV_URI_DATA);
        buttonUriMap.put(R.id.click_ui_ux, UI_UX_URI_DATA);
        buttonUriMap.put(R.id.click_supp, SUPPORTING_URI_DATA);
        buttonUriMap.put(R.id.click_cont, CONTRIBUTOR_URI_DATA);
    }

    private void setupLayoutPreference() {
        mLayoutPreference = findPreference("about_us_section");
        if (mLayoutPreference != null) {
            View root = mLayoutPreference.findViewById(R.id.about_us_section);
            for (int id : buttonUriMap.keySet()) {
                CardView cardView = root.findViewById(id);
                if (cardView != null) {
                    cardView.setOnClickListener(this);
                }
            }
        }
    }

    private void setupBackPressHandler() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), 
            new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_content, new MolecularComposeFragment())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
            });
    }

    @Override
    public void onClick(View view) {
        Uri uri = buttonUriMap.get(view.getId());
        if (uri != null && mContext != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ORION;
    }
}
