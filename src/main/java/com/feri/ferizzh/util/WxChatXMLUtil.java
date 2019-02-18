package com.feri.ferizzh.util;

import com.feri.ferizzh.pojo.message.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2019/2/18 12:04
 */
public class WxChatXMLUtil {

    private static XStream xStream=new XStream(new XppDriver(){
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out){
                @Override
                public void startNode(String name,Class clazz) {
                    super.startNode(name,clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    writer.write("<![CDATA[");
                    writer.write(text);
                    writer.write("]]>");
                }
            };
        }
    });
    //解析
    public static Map<String,String> parseXML(HttpServletRequest request){
        //dom4j
        Map<String,String> map=new HashMap<>();
        SAXReader reader=new SAXReader();
        try {
            //获取文档
            Document document=reader.read(request.getInputStream());
            Element root=document.getRootElement();
            List<Element> childs=root.elements();
            for(Element e:childs){
                map.put(e.getName(),e.getTextTrim());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
    //生成 --普通文本消息
    public static String createXML(TextMessage message){
        XStream xStream=new XStream();
        xStream.alias("xml",message.getClass());
        return xStream.toXML(message);
    }




}
