package com.ar0ne.stoppiler.ui;

import android.content.Context;
import android.util.AttributeSet;

public class BrandsFontAwesomeTextView extends FontAwesomeTextView {

    public BrandsFontAwesomeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BrandsFontAwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrandsFontAwesomeTextView(Context context) {
        super(context);
    }

    @Override
    String getPath() {
        return "fontawesome_brands.ttf";
    }
}
