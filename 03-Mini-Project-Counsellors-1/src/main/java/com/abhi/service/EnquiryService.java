package com.abhi.service;

import java.util.List;

import com.abhi.dto.Dashboard;
import com.abhi.entiey.Enquiry;

public interface EnquiryService {
	public Dashboard getDashboardInfo(Integer counsellorId);

	public boolean addEnquiry(Enquiry enquiry, Integer counsellorId);

	public List<Enquiry> getEnquiries(Enquiry enquiry, Integer counsellorId);

	public Enquiry getEnquiry(Integer enqId);

}
