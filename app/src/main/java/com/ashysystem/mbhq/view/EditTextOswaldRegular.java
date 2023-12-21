package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by android-krishnendu on 20/3/18.
 */

public class EditTextOswaldRegular extends EditText {

    public EditTextOswaldRegular(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public EditTextOswaldRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        overrideWithCustomFont();
    }

    public EditTextOswaldRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Oswald-Regular.ttf");
            setTypeface(tf);
        }
    }

}
