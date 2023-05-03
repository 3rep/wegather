<%@ include file="/WEB-INF/views/inc/user/header.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<!-- 메인화면 contents부분 -->
	<!-- 배경에 이미지(A) 깔고 
	그 위에 
		1. 로고 + 회원가입, 로그인 버튼?  있는 화면 : 백그라운드 연하게
			> 불투명 백그라운드(B) + 내용들어있는 div(C)
		2. 회원가입화면 바로 밑에 붙여서 소개 div(D)
	
		하나의 container에 넣어서 flex 적용시킨다.
		
		맨 뒤 바탕사진(A)은 스크롤할때 1씩 간다면,
		그 위에 1,2div는 5씩 이동해야함
	-->
	

	<div id="container" class="main-container"> <!-- css우선순위: id가 css보다 우선적용 -->
		<!-- <div class="rank-visual" > -->
		
		<img class="img" src="static/img/imgMain/tennis2.jpg" alt="football"/> 
		
		<div class="item" id="cloudy"> <!-- A -->
			<div class="login"> <!-- B -->
				<span>We gather &emsp;</span><br/>
				<span>&emsp; together </span><br/>
				<span id="featSpan">feat. sport</span><br/>
				<br/>
				<button class="button button--ujarak button--border-medium button--round-s button--text-upper" onclick="location.href='login'">회원</button>
          		<button class="button button--ujarak button--border-medium button--round-s button--text-upper" onclick="location.href='loginMan'">매니저</button>
			</div>
		</div>
		<div class="item" id="info">
			
			<!-- <img class="infoImg" src="static/img/imgMain/info.png" alt="info"/> -->
			
			<div class="infoTxt">
				<span class="first">신청, 경기시작 </span><br/>
				<span class="second">We gather, Just do it!</span> 
				<div class="jb-division-line"></div>
				<div class="third">
					인원모집 및 예약 서비스 <br/>
					랭크 시스템<br/>
					매니저 지원 서비스<br/>
					맞춤형 스포츠 추천
				</div>
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/views/inc/user/footer.jspf" %>
	
