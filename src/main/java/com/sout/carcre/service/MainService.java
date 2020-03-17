package com.sout.carcre.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.bean.interfacebean.BaseTripResult;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.expression.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取八维通行程列表
     * @return 返回最后一次的行程数据
     */
    public BaseTripResult baseTriplist(Integer user_id){

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://59.110.174.204:7280/v1.0/api/app/trip/baseTriplist";
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", String.valueOf(user_id));
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<>(null, headers);
        /*执行http请求*/
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class);
        System.out.println("response");
        /*获取参数*/
        JSONObject returnJson = JSONObject.parseObject(response.getBody());
        JSONObject jsonObject= (JSONObject) returnJson.get("result");
        JSONObject jsonObject1= (JSONObject) jsonObject.get("page");

        JSONArray jsonArray= (JSONArray) jsonObject1.get("rows");
        System.out.println(jsonArray);
        /*jsonarray装List<bean>*/
        List<BaseTripResult> list=JSONObject.parseArray(jsonArray.toJSONString(),BaseTripResult.class);
        //判断行程最后一次时间范围是否为今天
//        int index=-1;//标定今天第一行程的标号
//        for(int i=list.size()-1;i>=0;i++){
//            String inTime=list.get(i).getInTime().split(" ")[1];//判断开始时间是否为今天
//            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
//            if(!simpleDateFormat.format(new Date()).equals(inTime)) {
//                index=i+1;break;
//            }
//        }
//        if(index==-1) return null;
        return list.get(list.size()-1);

    }


}
