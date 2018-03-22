package com.example.alexanderlee.bmob_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alexanderlee on 2017/10/28.
 */

public class User_fragment2 extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.sysSet_layout)
    LinearLayout sysSetLayout;
    @BindView(R.id.circle_iamgeview)
    CircleImageView circleIamgeview;
    @BindView(R.id.tv_username)
    TextView tvUsername;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user, container, false);
        tvUsername = (TextView) view.findViewById(R.id.tv_username);
        unbinder = ButterKnife.bind(this, view);
        LoadData();
        return view;
    }

    private void LoadData() {
        MyUser myuser = BmobUser.getCurrentUser(getActivity(), MyUser.class);
//        String imagePath = null;
//        imagePath= myuser.getImgview().getUrl();
        if (myuser.getImgview() != null) {
            String imagePath=myuser.getImgview().getUrl();
            Glide.with(this).load(imagePath).into(circleIamgeview);
        } else {
            Glide.with(this).load("http://bmob-cdn-14975.b0.upaiyun.com/2017/11/19/d59c26d40a0d4a63b88358d8b2fb0349.jpg").into(circleIamgeview);
        }
        tvUsername.setText(myuser.getUsername());
        Log.i("MainActivity", "值为" + getActivity().getIntent().getExtras());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.circle_iamgeview, R.id.tv_username, R.id.sysSet_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_iamgeview:
                openModifyActivity();
                break;
            case R.id.tv_username:
                openModifyActivity();
                break;
            case R.id.sysSet_layout:
                Intent intent = new Intent(getActivity(), SysSetActivity.class);
                startActivity(intent);
//                getActivity().onBackPressed();
                break;
        }
    }

    private void openModifyActivity() {
        Intent intent1 = new Intent();
        intent1.setClass(getActivity(), ModifyDatumActivity.class);
        startActivity(intent1);
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

            return;
        } else {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().moveTaskToBack(true);
                    onHiddenChanged(true);
                    // handle back button
                    return true;
                }

                return false;
            }
        });

    }
}
