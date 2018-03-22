package com.example.alexanderlee.bmob_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.listener.SaveListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alexanderlee on 2017/10/27.
 */

public class Fragment_login extends Fragment {

    @BindView(R.id.btn_feedback)
    ImageButton btnFeedback;
    @BindView(R.id.etx_phone)
    EditText etxPhone;
    @BindView(R.id.etx_messagecode)
    EditText etxMessagecode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forgetpwd)
    TextView tvForgetpwd;
    @BindView(R.id.tv_toast)
    TextView tvToast;

    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    public static final String REGEX_PHONE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    @BindView(R.id.imv_see)
    ImageView imvSee;
    Unbinder unbinder;
    private boolean isPwdVisible = false;
    private Handler handler=new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ImageView imvSee = (ImageView) view.findViewById(R.id.imv_see);
        EditText etxMessagecode=(EditText)view.findViewById(R.id.etx_messagecode);
        etxMessagecode.setTransformationMethod(PasswordTransformationMethod.getInstance());
        imvSee.setImageResource(R.mipmap.unsee);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_feedback, R.id.btn_login, R.id.tv_register, R.id.tv_forgetpwd, R.id.imv_see})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback:
                getActivity().finish();
                break;
            case R.id.btn_login:
                loginverify();
                break;
            case R.id.tv_register:
                getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.loginfg_container, new Fragment_register()).commit();
                break;
            case R.id.tv_forgetpwd:
                getFragmentManager().beginTransaction().addToBackStack(null).add(R.id.loginfg_container, new Fragment_forgetpwd()).commit();
                break;
            case R.id.imv_see:
                isPwdVisible = !isPwdVisible;
                if (isPwdVisible) {
                    etxMessagecode.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imvSee.setImageResource(R.mipmap.see);
                } else {
                    etxMessagecode.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imvSee.setImageResource(R.mipmap.unsee);
                }
                break;
        }
    }

    private void loginverify() {
        final String phone = etxPhone.getText().toString();
        final String password = etxMessagecode.getText().toString();
        MyUser myuser = new MyUser();
//        myuser.setMobilePhoneNumber(phone);
        myuser.setUsername(phone);
        myuser.setPassword(password);
        myuser.login(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                storeData(phone);
                getActivity().finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("LoginActivity", "账号密码错误");
                tvToast.setVisibility(View.VISIBLE);
                tvToast.setPadding(20,0,20,0);
                tvToast.setText("账号或密码错误");

                tvanim();
            }
        });
    }


    private void tvanim() {
       AlphaAnimation alp=new AlphaAnimation(1.0f,0.0f);
       alp.setDuration(3000);
       tvToast.setAnimation(alp);
       alp.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {
               tvToast.setVisibility(View.GONE);
           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });
    }


    private void storeData(String username) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data2", MODE_PRIVATE).edit();
        editor.putBoolean("isOnline", true);
        editor.putString("username", username);
        editor.commit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public static boolean isPhone(String phone) {
        return Pattern.matches(REGEX_PHONE, phone);
    }

}


