package com.reizen.dao;

import java.util.List;
import java.util.Map;

import com.reizen.domain.Memo;

public interface MemoDao {
	public List<Memo> selectListFour(int no);

	public List<Memo> selectMemo(int cid);

	public void insertMemo(Map<String, Object> params);

	public void deleteMemo(int mno);

	public void updateMemo(Map<String, Object> params);
	
	public List<Map<String, Object>> memoUserCheck(int cid);

	public void insertMemoAlarm(Map<String, Object> params);

  public List<Map<String, Object>> checkAlarm(String numbers);
  
	public void deleteMemoAlarm(Map<String, Object> params);
}
