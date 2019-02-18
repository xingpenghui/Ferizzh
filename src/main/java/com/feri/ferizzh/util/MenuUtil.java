package com.feri.ferizzh.util;

import com.feri.ferizzh.pojo.menu.BaseMenu;
import com.feri.ferizzh.pojo.menu.ClickMenu;
import com.feri.ferizzh.pojo.menu.Menu;
import com.feri.ferizzh.pojo.menu.ViewMenu;
import java.util.ArrayList;

/**
 *@Author feri
 *@Date Created in 2019/2/18 17:23
 */
public class MenuUtil {
    //创建初始化的菜单
    public static Menu init(){
        //创建3个一级菜单
        BaseMenu menu1=new BaseMenu();
        menu1.setName("首页");
        menu1.setKey("home_1");
        menu1.setType("click");
        menu1.setSub_button(new ArrayList<>());
        ClickMenu menu11=new ClickMenu();
        menu1.setType("click");
        menu1.setKey("home_11");
        menu1.setName("我的简历");
        menu1.getSub_button().add(menu11);
        ViewMenu menu12=new ViewMenu();
        menu12.setName("在线简历");
        menu12.setKey("home_12");
        menu12.setType("view");
        menu12.setUrl("http://www.lugege.design:8080/note/");
        menu1.getSub_button().add(menu12);

        BaseMenu menu2=new BaseMenu();
        menu2.setName("Java");
        menu2.setKey("home_2");
        menu2.setType("click");
        menu2.setSub_button(new ArrayList<>());
        BaseMenu menu21=new BaseMenu();
        menu21.setName("扫描码");
        menu21.setKey("sm_1");
        menu21.setType("scancode_waitmsg");
        menu2.getSub_button().add(menu21);
        BaseMenu menu22=new BaseMenu();
        menu22.setName("天气");
        menu22.setKey("sm_2");
        menu22.setType("click");
        menu2.getSub_button().add(menu22);

        BaseMenu menu3=new BaseMenu();
        menu3.setName("我的");
        menu3.setKey("home3");
        menu3.setType("click");
        menu3.setSub_button(new ArrayList<>());

        ViewMenu menu31=new ViewMenu();
        menu31.setName("官网");
        menu31.setKey("sm_3");
        menu31.setType("view");
        menu31.setUrl("http://1000phone.com");
        menu3.getSub_button().add(menu31);
        BaseMenu menu32=new BaseMenu();
        menu32.setName("靓照");
        menu32.setKey("home4");
        menu32.setType("pic_photo_or_album");
        menu3.getSub_button().add(menu32);

       Menu menu=new Menu();
       menu.setButton(new BaseMenu[]{menu1,menu2,menu3});
       return menu;
    }
}
