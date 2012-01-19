
package com.gsbina.android.foltter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class UserFolderActivity extends FolderActivity implements
        OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_folder_list, container, false);
        GridView gridView = (GridView) v.findViewById(R.id.user_folder_grid);
        gridView.setAdapter(getFolderAdapter());
        gridView.setOnItemClickListener(this);
        return v;
    }

    private UserGridAdapter getFolderAdapter() {
        List<String> folderList = new ArrayList<String>();
        // TODO フォルダのユーザ追加処理
        folderList.add("toshi_a");
        folderList.add("mrshiromi");
        folderList.add("penguin2716");
        folderList.add("8796n");
        folderList.add("tenjyo");
        folderList.add("tan_go238");
        UserGridAdapter adapter = new UserGridAdapter(getActivity(),
                R.layout.user_row, folderList);
        return adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        String userName = (String) parent.getAdapter().getItem(position);
        Toast.makeText(getActivity(), userName, Toast.LENGTH_SHORT).show();
    }

}
