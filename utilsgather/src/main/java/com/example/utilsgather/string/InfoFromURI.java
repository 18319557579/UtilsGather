package com.example.utilsgather.string;

import java.net.URI;
import java.net.URISyntaxException;

public class InfoFromURI {
    /**
     * 拿到下面中的resource
     * https://www.example.com:8080/path/to/resource?param1=value1&param2=value2#section1
     */
    public static String justGetResource(String url) throws URISyntaxException {
        URI uri = new URI(url);

        //获取路径
        String path = uri.getPath();

        // 从路径中提取资源部分
        String[] pathSegments = path.split("/");
        String resource = pathSegments[pathSegments.length - 1];

        return resource;
    }
}
