package com.vincent.blurdialog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vincent.blurdialog.R;
import com.vincent.blurdialog.helper.BlurDialogHelper;
import com.vincent.blurdialog.listener.OnItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2017/11/22.
 */

public class SingleSelectAdapter extends RecyclerView.Adapter<SingleSelectAdapter.SingleViewHolder> {
    private List<CharSequence> mList;
    private float mRadius;
    private OnItemClick itemClick;

    public SingleSelectAdapter(List<CharSequence> list, float radius, OnItemClick itemClick) {
        if (list == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = list;
        }
        this.mRadius = radius;
        this.itemClick = itemClick;
    }

    @Override
    public SingleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vw_dialog_single_item, parent, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SingleViewHolder holder, int position) {
        Context ctx = holder.itemView.getContext();
        float[] radius;
        if (position == 0) {
            radius = new float[]{
                    mRadius, mRadius, //左上角
                    mRadius, mRadius, //右上角
                    0, 0, //右下角
                    0, 0 //左下角
            };
            holder.vw_dialog_item_divider.setVisibility(View.VISIBLE);
        } else if (position == mList.size() - 1) {
            radius = new float[]{
                    0, 0, //左上角
                    0, 0, //右上角
                    mRadius, mRadius, //右下角
                    mRadius, mRadius //左下角
            };
            holder.vw_dialog_item_divider.setVisibility(View.INVISIBLE);
        } else {
            radius = new float[]{
                    0, 0, //左上角
                    0, 0, //右上角
                    0, 0, //右下角
                    0, 0 //左下角
            };
            holder.vw_dialog_item_divider.setVisibility(View.VISIBLE);
        }

        BlurDialogHelper.setBackGroundDrawable(holder.itemView.getContext(), holder.itemView, radius);
        holder.vw_dialog_tv_item.setText(mList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick != null) {
                    itemClick.onClick(mList.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class SingleViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        TextView vw_dialog_tv_item;
        @NonNull
        View vw_dialog_item_divider;

        public SingleViewHolder(View itemView) {
            super(itemView);
            vw_dialog_tv_item = itemView.findViewById(R.id.vw_dialog_tv_item);
            vw_dialog_item_divider = itemView.findViewById(R.id.vw_dialog_item_divider);
        }
    }
}
