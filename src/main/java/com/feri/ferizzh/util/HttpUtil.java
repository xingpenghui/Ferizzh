package com.feri.ferizzh.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2019/1/24 09:58
 */
public class HttpUtil {
    public static final String GET="GET";
    public static final String POST="POST";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";
    /**
     * http 请求
     * @param data 请求的参数数据
     * @param url 请求路径
     * @return
     * @throws Exception
     */
    public static String httpRequest(String data, String url) throws Exception{
        BasicHttpClientConnectionManager connManager;
        connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );
        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }
    /**
     * 进行网络请求
     * @param method 请求方式 支持GET POST PUT DELETE
     * @param url 请求的资源路径
     * @param param  要传递的参数信息*/
    public static byte[] requestData(String method, String url, Map<String,String> param) throws IOException {
        //1、创建请求方式
        byte[] res=null;
        switch (method){
            case "GET":
                 res=sendRequest(new HttpGet(addParam(url,param)));
                break;
            case "POST":
                res= sendRequest(new HttpPost(url));
                break;
            case "PUT":
                res= sendRequest(new HttpPut(url));
                break;
            case "DELETE":
                res= sendRequest(new HttpDelete(url));
                break;
        }
        return res;
    }
    //基于HttpURLConnection
    public static byte[] requestDataJDK(String method, String resurl, Map<String,String> param) throws IOException {
        byte[] res=null;
        resurl=addParam(resurl,param);
        URL url=new URL(resurl);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(method);
        switch (method){
            case "POST":
            case "PUT":
            case "DELETE":
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                os.write(addParam(param).getBytes());
                os.flush();
                break;
        }
        System.out.println(httpURLConnection.getResponseCode());
        if(httpURLConnection.getResponseCode()==200){
            InputStream is=httpURLConnection.getInputStream();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            int len;
            byte[] data=new byte[1024];
            while ((len=is.read(data))!=-1){
                baos.write(data,0,len);
            }
            is.close();
            res=baos.toByteArray();
        }
        return res;
    }
    private static String addParam(String url,Map<String,String> param){
        if(param!=null && param.size()>0){
            StringBuffer buffer=new StringBuffer();
            buffer.append("?");
            for(String k:param.keySet()){
                buffer.append(k+"="+param.get(k)+"&");
            }
            if(buffer.lastIndexOf("&")>0){
                buffer.deleteCharAt(buffer.length()-1);
            }
            url=url+buffer.toString();
        }
        return url;
    }
    //获取字符串
    public static String getResStr(String method,String url){
        try {
           return parseStr(requestDataJDK(method,url,null));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取字符串
    public static String getResStr(String method,String url,Map<String,String> param){
        try {
          return parseStr(requestData(method,url,param));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //下载内容
    public static String downLoad(String url,Map<String,String> param, File file){
        try {
            byte[] data=requestDataJDK(HttpUtil.GET,url,param);
            if(data!=null && data.length>0) {
                FileUtils.writeByteArrayToFile(file, data);
                return file.getAbsolutePath();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String addParam(Map<String,String> param){
        StringBuffer buffer=new StringBuffer();
        if(param!=null && param.size()>0){

            buffer.append("?");
            for(String k:param.keySet()){
                buffer.append(k+"="+param.get(k)+"&");
            }
            if(buffer.lastIndexOf("&")>0){
                buffer.deleteCharAt(buffer.length()-1);
            }

        }
        return buffer.toString();
    }

    private static String parseStr(byte[] data){
        if(data!=null && data.length>0){
            return new String(data);
        }else {
            return null;
        }
    }
  private static byte[] sendRequest(HttpUriRequest request) throws IOException {
      HttpClient httpClient=HttpClientBuilder.create().build();
      HttpResponse httpResponse=httpClient.execute(request);
      if(httpResponse.getStatusLine().getStatusCode()==200){
          return EntityUtils.toByteArray(httpResponse.getEntity());
      }else {
          return null;
      }
  }
}
