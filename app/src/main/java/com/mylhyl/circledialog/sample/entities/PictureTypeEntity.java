package com.mylhyl.circledialog.sample.entities;

/**
 * Created by hupei on 2017/3/31.
 */

public class PictureTypeEntity {
    public int id;
    public String typeName;

    public PictureTypeEntity() {
    }

    public PictureTypeEntity(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
