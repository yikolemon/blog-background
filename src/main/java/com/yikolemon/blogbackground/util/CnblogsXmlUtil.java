package com.yikolemon.blogbackground.util;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.*;

/**
 * @author yikolemon
 * @date 2023/8/1 22:14
 * @description XML请求获取博客内容
 */
public class CnblogsXmlUtil {
    public static List<Element> getTargetElementByName(Document document){
        Element rootElement = document.getRootElement();
        rootElement.normalize();
        // 通过element对象的elementIterator方法获取迭代器
        Queue<Element> elements=new LinkedList<>();
        elements.add(rootElement);
        //在没找到之前一直层序遍历
        ArrayList<Element> res = new ArrayList<>();
        while (!elements.isEmpty()){
            Element poll = elements.poll();
            Iterator<Element> iterator = poll.elementIterator();
            while (iterator.hasNext()){
                Element next = iterator.next();
                if ("member".equals(next.getName())){
                    removeRedundantNode(next);
                    res.add(next);
                }else {
                    elements.add(next);
                }
            }
        }
        return res;
    }

    private static void removeRedundantNode(Element element){
        Queue<Element> elements=new LinkedList<>();
        elements.add(element);
        while (!elements.isEmpty()){
                Element curElement = elements.poll();
                curElement.content().removeIf(node -> node.getNodeType()==Node.TEXT_NODE&&node.getText().startsWith("\n"));
                elements.addAll(curElement.elements());
        }

    }

    /**
     * 通过List<Element> member列表，获取其中的name-value键值对，以Map<String,String>形式返回
     */
    public static Map<String, String> getMemberKV(List<Element> list){
        HashMap<String, String> map = new HashMap<>();
        for (Element tempElement : list) {
            Element nameElement = tempElement.element("name");
            Element valueElement = tempElement.element("value");
            String nameStr = nameElement.getText();
            //在value解析时，会把\n解析成一个defaultText对象，所以需要去除这些对象
            String valStr = getValStr(valueElement);
            map.put(nameStr,valStr);
        }
        return map;
    }

    private static String getValStr(Element element){
        StringBuilder builder=new StringBuilder();
        Queue<Element> queue=new LinkedList<>();
        queue.add(element);
        while (!queue.isEmpty()){
            Element poll = queue.poll();
            List<Node> content = poll.content();
            if (content.size()==1&&content.get(0).getNodeType()==Node.TEXT_NODE){
                if (builder.length()>0){
                    builder.append(",");
                }
                builder.append(content.get(0).getText());
            }else {
                if (content.size()>0){
                    queue.addAll(poll.elements());
                }
            }
        }
        return builder.toString();
    }

    /**
     * 通过dom4j的document获取其中所有需要的kv对
     */
    public static Map<String,String> getKVByDocument(Document document){
        List<Element> list = getTargetElementByName(document);
        return getMemberKV(list);
    }


}
