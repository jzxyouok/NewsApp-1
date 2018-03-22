package com.example.alexanderlee.bmob_test;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by alexanderlee on 2017/10/26.
 */

public class AnimationDialog extends Dialog {
    private Context context;
    private Handler handler;
    private RelativeLayout relativelayoutCover;
    private LinearLayout linearlayoutCover, btlayoutMultiply;
    private RadioButton layoutWrite, layoutPhoto, layoutPhotography, layoutQuestion;
    private ImageView btnMultiply;
    private LinearLayout main_linearlayout;

    public AnimationDialog(@NonNull Context context) {
        this(context, R.style.menu_whole_style);
    }

    public AnimationDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();

    }

    private void init() {
        handler = new Handler();
        setContentView(R.layout.addmenu2);
        linearlayoutCover = (LinearLayout) findViewById(R.id.cover_linearlayout);
        relativelayoutCover = (RelativeLayout) findViewById(R.id.cover_relativelayout);
        layoutWrite = (RadioButton) findViewById(R.id.layout_write);
        layoutPhoto = (RadioButton) findViewById(R.id.layout_photo);
        layoutPhotography = (RadioButton) findViewById(R.id.layout_photography);
        layoutQuestion = (RadioButton) findViewById(R.id.layout_question);
        btlayoutMultiply = (LinearLayout) findViewById(R.id.layout_multiply);
        btnMultiply = (ImageView) findViewById(R.id.btn_multiply);
        main_linearlayout = (LinearLayout) findViewById(R.id.main_liearlayout);
        btlayoutMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputAnim();
            }
        });
        relativelayoutCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputAnim();
            }
        });
        linearlayoutCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outputAnim();
            }
        });
    }


    private void inputAnim() {
        layoutWrite.setVisibility(View.INVISIBLE);
        layoutPhoto.setVisibility(View.INVISIBLE);
        layoutPhotography.setVisibility(View.INVISIBLE);
        layoutQuestion.setVisibility(View.INVISIBLE);
        btnMultiply.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_c_w));
        layoutWrite.setVisibility(View.VISIBLE);
        layoutWrite.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_in));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutPhoto.setVisibility(View.VISIBLE);
                layoutPhoto.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_in));
            }
        }, 100);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutPhotography.setVisibility(View.VISIBLE);
                layoutPhotography.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_in));
            }
        }, 200);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutQuestion.setVisibility(View.VISIBLE);
                layoutQuestion.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_in));
            }
        }, 300);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (isShowing()) {
            outputAnim();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void outputAnim() {
        btnMultiply.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_a_c));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 400);
        layoutWrite.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_out));
        layoutWrite.setVisibility(View.INVISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutPhoto.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_out));
                layoutPhoto.setVisibility(View.INVISIBLE);
            }
        }, 50);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutPhotography.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_out));
                layoutPhotography.setVisibility(View.INVISIBLE);
            }
        }, 100);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutQuestion.startAnimation(AnimationUtils.loadAnimation(context, R.anim.translate_out));
                layoutQuestion.setVisibility(View.INVISIBLE);
            }
        }, 150);
    }

    public void show() {
        super.show();
        inputAnim();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    public AnimationDialog setWriteBtnClickListener(android.view.View.OnClickListener clicklistener) {
        layoutWrite.setOnClickListener(clicklistener);
        return this;
    }

    public AnimationDialog setPhotoBtnClickListener(android.view.View.OnClickListener clickListener) {
        layoutPhoto.setOnClickListener(clickListener);
        return this;
    }

    public AnimationDialog setPhotographyBtnClickListener(android.view.View.OnClickListener clickListener) {
        layoutPhotography.setOnClickListener(clickListener);
        return this;
    }

    public AnimationDialog setQuestionBtnClickListener(android.view.View.OnClickListener clickListener) {
        layoutQuestion.setOnClickListener(clickListener);
        return this;
    }
}
