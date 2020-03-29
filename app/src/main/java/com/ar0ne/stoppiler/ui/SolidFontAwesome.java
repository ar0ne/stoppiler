package com.ar0ne.stoppiler.ui;

import android.content.Context;
import android.util.AttributeSet;

public class SolidFontAwesome extends FontAwesome {

    public SolidFontAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SolidFontAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SolidFontAwesome(Context context) {
        super(context);
    }

    @Override
    String getPath() {
        return "fontawesome_solid.ttf";
    }
}