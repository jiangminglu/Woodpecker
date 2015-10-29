package com.tuesda.walker.circlerefresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by luffy on 15/9/23.
 */
public class CListView extends RelativeLayout {
    private CircleRefreshLayout circleRefreshLayout;
    private CircleRefreshLayout.OnCircleLoadListener onCircleLoadListener;
    private ListView listView;
    private boolean isLoading;
    private View footView;

    public CListView(Context context) {
        super(context);
        init(context);
    }

    public CListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View rootView = inflate(context, R.layout.clistview_layout, this);
        this.circleRefreshLayout = (CircleRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        this.listView = (ListView) rootView.findViewById(R.id.clistview);
        this.listView.setOnScrollListener(onScrollListener);
        footView = inflate(context, R.layout.footer_layout, null);
    }

    public void setOnLoadListener(CircleRefreshLayout.OnCircleLoadListener loadListener) {
        this.onCircleLoadListener = loadListener;
    }

    public void setDividerHeight(int height){
        this.listView.setDividerHeight(height);
    }
    public void setDivider(Drawable divider){
        this.listView.setDivider(divider);
    }
    public void setBackground(Drawable drawable){
        this.listView.setBackground(drawable);
    }
    public void setOnRefreshListener(CircleRefreshLayout.OnCircleRefreshListener listener) {
        this.circleRefreshLayout.setOnRefreshListener(listener);
    }

    public void addHeadView(View view) {
        this.listView.addHeaderView(view);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.listView.setAdapter(adapter);
    }

    public void finishRefreshing() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                circleRefreshLayout.finishRefreshing();
            }
        }, 2 * 1000);
    }

    public void finishLoading() {
        this.isLoading = false;
        this.onCircleLoadListener.completeLoad();
        this.listView.removeFooterView(footView);
    }

    private boolean isLastRow = false;
    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            //正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
            //回调顺序如下
            //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
            //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
            //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
            //当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；
            //由于用户的操作，屏幕产生惯性滑动时为2

            //当滚到最后一行且停止滚动时，执行加载
            if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                //加载元素
                if (onCircleLoadListener != null && isLoading == false) {
                    isLoading = true;
                    listView.addFooterView(footView);
                    onCircleLoadListener.loading();
                    isLastRow = false;
                }
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
            //firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
            //visibleItemCount：当前能看见的列表项个数（小半个也算）
            //totalItemCount：列表项共数

            //判断是否滚到最后一行
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                isLastRow = true;
            }
        }
    };

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.listView.setOnItemClickListener(onItemClickListener);
    }
}
