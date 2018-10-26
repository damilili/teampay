package cn.kuwo.teampay.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kuwo.teampay.R;
import cn.kuwo.teampay.presenter.AbstractCouponComputer;

/**
 * Created by cdm on 2018/10/23.
 */

public class CouponAddAdapter extends RecyclerView.Adapter {


    //优惠券集合
    ArrayList<AbstractCouponComputer> coupons = new ArrayList<>();
    private final static int ViewType_0 = 0;
    private final static int ViewType_1 = 1;

    private View.OnClickListener onClickListener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case ViewType_0:
                itemView = View.inflate(parent.getContext(), R.layout.item_button, null);
                return new ButtonHolder(itemView);
            case ViewType_1:
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
                break;
            case ViewType_1:
                CouponHolder couponHolder = holder instanceof CouponHolder ? ((CouponHolder) holder) : null;
                couponHolder.setData(coupons.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ViewType_0;//最下方按钮
        }
        return ViewType_1;//优惠券卡片
    }

    @Override
    public int getItemCount() {
        return coupons.size() + 1;
    }

    public int getSpanSize(int position) {
        switch (getItemViewType(position)) {
            case ViewType_0:
                return 2;
            case ViewType_1:
                return 1;
            default:
                return 1;
        }
    }

    public void addCoupon(AbstractCouponComputer abstractCouponComputer) {
        coupons.add(abstractCouponComputer);
        notifyItemInserted(coupons.size() - 1);
    }

    public void removeCoupon(AbstractCouponComputer abstractCouponComputer) {
        int index = coupons.indexOf(abstractCouponComputer);
        if (index == -1) {
            //防止两次连续点击，因为有删除动画，容易出现连击的问题
            return;
        }
        coupons.remove(index);
        notifyItemRemoved(index);
    }
    public ArrayList<AbstractCouponComputer> getCoupons() {
        return coupons;
    }

    public void setCoupons(ArrayList<AbstractCouponComputer> couponList) {
        if (couponList == null) {
            return;
        }
        this.coupons = couponList;
        notifyDataSetChanged();
    }

    class CouponHolder extends RecyclerView.ViewHolder {
        TextView tv_coupon;
        TextView tv_coupon_desciption;
        Button bt_coupon_del;
        private AbstractCouponComputer coupon;

        public CouponHolder(View itemView) {
            super(itemView);
            tv_coupon = itemView.findViewById(R.id.tv_coupon);
            tv_coupon_desciption = itemView.findViewById(R.id.tv_coupon_desciption);
            bt_coupon_del = itemView.findViewById(R.id.bt_coupon_del);
            bt_coupon_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   removeCoupon(coupon);
                }
            });
        }

        public void setData(AbstractCouponComputer abstractCouponComputer) {
            this.coupon = abstractCouponComputer;
            tv_coupon.setText(coupon.couponName);
            tv_coupon_desciption.setText(coupon.couponDes);
        }
    }

    class ButtonHolder extends RecyclerView.ViewHolder {

        Button bt_add_pay;
        Button bt_add_coupon;

        public ButtonHolder(View itemView) {
            super(itemView);
            bt_add_pay = itemView.findViewById(R.id.bt_add_pay);
            bt_add_pay.setVisibility(View.GONE);
            bt_add_coupon = itemView.findViewById(R.id.bt_add_coupon);
            bt_add_coupon.setOnClickListener(onClickListener);
        }
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
