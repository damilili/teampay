package cn.kuwo.teampay.presenter;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by cdm on 2018/10/24.
 * 优惠券基类
 */

public abstract class AbstractCouponComputer implements Parcelable, Serializable {
    public String couponName;
    public String couponDes;

    public abstract float computeResult(float orgValue);
}
