package com.reizen.dao;

import java.util.List;
import java.util.Map;

import com.reizen.domain.Schedule;

public interface ScheduleDao {

	public int insertSchedule(Schedule sc);

	public List<Schedule> listSchedule(Map<String, Object> params);

	public List<Schedule> activeSelect(int no);

	public List<Schedule> totalCount(int no);

	public Map<String, Object> getLocationNames(Map<String, Object> params);

	public int checkProceeding(int no);
	
	public List<Schedule> checkSchedule(int userNo);

}
