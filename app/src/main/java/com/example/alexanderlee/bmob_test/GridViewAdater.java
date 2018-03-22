package com.example.alexanderlee.bmob_test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.io.File;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexanderlee on 2017/11/14.
 */

public class GridViewAdater extends BaseAdapter {

    private List<Map<String, Object>> datas;
    private Context context;
    private LayoutInflater inflater;
    private int maxImages = 9;

    public GridViewAdater(List<Map<String, Object>> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public int getMaxImages() {
        return maxImages;//获取最大上传张数
    }

    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;//设置最大上传张数
    }

    /**
     * 让GridView中的数据数目加1最后一个显示+号
     * 当到达最大张数时不再显示+号
     *
     * @return 返回GridView中的数量
     */

    @Override
    public int getCount() {
        int count = datas == null ? 1 : datas.size() + 1;
        if (count > maxImages) {
            return datas.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public void notifyDataSetChanged(List<Map<String, Object>> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertview == null) {
            convertview = inflater.inflate(R.layout.addphoto, parent, false);
            viewHolder = new ViewHolder(convertview);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        if (datas!= null && position < datas.size()) {
            final File file = new File(datas.get(position).get("path").toString());
            Glide.with(context).load(file).priority(Priority.HIGH).into(viewHolder.addImageview);
            viewHolder.cancelCheckbox.setVisibility(View.VISIBLE);
            viewHolder.cancelCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (file.exists()) {
                        file.delete();
                    }
                    datas.remove(position);
                    if (datas.size() != 0) {
                        PostActivity.sendBtn.setClickable(true);
                        PostActivity.sendBtn.setTextColor(context.getResources().getColor(R.color.colorDarkGreen));
                    } else {
                        PostActivity.sendBtn.setClickable(false);
                        PostActivity.sendBtn.setTextColor(context.getResources().getColor(R.color.colorLightGreen));
                    }
                    notifyDataSetChanged();
                }
            });
        } else {
            Glide.with(context).load(R.mipmap.addpost3).priority(Priority.HIGH).centerCrop().into(viewHolder.addImageview);
            viewHolder.addImageview.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.cancelCheckbox.setVisibility(View.GONE);
        }
        return convertview;

    }

    static class ViewHolder {
        @BindView(R.id.add_imageview)
        ImageView addImageview;
        @BindView(R.id.cancel_checkbox)
        CheckBox cancelCheckbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            addImageview = (ImageView) view.findViewById(R.id.add_imageview);
            cancelCheckbox = (CheckBox) view.findViewById(R.id.cancel_checkbox);
        }
    }

}
