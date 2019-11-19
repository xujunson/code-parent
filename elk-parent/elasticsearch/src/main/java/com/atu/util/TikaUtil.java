package com.atu.util;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用tika抽取文件内容
 */
public class TikaUtil {
    /**
     * 从其他文档里提取文件标题、内容
     *
     * @param path 文件路径
     * @return
     */
    public static Map<String, Object> getText(String path) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        File f = new File(path);
        Parser parser = new AutoDetectParser();
        InputStream is = new FileInputStream(f);
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
        ContentHandler handler = new BodyContentHandler();
        ParseContext context = new ParseContext();
        context.set(Parser.class, parser);
        //2、执行parser的parse()方法。
        parser.parse(is, handler, metadata, context);
        for (String name : metadata.names()) {
            if (name.equals("resourceName")) {
                resultMap.put("title", metadata.get(name));
            }

            System.out.println(name + ":" + metadata.get(name));
        }
        resultMap.put("content",handler.toString());
        System.out.println(handler.toString());
        is.close();
        return resultMap;
    }

    public static void main(String[] args) throws Exception {
       // getText("H:\\28.doc");
        getText("H:\\28.doc");
    }
}
