package springboot06mybatis.utils;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import springboot06mybatis.common.Const;

import java.util.HashMap;

/**
 * ClassName:    HttpClient
 * Package:    springboot06mybatis.utils
 * Description:
 * Datetime:    2020/4/11   21:50
 * Author:   hewson.chen@foxmail.com
 */
public class HttpClient {
    //get请求
    public static String sendGetRequest(String URL,HashMap<String,String> map){
//        String URL= Const.GaoDeMapAPI;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.getForEntity(URL,String.class,map);

        String body = res.getBody();

        return body;
    }
}
