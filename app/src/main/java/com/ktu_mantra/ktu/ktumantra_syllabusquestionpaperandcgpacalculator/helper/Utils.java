package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;


public class Utils {

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        NotificationBadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof NotificationBadgeDrawable) {
            badge = (NotificationBadgeDrawable) reuse;
        } else {
            badge = new NotificationBadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
}