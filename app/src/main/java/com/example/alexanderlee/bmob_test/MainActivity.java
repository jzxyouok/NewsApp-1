package com.example.alexanderlee.bmob_test;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {


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
    @BindView(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    @BindView(R.id.bt_add)
    ImageView btAdd;
    @BindView(R.id.main_liearlayout)
    LinearLayout mainLiearlayout;
    private String AppID = "c29260b3d28f78d157e89fb6625a451c";
    //    private String AppID = "8eddc16bffec08416f85d49fc958bf0e";
    private Home_fragment home_fragment;
    private Video_fragment video_fragment;
    private Space_fragment space_fragment;
    private User_fragment2 user_fragment2;
    private LinearLayout btnLinearlayoutAdd;
    private AnimationDialog ADialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, AppID);
        ButterKnife.bind(this);
        bottomLayout.setVisibility(View.VISIBLE);
        radioGroup.setOnCheckedChangeListener(this);
        rbtnUser.setChecked(true);
        Animation();
    }

    private void Animation() {
        LinearLayout btnLinearlayoutAdd = (LinearLayout) findViewById(R.id.btn_linearlayout_add);
        btnLinearlayoutAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ADialog == null) {
                    ADialog = new AnimationDialog(MainActivity.this);
                    ADialog.setWriteBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentwrite = new Intent(MainActivity.this, PostWriteActivity.class);
                            startActivity(intentwrite);
                        }
                    });
                    ADialog.setPhotoBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentwrite = new Intent(MainActivity.this, PostActivity.class);
                            startActivity(intentwrite);
                        }
                    });
                    ADialog.setPhotographyBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(MainActivity.this,"video",Toast.LENGTH_SHORT);
                        }
                    });
                    ADialog.setQuestionBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "question", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Window window = ADialog.getWindow();
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.x = getWindowManager().getDefaultDisplay().getWidth();
                wl.y = getWindowManager().getDefaultDisplay().getHeight();
                ADialog.onWindowAttributesChanged(wl);
                ADialog.show();
            }
        });
    }

    public void dialogLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.BottomDialog);
        builder.setView(R.layout.logintypes);
        final AlertDialog dialog = builder.create();
        dialog.show();
        ImageView imvQuit = (ImageView) dialog.findViewById(R.id.quit_imv);
        RelativeLayout phoneLayout = (RelativeLayout) dialog.findViewById(R.id.phone_layout);
        RelativeLayout wechatLayout = (RelativeLayout) dialog.findViewById(R.id.wechat_layout);
        RelativeLayout qqLayout = (RelativeLayout) dialog.findViewById(R.id.qq_layout);
        imvQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhoneLogin();
            }
        });
        wechatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhoneLogin();
            }
        });
        qqLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhoneLogin();
            }
        });
    }

    private void openPhoneLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
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
                if (user_fragment2 == null) {
                    user_fragment2 = new User_fragment2();
                    transaction.add(R.id.fg_container, user_fragment2);
                } else {
                    transaction.show(user_fragment2);
                }
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
        if (user_fragment2 != null) {
            transaction.hide(user_fragment2);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
