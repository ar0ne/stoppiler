package com.ar0ne.stoppiler.ui;

import android.content.Context;
import android.util.AttributeSet;

public class BrandsFontAwesome extends FontAwesome {

    public BrandsFontAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BrandsFontAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrandsFontAwesome(Context context) {
        super(context);
    }

    @Override
    String getPath() {
        return "fontawesome_brands.ttf";
    }
}
