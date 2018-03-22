package com.example.alexanderlee.bmob_test;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;

public class LoginActivity extends SlideBackActivity {
//    private String AppID = "c29260b3d28f78d157e89fb6625a451c";
//private String AppID = "8eddc16bffec08416f85d49fc958bf0e";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Bmob.initialize(this, AppID);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.loginfg_container, new Fragment_login()).commit();
        }
    }
}
