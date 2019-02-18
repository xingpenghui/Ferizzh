package com.feri.ferizzh.main;

import com.alibaba.fastjson.JSON;
import com.feri.ferizzh.pojo.menu.Menu;
import com.feri.ferizzh.util.HttpUtil;
import com.feri.ferizzh.util.MenuUtil;

/**
 *@Author feri
 *@Date Created in 2019/2/18 17:15
 */
public class Menu_Maiin {
    public static void main(String[] args) throws Exception {
        //1、获取Token
        String atoken="18_GOgOMNZHOrmSMbtGi7hQYjAGXHdUjUC_NgtwPFYS5LVLBgojfQ2593oQmO5WOJ-QiIcVU0phQJ5pkqd_xa5b3sPwSj4zDlOILrYhvUfOBKKNfvq9nI2BP4quUkuIvqSFzfpUnHYxsNFEJoq8PSNbAAAXAV";
        //2、组装菜单
        Menu menu=MenuUtil.init();
        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+atoken;
        //3、发送自定义菜单
        String res=HttpUtil.httpRequest(JSON.toJSONString(menu),url);
        System.out.println("自定义菜单："+res);

    }
    //支持SSL

}
