package com.example.alexanderlee.bmob_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class PostWriteActivity extends AppCompatActivity {

    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.send_btn)
    Button sendBtn;
    @BindView(R.id.etx_share)
    EditText etxShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);
        ButterKnife.bind(this);
        etxShare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendBtn.setClickable(false);
                sendBtn.setTextColor(getResources().getColor(R.color.colorLightGreen));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendBtn.setClickable(true);
                sendBtn.setTextColor(getResources().getColor(R.color.colorDarkGreen));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    sendBtn.setClickable(false);
                    sendBtn.setTextColor(getResources().getColor(R.color.colorLightGreen));
                } else {
                    sendBtn.setClickable(true);
                    sendBtn.setTextColor(getResources().getColor(R.color.colorDarkGreen));
                }
            }
        });
    }


    @OnClick(R.id.cancel_btn)
    public void onCancelBtnClicked() {
        finish();
    }

    @OnClick(R.id.send_btn)
    public void onSendBtnClicked() {
        String etxContent = etxShare.getText().toString();
        MyUser user = BmobUser.getCurrentUser(PostWriteActivity.this, MyUser.class);
        Post post = new Post();
        post.setAuthor(user);
        post.setContent(etxContent);
        post.save(PostWriteActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("bmob", "文字上传成功");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("bmob", "文字上传失败");
                Toast.makeText(PostWriteActivity.this, "文字上传失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
