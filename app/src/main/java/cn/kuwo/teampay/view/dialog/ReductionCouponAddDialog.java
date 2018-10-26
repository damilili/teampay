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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kuwo.teampay.R;

/**
 * Created by cdm on 2018/10/25.
 */

public class ReductionCouponAddDialog extends Dialog {


    private float thresholdvalue;
    private float lessvalue;

    private View.OnClickListener onSureClickListener;
    private EditText et_thresholdvalue;
    private EditText et_lessvalue;

    public ReductionCouponAddDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ReductionCouponAddDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ReductionCouponAddDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public void init() {
        setTitle("添加满减券");
        setContentView(R.layout.dialog_add_coupon);
        et_thresholdvalue = findViewById(R.id.et_thresholdvalue);
        et_lessvalue = findViewById(R.id.et_lessvalue);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int selectionStart = et_thresholdvalue.getSelectionStart();
                int selectionEnd = et_thresholdvalue.getSelectionEnd();
                if (!isOnlyPointNumber(s.toString()) && s.length() > 0) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    et_thresholdvalue.setText(s);
                    et_thresholdvalue.setSelection(s.length());
                }
                thresholdvalue = TextUtils.isEmpty(s.toString()) ? 0 : Float.valueOf(s.toString());

            }
        };
        et_thresholdvalue.addTextChangedListener(watcher);

        TextWatcher lessvalueWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int selectionStart = et_lessvalue.getSelectionStart();
                int selectionEnd = et_lessvalue.getSelectionEnd();
                if (!isOnlyPointNumber(s.toString()) && s.length() > 0) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    et_lessvalue.setText(s);
                    et_lessvalue.setSelection(s.length());
                }
                lessvalue = TextUtils.isEmpty(s.toString()) ? 0 : Float.valueOf(s.toString());
            }
        };
        et_lessvalue.addTextChangedListener(lessvalueWatcher);

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
