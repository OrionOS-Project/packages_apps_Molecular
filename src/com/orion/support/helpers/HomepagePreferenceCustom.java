/*
 * Copyright (C) 2021 The Android Open Source Project
 *               2025 OrionOS
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

package com.orion.support.helpers;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/** A customized layout for homepage preference. */
public class HomepagePreferenceCustom extends Preference implements
        HomepagePreferenceCustomLayoutHelper.HomepagePreferenceCustomLayout {

    private final HomepagePreferenceCustomLayoutHelper mHelper;

    public HomepagePreferenceCustom(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mHelper = new HomepagePreferenceCustomLayoutHelper(this);
    }

    public HomepagePreferenceCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = new HomepagePreferenceCustomLayoutHelper(this);
    }

    public HomepagePreferenceCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new HomepagePreferenceCustomLayoutHelper(this);
    }

    public HomepagePreferenceCustom(Context context) {
        super(context);
        mHelper = new HomepagePreferenceCustomLayoutHelper(this);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mHelper.onBindViewHolder(holder);
    }

    @Override
    public HomepagePreferenceCustomLayoutHelper getHelper() {
        return mHelper;
    }
}