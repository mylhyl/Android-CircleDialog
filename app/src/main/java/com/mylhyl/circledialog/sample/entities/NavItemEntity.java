package com.mylhyl.circledialog.sample.entities;

import com.mylhyl.circledialog.callback.CircleItemLabel;

/**
 * Created by hupei on 2018/8/24 13:46.
 */
public class NavItemEntity implements CircleItemLabel {
    private String name;
    private int textResId;

    public NavItemEntity() {
    }

    public NavItemEntity(String name, int textResId) {
        this.name = name;
        this.textResId = textResId;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTextResId() {
        return textResId;
    }

    public void setTextResId(int textResId) {
        this.textResId = textResId;
    }

    @Override
    public String getItemLabel() {
        return getName();
    }
}
