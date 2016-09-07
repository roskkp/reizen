package com.reizen.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Excel {

  public static void excel(){
    WritableWorkbook workbook = null;
    
    // 시트 객체 생성
    WritableSheet sheet = null;
     
    // 셀 객체 생성
    Label label = null;
     
     
    // 저장할 파일 객체 생성
    File file = new File("C:\\test.xls");
     
     
    // 테스트 데이터
    HashMap hm_0 = new HashMap() ;
    hm_0.put("name", "홍길동") ;
    hm_0.put("age", "21") ;
     
    HashMap hm_1 = new HashMap() ;
    hm_1.put("name", "김영희") ;
    hm_1.put("age", "20") ;
     
    List list = new ArrayList();
    list.add(hm_0) ;
    list.add(hm_1) ;
     
     
    try{
         
        // 파일 생성
        workbook = Workbook.createWorkbook(file);
         
        // 시트 생성
        workbook.createSheet("sheet1", 0);
        sheet = workbook.getSheet(0);
         
        // 셀에 쓰기
        label = new Label(0, 0, "name");
        sheet.addCell(label);
         
        label = new Label(1, 0, "age");
        sheet.addCell(label);
         
         
         
        // 데이터 삽입
        for(int i=0; i < list.size(); i++){
            HashMap rs = (HashMap)list.get(i) ;
             
            label = new Label(0, (i+1), (String)rs.get("name"));
            sheet.addCell(label);
             
            label = new Label(1, (i+1), (String)rs.get("age"));
            sheet.addCell(label);
             
        }
         
         
        workbook.write();
        workbook.close();

    }catch(Exception e){
        e.printStackTrace();
    }
  }
}
