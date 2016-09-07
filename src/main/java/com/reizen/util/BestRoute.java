package com.reizen.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestRoute {

  public static Map<String, Object> routeOptimum(List<Map<String, Double>> list, Map<String, Double> start,String targets){

    // return 해줄 데이터 선언 및 초기화;
    Map<String, Object> result = new HashMap<String, Object>();
    String resultPath = null;
    double resultDistance = 999999999;

    // 들어온 경로의 수 파악 및 데이터 준비
    int size = list.size();
    List<Map<String, Double>> data = new ArrayList<>();
    String end = "t"+(list.size()+1);

    // 시작 데이터를 기본으로 data에 기본 방향 설정 n! 에서 n 부분 수행
    for (String key : start.keySet()) {
      Map<String, Double> value = new HashMap<>();
      value.put(key, start.get(key));
      data.add(value);
    }

    if (size != 0) {
      toFor(data,list, size,end,targets);
    } else {
      data.get(0).put("t1t2", list.get(0).get("t2")+data.get(0).get("t1"));
      data.get(0).remove("t1");
    }

    // 계산된 데이터 중 최소거리 찾기
    for (int i = 0; i < data.size(); i++) {
      for (String key : data.get(i).keySet()) {
        if (key.length() == (size+1)*2 && resultDistance > data.get(i).get(key)) {
          resultDistance = data.get(i).get(key);
          resultPath = key;
        }
      }
    }

    // 결과 값 셋팅 후 반환
    result.put("path", resultPath);
    result.put("distance", resultDistance);
    return result;
  }

  public static void toFor(List<Map<String, Double>> data , List<Map<String, Double>> list, int size,String end,String targets){

    // 현재 키의 길이를 판단 data에 기존 데이터 제거를 하지 않아, 최신의 데이터를 찾으려는 용도로 사용
    int keyLength = 0;
    for (String key : data.get(data.size()-1).keySet()) {
      keyLength = key.length();
    }

    // 작업 수행
    // data를 기본으로 n * (n-1) 수행
    // 밖의 for문 2개는 현재 경로 / 그 다음 for문 2개는 추가될 경로
    // ex) t1 + t2 = t1t2
    // key 는 현재까지의 경로로 1번 수행 했으면 t1, t2, t3 등이 될 수 있다.
    // key2 는 현재 위치에서 움직일 경로로 현재 위치가 t1 이라면, t2 t3 등이 될 수 있다.
    // key.endswith(target) 은 현재 위치를 찾는 것으로 수행된 key의 마지막이 일치하는지 비교하여 일치하면 수행시킨다.
    // !key.contains(key2) 는 한번 지나간 곳으로 돌아가지 못하게 걸어주는 조건이다.
    int depth = size-1;
    for (int i = 0; i < data.size(); i++) {
      for (String key : data.get(i).keySet()) {
        if (keyLength == key.length()) {
          for (int j = 0; j < list.size(); j++) {
            for (String key2 : list.get(j).keySet()) {
              if (!key.contains(key2)) {
                String target = targets;
                for (String ks : list.get(j).keySet()){
                  target = target.replace(ks, "");
                }
                if (checkIf(depth,key,target,key2)) {
                  Map<String, Double> value = new HashMap<>();
                  value.put(key+key2, data.get(i).get(key)+list.get(j).get(key2));
                  data.add(value);
                }
              }
            } 
          }
        }
      }
    }

    // 경로가 끝이 아니므로, 재귀
    if (depth != 0) {
      toFor(data,list, depth,end,targets);  
    }
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
