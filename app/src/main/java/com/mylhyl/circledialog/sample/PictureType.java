package com.mylhyl.circledialog.sample;

/**
 * Created by hupei on 2017/3/31.
 */

public class PictureType {
    public int id;
    public String typeName;

    public PictureType() {
    }

    public PictureType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
