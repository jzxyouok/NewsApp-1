package com.example.alexanderlee.bmob_test.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alexanderlee on 2017/12/1.
 */

public class AllUtils {

    public static AllUtils allUtils = new AllUtils();

    public  <T> List<T> initList(T... t) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, t);
        return list;
    }
}
