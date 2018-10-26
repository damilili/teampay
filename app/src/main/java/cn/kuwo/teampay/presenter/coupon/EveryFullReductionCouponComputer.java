package cn.kuwo.teampay.presenter.coupon;

import android.os.Parcel;

import cn.kuwo.teampay.presenter.AbstractCouponComputer;


/**
 * Created by cdm on 2018/10/25.
 * 每满减定额券
 */

public class EveryFullReductionCouponComputer extends AbstractCouponComputer {

    float mThresholdValue = 0;
    float mLessValue = 0;

    public EveryFullReductionCouponComputer() {
        couponName = "每满减定额券";
    }

    public float getmThresholdValue() {
        return mThresholdValue;
    }

    public void setmThresholdValue(float mThresholdValue) {
        this.mThresholdValue = mThresholdValue;
    }

    public float getmLessValue() {
        return mLessValue;
    }

    public void setmLessValue(float mLessValue) {
        this.mLessValue = mLessValue;
    }

    @Override
    public float computeResult(float orgValue) {
        if (orgValue < mThresholdValue) {
            return orgValue;
        }
        if (orgValue < mLessValue) {
            throw new ArithmeticException("优惠力度不能大于价格阈值");
        }
        if (mThresholdValue != 0) {
            mLessValue = (orgValue % mThresholdValue) * mLessValue;
        }
        return orgValue - mLessValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.mThresholdValue);
        dest.writeFloat(this.mLessValue);
        dest.writeString(this.couponName);
        dest.writeString(this.couponDes);
    }

    protected EveryFullReductionCouponComputer(Parcel in) {
        this.mThresholdValue = in.readFloat();
        this.mLessValue = in.readFloat();
        this.couponName = in.readString();
        this.couponDes = in.readString();
    }

    public static final Creator<EveryFullReductionCouponComputer> CREATOR = new Creator<EveryFullReductionCouponComputer>() {
        @Override
        public EveryFullReductionCouponComputer createFromParcel(Parcel source) {
            return new EveryFullReductionCouponComputer(source);
        }

        @Override
        public EveryFullReductionCouponComputer[] newArray(int size) {
            return new EveryFullReductionCouponComputer[size];
        }
    };
}
