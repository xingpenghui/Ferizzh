package com.feri.ferizzh.pojo.menu;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/2/18 17:18
 */
public class BaseMenu {
    /*
    *  "type":"view",
               "name":"搜索",
               key*/
    private String type;
    private String name;
    private String key;
    private List<BaseMenu> sub_button;

    public List<BaseMenu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<BaseMenu> sub_button) {
        this.sub_button = sub_button;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
