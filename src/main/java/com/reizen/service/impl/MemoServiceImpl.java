package com.reizen.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reizen.dao.MemoDao;
import com.reizen.domain.Memo;
import com.reizen.domain.User;
import com.reizen.service.MemoService;
import com.reizen.util.CalculateTime;

@Service
public class MemoServiceImpl implements MemoService {

	@Autowired
	MemoDao memoDao;
	
	@Override
	public List<Memo> getListFour(int no) {
		return memoDao.selectListFour(no);
	}
	@Override
	public Map<String, Object> getMemoList(int cid,HttpSession httpSession) {
    Map<String, Object> result = new HashMap<String, Object>();
    List<Memo> list = memoDao.selectMemo(cid);
	  int i = 0;
    for (Memo memo : list) {
      memo.setDateAgo(CalculateTime.calc(memo.getRegDate()));
      list.set(i, memo);
      i++;
    }
    if(httpSession.getAttribute("user")!=null){
      result.put("nick", ((User)(httpSession.getAttribute("user"))).getNickName());
    }else{
      result.put("nick",null);
    }
    result.put("data",list);
		return result;
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
