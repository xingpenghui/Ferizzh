package com.feri.ferizzh.app;

import com.feri.ferizzh.pojo.message.TextMessage;
import com.feri.ferizzh.util.SignUtil;
import com.feri.ferizzh.util.WxChatXMLUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2019/2/18 11:28
 */
@Controller
public class IndexApp {

    @RequestMapping(value = "wxchartindex.do",method={RequestMethod.GET,RequestMethod.POST})
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        if (request.getMethod().toUpperCase().equals("GET")){
            //认证
            String signature=request.getParameter("signature");
            String timestamp=request.getParameter("timestamp");
            String nonce=request.getParameter("nonce");
            String echostr=request.getParameter("echostr");
            if(SignUtil.checkSign(signature,timestamp,nonce)){
                //认证来自微信
                //若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
                response.getWriter().write(echostr);
            }
        }else{
            //消息交互
            Map<String,String> map=WxChatXMLUtil.parseXML(request);
            String msg="";
            if(map.containsKey("MsgType")) {
                switch (map.get("MsgType")){
                    case "event":
                        //事件
                        if(map.containsKey("Event")){
                            switch (map.get("Event")){
                                case "subscribe":
                                    //用户订阅
                                    System.out.println("关注……");
                                    msg="感谢你的关注，我们会为你提供贴心的服务……";
                                    break;
                                case "unsubscribe":
                                    //用户取消订阅
                                    System.out.println("取消关注……");
                                    break;
                            }
                        }
                        break;
                    case "text":
                        //文本消息
                        System.out.println("接收消息："+map.get("Content"));
                        String m=map.get("Content");
                        switch (m){
                            case "1":
                                StringBuffer buffer=new StringBuffer();
                                buffer.append("1、进入首页 home\r\n");
                                buffer.append("2、查看简历 jianli\r\n");
                                buffer.append("3、官网 oa\r\n");
                                buffer.append("4、靓照 lz\r\n");
                                buffer.append("5、Java java\r\n");
                                buffer.append("6、菜单 1\r\n");
                                msg=buffer.toString();
                                break;
                            case "home":
                                msg="请点击主页：http://www.1000phone.com";

                                break;
                            case "jianli":
                                msg="优秀简历，请点击主页：http://www.lugege.design:8080/note/";
                                break;
                                default:
                                    msg="收到你的消息："+map.get("Content");
                                    break;
                        }

                        break;
                    case "image":
                        System.out.println("接收图片："+map.get("PicUrl"));
                        msg="收到你的图片";
                        break;
                    case "voice":
                        System.out.println("接收语音："+map.get("MediaId"));
                        msg="收到语音，正在解析……";
                        break;
                }
                TextMessage textMessage=new TextMessage();
                textMessage.setContent(msg);
                textMessage.setToUserName(map.get("FromUserName"));
                textMessage.setFromUserName(map.get("ToUserName"));
                textMessage.setMsgType("text");
                textMessage.setCreateTime(System.currentTimeMillis()/1000);
                String resStr=WxChatXMLUtil.createXML(textMessage);
                System.out.println("消息回复："+resStr);
                response.getWriter().print(resStr);
            }
        }
    }
}