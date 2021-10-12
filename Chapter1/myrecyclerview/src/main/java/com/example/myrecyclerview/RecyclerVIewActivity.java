package com.example.myrecyclerview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerVIewActivity extends AppCompatActivity {

    private List<String> mList = new ArrayList<>();

    private RecyclerView mRecyclerView;

    private HomeAdapter mHomeAdapter;

    private StaggeredHomeAdapter mStaggeredHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 1; i < 20; i++)
        {
            mList.add(i + "");
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.id_recyclerview);
        //setListView();
        //setGridView();
        setWaterfallView();
    }

    private void setListView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //LinearLayoutManager默认为垂直分布
        mRecyclerView.setLayoutManager(manager);
        //设置水平分布
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(RecyclerVIewActivity.this, DividerItemDecoration.VERTICAL_LIST));
        //设置增加和删除时的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeAdapter(this, mList);
        mRecyclerView.setAdapter(mHomeAdapter);
        setLister();
    }

    private void setGridView() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mHomeAdapter = new HomeAdapter(this, mList);
        setLister();
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    public void setWaterfallView(){
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mStaggeredHomeAdapter = new StaggeredHomeAdapter(this, mList);
        mRecyclerView.setAdapter(mStaggeredHomeAdapter);
    }

    private void setLister() {
        mHomeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerVIewActivity.this, "点击第" + (position + 1) + "条", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                new AlertDialog.Builder(RecyclerVIewActivity.this)
                        .setTitle("确认删除吗?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mHomeAdapter.removeData(position);
                            }
                        }).show();
            }
        });
    }
}