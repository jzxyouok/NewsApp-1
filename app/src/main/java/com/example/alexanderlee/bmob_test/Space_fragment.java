package com.example.alexanderlee.bmob_test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a.a.V;
import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.alexanderlee.bmob_test.util.AllUtils.allUtils;

/**
 * Created by alexanderlee on 2017/10/23.
 */
public class Space_fragment extends BaseFragment {

    @BindView(R.id.recycler_view_space)
    RecyclerView recyclerViewSpace;
    @BindView(R.id.materialrefresh_space)
    MaterialRefreshLayout materialrefreshSpace;
    Unbinder unbinder;
    private List<Post> firstList = new ArrayList<Post>();
    Handler handler;
    private Fragment mContent;
    private boolean haveLiked = false;

    @Override
    protected int getLayoutId() {
        return R.layout.sapce;
    }

    @Override
    protected void initData(View view) {
        QueryPost();
        MaterialRefreshLayout materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.materialrefresh_space);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        QueryPost();
                        materialRefreshLayout.finishRefresh();
                    }
                }, 1400);
            }
        });
    }

    private void QueryPost() {
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.order("-createdAt");
        query.include("author");
        query.findObjects(getContext(), new FindListener<Post>() {
            @Override
            public void onSuccess(final List<Post> list) {
                CommonAdapter adapter = new CommonAdapter<Post>(getActivity(), R.layout.spaceitem, list) {
                    @Override
                    protected void convert(ViewHolder viewHolder, final Post post, int i) {
                        ImageView imageViewone = viewHolder.getView(R.id.imv_space_one);
                        ImageView imageViewtwo = viewHolder.getView(R.id.imv_space_two);
                        ImageView imageViewthree = viewHolder.getView(R.id.imv_space_three);
                        ImageView imageViewfour = viewHolder.getView(R.id.imv_space_four);
                        ImageView imageViewfive = viewHolder.getView(R.id.imv_space_five);
                        ImageView imageViewsix = viewHolder.getView(R.id.imv_space_six);
                        ImageView imageViewseven = viewHolder.getView(R.id.imv_space_seven);
                        ImageView imageVieweight = viewHolder.getView(R.id.imv_space_eight);
                        ImageView imageViewnine = viewHolder.getView(R.id.imv_space_nine);
                        List<ImageView> imvList = allUtils.initList(imageViewone, imageViewtwo, imageViewthree, imageViewfour, imageViewfive, imageViewsix, imageViewseven, imageVieweight, imageViewnine);
                        CircleImageView circleImageView = viewHolder.getView(R.id.circle_ivm_space);
                        Glide.with(getContext()).load(post.getAuthor().getImgview().getUrl()).into(circleImageView);
                        TextView usernameSpace = viewHolder.getView(R.id.username_space);
                        usernameSpace.setText(post.getAuthor().getUsername());
                        TextView createtimeSpace = viewHolder.getView(R.id.createtime_space);
                        createtimeSpace.setText(post.getCreatedAt());
                        TextView contentSpace = viewHolder.getView(R.id.content_space);
                        if (post.getContent() != null) {
                            contentSpace.setVisibility(View.VISIBLE);
                            contentSpace.setText(post.getContent());
                        }
                        for (int j = 0; j < 9; j++) {
                            imvList.get(j).setVisibility(View.GONE);
                        }
                        if (post.getImages() != null) {
                            for (int j = 0; j < post.getImages().size(); j++) {
                                imvList.get(j).setVisibility(View.VISIBLE);
                                Glide.with(getContext()).load(post.getImages().get(j)).into(imvList.get(j));
                            }
                        }
                        final TextView tvLikes = viewHolder.getView(R.id.tv_likes_space);
                        final ImageView imvLikes = viewHolder.getView(R.id.imv_likes_space);
                        if (post.getLikes() != null) {
                            if (post.getLikes().intValue() == 0) {
                                tvLikes.setText("点赞");
                            } else {

                                tvLikes.setText(post.getLikes().toString());
                            }
                        }
                        LinearLayout linearLayoutSpace = viewHolder.getView(R.id.clicklayout_space);
                        final LinearLayout linearLayoutLikes = viewHolder.getView(R.id.likes_space);
                        LinearLayout linearLayoutComment = viewHolder.getView(R.id.comment_space);
                        LinearLayout linearLayoutForword = viewHolder.getView(R.id.forword_space);
                        linearLayoutLikes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                haveLiked = !haveLiked;
                                if (haveLiked) {
                                    imvLikes.setBackgroundResource(R.drawable.ic_likes2);
                                    post.increment("likes", 1);
                                    tvLikes.setText(String.valueOf(post.getLikes()+1));
                                    post.setHaveliked(true);
                                } else {
                                    imvLikes.setBackgroundResource(R.drawable.ic_likes);
                                    post.increment("likes", -1);
                                    tvLikes.setText(String.valueOf(post.getLikes()));
                                    post.setHaveliked(false);
                                }
                                post.update(getContext(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i("Space_fragment", "成功");
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        Log.i("Space_fragment", "失败");
                                    }
                                });
                            }
                        });
                        linearLayoutSpace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), CommentActivity.class);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.center);
                            }
                        });
                        linearLayoutComment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), CommentActivity.class);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.center);
                            }
                        });

                    }
                };
                recyclerViewSpace.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                recyclerViewSpace.setLayoutManager(manager);
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }
}
