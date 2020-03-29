package com.ar0ne.stoppiler.ui;

import android.content.Context;
import android.util.AttributeSet;

public class SolidFontAwesomeTextView extends FontAwesomeTextView {

    public SolidFontAwesomeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SolidFontAwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SolidFontAwesomeTextView(Context context) {
        super(context);
    }

    @Override
    String getPath() {
        return "fontawesome_solid.ttf";
    }
}