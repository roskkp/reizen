package com.reizen.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestRoute {

  public static Map<String, Object> routeOptimum(Map<String,Map<String, Double>> list, Map<String, Double> start,String targets){
    // return 해줄 데이터 선언 및 초기화;
    Map<String, Object> result = new HashMap<String, Object>();
    // 들어온 경로의 수 파악 및 데이터 준비
    int size = list.size();
    Map<String,Map<String, Double>> data = new HashMap<>();
    String end = "t"+(list.size()+1);
    // 시작 데이터를 기본으로 data에 기본 방향 설정 n! 에서 n 부분 수행
    int index = 0;
    for (String key : start.keySet()) {
      Map<String, Double> value = new HashMap<>();
      value.put(key, start.get(key));
      data.put(""+index,value);
      index++;
    }
    if (size != 0) {
      data = toFor(data,list, size,end,targets);
    } else {
      result.put("path", "t1t2");
      result.put("distance",list.get("0").get("t2")+data.get("0").get("t1"));
    }
    // 계산된 데이터 중 최소거리 찾기
    // 결과 값 셋팅 후 반환
    result.put("path", data.get("end").keySet().toString().replace("[", "").replace("]", ""));
    return result;
  }

  public static Map<String,Map<String, Double>> toFor(Map<String,Map<String, Double>> data , Map<String,Map<String, Double>> list, int size,String end,String targets){
    Map<String,Map<String, Double>> result = new HashMap<>();
    String resultPath = null;
    double resultDistance = 999999999; 
    // 작업 수행
    // data를 기본으로 n * (n-1) 수행
    // 밖의 for문 2개는 현재 경로 / 그 다음 for문 2개는 추가될 경로
    // ex) t1 + t2 = t1t2
    // key 는 현재까지의 경로로 1번 수행 했으면 t1, t2, t3 등이 될 수 있다.
    // key2 는 현재 위치에서 움직일 경로로 현재 위치가 t1 이라면, t2 t3 등이 될 수 있다.
    // key.endswith(target) 은 현재 위치를 찾는 것으로 수행된 key의 마지막이 일치하는지 비교하여 일치하면 수행시킨다.
    // !key.contains(key2) 는 한번 지나간 곳으로 돌아가지 못하게 걸어주는 조건이다.
    int depth = size-1;
    int index = 0;
    for (int i = 0; i < data.size(); i++) {
      for (String key : data.get(""+i).keySet()) {
        for (int j = 1; j < list.size()+1; j++) {
          for (String key2 : list.get("t"+j).keySet()) {
            if (!key.contains(key2)) {
              String target = targets;
              for (String ks : list.get("t"+j).keySet()){
                target = target.replace(ks, "");
              }
              if (checkIf(depth,key,target,key2)) {
                if (depth != 0) {
                  Map<String, Double> value = new HashMap<>();
                  value.put(key+key2, data.get(""+i).get(key)+list.get("t"+j).get(key2));
                  result.put(""+index,value); 
                  index++;
                } else {
                  if (resultDistance > data.get(""+i).get(key)+list.get("t"+j).get(key2)) {
                    resultDistance = data.get(""+i).get(key)+list.get("t"+j).get(key2);
                    resultPath = key+key2;
                  }
                }
              }
            }
          } 
        }
      }
    }
    int length = Integer.parseInt(targets.substring(targets.lastIndexOf("t")+1))-1;
    if ( length - depth != 1 && depth > 1) {
      System.out.println("length : "+length+" / depth : "+depth+" / size : "+size);
      System.out.println(length - depth);
      double targetValue;
      String targetName;
      List<String> targetNames = new ArrayList<>();
      Map<String, Double> value = null;
      Map<String,Map<String, Double>> results = new HashMap<>();
      for (int k = 0; k < length; k++) {
        targetValue = 999999999;
        targetName = "t"+(k+1);
        for (int a = 0; a < length; a++) {
          if (k != a && length - depth != targetNames.size()) {
            targetNames.add("t"+(a+1)); 
          }
          System.out.println("targetName : "+targetName);
          System.out.println("targetNames : "+targetNames);
        }
        for (int i = 0; i < result.size(); i++) {
          for (String key : result.get(""+i).keySet()) {
            value = new HashMap<>();
            targetValue = result.get(""+i).get(key);
            value.put(key, targetValue);
//            System.out.println("key : "+key+" / value : "+targetValue);
//            if (key.endsWith(targetName)) {
//              if (targetValue > result.get(""+i).get(key)) {
//                value = new HashMap<>();
//                targetValue = result.get(""+i).get(key);
//                value.put(key, targetValue);
//                System.out.println("key : "+key+" / value : "+targetValue);
//              }
//            }
          }
        }
//        results.put(""+k, value);
      }
//      result = results;
      System.out.println(result.size());
    } else if (depth == 1) {
      double targetValue;
      String targetName;
      Map<String, Double> value = null;
      Map<String,Map<String, Double>> results = new HashMap<>();
      for (int k = 0; k < length; k++) {
        targetValue = 999999999;
        targetName = "t"+(k+1);
        for (int i = 0; i < result.size(); i++) {
          for (String key : result.get(""+i).keySet()) {
            if (key.endsWith(targetName)) {
              if (targetValue > result.get(""+i).get(key)) {
                value = new HashMap<>();
                targetValue = result.get(""+i).get(key);
                value.put(key, targetValue);
              }
            }
          }
        }
        results.put(""+k, value);
      }
      result = results;
    }
    // 경로가 끝이 아니므로, 재귀
    if (depth != 0) {
      result = toFor(result,list, depth,end,targets);  
    } else {
      Map<String, Double> value = new HashMap<>();
      value.put(resultPath,resultDistance);
      result.put("end",value);
    }
    return result;
  }
  // 중복된 for 문을 줄이려고 동적으로 if 문 변경
  public static boolean checkIf (int depth, String key, String target, String key2) {
    if (depth == 0 ) {
      return key.endsWith(target) && !key.contains(key2);
    } else {
      return key.endsWith(target) && !key.replace(target, "").equals(key2)  && !key.contains(key2);
    }
  }
}
