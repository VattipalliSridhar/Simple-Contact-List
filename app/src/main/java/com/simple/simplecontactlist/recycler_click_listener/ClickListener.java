package com.simple.simplecontactlist.recycler_click_listener;

import android.view.View;

/**
 * Created by sridhar on 13/11/2018.
 */

public interface ClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
