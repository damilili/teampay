package cn.kuwo.teampay.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kuwo.teampay.R;
import cn.kuwo.teampay.module.bean.PayBean;
import cn.kuwo.teampay.presenter.AbstractCouponComputer;
import cn.kuwo.teampay.presenter.coupon.FullReductionCouponComputer;

/**
 * Created by cdm on 2018/10/23.
 */

public class PayAddAdapter extends RecyclerView.Adapter {
    //付款品种集合
    ArrayList<PayBean> payBeans = new ArrayList<>();

    public void setCoupons(ArrayList<AbstractCouponComputer> coupons) {
        if (coupons == null) {
            return;
        }
        this.coupons = coupons;
    }

    //优惠券集合
    ArrayList<AbstractCouponComputer> coupons = new ArrayList<>();
    //使用的优惠券集合
    ArrayList<AbstractCouponComputer> couponsUsed = new ArrayList<>();
    private final static int ViewType_0 = 0;
    private final static int ViewType_1 = 1;
    private final static int ViewType_2 = 2;
    private final static int ViewType_3 = 3;
    private float sumPay = 0;
    private float sumPayReal = 0;
    private DecimalFormat df = new DecimalFormat("#.0");


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case ViewType_0:
                itemView = View.inflate(parent.getContext(), R.layout.item_button, null);
                return new ButtonHolder(itemView);
            case ViewType_1:
                itemView = View.inflate(parent.getContext(), R.layout.item_sum, null);
                return new SumInfoHolder(itemView);
            case ViewType_2:
                itemView = View.inflate(parent.getContext(), R.layout.item_pay, null);
                return new PayHolder(itemView);
            case ViewType_3:
                itemView = View.inflate(parent.getContext(), R.layout.item_coupon, null);
                return new CouponHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ViewType_0:
                ButtonHolder buttonHolder = holder instanceof ButtonHolder ? ((ButtonHolder) holder) : null;
                buttonHolder.bt_add_pay.setText("添加付款");
                break;
            case ViewType_1:
                SumInfoHolder sumInfoHolder = holder instanceof SumInfoHolder ? ((SumInfoHolder) holder) : null;
                sumInfoHolder.tv_sum.setText(String.format("总价：%s 元", df.format(sumPay)));
                sumInfoHolder.tv_sum_preferential.setText(String.format("优惠价：%s 元", df.format(sumPayReal)));
                break;
            case ViewType_2:
                PayHolder payHolder = holder instanceof PayHolder ? ((PayHolder) holder) : null;
                payHolder.setData(payBeans.get(position));
                break;
            case ViewType_3:
                CouponHolder couponHolder = holder instanceof CouponHolder ? ((CouponHolder) holder) : null;
                couponHolder.setData(coupons.get(position - payBeans.size()));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ViewType_0;//最下方按钮
        } else if (position == getItemCount() - 2) {
            return ViewType_1;//总览信息
        } else if (payBeans.size() > 0 && position < payBeans.size()) {
            return ViewType_2;//普通付款条目
        }
        return ViewType_3;//普通付款条目
    }

    @Override
    public int getItemCount() {
        return payBeans.size() + coupons.size() + 2;
    }

    public int getSpanSize(int position) {
        switch (getItemViewType(position)) {
            case ViewType_0:
                return 2;
            case ViewType_1:
                return 2;
            case ViewType_2:
                return 2;
            default:
                return 1;
        }
    }

    class PayHolder extends RecyclerView.ViewHolder {

        private View.OnClickListener onClickListener;
        private View bt_pay_del;
        private EditText tv_pay_price;
        private EditText tv_pay_count;
        private PayBean mPayBean;

        public PayHolder(View itemView) {
            super(itemView);
            bt_pay_del = itemView.findViewById(R.id.bt_pay_del);
            tv_pay_count = itemView.findViewById(R.id.tv_pay_count);
            tv_pay_price = itemView.findViewById(R.id.tv_pay_price);
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = payBeans.indexOf(mPayBean);
                    if (index == -1) {
                        //防止两次连续点击，因为有删除动画，容易出现连击的问题
                        return;
                    }
                    payBeans.remove(index);
                    notifyItemRemoved(index);
                    refreshSum();
                }
            };
            bt_pay_del.setOnClickListener(onClickListener);
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int selectionStart = tv_pay_price.getSelectionStart();
                    int selectionEnd = tv_pay_price.getSelectionEnd();
                    if (!isOnlyPointNumber(s.toString()) && s.length() > 0) {
                        //删除多余输入的字（不会显示出来）
                        s.delete(selectionStart - 1, selectionEnd);
                        tv_pay_price.setText(s);
                        tv_pay_price.setSelection(s.length());
                    }
                    float price = TextUtils.isEmpty(s.toString()) ? 0 : Float.valueOf(s.toString());
                    if (mPayBean.getPrice() == price) {
                        return;
                    }
                    mPayBean.setPrice(price);
                    refreshSum();
                }
            };
            tv_pay_price.addTextChangedListener(watcher);
            TextWatcher countWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int count = TextUtils.isEmpty(s.toString()) ? 1 : Integer.valueOf(s.toString());
                    if (count == mPayBean.getCount()) {
                        return;
                    }
                    mPayBean.setCount(count);
                    refreshSum();
                }
            };
            tv_pay_count.addTextChangedListener(countWatcher);
        }

        public boolean isOnlyPointNumber(String number) {
            Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,1}$");
            Matcher matcher = pattern.matcher(number);
            return matcher.matches();
        }

        public void setData(PayBean payBean) {
            mPayBean = payBean;
            tv_pay_price.setText(mPayBean.getPrice() + "");
            tv_pay_count.setText(mPayBean.getCount() + "");
            bt_pay_del.setTag(mPayBean);
        }
    }

    private void refreshSum() {
        sumPay = 0;
        for (PayBean payBean : payBeans) {
            sumPay += payBean.getPrice() * payBean.getCount();
        }
        float result = sumPay;
        sumPayReal = sumPay;
        AbstractCouponComputer couponMax = null;
        for (AbstractCouponComputer coupon : coupons) {
            if (coupon.computeResult(sumPay) < result) {
                result = coupon.computeResult(sumPay);
                couponMax = coupon;
            }
        }
        if (couponMax != null) {
            sumPayReal = result;
            Iterator<AbstractCouponComputer> iterator = couponsUsed.iterator();
            while (iterator.hasNext()) {
                AbstractCouponComputer next = iterator.next();
                iterator.remove();
                notifyItemChanged(coupons.indexOf(next) + payBeans.size());
            }
            couponsUsed.add(couponMax);
            notifyItemChanged(coupons.indexOf(couponMax) + payBeans.size());
        } else {
            Iterator<AbstractCouponComputer> iterator = couponsUsed.iterator();
            while (iterator.hasNext()) {
                AbstractCouponComputer next = iterator.next();
                iterator.remove();
                notifyItemChanged(coupons.indexOf(next) + payBeans.size());
            }
        }
        notifyItemChanged(getItemCount() - 2);
    }

    class SumInfoHolder extends RecyclerView.ViewHolder {
        TextView tv_sum;
        TextView tv_sum_preferential;

        public SumInfoHolder(View itemView) {
            super(itemView);
            tv_sum = itemView.findViewById(R.id.tv_sum);
            tv_sum_preferential = itemView.findViewById(R.id.tv_sum_preferential);
        }
    }

    class CouponHolder extends RecyclerView.ViewHolder {
        TextView tv_coupon;
        TextView tv_coupon_desciption;
        Button bt_coupon_del;
        View rl_coupon_base;
        private AbstractCouponComputer coupon;

        public CouponHolder(View itemView) {
            super(itemView);
            tv_coupon = itemView.findViewById(R.id.tv_coupon);
            tv_coupon_desciption = itemView.findViewById(R.id.tv_coupon_desciption);
            bt_coupon_del = itemView.findViewById(R.id.bt_coupon_del);
            rl_coupon_base = itemView.findViewById(R.id.rl_coupon_base);
            bt_coupon_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = coupons.indexOf(coupon);
                    if (index == -1) {
                        //防止两次连续点击，因为有删除动画，容易出现连击的问题
                        return;
                    }
                    coupons.remove(index);
                    notifyItemRemoved(payBeans.size() + index);
                    refreshSum();
                }
            });
        }

        public void setData(AbstractCouponComputer abstractCouponComputer) {
            this.coupon = abstractCouponComputer;
            tv_coupon.setText(coupon.couponName);
            tv_coupon_desciption.setText(coupon.couponDes);
            rl_coupon_base.setBackgroundColor(couponsUsed.contains(coupon) ? Color.GRAY : Color.WHITE);
        }
    }

    class ButtonHolder extends RecyclerView.ViewHolder {
        private View.OnClickListener onClickListener;
        Button bt_add_pay;
        Button bt_add_coupon;

        public ButtonHolder(View itemView) {
            super(itemView);
            bt_add_pay = itemView.findViewById(R.id.bt_add_pay);
            bt_add_coupon = itemView.findViewById(R.id.bt_add_coupon);
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.bt_add_coupon:
                            FullReductionCouponComputer fullReductionCouponComputer = new FullReductionCouponComputer();
                            coupons.add(fullReductionCouponComputer);
                            notifyItemInserted(payBeans.size() + coupons.size() - 1);
                            break;
                        case R.id.bt_add_pay:
                            PayBean payBean = new PayBean();
                            payBeans.add(payBean);
                            notifyItemInserted(payBeans.size() - 1);
                            break;
                    }
                }
            };
            bt_add_pay.setOnClickListener(onClickListener);
            bt_add_coupon.setOnClickListener(onClickListener);
        }
    }
}
