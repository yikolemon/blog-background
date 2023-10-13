package com.yikolemon.blogbackground.dao;

import com.yikolemon.blogbackground.entity.po.Blog;
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
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yikolemon
 * @date 2023/7/30 22:51
 * @description
 */
@SuppressWarnings("ALL")
@Repository
@Slf4j
public class MetaWeblogClient {

    private static final Logger logger=LoggerFactory.getLogger(MetaWeblogClient.class);

    private static final String CNBLOGS_URL="https://www.cnblogs.com";

    @Value("${cnblogs.metaWeblog.url}")
    private String url;

    @Value("${cnblogs.metaWeblog.username}")
    private String username;


    @Value("${cnblogs.metaWeblog.token}")
    private String token;

    public Blog getPost(String id) throws FetchBlogException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","application/xml");
        String body = MetaWeblogUtil.getBody(new String[]{id, username, token});
        httpPost.setEntity(new StringEntity(body));
        try {
            try (CloseableHttpClient client= HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(httpPost)) {
                int resCode = response.getCode();
                if (resCode!=200){
                    //出错
                    throw new FetchBlogException("获取博客内容失败");
                }else {
                    HttpEntity entity = response.getEntity();
                    String xml = EntityUtils.toString(entity);
                    org.dom4j.Document document = DocumentHelper.parseText(xml);
                    Map<String, String> articleMap = CnblogsXmlUtil.getKVByDocument(document);
                    return mapToBlog(articleMap);
                }
            } catch (DocumentException | ParseException e ) {
                throw new RuntimeException(e);
            } catch (java.text.ParseException e) {
                throw new RuntimeException(e);
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

    public List<String> getBlogList(Integer page){
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

    private static Blog mapToBlog(Map<String,String> map) throws java.text.ParseException {
        String id=map.get("postid");
        String title=map.get("title");
        String content=map.get("description");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        String dateCreated = map.get("dateCreated");
        Date creatTime = simpleDateFormat.parse(dateCreated);
        String categoryStr =getCategoryStr(map.get("categories"));
        return Blog.builder().id(id)
                .title(title)
                .content(content)
                .createTime(creatTime)
                .categoryStr(categoryStr)
                .build();
    }


    private static String getCategoryStr(String categories){
        if (StringUtils.isEmpty(categories)){
            return "";
        }
        String[] split = categories.split(",");
        for (String str : split) {
            if (str.contains("[随笔分类]")){
                return str.substring(6);
            }
        }
        return "";
    }
}
