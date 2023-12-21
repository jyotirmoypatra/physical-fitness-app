package com.ashysystem.mbhq.listener;

/**
 * Created by AQB Solutions PVT. LTD. on 16/3/18.
 */

public interface OnToggleButtonListener {
    /**
     * On Show Complete Button Toggle
     *
     * @param show
     */
    void onCompleteToggle(Boolean show);

    /**
     * On No Measure Toggle
     *
     * @param show
     */
    void onNoMeasureToggle(Boolean show);
}