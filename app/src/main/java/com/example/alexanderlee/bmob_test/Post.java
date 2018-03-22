package com.example.alexanderlee.bmob_test;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by alexanderlee on 2017/11/15.
 */

public class Post extends BmobObject {
    private  String content;
    private MyUser author;
    private List<String>images;
    private Integer likes;
    private Boolean haveliked;
    private BmobRelation commentsUser;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

//    public BmobFile getImages() {
//        return images;
//    }


    public void setHaveliked(Boolean haveliked) {
        this.haveliked = haveliked;
    }

    public Boolean getHaveliked() {
        return haveliked;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public BmobRelation getCommentsUser() {
        return commentsUser;
    }

    public void setCommentsUser(BmobRelation commentsUser) {
        this.commentsUser = commentsUser;
    }
}
