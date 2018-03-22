package com.example.alexanderlee.bmob_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SysSetActivityUnonline extends AppCompatActivity {

    @BindView(R.id.btn_back_sysSet)
    ImageButton btnBackSysSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_set_unonline);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_back_sysSet)
    public void onBtnBackSysSetClicked() {
        finish();
    }
}
