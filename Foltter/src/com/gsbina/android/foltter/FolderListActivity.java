
package com.gsbina.android.foltter;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.gsbina.android.view.FolderView;

public class FolderListActivity extends FragmentActivity {

    private TabHost mTabHost;
    public TabWidget mTabWidget;
    private TabManager mTabManager;

    private DatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mHelper = new DatabaseHelper(this);

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabWidget = mTabHost.getTabWidget();

        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);

        // TODO : データベースからフォルダ読み込み、作成
        int i = 1;
        createTab(i++, "仕事");
        createTab(i++, "友達");

        createOtherTab();

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }

    }

    private void createTab(int tabId, String title) {
        Bundle tabInfo = new Bundle();
        tabInfo.putInt(FolderActivity.TAB_ID, tabId);
        createTab(title, UserFolderActivity.class, tabInfo);
    }

    private void createOtherTab() {
        String title = "その他";
        Bundle tabInfo = new Bundle();
        tabInfo.putInt(FolderActivity.TAB_ID, 0);
        createTab(title, OtherFolderActivity.class, tabInfo);
    }

    @SuppressWarnings("rawtypes")
    private void createTab(String title, Class cls, Bundle tabInfo) {
        FolderTab folderTab = new FolderTab(this, title);

        mTabManager.addTab(mTabHost.newTabSpec(title).setIndicator(folderTab),
                cls, tabInfo);
    }

    private class FolderTab extends FrameLayout {
        private LayoutInflater inflater;

        public FolderTab(Context context) {
            super(context);
            inflater = LayoutInflater.from(context);
        }

        public FolderTab(Context context, String title) {
            this(context);

            View v = inflater.inflate(R.layout.folder_row, null);
            v.setMinimumHeight(76);

            // テキスト
            FolderView folder = (FolderView) v.findViewById(R.id.folder);
            folder.setText(title);

            addView(v);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

    /**
     * This is a helper class that implements a generic mechanism for
     * associating fragments with the tabs in a tab host. It relies on a trick.
     * Normally a tab host has a simple API for supplying a View or Intent that
     * each tab will show. This is not sufficient for switching between
     * fragments. So instead we make the content part of the tab host 0dp high
     * (it is not shown) and the TabManager supplies its own dummy view to show
     * as the tab content. It listens to changes in tabs, and takes care of
     * switch to the correct fragment shown in a separate content area whenever
     * the selected tab changes.
     */
    public static class TabManager implements TabHost.OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state. If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(String tabId) {
            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity,
                                newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastTab = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
        }
    }

}
