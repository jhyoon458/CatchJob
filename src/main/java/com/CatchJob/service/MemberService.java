package com.CatchJob.service;

import java.util.List;
import java.util.Map;

import com.CatchJob.model.Member;

public interface MemberService {
	
	public List<Member> getListMembers(Map<String, String> map);

	public Member getMember(int mberIndex);

	public boolean login(String mberId, String mberPw);

	public boolean join(Member member);
	
	public boolean updateMember(Member member);

	public boolean deleteMember(Member member);
	
	public boolean socialJoin(Member member);
	
	public Member getMemberById(String mberId);
	
	public Member getOauthId(String mberId,String oauthId);

	public Map<String, Object> getMessageList(Map<String, Object> data);
	
	public int calPageTotalCount(int totalCount, int numOfMsgPage);
	
	public int getStartPage(int pageNum);
	
	public int getEndPage(int pageNum);
}
