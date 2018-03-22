package com.example.alexanderlee.bmob_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class SysSetActivity extends AppCompatActivity {


    @BindView(R.id.exit_layout)
    LinearLayout exitLayout;
    @BindView(R.id.btn_back_sysSet)
    ImageButton btnBackSysSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_set);
        ButterKnife.bind(this);
    }



    @OnClick(R.id.btn_back_sysSet)
    public void onBtnBackSysSetClicked() {
        finish();
    }

    @OnClick(R.id.exit_layout)
    public void onExitLayoutClicked() {
        SharedPreferences.Editor editor = getSharedPreferences("data2", MODE_PRIVATE).edit();
        editor.putBoolean("isOnline", false);
        editor.commit();
        BmobUser.logOut(getApplicationContext());
        Intent intent = new Intent(SysSetActivity.this, UnonlionActivity.class);
        startActivity(intent);
    }
}
