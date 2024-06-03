package com.abhi.service;

import org.springframework.stereotype.Controller;

import com.abhi.entiey.Counsellor;

public interface CounsellorService {
	public boolean saveCounsellor(Counsellor counsellor);

	public Counsellor getCounsellor(String email, String pwd);
}




