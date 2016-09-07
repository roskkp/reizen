package com.reizen.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonArraySort {

	public JsonArraySort() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<JSONObject> updateJsonArray(String jsonData) {
    JSONArray jsonArray = new JSONArray(jsonData);
    List<JSONObject> jsonValues = new ArrayList<>();

    for (int i = 0; i < jsonArray.length(); i++) {
      jsonValues.add(jsonArray.getJSONObject(i));
    }
    Collections.sort( jsonValues, new Comparator<JSONObject>() {
      //You can change "Name" with "ID" if you want to sort by ID
      private static final String KEY_NAME = "time";

      @Override
      public int compare(JSONObject a, JSONObject b) {
        String valA = new String();
        String valB = new String();

        try {
          valA = (String) a.get(KEY_NAME);
          valB = (String) b.get(KEY_NAME);
        } 
        catch (JSONException e) {
          System.out.println("정렬 실패");
        }
        return valA.compareTo(valB);
      }
    });
    
    return jsonValues;
	}
}
