package com.sk.woodpecker.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sk.woodpecker.R;
import com.sk.woodpecker.bean.Service;

public class ServiceItemLayoutAdapter extends BaseAdapter {

    private List<Service> objects = new ArrayList<Service>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ServiceItemLayoutAdapter(Context context,ArrayList<Service> list) {
        this.context = context;
        this.objects = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Service getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.service_item_layout, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.serviceItemContentTv = (TextView) convertView.findViewById(R.id.service_item_content_tv);
            viewHolder.serviceItemTagTv = (TextView) convertView.findViewById(R.id.service_item_tag_tv);
            viewHolder.serviceItemStatusTv = (TextView) convertView.findViewById(R.id.service_item_status_tv);

            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Service object, ViewHolder holder) {
        //TODO implement
    }

    protected class ViewHolder {
        private TextView serviceItemContentTv;
        private TextView serviceItemTagTv;
        private TextView serviceItemStatusTv;
    }
}
