
package com.gsbina.android.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gsbina.android.foltter.R;

public class UserGridAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;
    private int mLayoutId;

    public UserGridAdapter(Context context, int layoutId,
            List<String> folderList) {
        super(context, layoutId, folderList);
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // ÉrÉÖÅ[ÇéÛÇØéÊÇÈ
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(mLayoutId, null);
            TextView folder = (TextView) view.findViewById(R.id.user);
            folder.setText(getItem(position));

            holder = new ViewHolder();
            holder.folder = folder;

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder {
        TextView folder;
    }

}
