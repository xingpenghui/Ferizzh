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
        if (request.getMethod().toUpperCase().equals("GET")){
            //认证
            /*
            * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
timestamp	时间戳
nonce	随机数
echostr*/
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
