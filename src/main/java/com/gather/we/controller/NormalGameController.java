package com.gather.we.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gather.we.dto.NormGameDTO;
import com.gather.we.dto.NormGameDetailDTO;
import com.gather.we.dto.NormGameListDTO;
import com.gather.we.dto.SportDTO;
import com.gather.we.dto.StadiumInfoDTO;
import com.gather.we.service.NormalGameService;
import com.gather.we.service.SportService;
import com.gather.we.service.StadiumInfoService;

@RestController
@RequestMapping("/normgame")
public class NormalGameController {
	@Autowired
	SportService sportService;
	@Autowired
	NormalGameService normGameService;
	@Autowired
	StadiumInfoService stadiumService;
	
	// 종목목록
	@GetMapping("/sportlist")
	public ModelAndView sportList() {
		ModelAndView mav = new ModelAndView();
		
		List<SportDTO> sportList = sportService.sportAllSelect();
		
		mav.addObject("sportList", sportList);
		mav.setViewName("user/normGame/sportList");
		
		return mav;
	}
	
	// 일반경기 목록
	@GetMapping("/normgamelist")
	public ModelAndView normGameList(NormGameListDTO dto) { 
		ModelAndView mav = new ModelAndView();
		
		// 지역 필터링 시 db에서 해당 지역의 경기만 선택하여 가져오기 위해 지역 카테고리를 세부 지역으로 나누어 리스트에 담는다.
		String region = dto.getRegion();// '대전/세종/충청'
		if(region!=null) {
			List<String> regionList = Arrays.asList(region.split("/"));// ['대전', '세종', '충청']
			dto.setRegionList(regionList);
		}
		
		// DB에서 일반경기 목록 받아오기
		List<NormGameDTO> normGameList = normGameService.normGameListSelect(dto);

		mav.addObject("s_no", dto.getS_no());
		mav.addObject("normGameList", normGameList);
		mav.setViewName("user/normGame/normGameList");
		
		return mav;
	}
	
	// 일반경기 세부정보
	@GetMapping("/detail")
	public ModelAndView normGameDetail(int no) {
		ModelAndView mav = new ModelAndView();
		
		// DB에서 일반경기 세부정보 받아오기
		NormGameDetailDTO normGameDetail = normGameService.normGameDetailSelect(no);

		mav.addObject("normGameDetail", normGameDetail);
		mav.setViewName("user/normGame/normGameDetail");
		
		return mav;
	}
	
	// �씪諛섍꼍湲� 媛쒖꽕 �럹�씠吏�
	@GetMapping("/new")
	public ModelAndView normGameNew(int s_no, StadiumInfoDTO dto) {
		ModelAndView mav = new ModelAndView();
		
		int st_no = dto.getSt_no();
		
		if(dto.getRegion() == null || dto.getRegion().equals("")) {
			dto.setRegion("�꽌�슱");
		} // 珥덇린媛� �꽌�슱濡� 吏��젙
		
		if(st_no != 0) {
			
			// DB�뿉�꽌 誘멸컻�꽕�맂 �씪諛섍꼍湲� �젙蹂� 諛쏆븘�삤湲�
			List<NormGameDetailDTO> newNormGameList = normGameService.newNormGameList(st_no);
			
			mav.addObject("newNormGameList", newNormGameList);
		}
		
		// 議댁옱�븯�뒗 援ъ옣 �젙蹂� 諛쏆븘�삤湲�
		List<StadiumInfoDTO> stadiumList = stadiumService.stadiumInfoAllSelect();
		
		// �뒪�룷痢� 醫낅ぉ �젙蹂� 諛쏆븘�삤湲�
		SportDTO sportDTO =  sportService.sportOneSelect(s_no);
		
		mav.addObject("stadiumList", stadiumList);
		mav.addObject("sportDTO", sportDTO);
		mav.addObject("stadiumDTO", dto);
		mav.setViewName("user/normGame/normGameNew");
		
		return mav;
	}
	
	// �씪諛섍꼍湲� 媛쒖꽕 - �궗�슜�옄 �엯�젰 �럹�씠吏�
	@GetMapping("/newdetail")
	public ModelAndView normGameNewDetail(int no, int s_no) {
		ModelAndView mav = new ModelAndView();
		
		NormGameDetailDTO normGameDTO = normGameService.normGameDetailSelect(no);
		SportDTO sportDTO = sportService.sportOneSelect(s_no);
		
		mav.addObject("normGameDTO", normGameDTO);
		mav.addObject("sportDTO", sportDTO);
		mav.setViewName("user/normGame/normGameNewDetail");
		return mav;
	}
	
	// �씪諛섍꼍湲� 媛쒖꽕 (DB�벑濡�)
	@PostMapping("/newdetailOk")
	public ResponseEntity<String> normGameNewDetailOk(NormGameDTO dto, HttpServletRequest request){
		ResponseEntity<String> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=utf-8");
		try {
			// �궗�슜�옄媛� �옉�꽦�븳 �젙蹂대�� DB�뿉 ����옣
			normGameService.normGameUpdate(dto);
			
			// 寃쎄린 李몄뿬�옄 �닔 +1
			normGameService.normGameCountUp(dto.getNo());
			
			// �씪諛섍꼍湲� 紐⑸줉�쑝濡� �씠�룞
			String body = "<script> alert('�씪諛섍꼍湲곕�� 媛쒖꽕�븯����뒿�땲�떎.'); location.href='/normgame/normgamelist?s_no="+dto.getS_no()+"';</script>";
			entity = new ResponseEntity<String>(body, headers, HttpStatus.OK);
		}catch(Exception e) {
			// �씪諛섍꼍湲� �벑濡� �떎�뙣
			e.printStackTrace();
			String body = "<script>";
			body += "alert('�씪諛섍꼍湲� 媛쒖꽕�쓣 �떎�뙣�븯����뒿�땲�떎.');";
			body += "history.go(-1);";
			body += "</script>";
			entity = new ResponseEntity<String>(body, headers, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
