package com.example.alexanderlee.bmob_test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import android.widget.Toast;

import java.io.File;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by alexanderlee on 2017/10/28.
 */

public class Fragment_register extends Fragment {
    @BindView(R.id.btn_feedback)
    ImageButton btnFeedback;
    @BindView(R.id.etx_phone)
    EditText etxPhone;
    @BindView(R.id.etx_password)
    EditText etxPassword;
    @BindView(R.id.etx_code)
    EditText etxCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_feedback2)
    ImageButton btnFeedback2;
    @BindView(R.id.tv_sendcode_register)
    TextView tvSendcodeRegister = null;
    Unbinder unbinder;
    @BindView(R.id.tv_phone_toast)
    TextView tvPhoneToast;
    @BindView(R.id.tv_pwd_toast)
    TextView tvPwdToast;
    MyCountTimer myCountTimer;
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    public static final String REGEX_PHONE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    @BindView(R.id.imv_see)
    ImageView imvSee;
    @BindView(R.id.tv_toast)
    TextView tvToast;
    private boolean isPwdVisible = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ImageView imvSee = (ImageView) view.findViewById(R.id.imv_see);
        EditText etxPassword = (EditText) view.findViewById(R.id.etx_password);
        etxPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        imvSee.setImageResource(R.mipmap.unsee);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("FJActivity", "onDetach");
//        myCountTimer.cancel();
        System.gc();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_feedback, R.id.tv_sendcode_register, R.id.btn_register, R.id.btn_feedback2, R.id.imv_see})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback2:
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.loginfg_container, new Fragment_login()).commit();
                break;
            case R.id.btn_feedback:
                getActivity().finish();
                break;
            case R.id.tv_sendcode_register:
                requestSMSCode();
                break;
            case R.id.btn_register:
                verifyOrBind();
                break;
            case R.id.imv_see:
                isPwdVisible = !isPwdVisible;
                if (isPwdVisible) {
                    etxPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imvSee.setImageResource(R.mipmap.see);
                } else {
                    etxPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imvSee.setImageResource(R.mipmap.unsee);
                }
                break;
        }
    }


    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tvSendcodeRegister.setText((l / 1000) + "秒后重发");
            tvSendcodeRegister.setClickable(false);
        }

        @Override
        public void onFinish() {
            tvSendcodeRegister.setText("重新发送");
            tvSendcodeRegister.setClickable(true);
        }

    }

    private void requestSMSCode() {
        String phone = etxPhone.getText().toString();
        if (isPhone(phone)) {
            myCountTimer = new MyCountTimer(60000, 1000);
            myCountTimer.start();
            BmobSMS.requestSMSCode(getContext(), phone, "手机号码注册", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        Log.d("LoginActivity", "验证码发送成功");
                    } else {
//                        myCountTimer.cancel();
                        myCountTimer.onFinish();
                        tvToast.setVisibility(View.VISIBLE);
                        tvToast.setPadding(20, 0, 20, 0);
                        tvToast.setText("验证码发送失败!");
                        tvanim();
                    }
                }
            });
        } else {
            Log.d("LoginActivity", "请输入手机号");
            tvToast.setVisibility(View.VISIBLE);
            tvToast.setPadding(20, 0, 20, 0);
            tvToast.setText("手机号不合法!");
            tvanim();

        }
    }


    private void verifyOrBind() {
        final String phone = etxPhone.getText().toString();
        final String password = etxPassword.getText().toString();
        final String code = etxCode.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            tvToast.setVisibility(View.VISIBLE);
            tvToast.setPadding(20, 0, 20, 0);
            tvToast.setText("账号或密码不能为空");
            tvanim();
        }
        if (isPhone(phone)) {
            if (!isPassword(password)) {
                tvToast.setVisibility(View.VISIBLE);
                tvToast.setPadding(20, 0, 20, 0);
                tvToast.setText("密码不合法!(6-20位数字、字母)");
                tvanim();
            } else {
                BmobSMS.verifySmsCode(getContext(), phone, code, new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("LoginActivity", "手机号码已验证");
                            bindMobilePhone(phone, password);
                        } else {
                            Log.i("LoginActivity", "验证失败");
                            tvToast.setVisibility(View.VISIBLE);
                            tvToast.setPadding(20, 0, 20, 0);
                            tvToast.setText("手机号验证失败!");
                            tvanim();
                        }
                    }
                });
            }
        } else {
            tvToast.setVisibility(View.VISIBLE);
            tvToast.setPadding(20, 0, 20, 0);
            tvToast.setText("手机号码不合法!");
            tvanim();
        }
    }

    private void bindMobilePhone(String phone, String password) {
        MyUser myuser = new MyUser();
        myuser.setMobilePhoneNumber(phone);
        myuser.setMobilePhoneNumberVerified(true);
        myuser.setUsername(phone);
        myuser.setPassword(password);
        myuser.setGender(true);
        myuser.setIntroduction("头条生活趣味多");

        myuser.signUp(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("LoginActivity", "数据绑定成功");
                getFragmentManager().beginTransaction().replace(R.id.loginfg_container, new Fragment_login()).commit();
                //getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.loginfg_container, new Fragment_login()).commit();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("LoginActivity", "数据绑定失败");
            }
        });
    }

    private void bindMobilePhone2(final String phone, final String password) {
        final BmobFile imgview = new BmobFile(new File("http://oxm0wpojp.bkt.clouddn.com/timg.jpeg"));
        imgview.upload(getActivity(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                Log.i("Activity", "上传成功2");
                MyUser myuser = new MyUser();
                myuser.setMobilePhoneNumber(phone);
                myuser.setMobilePhoneNumberVerified(true);
                myuser.setUsername(phone);
                myuser.setPassword(password);
                myuser.setGender(true);
                myuser.setIntroduction("你爱的永远得不到");
                myuser.setImgview(imgview);
                myuser.signUp(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Log.i("ModifyDatumActivity", "上传成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("ModifyDatumActivity", "上传失败");
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("ModifyDatumActivity", "上传失败2");
            }
        });
    }

    private void tvanim() {
        AlphaAnimation alp = new AlphaAnimation(1.0f, 0.0f);
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

    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public static boolean isPhone(String phone) {
        return Pattern.matches(REGEX_PHONE, phone);
    }

}
