package com.example.alexanderlee.bmob_test.HomeTablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexanderlee.bmob_test.R;
import com.example.alexanderlee.bmob_test.adpter.NewsAdapter;
import com.example.alexanderlee.bmob_test.bean.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderlee on 2017/11/17.
 */

public class fifthFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<News> newsList;
    private NewsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.onefragment,container,false);
        return view;
    }

//    private void initPersonData() {
//        newsList =new ArrayList<>();
//        //添加新闻
//        newsList.add(new News(getString(R.string.news_one_title),getString(R.string.news_one_desc),R.drawable.news));
//        newsList.add(new News(getString(R.string.news_two_title),getString(R.string.news_two_desc),R.drawable.news));
//        newsList.add(new News(getString(R.string.news_three_title),getString(R.string.news_three_desc),R.drawable.news));
//        newsList.add(new News(getString(R.string.news_four_title),getString(R.string.news_four_desc),R.drawable.news));
//    }
}
