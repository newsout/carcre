package com.sout.carcre.serivce;

import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.mapper.bean.UserInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MainService {

    /*
    八维通获取数据，返回userinfo bean
     */
    public UserInfo getUserInfoByBWT(Integer user_id){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://59.110.174.204:7280/v1.0/api/app/user/getUserInfo";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("user_id", ""+user_id);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class);
        JSONObject returnJson = JSONObject.parseObject(response.getBody());
        JSONObject jsonObject= (JSONObject) returnJson.get("result");
        UserInfo bean=JSONObject.parseObject(String.valueOf(jsonObject.get("user")), UserInfo.class);
        return bean;
    }

}
