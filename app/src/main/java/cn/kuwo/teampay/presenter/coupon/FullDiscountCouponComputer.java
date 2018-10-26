package cn.kuwo.teampay.presenter.coupon;

import android.os.Parcel;

import cn.kuwo.teampay.presenter.AbstractCouponComputer;


/**
 * Created by cdm on 2018/10/25.
 * 满折扣券
 */

public class FullDiscountCouponComputer extends AbstractCouponComputer {

    float mThresholdValue = 0;

    public float getmDiscountValue() {
        return mDiscountValue;
    }

    public void setmDiscountValue(float mDiscountValue) {
        this.mDiscountValue = mDiscountValue;
    }

    float mDiscountValue = 10f;

    public FullDiscountCouponComputer() {
        couponName = "折扣券";
    }

    public float getmThresholdValue() {
        return mThresholdValue;
    }

    public void setmThresholdValue(float mThresholdValue) {
        this.mThresholdValue = mThresholdValue;
    }
    @Override
    public float computeResult(float orgValue) {
        if (orgValue < mThresholdValue) {
            return orgValue;
        }
        return orgValue * mDiscountValue / 10;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.mThresholdValue);
        dest.writeFloat(this.mDiscountValue);
        dest.writeString(this.couponName);
        dest.writeString(this.couponDes);
    }

    protected FullDiscountCouponComputer(Parcel in) {
        this.mThresholdValue = in.readFloat();
        this.mDiscountValue = in.readFloat();
        this.couponName = in.readString();
        this.couponDes = in.readString();
    }

    public static final Creator<FullDiscountCouponComputer> CREATOR = new Creator<FullDiscountCouponComputer>() {
        @Override
        public FullDiscountCouponComputer createFromParcel(Parcel source) {
            return new FullDiscountCouponComputer(source);
        }

        @Override
        public FullDiscountCouponComputer[] newArray(int size) {
            return new FullDiscountCouponComputer[size];
        }
    };
}
