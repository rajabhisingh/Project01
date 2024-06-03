package com.abhi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.entiey.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {
	public Counsellor findByEmailAndPwd(String email, String pwd);
    public Counsellor findByEmail(String email);
}
