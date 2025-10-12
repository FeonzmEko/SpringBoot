package com.itheima.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CodeUtils {

    private String[] path = {"000000","00000","0000","000","00","0",""};
    public String generator(String tele){
        long hash = tele.hashCode();
        long encryption = 20020126;
        long result = hash ^ encryption;
        long nowTime = System.currentTimeMillis();
        long randomFactor = new java.util.Random().nextLong();
        result = result ^ nowTime ^ randomFactor;
        long code = result % 1000000;
        code = code < 0 ? -code:code;

        String codeStr = code + "";
        int len = codeStr.length();

        return path[len] + code;
    }

/*    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(new CodeUtils().generator("19913352864"));
        }
    }*/

    @Cacheable(value = "smsCode",key = "#tele")
    public String get(String tele){
        return null;
    }
}
