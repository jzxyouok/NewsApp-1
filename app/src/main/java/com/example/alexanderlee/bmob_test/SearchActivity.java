package com.example.alexanderlee.bmob_test;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends SlideBackRightActivity {

    @BindView(R.id.etx_search)
    EditText etxSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.grid_view_search)
    GridView gridViewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(savedInstanceState!=null){
            String editSearch=savedInstanceState.getString("data_key");
            etxSearch.setText(editSearch);
        }
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_cancel)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.center,R.anim.out_to_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.center,R.anim.out_to_right);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String editSearch=etxSearch.getText().toString();
        outState.putString("data_key",editSearch);
    }
}
