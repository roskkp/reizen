package com.reizen.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reizen.dao.MemoDao;
import com.reizen.domain.Memo;
import com.reizen.service.MemoService;

@Service
public class MemoServiceImpl implements MemoService {

	@Autowired
	MemoDao memoDao;
	
	@Override
	public List<Memo> getListFour(int no) {
		return memoDao.selectListFour(no);
	}
	@Override
	public List<Memo> getMemoList(int cid) {
		return memoDao.selectMemo(cid);
	}

	@Override
	public void writeMemo(int cid, int dsno, String content) {
		Map<String, Object> map = new HashMap<>();
		map.put("cid",cid);
		map.put("dsno",dsno);
		map.put("content",content);
		memoDao.insertMemo(map);
	}

	@Override
	public void removeMemo(int mno) {
		memoDao.deleteMemo(mno);
	}

	@Override
	public void updateMemo(int mno, String content) {
		Map<String, Object> map = new HashMap<>();
		map.put("mno",mno);
		map.put("content",content);
		memoDao.updateMemo(map);
	}
	
  @Override
  public void insertMemoAlarm(int cid) {
    List<Map<String, Object>> list = memoDao.memoUserCheck(cid);
    for (Map<String, Object> map : list) {
      try {
        memoDao.insertMemoAlarm(map);
      } catch (Exception e) {
      }
    }
  }
  
  @Override
  public List<Map<String, Object>> checkAlarm(String numbers){
    return memoDao.checkAlarm(numbers);
  }
  @Override
  public void deleteMemoAlarm(int rno) {
    memoDao.deleteMemoAlarm(rno);
    
  }

}
