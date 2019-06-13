package com.mylhyl.circledialog.sample;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylhyl.circledialog.sample.entities.PictureTypeEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hupei on 2019/5/7 16:28.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.Holder> {

    private Context mContext;
    private List<PictureTypeEntity> mListData;
    private LayoutInflater mLayoutInflater;

    public RvAdapter(Context context, List<PictureTypeEntity> listData) {
        this.mContext = context;
        this.mListData = listData;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        Holder holder = new Holder(textView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.textView.setText(mListData.get(position).typeName);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void addData(PictureTypeEntity entity) {
        mListData.add(entity);

//        notifyItemInserted(mListData.size());
//        final int dataSize = mListData == null ? 0 : mListData.size();
//        if (dataSize == 1) {
//            notifyDataSetChanged();
//        }
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView textView;

        public Holder(TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }
}
