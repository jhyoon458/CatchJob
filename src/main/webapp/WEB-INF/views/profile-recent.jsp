<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="include/header.jsp" flush="true" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/late/fontawesome-stars.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.barrating.min.js"></script>
<script>

$(function(){
	/* 팔로잉 기업 색칠 되어 있게 하는 것 */
	var recentViewJson = JSON.parse('${recentViewJson}');	
	for(var i in recentViewJson ){
		if(recentViewJson[i].following != 0){
			/* 팔로잉 기업 */
			var $this = $("#follow"+i);
		   	$this.toggleClass("fa-heart-o");
			$this.toggleClass("fa-heart");
		}
		
	};

  	/* 버튼 클릭 */
	$(".follow-btn").click(function (e) {	
      var $this = $(this).find("i");	
      var fa = $this.hasClass("fa");
      var entIndex = $this.next().val();
		
	  /* 로그인 상태임 */
	  if($this.hasClass("fa-heart-o")){
		  /* 팔로잉 기업 추가  */
		  $.ajax({
		  		url:"${pageContext.request.contextPath}/enterprise/regFollow",				  		
		  		data:{entIndex : entIndex},
		  		type: "post",
		  		dataType:"json",
		  		success: function(result){
		  			/* 성공 */
		    	    alert("팔로잉 하였습니다. 팔로잉한 회사는 [마이페이지 > 팔로잉]에서 확인할 수 있습니다.");
		    	    $this.toggleClass("fa-heart-o");
		    	    $this.toggleClass("fa-heart");
		  		}	  		
		  })
	  }else{
		  if(confirm("팔로잉을 중단하시겠습니까?") != 0 ){
			  /* 팔로잉 기업 해지  */
			  $.ajax({
			  		url:"${pageContext.request.contextPath}/enterprise/revFollow",				  		
			  		data:{entIndex : entIndex},
			  		type: "post",
			  		dataType:"json",
			  		success: function(result){
			  			/* 성공 */
			  			 alert("팔로잉이 해제되었습니다.");
						 $this.toggleClass("fa-heart");
						 $this.toggleClass("fa-heart-o");
			  		}					  		
			  })					 
		  }
	  }		  

      
	});
	
})


</script>


<article class="container">
	<div class="contents-wrap">
	  
	  <h1>최근 본 기업</h1>
	  		<c:forEach begin="0" varStatus="status"  var="recent" items="${recentView}">
			<div class="row ent-list">
	
				<div class="col-sm-8">
					<div class="row">
						<a href="${pageContext.request.contextPath}/enterprise/view?entIndex=${recent.entIndex}" class="p25">${recent.entName}</a>
						<a href="#" class="follow follow-btn follow">
							<i class="fa fa-heart-o follow" id="follow${status.index}"></i><!-- 123 -->
							<input type="hidden" value="${recent.entIndex}">
						</a>
					</div>
					<div class="row visible-lg visible-md visible-sm">
						${recent.industryName} | ${recent.bcityName} ${recent.signguName}
					</div>
					<div class="row">
						<p class="p-1">평균연봉 ${recent.salaryAvg} 만원 </p>
					</div>
				</div>
	
				<div class="col-sm-4">
					<div class="row text-center">
						<c:forEach begin="1" end="${recent.evaluationAvg}" step="1">
							<span class="stars-on"></span>
						</c:forEach>
						<c:forEach begin="${recent.evaluationAvg}" end="4" step="1">
							 <span class="stars-off"></span>
						</c:forEach>
						<span class="ent-score">${recent.evaluationAvg}</span>
					</div>
					<div class="row">
						<div class="col-xs-6 text-center cell-1">
							<strong>${recent.reviewCount}</strong>
							<a href="${pageContext.request.contextPath}/enterprise/view?entIndex=${recent.entIndex}#section2"><br>리뷰코멘트</a>
						</div>
						<div class="col-xs-6 text-center cell-2">
							<strong>${recent.interviewCount}</strong>
							<a href="${pageContext.request.contextPath}/enterprise/view?entIndex=${recent.entIndex}#section3"><br>면접정보</a>
						</div>
					</div>
				</div>
	
			</div>
		</c:forEach>
	</div>
</article>

<jsp:include page="include/footer.jsp" flush="true" />