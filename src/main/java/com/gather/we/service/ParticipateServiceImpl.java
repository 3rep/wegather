package com.gather.we.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gather.we.dao.ParticipateDAO;
import com.gather.we.dto.NParticipateDTO;
import com.gather.we.dto.RParticipateDTO;

@Service
public class ParticipateServiceImpl implements ParticipateService {
	@Inject
	ParticipateDAO dao;

	@Override
	public int rParticipateInsert(RParticipateDTO dto) {
		return dao.rParticipateInsert(dto);
	}

	@Override
	public int nParticipateInsert(NParticipateDTO dto) {
		return dao.nParticipateInsert(dto);
	}
}
