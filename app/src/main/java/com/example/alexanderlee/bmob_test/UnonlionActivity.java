package com.example.alexanderlee.bmob_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class UnonlionActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.fg_container)
    LinearLayout fgContainer;
    @BindView(R.id.rbtn_home)
    RadioButton rbtnHome;
    @BindView(R.id.rbtn_video)
    RadioButton rbtnVideo;
    @BindView(R.id.rbtn_space)
    RadioButton rbtnSpace;
    @BindView(R.id.rbtn_user)
    RadioButton rbtnUser;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.main_liearlayout)
    LinearLayout mainLiearlayout;
    @BindView(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    private String AppID = "c29260b3d28f78d157e89fb6625a451c";
    private Home_fragment home_fragment;
    private Video_fragment video_fragment;
    private Space_fragment space_fragment;
    private User_fragment user_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pre = getSharedPreferences("data2", MODE_PRIVATE);
        setContentView(R.layout.activity_unonlion);
        ButterKnife.bind(this);
        Bmob.initialize(this, AppID);
        radioGroup.setOnCheckedChangeListener(this);
        rbtnHome.setChecked(true);
        Boolean isOnline=pre.getBoolean("isOnline",false);
        if (isOnline){
            Intent intent=new Intent(UnonlionActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        SharedPreferences pref = getSharedPreferences("data2", MODE_PRIVATE);
//        boolean isOnline = pref.getBoolean("isOnline", false);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case R.id.rbtn_home:
                if (home_fragment == null) {
                    home_fragment = new Home_fragment();
                    transaction.add(R.id.fg_container, home_fragment);
                } else {
                    transaction.show(home_fragment);
                }
                break;
            case R.id.rbtn_video:
                if (video_fragment == null) {
                    video_fragment = new Video_fragment();
                    transaction.add(R.id.fg_container, video_fragment);
                } else {
                    transaction.show(video_fragment);
                }
                break;
            case R.id.rbtn_space:
                if (space_fragment == null) {
                    space_fragment = new Space_fragment();
                    transaction.add(R.id.fg_container, space_fragment);
                } else {
                    transaction.show(space_fragment);
                }
                break;
            case R.id.rbtn_user:
                if (user_fragment == null) {
                    user_fragment = new User_fragment();
                    transaction.add(R.id.fg_container, user_fragment);
                } else {
                    transaction.show(user_fragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (home_fragment != null) {
            transaction.hide(home_fragment);
        }
        if (video_fragment != null) {
            transaction.hide(video_fragment);
        }
        if (space_fragment != null) {
            transaction.hide(space_fragment);
        }
        if (user_fragment != null) {
            transaction.hide(user_fragment);
        }

    }
}


