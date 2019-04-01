package com.stockone.pod.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stockone.pod.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeRefreshRecyclerView extends RelativeLayout {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private RecyclerView.LayoutManager layoutManager;
    private boolean softEnable = false;
    private String text = null;
    private LinearLayoutManager linearLayoutManager;
    private int totalItemCount;
    private int lastVisibleItem;
    private OnBottomReached onBottomReached;
    private int page = 2;
    private boolean isLoading = false;
    private int visibleThreshold = 5;
    private boolean end = false;

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshRecyclerView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SwipeRefreshRecyclerView_rg_empty_text:
                    text = a.getString(attr);
                    break;
            }
        }
        a.recycle();
        init();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.custom_recycler_view, this);
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setEnabled(false);

        if(text != null){

        }
    }

    public void loadMore(){
        if(!end){
            isLoading = false;
        }
    }

    public void end(){
        end = true;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.setEnabled(true);
    }

    public void loading(){
        if(softEnable)
            swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
    }

    public void stop(){
        swipeRefreshLayout.setRefreshing(false);
        if(softEnable)
            swipeRefreshLayout.setEnabled(false);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        recyclerView.setLayoutManager(layoutManager);
    }


    public void setOnBottomReached(final OnBottomReached onBottomReached) {
        this.onBottomReached = onBottomReached;
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount <= (lastVisibleItem + visibleThreshold) && !isLoading) {
                    onBottomReached.bottomReached(page);
                    page++;
                    isLoading = true;
                }
            }
        });
    }

    public boolean isLoading(){
        return swipeRefreshLayout.isRefreshing();
    }

    public boolean isSoftEnable() {
        return softEnable;
    }

    public void setSoftEnable(boolean softEnable) {
        this.softEnable = softEnable;
    }

    public void resetScroll() {
        page = 1;
    }

    public interface OnBottomReached{
        void bottomReached(int page);
    }
}
