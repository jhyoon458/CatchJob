package com.CatchJob.controller;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.CatchJob.commons.Constants;
import com.CatchJob.model.Interview;
import com.CatchJob.model.Review;
import com.CatchJob.service.EnterpriseService;
import com.CatchJob.service.FollowService;
import com.CatchJob.service.InterviewService;
import com.CatchJob.service.RecordService;
import com.CatchJob.service.ReviewService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

	@Autowired
	private EnterpriseService entService;
	@Autowired
	private RecordService recordService;
	@Autowired
	private InterviewService itvwService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private FollowService followService;
	

	@RequestMapping(value = "/EnterpriseService", method = RequestMethod.GET)
	public String entListForm() {
		// 기업 리스트 출력화면
		return null;
	}

	@RequestMapping(value = "/search")
	public String getEntList(String keyword, Model model, HttpSession session) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("keyword", keyword);		
		try {
			data.put("MBER_IDX",  (session.getAttribute("mberIndex").toString()));			
		}catch(NullPointerException e) {
			//System.out.println("로그인 안 한 상태임");
		}		
		//System.out.println("확인해보자!:"+entService.getEntList(data));
		
		                            
		Gson gson = new GsonBuilder().create();
		JsonArray myCustomArray = gson.toJsonTree(entService.getEntList(data)).getAsJsonArray();
		model.addAttribute("entList1",myCustomArray);
		// 기업 리스트 출력
		return "enterprise-list";
	}
  
	@RequestMapping(value = "/view")
	public String entDetailsForm(int entIndex,@RequestParam(defaultValue = "1")int page, HttpServletRequest req, Model model, HttpSession session)  {
		// 기업정보 표출될때마다 viewCount올리는 부분
		System.out.println("뷰 컨트롤러 진입---");
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("ENT_IDX", Integer.toString(entIndex));
		try {
			mapData.put("MBER_IDX",  (session.getAttribute("mberIndex").toString()));//0816인영추가					
		}catch (NullPointerException e) {
			System.out.println("단지.. 로그인 안 되어 있을 뿐");
		}
		
		try {
			mapData.put("CONN_IP", Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			System.out.println("errer");
		} 
		mapData.put("BROWSER", req.getHeader("User-Agent"));
		recordService.regViewRecord(mapData);
		// End

		// 리뷰 표출될 때 필요한 정보 : 기업식별번호, 질문번호(1~5)
//		Map<String, String> reviewMap = new HashMap<String, String>();
//		reviewMap.put("entIndex", Integer.toString(entIndex));
//		reviewMap.put("questionNum", "1");
		//System.out.println(reviewMap);
		model.addAttribute("viewDataJson", new Gson().toJson(entService.empCountGraph(entIndex)));
		model.addAttribute("entInfo", entService.getEntInfo(mapData));
		model.addAttribute("personJson", new Gson().toJson(entService.selectEntPeopleInfo(entIndex)));
		model.addAttribute("interviewPieChartJson", new Gson().toJson(itvwService.interviewPieChart(entIndex)));
		model.addAttribute("question", reviewService.question(entIndex));
		
		//int currentPage = 1;
		int currentPage= page;
		Map<String, Integer> dataItvw = new HashMap<String, Integer>();
		dataItvw.put("ENT_IDX", entIndex);
		//dataItvw.put("NUM_OF_ITVW_PER_PAGE", entIndex);
		dataItvw.put("PAGE_NUM", currentPage);
		model.addAttribute("interview", itvwService.getInterviewList(dataItvw));
		model.addAttribute("interviewJson", new Gson().toJson(itvwService.getInterviewList(dataItvw)));
//		System.out.println("페이징처리:"+itvwService.getInterviewList(dataItvw));
//		System.out.println("페이지 데이터: "+itvwService.interviewPageData(currentPage, entIndex));
		model.addAttribute("interviewPageData", itvwService.interviewPageData(currentPage, entIndex));
		
		return "enterprise-view";
	}
	
	@ResponseBody
	@RequestMapping(value = "/regFollow")
	public boolean regFollow (String entIndex, HttpSession session) {
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("MBER_IDX",  (session.getAttribute("mberIndex").toString()));
		mapData.put("ENT_IDX", entIndex );	
//		(int) (session.getAttribute("mberIndex"))
//		mapData.put("ENT_IDX", entIndex );	
		return followService.regFollowEnt(mapData);
	}
	@ResponseBody
	@RequestMapping(value = "/revFollow")
	public boolean revFollow (String entIndex, HttpSession session) {
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("MBER_IDX",  (session.getAttribute("mberIndex").toString()));
		mapData.put("ENT_IDX", entIndex );
		return followService.revFollowEnt(mapData);
	}
	
	
//	@ResponseBody
//	@RequestMapping(value = "/reviewList/{entIndex}")
//	public ResponseEntity<List<Review>> list(
//			@PathVariable("entIndex") int entIndex){		
//		ResponseEntity<List<Review>> entity = null;		
//		try {
//			List<Review> replyList = reviewService.reviewList(entIndex);
//			entity = new ResponseEntity<List<Review>>(replyList,HttpStatus.OK);
//		}catch(Exception e) {
//			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		return entity;
//	}
	@ResponseBody
	@RequestMapping(value = "/reviewList")
	public List<Review> list( int entIndex, @RequestParam(defaultValue = "1")int questionNum, @RequestParam(defaultValue = "1")int page, Model model){		
		System.out.println("컨트롤러 리뷰 entIndex: "+entIndex);
		Map<String, Integer> dataRvw = new HashMap<String, Integer>();
		int currentPage= page;		
		dataRvw.put("PAGE_NUM", currentPage);
		dataRvw.put("ENT_IDX", entIndex);		
		dataRvw.put("QESTN_NO", questionNum);
		System.out.println("리뷰: "+reviewService.getReviewsList(dataRvw));
		System.out.println("페이지 데이터: "+reviewService.reviewPageData(currentPage, entIndex, questionNum));
		model.addAttribute("reviewPageData", reviewService.reviewPageData(currentPage, entIndex, questionNum));
		return reviewService.getReviewsList(dataRvw);
	}
	
	@ResponseBody
	@RequestMapping(value = "/writeReview")
	public boolean writeReview(HttpSession session, Review review) throws IOException {
		System.out.println("writeReview-컨트롤러1"+review);
		
		//Review review = new Review();
		//review.setContents(req.getParameter("contents"));
		//review.setEntIndex(Integer.parseInt((req.getParameter("entIndex"))));
		//review.setEvaluationScore(Integer.parseInt(req.getParameter("evaluationScore")));
		review.setMberIndex((int) (session.getAttribute("mberIndex")));
		//review.setQuestionNum(Integer.parseInt(req.getParameter("questionNum")));
		review.setReviewFlag("1");
		//req.getParameter("contents");
		//req.getParameter("evaluationScore");

		System.out.println("writeReview-컨트롤러2"+review);
		boolean result = reviewService.insertReview(review);
		
		if(result) {
			System.out.println("컨트롤러: 리뷰 등록 성공"+result);
			return true;
		}else {
			System.out.println("컨트롤러: 리뷰 등록 실패"+result);
			return false;			
		}				
	}
	
	@ResponseBody
	@RequestMapping(value = "/itvwDuplicationCheck")
	public boolean interviewDuplicationCheck(HttpSession session, String entIndex) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("MBER_IDX",  (session.getAttribute("mberIndex").toString()));
		data.put("ENT_IDX", entIndex );
		return itvwService.interviewDuplicationCheck(data);
	}
	
	//@ResponseBody
	@RequestMapping(value = "/writeInterview")
	public String writeInterview(Interview interview, HttpSession session) {
		System.out.println("123456"+interview);
		interview.setMberIndex((int) (session.getAttribute("mberIndex")));
		interview.setIntrvwFlag("1");
//		// boolean result = entService.insertInterview(interview);
		itvwService.insertInterview(interview);
//		if(result) {
//			System.out.println("컨트롤러: 리뷰 등록 성공"+result);
//			return true;
//		}else {
//			System.out.println("컨트롤러: 리뷰 등록 실패"+result);
//			return false;			
//		}	
		return "redirect:view?entIndex="+interview.getEntIndex();
	}
	


}
