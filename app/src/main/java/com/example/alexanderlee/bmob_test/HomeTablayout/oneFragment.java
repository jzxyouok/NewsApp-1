package com.example.alexanderlee.bmob_test.HomeTablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

public class oneFragment extends Fragment {


    private RecyclerView recyclerView;
    private NewsAdapter adapter;


    private List<News> newsList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.onefragment,container,false);
//        ImageView imageView=(ImageView)view.findViewById(R.id.image_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView= (RecyclerView)view.findViewById(R.id.recyclernewsView);
        recyclerView.setLayoutManager(layoutManager);
        initPersonData();
        adapter=new NewsAdapter(newsList,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initPersonData() {
        newsList =new ArrayList<>();
        //添加新闻
        newsList.add(new News(getString(R.string.news_one_title),getString(R.string.news_one_desc),R.drawable.news));
        newsList.add(new News(getString(R.string.news_two_title),getString(R.string.news_two_desc),R.drawable.img_demo));
        newsList.add(new News(getString(R.string.news_three_title),getString(R.string.news_three_desc),R.drawable.img_demo1));
        newsList.add(new News(getString(R.string.news_four_title),getString(R.string.news_four_desc),R.drawable.news));
    }
//    private void sendRequestWithOKHttp() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder().url("http://oxm0wpojp.bkt.clouddn.com/news5.json").build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
//                    getJsonData(responseData);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

//    private void getJsonData(String jsonData) {
//        try {
//            JSONArray jsonArray=new JSONArray(jsonData);
//            for(int i=0;i<jsonArray.length();i++){
//                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                String title=jsonObject.getString("title");
//                String content=jsonObject.getString("content");
////                String url=jsonObject.getString("ImageUrl");
//                News news=new News(title,content,"http://oxm0wpojp.bkt.clouddn.com/timg.jpeg");
//                newsList.add(news);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
