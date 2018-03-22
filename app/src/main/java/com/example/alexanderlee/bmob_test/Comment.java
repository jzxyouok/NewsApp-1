package com.example.alexanderlee.bmob_test;

import cn.bmob.v3.BmobObject;

/**
 * Created by alexanderlee on 2017/11/15.
 */

public class Comment extends BmobObject {
    private String content;
    private MyUser user;
    private Post post;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyUser getUser() {
        return user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
