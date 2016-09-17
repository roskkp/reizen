package com.reizen.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.reizen.domain.Memo;

public interface MemoService {
	public List<Memo> getListFour(int no);

	public Map<String, Object> getMemoList(int cid,HttpSession httpSession);

	public void writeMemo(int cid, int dsno, String content);

	public void removeMemo(int mno);

	public void updateMemo(int mno, String content);
	
  public void insertMemoAlarm(int cid);

  public List<Map<String, Object>> checkAlarm(String numbers);

  public void deleteMemoAlarm(int rno);
}
