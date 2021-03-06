<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bower_components/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">
<script>
	$(document).ready(function() {
		$('#sidebarCollapse').on('click', function() {
			$('#sidebar').toggleClass('active');
		});
	});
</script>
<nav class="navbar navbar-inverse bg-color-bl">
<div class="navbar-header">
	<div class="navbar-brand">
		<a id="sidebarCollapse"> &nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-th-list">&nbsp;</span>Admin Page</a>
	</div>
</div>
<div id="navbar" class="navbar-collapse collapse">
	<ul class="nav navbar-nav navbar-right">
		<li><p class="margin-15"><span class="glyphicon glyphicon-user">&nbsp;</span>
				<security:authentication property="principal.username" /> 님 접속 상태입니다</p></li>
		<li class="pull-right"><button class="btn btn-info navbar-btn"
				onclick="location.href='logout'">로그아웃</button>&nbsp;</li>
	</ul>
</div>
</nav>
<div class="container-fluid">
	<div class="row">
		<div class="wrapper">
			<nav id="sidebar" class="col-sm-2">
			<ul class="list-unstyled components" style="margin-top: 50px">
				<li><a href="#pageSubmenu1" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle"> <i
						class="fa fa-users" aria-hidden="true"></i> MEMBER <i
						class="fa  fa-angle-down pull-right" aria-hidden="true"></i></a>
					<ul class="collapse list-unstyled" id="pageSubmenu1">
						<li><a href="${pageContext.request.contextPath}/admin/mngMber">회원 그룹</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/mngAdmin">관리자 그룹</a></li>
					</ul></li>
				<li><a href="#pageSubmenu2" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle"> <i class="fa fa-building" aria-hidden="true"></i>
					COMPANY <i class="fa fa-angle-down pull-right" aria-hidden="true"></i></a>
					<ul class="collapse list-unstyled" id="pageSubmenu2">
						<li><a href="${pageContext.request.contextPath}/admin/mngEnt">기업 관리</a></li>
						<li><a
							href="${pageContext.request.contextPath}/admin/mngReview">리뷰 관리</a></li>
						<li><a
							href="${pageContext.request.contextPath}/admin/mngInterview">면접 관리</a></li>
					</ul>
				</li>
				<li><a href="#pageSubmenu3" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle"> <i class="fa fa-list" aria-hidden="true"></i> CATEGORY <i
						class="fa  fa-angle-down pull-right" aria-hidden="true"></i></a>
					<ul class="collapse list-unstyled" id="pageSubmenu3">
						<li><a href="mngInduty">산업군 관리</a></li>
						<!-- <li><a href="mngDomain">도메인 관리</a></li> -->
					</ul></li>
				<!-- <li><a href="#pageSubmenu4" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle"> <i
						class="fa fa-tachometer" aria-hidden="true"></i> DASHBOARD <i
						class="fa  fa-angle-down pull-right" aria-hidden="true"></i></a>
					<ul class="collapse list-unstyled" id="pageSubmenu4">
						<li><a href="#">통계</a></li>
					</ul></li> -->
				<%-- 	<li><a href="#pageSubmenu5" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle"> <i
						class="fa fa-cogs" aria-hidden="true"></i> MANAGEMENT <i
						class="fa  fa-angle-down pull-right" aria-hidden="true"></i></a>
					<ul class="collapse list-unstyled" id="pageSubmenu5">
						<li><a
							href="${pageContext.request.contextPath}/admin/mngMain">메인화면
								관리</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/mngFAQ">FAQ
								관리</a></li>
					</ul></li>--%>
			</ul>
			</nav>
