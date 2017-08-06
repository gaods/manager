package com.hesheng.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author caolin
 * 2015-11
 */
public class HttpClientUtil {
    private static PoolingHttpClientConnectionManager cm;
    private static String EMPTY_STR = "";
    private static String UTF_8 = "UTF-8";

    private static void init(){
        if(cm == null){
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(50);//整个连接池最大连接数
            cm.setDefaultMaxPerRoute(5);//每路由最大连接数，默认值是2
        }
    }

    /**
     * 通过连接池获取HttpClient
     * @return
     */
    private static CloseableHttpClient getHttpClient(){
        init();//可以使用静态块解决初始化问题
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     *
     * @param url
     * @return
     */
    public static String httpGetRequest(String url){
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, String> params) throws URISyntaxException{
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, String> headers,
                                        Map<String, String> params) throws URISyntaxException{
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, String> param: headers.entrySet()) {
            httpGet.addHeader(param.getKey(), param.getValue());
        }
        return getResult(httpGet);
    }

    public static String httpPostRequest(String url){
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    public static String httpPostJsonRequest(String url,String json)  {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE,  "application/json");

        //String encoderJson = null;
        //encoderJson = URLEncoder.encode(json, "utf-8");
        StringEntity entity=new StringEntity(json, "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, String> params) throws UnsupportedEncodingException{
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, String> headers,
            Map<String, String> params) throws UnsupportedEncodingException{
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, String> param: headers.entrySet()) {
            httpPost.addHeader(param.getKey(), param.getValue());
        }

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

        return getResult(httpPost);
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, String> params){
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> param: params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }

        return pairs;
    }

    public static String httpPutRequest(String url, Map<String, String> headers,
                                         Map<String, String> params) throws UnsupportedEncodingException{
        HttpPut httpPut = new HttpPut(url);
        for (Map.Entry<String, String> param: headers.entrySet()) {
            httpPut.addHeader(param.getKey(), param.getValue());
        }
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPut.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPut);
    }

    public static String httpDeleteRequest(String url, Map<String, String> headers) throws UnsupportedEncodingException{
        HttpDelete httpDelete = new HttpDelete(url);
        for (Map.Entry<String, String> param: headers.entrySet()) {
            httpDelete.addHeader(param.getKey(), param.getValue());
        }
        return getResult(httpDelete);
    }

    /**
     * 处理Http请求
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request){
        String result=null;
        //CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        try{
            CloseableHttpResponse response = httpClient.execute(request);
            //response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                //long len = entity.getContentLength();// -1 表示长度未知
                result = EntityUtils.toString(entity);
                result= new String(result.getBytes("ISO-8859-1"),"UTF-8");
                response.close();
                //httpClient.close();//连接池管理
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{

        }
        return result;
    }

}