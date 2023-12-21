package com.ashysystem.mbhq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by AQB Solutions PVT. LTD. on 17/2/17.
 */

/**
 * {@link TextView} that automatically adds bullet point to text if set in layout
 */
public class BulletTextView extends TextView {
    public BulletTextView(Context context) {
        super(context);
        overrideWithCustomFont();
    }

    public BulletTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        overrideWithCustomFont();
    }

    public BulletTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        overrideWithCustomFont();
    }

    private void overrideWithCustomFont() {
        //if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Regular.ttf");
            setTypeface(tf, 1);
            addBullet();
       // }
    }

    private void addBullet() {
        CharSequence text = getText();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        SpannableString spannable = new SpannableString(text);
        spannable.setSpan(new BulletSpan(16), 0, text.length(), 0);
        setText(spannable);
    }
}
