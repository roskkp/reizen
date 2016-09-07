package com.reizen.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class HttpSend {

  public static JSONObject getSend(String path) throws Exception {
    HttpClient httpClient = new DefaultHttpClient();
    
    HttpGet httpGet = new HttpGet(path);
    httpGet.setHeader("Accept","application/json");
    httpGet.setHeader("Content-Type","application/json");
    HttpResponse httpResponse = httpClient.execute(httpGet);
    HttpEntity entity = httpResponse.getEntity();
    InputStream is = entity.getContent();
    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    return (JSONObject)JSONValue.parse(br);
  }
}
