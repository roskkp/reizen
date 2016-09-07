package com.reizen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reizen.dao.ScheduleDao;
import com.reizen.domain.Schedule;
import com.reizen.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  @Autowired
  ScheduleDao scheduleDao;

  @Override
  public int addSchedule(Schedule sc) {
    return scheduleDao.insertSchedule(sc);
  }

  @Override
  public Map<String, Object> listSchedule(Map<String, Object> params) {
    Map<String, Object> result = new HashMap<>();
    try {
      List<Schedule> data = scheduleDao.listSchedule(params);
      result.put("data", data);
      int i = 1;
      for (Schedule schedule : data) {
        params.put("sdno"+i, schedule.getScheduleNo());
        i++;
      }
      if (params.get("sdno1") != null) {
        Map<String, Object> names = scheduleDao.getLocationNames(params);
        List<String> imgSource = new ArrayList<String>();
        for (int j = 1; j < names.size()+1; j++) {
          imgSource.add(names.get("img"+j).toString());
        }
        result.put("img", imgSource);
      }
    } catch (Exception e) {
      e.printStackTrace();
      result.put("data", "noData");
    }
    return result;
  }

  @Override
  public List<Schedule> activeSchedule(int no) {
    return scheduleDao.activeSelect(no);
  }

  @Override
  public List<Schedule> totalCount(int no) {
    return scheduleDao.totalCount(no);
  }

  @Override
  public int checkProceeding(int no) {
    int checkNo = scheduleDao.checkProceeding(no);
    if( checkNo == 0 ){
      return 0;
    }
    return checkNo;
  }

}
