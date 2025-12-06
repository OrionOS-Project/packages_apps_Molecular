/*
 * Copyright (C) 2025 OrionOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.orion.support.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.TextView;
import android.content.res.TypedArray;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class OrionRandomText extends TextView implements Runnable {

    static final ArrayList<String> randomText = new ArrayList<>();

    static {
        randomText.add("Welcome Back to Molecular.");
        randomText.add("Bonding complete.");
        randomText.add("Stable structure detected.");
        randomText.add("Let’s react!");
        randomText.add("Energy levels rising...");
        randomText.add("Assemble your atoms.");
        randomText.add("Fusion initialized.");
        randomText.add("Searching for new bonds?");
        randomText.add("Maintaining stability...");
        randomText.add("Optimize your structure.");
    }

    private final Handler handler;
    private final boolean runnable;

    public OrionRandomText(Context context, AttributeSet attrs) {
        super(context, attrs);

        handler = new Handler(Looper.getMainLooper());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OrionRandomText);
        runnable = a.getBoolean(R.styleable.OrionRandomText_orion_runnable, true);
        a.recycle();

        if (runnable) {
            run();
        } else {
            setRandomText();
        }
    }

    private void setRandomText() {
        int index = ThreadLocalRandom.current().nextInt(randomText.size());
        setText(randomText.get(index));
    }

    @Override
    public void run() {
        setRandomText();
        handler.postDelayed(this, 5000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(this);
    }
}
