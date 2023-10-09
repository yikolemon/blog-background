package com.yikolemon.blogbackground.util;


public class MetaWeblogUtil {


    private static final String REQUEST_HEAD="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<methodCall>\n<methodName>metaWeblog.getPost</methodName>\n<params>";

    private static final String REQUEST_TAIL="</params>\n</methodCall>";

    private static final String PARAM_HEAD="<param>\n<value><string>";

    private static final String PARAM_TAIL="</string></value>\n</param>";;


    public static String getBody(String[] strs){
        StringBuilder builder=new StringBuilder(REQUEST_HEAD);
        for (String str : strs) {
            builder.append(getParamStr(str));
        }
        builder.append(REQUEST_TAIL);
        return builder.toString();
    }

    public static String getParamStr(String parm){
        StringBuilder builder = new StringBuilder(PARAM_HEAD);
        builder.append(parm);
        builder.append(PARAM_TAIL);
        return builder.toString();
    }

}
