package cn.kuwo.teampay.view.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.kuwo.teampay.R;
import cn.kuwo.teampay.view.adapter.CouponAddAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        View bt_start_pay = findViewById(R.id.bt_start_pay);
        View bt_add_coupon = findViewById(R.id.bt_add_coupon);
        bt_start_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PayAddActivity.class));
            }
        });
        bt_add_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CouponAddActivity.class));
            }
        });
    }
}
