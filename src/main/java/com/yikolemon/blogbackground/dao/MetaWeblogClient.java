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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
                    //return Map2EntityUtil.maptoArticle(articleMap);
                    //TODO
                    return null;
                }
            } catch (DocumentException | ParseException e ) {
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

    private static Blog mapToBlog(Map<String,Object> map){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
        map.get("dateCreated");
        //时间进行特殊处理注入
//        String dateCreated = (String) map.remove("dateCreated");
//        //将description修改为content
//        String description = (String) map.get("description");
//        map.remove("description");
//        map.put("content",description);
//        String postid = (String) map.remove("postid");
//        map.put("id",postid);
//        List<String> categories = (List<String>)map.remove("categories");
//        for (String category : categories) {
//            if (category.contains("随笔分类")) {
//                map.put("category", category.replace("[随笔分类]", ""));
//                break;
//            }
//        }
//        if (map.containsKey("mt_keywords")){
//            String keywordsStr = (String)map.remove("mt_keywords");
//            String[] keywords = keywordsStr.split(",");
//            map.put("tags",keywords);
//        }
//        Article article = BeanUtil.mapToBean(map, Article.class, true);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss");
//        try {
//            Date parse = simpleDateFormat.parse(dateCreated);
//            article.setCreateTime(parse);
//            return article;
//        } catch (java.text.ParseException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }
}
