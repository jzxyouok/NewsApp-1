package com.example.alexanderlee.bmob_test;

import android.location.Geocoder;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by alexanderlee on 2017/10/20.
 */

public class MyUser extends BmobUser {
    private BmobFile imgview;
    private Boolean gender;
    private BmobDate birthday;
    private BmobGeoPoint place;
    private String introduction;

    public void setImgview(BmobFile imgview) {
        this.imgview = imgview;
    }

    public BmobFile getImgview() {
        return imgview;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public BmobDate getBirthday() {
        return birthday;
    }

    public void setBirthday(BmobDate birthday) {
        this.birthday = birthday;
    }

    public BmobGeoPoint getPlace() {
        return place;
    }

    public void setPlace(BmobGeoPoint place) {
        this.place = place;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
