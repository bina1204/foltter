
package com.gsbina.android.view;

import java.util.ArrayList;

import android.app.TabActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.TabWidget;

public class ScrollTabActivity extends TabActivity {

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        final int FP = ViewGroup.LayoutParams.FILL_PARENT;
        final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

        TabWidget widget = getTabWidget();
        ViewParent widgetParent = widget.getParent();
        if (widgetParent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) widgetParent;

            HorizontalScrollView scrollView = new HorizontalScrollView(group.getContext());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(FP, WC);
            scrollView.setLayoutParams(lp);

            group.removeView(widget);
            scrollView.addView(widget);

            ArrayList<View> viewList = new ArrayList<View>();
            for (int i = 0; i < group.getChildCount(); i++) {
                viewList.add(group.getChildAt(i));
            }
            group.removeAllViews();

            group.addView(scrollView);

            for (View view : viewList) {
                group.addView(view);
            }
        }
    }
}
