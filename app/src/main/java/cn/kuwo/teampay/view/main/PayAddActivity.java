package cn.kuwo.teampay.view.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import cn.kuwo.teampay.R;
import cn.kuwo.teampay.presenter.CouponPresenter;
import cn.kuwo.teampay.view.adapter.PayAddAdapter;
import cn.kuwo.teampay.view.decoration.SpaceItemDecoration;

public class PayAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_add);
        initView();
    }

    private void initView() {
        RecyclerView recycler_pay_info = findViewById(R.id.recycler_pay_info);
        GridLayoutManager layoutManage = new GridLayoutManager(this, 2);
        final PayAddAdapter adapter = new PayAddAdapter();
        adapter.setCoupons(CouponPresenter.getCouponList(PayAddActivity.this));
        layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getSpanSize(position);
            }
        });
        recycler_pay_info.setLayoutManager(layoutManage);
        recycler_pay_info.setAdapter(adapter);
        recycler_pay_info.addItemDecoration(new SpaceItemDecoration(15,15));
    }
}
