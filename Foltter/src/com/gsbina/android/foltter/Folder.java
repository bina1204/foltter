
package com.gsbina.android.foltter;

public class Folder {
    private int mFolderId;
    private String mFolderName;
    private int mUnreadCount;

    public int getFolderId() {
        return mFolderId;
    }

    public void setFolderId(int folderId) {
        mFolderId = folderId;
    }

    public String getFolderName() {
        return mFolderName;
    }

    public void setFolderName(String folderName) {
        mFolderName = folderName;
    }

    public int getUnreadCount() {
        return mUnreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        mUnreadCount = unreadCount;
    }

}
