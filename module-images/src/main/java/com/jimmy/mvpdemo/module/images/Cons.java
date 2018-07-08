package com.jimmy.mvpdemo.module.images;

import com.jimmy.mvp.AppPermission;

/**
 * @author yangyoujun
 * @Date 17-11-9
 */
public final class Cons {

    public static final String URL = getUrl();

    private static final String URL_RELEASE = "http://gank.io/";
    private static final String URL_DEBUG = "http://pregank.io/";

    private static String getUrl() {
        /*
          此处用于判断是否为拟真环境
         */
        return AppPermission.ins().has() ? URL_RELEASE : URL_DEBUG;
    }

}
