package com.ashysystem.mbhq.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.ashysystem.mbhq.view.EditTextRaleWay;

/**
 * Created by android-krishnendu on 3/31/17.
 */

@SuppressLint("AppCompatCustomView")
public class ActionEditText extends EditTextRaleWay {

    public ActionEditText(Context context)
    {
        super(context);
    }

    public ActionEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ActionEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return conn;
    }

}
