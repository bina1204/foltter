
package com.gsbina.android.foltter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FolderActivity extends Fragment {

    public static final String TAB_ID = "tab_id";
    private int mNum;

    public FolderActivity() {
        super();
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt(TAB_ID) : 1;
    }

    protected int getNum() {
        return mNum;
    }

}
