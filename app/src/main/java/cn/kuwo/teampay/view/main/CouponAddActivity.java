package cn.kuwo.teampay.view.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;

import cn.kuwo.teampay.R;
import cn.kuwo.teampay.presenter.CouponPresenter;
import cn.kuwo.teampay.presenter.coupon.EveryFullReductionCouponComputer;
import cn.kuwo.teampay.presenter.coupon.FullDiscountCouponComputer;
import cn.kuwo.teampay.presenter.coupon.FullReductionCouponComputer;
import cn.kuwo.teampay.view.adapter.CouponAddAdapter;
import cn.kuwo.teampay.view.decoration.SpaceItemDecoration;
import cn.kuwo.teampay.view.dialog.CouponSelectDialog;
import cn.kuwo.teampay.view.dialog.EveryFullReductionCouponAddDialog;
import cn.kuwo.teampay.view.dialog.ReductionCouponAddDialog;
import cn.kuwo.teampay.view.dialog.DiscountCouponAddDialog;

public class CouponAddActivity extends AppCompatActivity {

    CouponAddAdapter adapter = new CouponAddAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_add);

        initView();
    }

    private void initView() {
        RecyclerView recycler_pay_info = findViewById(R.id.recycler_coupon_info);
        GridLayoutManager layoutManage = new GridLayoutManager(this, 2);
        adapter.setCoupons(CouponPresenter.getCouponList(CouponAddActivity.this));
        layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getSpanSize(position);
            }
        });
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();

            }
        });
        recycler_pay_info.setLayoutManager(layoutManage);
        recycler_pay_info.setAdapter(adapter);
        recycler_pay_info.addItemDecoration(new SpaceItemDecoration(15, 15));
    }

    private void showAddDialog() {
        final CouponSelectDialog dialog = new CouponSelectDialog(this);
        dialog.setOnSureClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dialog.getmCheckedId()) {
                    case R.id.rb_reduce:
                        showAddReductionDialog();
                        break;
                    case R.id.rb_discount:
                        showAddDiscountCouponAddDialog();
                        break;
                    case R.id.rb_every_discount:
                        showEveryFullReductionCouponAddDialog();
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showAddDiscountCouponAddDialog() {
        final DiscountCouponAddDialog dialog = new DiscountCouponAddDialog(this);
        dialog.setOnSureClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float lessvalue = dialog.getLessvalue();
                float thresholdvalue = dialog.getThresholdvalue();
                if (thresholdvalue < lessvalue) {
                    Toast.makeText(CouponAddActivity.this, "优惠力度不能大于价格阈值", Toast.LENGTH_SHORT).show();
                    return;
                }
                FullDiscountCouponComputer fullReductionCouponComputer = new FullDiscountCouponComputer();
                fullReductionCouponComputer.setmDiscountValue(lessvalue);
                fullReductionCouponComputer.setmThresholdValue(thresholdvalue);
                DecimalFormat df = new DecimalFormat("#.0");
                fullReductionCouponComputer.couponDes = String.format("满%s元,打%s折", df.format(thresholdvalue), df.format(lessvalue));
                adapter.addCoupon(fullReductionCouponComputer);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showAddReductionDialog() {
        final ReductionCouponAddDialog dialog = new ReductionCouponAddDialog(this);
        dialog.setOnSureClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float lessvalue = dialog.getLessvalue();
                float thresholdvalue = dialog.getThresholdvalue();
                if (thresholdvalue < lessvalue) {
                    Toast.makeText(CouponAddActivity.this, "优惠力度不能大于价格阈值", Toast.LENGTH_SHORT).show();
                    return;
                }
                FullReductionCouponComputer fullReductionCouponComputer = new FullReductionCouponComputer();
                fullReductionCouponComputer.setmLessValue(lessvalue);
                fullReductionCouponComputer.setmThresholdValue(thresholdvalue);
                DecimalFormat df = new DecimalFormat("#.0");
                fullReductionCouponComputer.couponDes = String.format("满%s元,减%s元", df.format(thresholdvalue), df.format(lessvalue));
                adapter.addCoupon(fullReductionCouponComputer);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showEveryFullReductionCouponAddDialog() {
        final EveryFullReductionCouponAddDialog dialog = new EveryFullReductionCouponAddDialog(this);
        dialog.setOnSureClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float lessvalue = dialog.getLessvalue();
                float thresholdvalue = dialog.getThresholdvalue();
                if (thresholdvalue < lessvalue) {
                    Toast.makeText(CouponAddActivity.this, "优惠力度不能大于价格阈值", Toast.LENGTH_SHORT).show();
                    return;
                }
                EveryFullReductionCouponComputer everyFullReductionCouponComputer = new EveryFullReductionCouponComputer();
                everyFullReductionCouponComputer.setmLessValue(lessvalue);
                everyFullReductionCouponComputer.setmThresholdValue(thresholdvalue);
                DecimalFormat df = new DecimalFormat("#.0");
                everyFullReductionCouponComputer.couponDes = String.format("每满%s元,减%s元", df.format(thresholdvalue), df.format(lessvalue));
                adapter.addCoupon(everyFullReductionCouponComputer);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CouponPresenter.saveCouponList(this, adapter.getCoupons());
    }
}
