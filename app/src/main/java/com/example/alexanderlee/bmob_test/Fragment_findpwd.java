package com.example.alexanderlee.bmob_test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by alexanderlee on 2017/10/28.
 */

public class Fragment_findpwd extends Fragment {
    @BindView(R.id.btn_feedback)
    ImageButton btnFeedback;
    @BindView(R.id.etx_messagecode)
    EditText etxMessagecode;
    @BindView(R.id.etx_newpwd)
    EditText etxNewpwd;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;
    @BindView(R.id.btn_feedback2)
    ImageButton btnFeedback2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findpwd, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_feedback, R.id.btn_submit, R.id.btn_feedback2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback2:
                Log.i("MainActivity", "断点3");
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.loginfg_container, new Fragment_forgetpwd()).commit();
                Log.i("MainActivity", "断点4");
                break;
            case R.id.btn_feedback:
                getActivity().finish();
                break;
            case R.id.btn_submit:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.loginfg_container, new Fragment_login()).commit();
                break;
        }
    }

}
