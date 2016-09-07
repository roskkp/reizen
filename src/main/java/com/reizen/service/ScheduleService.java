package com.reizen.service;

import java.util.List;
import java.util.Map;

import com.reizen.domain.Schedule;

public interface ScheduleService {

	public int addSchedule(Schedule sc);

	public Map<String, Object> listSchedule(Map<String, Object> params);

	public List<Schedule> activeSchedule(int no);

	public List<Schedule> totalCount(int no);

	public int checkProceeding(int no);
}
