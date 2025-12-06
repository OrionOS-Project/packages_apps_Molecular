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

package com.android.settings.core.gateway;

import com.android.settings.Settings;
import com.orion.support.Molecular;
import com.orion.support.fragment.About;
import com.orion.support.fragment.Buttons;
import com.orion.support.fragment.Lockscreen;
import com.orion.support.fragment.Misc;
import com.orion.support.fragment.QS;
import com.orion.support.fragment.StatusBar;
import com.orion.support.fragment.Spoofing;
import com.orion.support.fragment.MonetSettings;

public class OrionSettingsGateway {

  /**
   * A list of fragment that can be hosted by MolecularSettingsActivity. SettingsActivity will throw a
   * security exception if the fragment it needs to display is not in this list.
   */
  public static final String[] ENTRY_FRAGMENTS = {
    Molecular.class.getName(),
    StatusBar.class.getName(),
    QS.class.getName(),
    Misc.class.getName(),
    MonetSettings.class.getName(),
    Buttons.class.getName(),
    Lockscreen.class.getName(),
    Spoofing.class.getName(),
    About.class.getName()
  };

  public static final String[] SETTINGS_FOR_RESTRICTED = {
    Settings.MolecularActivity.class.getName(),
    Settings.MolecularStatusbarActivity.class.getName(),
    Settings.MolecularQuickSettingsActivity.class.getName(),
    Settings.MolecularMiscActivity.class.getName(),
    Settings.MolecularMonetActivity.class.getName(),
    Settings.MolecularButtonActivity.class.getName(),
    Settings.MolecularLockScreenActivity.class.getName(),
    Settings.MolecularAboutActivity.class.getName(),
    Settings.MolecularSpoofActivity.class.getName()
  };
}