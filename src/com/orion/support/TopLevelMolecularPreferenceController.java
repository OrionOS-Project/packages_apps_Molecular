/*
 * SPDX-FileCopyrightText: OrionOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.orion.support;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class TopLevelMolecularPreferenceController extends BasePreferenceController {

    public TopLevelMolecularPreferenceController(Context context,
            String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}
