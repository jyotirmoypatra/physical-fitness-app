package com.ashysystem.mbhq.listener;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


/**
 * Created by root on 22/6/17.
 */

public interface OnLoadFragmentRequestListener {
    void onLoadFragmentRequest(Fragment fragment, String title, Bundle bundle);
}
