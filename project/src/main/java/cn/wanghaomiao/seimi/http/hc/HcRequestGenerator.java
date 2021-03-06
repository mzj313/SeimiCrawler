/*
   Copyright 2015 Wang Haomiao<et.tw@163.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package cn.wanghaomiao.seimi.http.hc;

import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.exception.SeimiProcessExcepiton;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.http.SeimiAgentContentType;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import cn.wanghaomiao.seimi.struct.Request;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 汪浩淼 et.tw@163.com
 * @since 2016/4/14.
 */
public class HcRequestGenerator {
    public static RequestBuilder getHttpRequestBuilder(Request request, CrawlerModel crawlerModel) {
        RequestBuilder requestBuilder;
        BaseSeimiCrawler crawler = crawlerModel.getInstance();
        if (request.isUseSeimiAgent()) {
            if (StringUtils.isBlank(crawler.seimiAgentHost())) {
                throw new SeimiProcessExcepiton("SeimiAgentHost is blank.");
            }
            String seimiAgentUrl = "http://" + crawler.seimiAgentHost() + (crawler.seimiAgentPort() != 80 ? (":" + crawler.seimiAgentPort()) : "") + "/doload";
            requestBuilder = RequestBuilder.post().setUri(seimiAgentUrl);
            List<NameValuePair> nameValuePairList = new LinkedList<>();
            requestBuilder.addParameter("url", request.getUrl());
            if (StringUtils.isNotBlank(crawler.proxy())) {
                nameValuePairList.add(new BasicNameValuePair("proxy", crawler.proxy()));
            }
            if (request.getSeimiAgentRenderTime() > 0) {
                nameValuePairList.add(new BasicNameValuePair("renderTime", String.valueOf(request.getSeimiAgentRenderTime())));
            }
            if (StringUtils.isNotBlank(request.getSeimiAgentScript())) {
                nameValuePairList.add(new BasicNameValuePair("script", request.getSeimiAgentScript()));
            }
            //如果针对SeimiAgent的请求设置是否使用cookie，以针对请求的设置为准，默认使用全局设置
            if ((request.isSeimiAgentUseCookie() == null && crawlerModel.isUseCookie()) || (request.isSeimiAgentUseCookie() != null && request.isSeimiAgentUseCookie())) {
                nameValuePairList.add(new BasicNameValuePair("useCookie", "1"));
            }
            if (request.getParams() != null && request.getParams().size() > 0) {
                nameValuePairList.add(new BasicNameValuePair("postParam", JSON.toJSONString(request.getParams())));
            }
            if (request.getSeimiAgentContentType().val() > SeimiAgentContentType.HTML.val()) {
                nameValuePairList.add(new BasicNameValuePair("contentType", request.getSeimiAgentContentType().typeVal()));
            }
            requestBuilder.setEntity(new UrlEncodedFormEntity(nameValuePairList, Charset.forName("utf8")));
        } else {
            if (HttpMethod.POST.equals(request.getHttpMethod())) {
                requestBuilder = RequestBuilder.post().setUri(request.getUrl());
                if (request.getParams() != null) {
                    List<NameValuePair> nameValuePairList = new LinkedList<>();
                    for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                        nameValuePairList.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                    }
                    requestBuilder.setEntity(new UrlEncodedFormEntity(nameValuePairList, Charset.forName("utf8")));
                }
            } else {
                requestBuilder = RequestBuilder.get().setUri(request.getUrl());
                if (request.getParams() != null) {
                    for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                        requestBuilder.addParameter(entry.getKey(), entry.getValue());
                    }
                }
            }
            
            //mzj modify
            HttpHost proxy = crawlerModel.getProxy();
//            if(proxy == null) proxy = new HttpHost("219.150.242.54", 9999);
//            if(proxy == null) proxy = new HttpHost("124.88.67.52", 843);
//            if(proxy == null) proxy = new HttpHost("58.59.68.91", 9797);
//            if(proxy == null) proxy = new HttpHost("58.67.159.50", 80);
//            if(proxy == null) proxy = new HttpHost("182.39.153.2", 8118);
//            if(proxy == null) proxy = new HttpHost("123.13.205.185", 8080);//
            
//            System.out.println("====HcRequestGenerator. 代理服务器: " + proxy);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setCircularRedirectsAllowed(true).build();

            String userAgent = crawlerModel.isUseCookie() ? crawlerModel.getCurrentUA() : crawler.getUserAgent();
            //request.addHeader("User-Agent", "Mozilla/4.0 (compatible;MSIE 6.0; Windows NT 5.0)");
//            System.out.println("====HcRequestGenerator. User-Agent: " + userAgent);
            
			requestBuilder.setConfig(config).setHeader("User-Agent", userAgent);
            requestBuilder.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            requestBuilder.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            
            //mzj add
            requestBuilder.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
            requestBuilder.setHeader("Cookie", "lianjia_uuid=1a6aa839-9a41-475b-b53c-2441b3aedd6a; select_city=110000; all-lj=3e56656136803bc056cf7a329e54869e; _smt_uid=589dbddb.434cda67; CNZZDATA1253477573=1143617682-1486731511-%7C1486819843; CNZZDATA1254525948=146968761-1486730496-%7C1486816905; CNZZDATA1255633284=452336933-1486731501-%7C1486818322; CNZZDATA1255604082=1701734035-1486729061-%7C1486820864; _gat=1; _gat_past=1; _gat_global=1; _gat_new_global=1; _ga=GA1.2.330336557.1486732767; _gat_dianpu_agent=1; lianjia_ssid=3fa5a1b9-211f-4fe8-a8d8-d0a521e0358e");
            requestBuilder.setHeader("Pragma", "no-cache");
            requestBuilder.setHeader("Upgrade-Insecure-Requests", "1");
            requestBuilder.setHeader("Connection", "keep-alive");
        }
        if (!CollectionUtils.isEmpty(request.getHeader())) {
            for (Map.Entry<String, String> entry : request.getHeader().entrySet()) {
                requestBuilder.setHeader(entry.getKey(), entry.getValue());
            }
        }
        return requestBuilder;
    }
}
