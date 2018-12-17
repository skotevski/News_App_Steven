package com.stevenkotevski.news_app_steven_test.Interface;

import android.view.View;

/**
 * The purpose of this class is to be able to load the news articles when clicked through the Web Browser
 * while using the RecyclerView
 */

public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
}
