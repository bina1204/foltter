
package com.gsbina.android.foltter;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class FolderView extends TextView {

    private Folder mFolder;

    public FolderView(Context context) {
        super(context);
    }

    public FolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FolderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setFolder(Folder folder) {
        mFolder = folder;
    }

    public Folder getFolder() {
        return mFolder;
    }
}
