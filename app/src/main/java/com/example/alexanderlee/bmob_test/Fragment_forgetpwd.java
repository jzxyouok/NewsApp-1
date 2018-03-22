package com.example.alexanderlee.bmob_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by alexanderlee on 2017/10/28.
 */

public class Fragment_forgetpwd extends Fragment {
    @BindView(R.id.btn_feedback)
    ImageButton btnFeedback;
    @BindView(R.id.etx_phone)
    EditText etxPhone;
    @BindView(R.id.btn_next)
    Button btnNext;
    Unbinder unbinder;
    @BindView(R.id.btn_feedback2)
    ImageButton btnFeedback2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgetpwd, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_feedback, R.id.btn_next,R.id.btn_feedback2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback2:
                Log.i("MainActivity", "断点1");
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.loginfg_container, new Fragment_login()).commit();
                Log.i("MainActivity", "断点2");
                break;
            case R.id.btn_feedback:
                getActivity().finish();
                break;
            case R.id.btn_next:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.loginfg_container, new Fragment_findpwd()).commit();
                break;
        }
    }
}
