package com.example.alexanderlee.bmob_test;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by alexanderlee on 2017/12/1.
 */

public abstract class BaseFragment extends Fragment {

    View mRoot;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, mRoot);
            initData(mRoot);
        }
        return mRoot;
    }



    protected void initData(View mRoot) {

    }


    protected abstract int getLayoutId();

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
        Log.i("Bmobgo", "OnResume");
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
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
