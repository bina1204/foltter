
package com.gsbina.android.foltter;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class OtherFolderActivity extends FolderActivity {
    
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        Intent service = new Intent(activity, UserService.class);
        activity.startService(service);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_folder_list, container, false);
        GridView gridView = (GridView) v.findViewById(R.id.user_folder_grid);
        gridView.setAdapter(getFolderAdapter());
        return v;
    }

    private UserGridAdapter getFolderAdapter() {
        List<String> folderList = new ArrayList<String>();
        // TODO ñ¢ï™óﬁÇÃÉÜÅ[ÉUí«â¡èàóù
        folderList.add("toshi_a");
        folderList.add("mrshiromi");
        folderList.add("penguin2716");
        folderList.add("8796n");
        folderList.add("tenjyo");
        folderList.add("tan_go238");
        UserGridAdapter adapter = new UserGridAdapter(getActivity(), R.layout.user_row, folderList);
        return adapter;
    }
}
