package com.atu.agent.trace;

/**
 * @author: Tom
 * @date: 2023/4/15 13:57
 * @description: TODO
 **/
public class TrackContext {

    private static final ThreadLocal<String> trackLocal = new ThreadLocal<String>();

    public static void clear(){
        trackLocal.remove();
    }

    public static String getLinkId(){
        return trackLocal.get();
    }

    public static void setLinkId(String linkId){
        trackLocal.set(linkId);
    }

}

