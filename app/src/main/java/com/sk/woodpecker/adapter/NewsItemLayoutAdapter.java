package com.sk.woodpecker.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sk.woodpecker.R;
import com.sk.woodpecker.bean.News;

public class NewsItemLayoutAdapter extends BaseAdapter {

    private List<News> objects = new ArrayList<News>();

    private Context context;
    private LayoutInflater layoutInflater;

    public NewsItemLayoutAdapter(Context context, ArrayList<News> list) {
        this.context = context;
        this.objects = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public News getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.news_item_layout, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.newsItemImg = (ImageView) convertView.findViewById(R.id.news_item_img);
            viewHolder.newsItemNameTv = (TextView) convertView.findViewById(R.id.news_item_name_tv);
            viewHolder.newsItemContentTv = (TextView) convertView.findViewById(R.id.news_item_content_tv);
            viewHolder.newsItemFavImg = (ImageView) convertView.findViewById(R.id.news_item_fav_img);
            viewHolder.newsItemFavNumTv = (TextView) convertView.findViewById(R.id.news_item_fav_num_tv);
            viewHolder.newsItemCommentImg = (ImageView) convertView.findViewById(R.id.news_item_comment_img);
            viewHolder.newsItemCommentNumTv = (TextView) convertView.findViewById(R.id.news_item_comment_num_tv);

            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(News object, ViewHolder holder) {
        //TODO implement
    }

    protected class ViewHolder {
        private ImageView newsItemImg;
        private TextView newsItemNameTv;
        private TextView newsItemContentTv;
        private ImageView newsItemFavImg;
        private TextView newsItemFavNumTv;
        private ImageView newsItemCommentImg;
        private TextView newsItemCommentNumTv;
    }
}
