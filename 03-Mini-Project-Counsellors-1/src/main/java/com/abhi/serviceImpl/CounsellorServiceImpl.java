package com.abhi.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.entiey.Counsellor;
import com.abhi.repo.CounsellorRepo;
import com.abhi.service.CounsellorService;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public boolean saveCounsellor(Counsellor counsellor) {
		Counsellor findByEmail = counsellorRepo.findByEmail(counsellor.getEmail());
		if (findByEmail != null) {
			return false;
		} else {

			Counsellor savedCounsellor = counsellorRepo.save(counsellor);
			return savedCounsellor.getCounsellorId() != null;

		}
	}

	@Override
	public Counsellor getCounsellor(String email, String pwd) {
		return counsellorRepo.findByEmailAndPwd(email, pwd);
	}

}
