package com.yikolemon.blogbackground.dao;

import com.yikolemon.blogbackground.exception.FetchBlogException;
import com.yikolemon.blogbackground.util.CnblogsXmlUtil;

import com.yikolemon.blogbackground.util.MetaWeblogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author yikolemon
 * @date 2023/7/30 22:51
 * @description
 */
@Component
@Slf4j
public class MetaWbelogClient {

    private static final Logger logger=LoggerFactory.getLogger(MetaWbelogClient.class);

    private static final String CNBLOGS_URL="https://www.cnblogs.com";

    @Value("${cnblogs.url}")
    private String url;

    @Value("${cnblogs.username}")
    private String username;


    @Value("${cnblogs.token}")
    private String token;

    public Article getPost(String id) throws FetchBlogException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","application/xml");
        String body = MetaWeblogUtil.getBody(new String[]{id, username, token});
        httpPost.setEntity(new StringEntity(body));
        try {
            try (CloseableHttpClient client= HttpClients.createDefault()) {
                try (CloseableHttpResponse response = client.execute(httpPost)) {
                    int resCode = response.getCode();
                    if (resCode!=200){
                        //出错
                        throw new FetchBlogException("获取博客内容失败");
                    }else {
                        HttpEntity entity = response.getEntity();
                        String xml = EntityUtils.toString(entity);
                        Document document = DocumentHelper.parseText(xml);
                        Map<String, Object> articleMap = CnblogsXmlUtil.getKVByDocument(document);
                        return Map2EntityUtil.maptoArticle(articleMap);
                    }
                } catch (DocumentException | ParseException e ) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }}

    private String getReptileURl(Integer page){
        return CNBLOGS_URL + "/" +
                username +
                "/?page=" +
                page;
    }

    private List<String> getBlogList(Integer page){
        String reptileURl = getReptileURl(page);
        Document doc=null;
        try{
            doc=Jsoup.connect(reptileURl).get();
        }catch (IOException e) {
            logger.error("获取博客列表失败");
        }
        if (doc==null){
            return Collections.emptyList();
        }
        Elements elements = doc.getElementsByClass("postTitle2");
        ArrayList<String> blogIdList = new ArrayList<>();
        for (Element element : elements) {
            String href = element.attr("href");
            blogIdList.add( getBlogIdByHref(href));
        }
        return blogIdList;
    }

    private static String getBlogIdByHref(String href){
        int i = href.lastIndexOf('/');
        StringBuilder builder=new StringBuilder();
        for (int j = i+1; j < href.length(); j++) {
            char c = href.charAt(j);
            if (c<'0'||c>'9'){
                break;
            }else {
                builder.append(c);
            }
        }
        return  builder.toString();
    }
}
