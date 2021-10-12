package com.example.myrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StaggeredHomeAdapter extends RecyclerView.Adapter<StaggeredHomeAdapter.MyViewHolder> {

    private List<String> data;
    private LayoutInflater mInflater;

    private List<Integer> mHeights = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public StaggeredHomeAdapter(Context context, List<String> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        for (int i = 0; i < this.data.size(); ++i) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_recycler, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
        lp.height = mHeights.get(position);

        holder.tv.setLayoutParams(lp);
        holder.tv.setText(data.get(position));

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    removeData(pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void addData(int position) {
        data.add(position, "Insert one");
        mHeights.add((int) (100 + Math.random() * 300));
        notifyItemInserted(position);
    }

    private void removeData(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

}
