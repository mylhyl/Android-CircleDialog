package com.mylhyl.circledialog.sample.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

/**
 * Created by hupei on 2018/4/12.
 */

public class CheckedAdapter extends ArrayAdapter<String> {
    private SparseArray<String> saveChecked = new SparseArray<>();

    public CheckedAdapter(@NonNull Context context, String[] objects) {
        super(context, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(android.R.id.text1);
        checkedTextView.setChecked(saveChecked.get(position) != null);
        return view;
    }

    public void toggle(int key, java.lang.String value) {
        if (saveChecked.get(key) == null)
            saveChecked.put(key, value);
        else
            saveChecked.remove(key);
        notifyDataSetChanged();
    }

    public SparseArray<String> getSaveChecked() {
        return saveChecked;
    }
}
