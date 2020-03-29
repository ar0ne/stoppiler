package com.ar0ne.stoppiler.ui;

import android.content.Context;
import android.util.AttributeSet;

public class RegularFontAwesomeTextView extends FontAwesomeTextView {

    public RegularFontAwesomeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RegularFontAwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegularFontAwesomeTextView(Context context) {
        super(context);
    }

    @Override
    String getPath() {
        return "fontawesome_regular.ttf";
    }
}