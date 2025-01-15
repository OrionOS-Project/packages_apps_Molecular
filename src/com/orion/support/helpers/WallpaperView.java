/*
 * Copyright (C) 2023-2024 The risingOS Android Project
                 2025 OrionOS
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

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.settings.R;

public class WallpaperView extends ImageView {

    private final Handler handler = new Handler();
    private Drawable currentWallpaperDrawable;
    private int foregroundColor;

    private final Runnable wallpaperChecker = new Runnable() {
        @Override
        public void run() {
            setWallpaperPreview();
            handler.postDelayed(this, 2000);
        }
    };

    public WallpaperView(Context context) {
        super(context);
        init();
    }

    public WallpaperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WallpaperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        foregroundColor = getContext().getResources().getColor(R.color.wallpaper_foreground_color, null);
        setWallpaperPreview();
        handler.postDelayed(wallpaperChecker, 2000);
    }

    protected void updateWallpaper() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        if (wallpaperDrawable != currentWallpaperDrawable) {
            currentWallpaperDrawable = wallpaperDrawable;
            if (wallpaperDrawable != null) {
                applyForegroundColor(wallpaperDrawable);
                setImageDrawable(wallpaperDrawable);
            }
        }
    }

    protected void setWallpaperPreview() {
        updateWallpaper();
    }

    /**
     * Set the foreground color into wallpaper.
     *
     * @param wallpaperDrawable Wallpaper drawable now.
     */
    private void applyForegroundColor(Drawable wallpaperDrawable) {
        if (foregroundColor != 0) {
            PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(
                foregroundColor, PorterDuff.Mode.SRC_OVER);
            wallpaperDrawable.setColorFilter(colorFilter);
        } else {
            wallpaperDrawable.clearColorFilter();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(wallpaperChecker);
    }
}
