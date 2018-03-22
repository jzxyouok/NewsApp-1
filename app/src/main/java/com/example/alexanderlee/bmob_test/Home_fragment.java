package com.example.alexanderlee.bmob_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.alexanderlee.bmob_test.HomeTablayout.eighthFragment;
import com.example.alexanderlee.bmob_test.HomeTablayout.fifthFragment;
import com.example.alexanderlee.bmob_test.HomeTablayout.fourthFragment;
import com.example.alexanderlee.bmob_test.HomeTablayout.oneFragment;
import com.example.alexanderlee.bmob_test.HomeTablayout.seventhFragment;
import com.example.alexanderlee.bmob_test.HomeTablayout.sixthFragment;
import com.example.alexanderlee.bmob_test.HomeTablayout.threeFragment;
import com.example.alexanderlee.bmob_test.HomeTablayout.twoFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by alexanderlee on 2017/10/26.
 */

public class Home_fragment extends Fragment {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.etx_search)
    LinearLayout etxSearch;
    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("关注");
        add("推荐");
        add("热点");
        add("教育");
        add("旅游");
        add("国际");
        add("政治");
        add("科技");
    }};
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new oneFragment());
        add(new twoFragment());
        add(new threeFragment());
        add(new fourthFragment());
        add(new fifthFragment());
        add(new sixthFragment());
        add(new seventhFragment());
        add(new eighthFragment());
    }};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        unbinder = ButterKnife.bind(this, view);
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getActivity().getSupportFragmentManager(), titleList, fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.setTabsFromPagerAdapter(adapter);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @OnClick(R.id.etx_search)
    public void onEtxSearchClicked() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.center);
    }


    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

            return;
        } else {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().moveTaskToBack(true);
                    onHiddenChanged(true);
                    // handle back button
                    return true;

                }

                return false;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
