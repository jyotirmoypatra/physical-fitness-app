package com.ashysystem.mbhq.listener;

/**
 * Created by AQB Solutions PVT. LTD. on 12/6/17.
 */

public interface OnItemUpdateListener {
    int TYPE_UPDATE = 1;
    int TYPE_REMOVE = 2;

    /**
     * @param o
     * @param type
     */
    void onItemUpdate(final Object o, final int type);
}
