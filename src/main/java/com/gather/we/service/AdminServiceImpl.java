package com.gather.we.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gather.we.dao.AdminDAO;
import com.gather.we.dto.AdminDTO;
import com.gather.we.dto.AdminManagerSettlementDTO;
import com.gather.we.dto.ManagerSettlementDTO;
import com.gather.we.dto.UserPayDTO;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminDAO dao;

	@Override
	public AdminDTO loginAdminOk(String adminid, String password) {
		return dao.loginAdminOk(adminid, password);
	}

	@Override
	public List<UserPayDTO> revenue() {
		return dao.revenue();
	}

	@Override
	public List<AdminManagerSettlementDTO> expense() {
		return dao.expense();
	}
	
	@Override
	public List<AdminManagerSettlementDTO> managerFee() {
		return dao.managerFee();
	}

	@Override
	public int waitOk(AdminManagerSettlementDTO dto) {
		return dao.waitOk(dto);
	}
}
