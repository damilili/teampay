package cn.kuwo.teampay.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kuwo.teampay.R;

/**
 * Created by cdm on 2018/10/25.
 */

public class CouponSelectDialog extends Dialog {


    private float thresholdvalue;
    private float lessvalue;

    private View.OnClickListener onSureClickListener;
    private RadioGroup rg_coupon_seclet;

    public CouponSelectDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public CouponSelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected CouponSelectDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public int getmCheckedId() {
        return mCheckedId;
    }

    public int mCheckedId;
    public void init() {
        setTitle("选择要添加的优惠券");
        setContentView(R.layout.dialog_select_coupon);
        rg_coupon_seclet = findViewById(R.id.rg_coupon_seclet);
        rg_coupon_seclet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mCheckedId = checkedId;
            }
        });

        findViewById(R.id.bt_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnSureClickListener(View.OnClickListener onSureClickListener) {
        this.onSureClickListener = onSureClickListener;
        findViewById(R.id.bt_sure).setOnClickListener(onSureClickListener);
    }

    public boolean isOnlyPointNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,1}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public float getThresholdvalue() {
        return thresholdvalue;
    }

    public float getLessvalue() {
        return lessvalue;
    }
}
