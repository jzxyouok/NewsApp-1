package com.example.alexanderlee.bmob_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexanderlee.bmob_test.bean.News;

public class NewsActivity extends AppCompatActivity {
    private ImageView newsPhoto;
    private TextView newsTitle;
    private TextView newsDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsPhoto= (ImageView) findViewById(R.id.news_info_photo);
        newsTitle= (TextView) findViewById(R.id.news_info_title);
        newsDesc= (TextView) findViewById(R.id.news_info_desc);

        Intent intent=getIntent();

        News item= (News) intent.getSerializableExtra("News");
        newsPhoto.setImageResource(item.getPhotoId());
        newsTitle.setText(item.getTitle());
        newsDesc.setText(item.getDesc());

    }
}
