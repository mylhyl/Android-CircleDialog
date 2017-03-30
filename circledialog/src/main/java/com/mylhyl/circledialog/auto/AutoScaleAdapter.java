package com.mylhyl.circledialog.auto;

import android.content.Context;

/**
 * Created by hupei on 2016/3/8 17:33.
 */
class AutoScaleAdapter {

    private Context mContext;

    public AutoScaleAdapter(Context context) {
        mContext = context;
    }

    public float adapt(float scale, int screenWidth, int screenHeight) {
        if (screenWidth < 720 || screenHeight < 720) {//针对小屏（小分辨率）设备做调整
            if (screenWidth <= 480 || screenHeight <= 480) {//普通480设备
                scale *= 1.2f;
            } else {
                if (AutoUtils.getDevicePhysicalSize(mContext) < 4.0) {//小屏手机，较高分辨率（如 mx）
                    scale *= 1.3f;
                } else {//华为U9200
                    scale *= 1.05f;
                }
            }
        }
        return scale;
    }
}
