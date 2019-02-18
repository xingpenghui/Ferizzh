package com.feri.ferizzh.util;

import java.io.StringReader;
import java.util.Comparator;
import java.util.TreeSet;

/**
 *@Author feri
 *@Date Created in 2019/2/18 11:34
 */
public class SignUtil {
    //自定义的token 必须和微信公众号服务器的配置一样
    private static final String TOKEN="xingtoken";

    public static boolean checkSign(String signature, String timestamp,String nonce){
        //1、将token、timestamp、nonce三个参数进行字典序排序
        TreeSet<String> treeSet=new TreeSet<>();
        treeSet.add(TOKEN);
        treeSet.add(timestamp);
        treeSet.add(nonce);
        //2、将三个参数字符串拼接成一个字符串
        StringBuffer buffer=new StringBuffer();
        for(String s:treeSet){
            buffer.append(s);
        }
        System.out.println("字典顺序："+buffer);
        //3、进行sha1加密
        String jm=EncryptUtil.SHAEnc(EncryptUtil.SHA1,buffer.toString());
        //4、开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return jm.equals(signature);
    }

}
