package com.example.utilsgather.list_guide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GuideAdapter extends BaseAdapter {
    private GuideItemEntity[] datas;

    public GuideAdapter(GuideItemEntity[] datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            View mView = LayoutInflater.from(parent.getContext()).inflate(
                    android.R.layout.simple_list_item_1, parent, false);
            mViewHolder.mTextView = mView.findViewById(android.R.id.text1);
            mView.setTag(mViewHolder);
            convertView = mView;
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mTextView.setText(datas[position].getGuideTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas[position].getRunnable().run();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView mTextView;
    }
}
