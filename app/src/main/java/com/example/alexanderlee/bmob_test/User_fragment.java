package com.example.alexanderlee.bmob_test;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by alexanderlee on 2017/10/23.
 */

public class User_fragment extends Fragment {

    @BindView(R.id.phone_btn)
    ImageButton phoneBtn;
    Unbinder unbinder;
    @BindView(R.id.qq_btn)
    ImageButton qqBtn;
    @BindView(R.id.wechat_btn)
    ImageButton wechatBtn;
    @BindView(R.id.collection_layout)
    LinearLayout collectionLayout;
    @BindView(R.id.space_layout)
    LinearLayout spaceLayout;
    @BindView(R.id.history_layout)
    LinearLayout historyLayout;
    @BindView(R.id.attention_layout)
    LinearLayout attentionLayout;
    @BindView(R.id.message_layout)
    LinearLayout messageLayout;
    @BindView(R.id.market_layout)
    LinearLayout marketLayout;
    @BindView(R.id.tipoff_layout)
    LinearLayout tipoffLayout;
    @BindView(R.id.complaint_layout)
    LinearLayout complaintLayout;
    @BindView(R.id.sysSet_layout)
    LinearLayout sysSetLayout;
    private BottomSheetDialog mbottomSheetDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_unonline, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.phone_btn)
    public void onViewClicked() {
        openPhoneLogin();
    }

    private void openPhoneLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.qq_btn)
    public void onQqBtnClicked() {
    }

    @OnClick(R.id.wechat_btn)
    public void onWechatBtnClicked() {
    }

    @OnClick(R.id.collection_layout)
    public void onCollectionLayoutClicked() {
        dialogLogin();
    }

    @OnClick(R.id.space_layout)
    public void onSpaceLayoutClicked() {
        dialogLogin();
    }

    @OnClick(R.id.history_layout)
    public void onHistoryLayoutClicked() {
        dialogLogin();

    }

    @OnClick(R.id.attention_layout)
    public void onAttentionLayoutClicked() {
        dialogLogin();
    }

    @OnClick(R.id.message_layout)
    public void onMessageLayoutClicked() {
        dialogLogin();
    }

    @OnClick(R.id.market_layout)
    public void onMarketLayoutClicked() {dialogLogin();
    }

    @OnClick(R.id.tipoff_layout)
    public void onTipoffLayoutClicked() {
        dialogLogin();
    }

    @OnClick(R.id.complaint_layout)
    public void onComplaintLayoutClicked() {
        dialogLogin();
    }

    @OnClick(R.id.sysSet_layout)
    public void onSysSetLayoutClicked() {
        Intent intent=new Intent(getActivity(),SysSetActivityUnonline.class);
        startActivity(intent);
    }

    public void dialogLogin(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.BottomDialog);
        builder.setView(R.layout.logintypes);
        final AlertDialog dialog=builder.create();
        dialog.show();
        ImageView imvQuit=(ImageView) dialog.findViewById(R.id.quit_imv);
        RelativeLayout phoneLayout=(RelativeLayout)dialog.findViewById(R.id.phone_layout);
        RelativeLayout wechatLayout=(RelativeLayout)dialog.findViewById(R.id.wechat_layout);
        RelativeLayout qqLayout=(RelativeLayout)dialog.findViewById(R.id.qq_layout);
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
            }
        });
        qqLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
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
