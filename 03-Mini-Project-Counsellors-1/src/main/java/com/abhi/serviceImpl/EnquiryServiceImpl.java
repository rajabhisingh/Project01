package com.abhi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.abhi.dto.Dashboard;
import com.abhi.entiey.Counsellor;
import com.abhi.entiey.Enquiry;
import com.abhi.repo.CounsellorRepo;
import com.abhi.repo.EnquiryRepo;
import com.abhi.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	@Autowired
	private EnquiryRepo enquiryRepo;

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public Dashboard getDashboardInfo(Integer counsellorId) {
		Long totalEnq = enquiryRepo.getEnquries(counsellorId);

		Long openCnt = enquiryRepo.getEnquries(counsellorId, "new");

		Long lostCnt = enquiryRepo.getEnquries(counsellorId, "lost");

		Long enrolledCnt = enquiryRepo.getEnquries(counsellorId, "enrolled");

		Dashboard d = new Dashboard();

		d.setTotalEnqs(totalEnq);
		d.setOpenEnqs(openCnt);
		d.setLostEnqs(lostCnt);
		d.setEnrolledEnqs(enrolledCnt);

		return d;
	}

	@Override
	public boolean addEnquiry(Enquiry enquiry, Integer counsellorId) {
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow();
		enquiry.setCounsellor(counsellor);

		Enquiry saveEnq = enquiryRepo.save(enquiry);
		return saveEnq.getEnqId() != null;
	}

	@Override
	public List<Enquiry> getEnquiries(Enquiry enquiry, Integer counsellorId) {

		// Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow();
		Counsellor counsellor = new Counsellor();
		counsellor.setCounsellorId(counsellorId);

		// adding filter values to entity
		Enquiry searchCriteria = new Enquiry();
		searchCriteria.setCounsellor(counsellor);

		if (null != enquiry.getCourse() && !"".equals(enquiry.getCourse())) {
			searchCriteria.setCourse(enquiry.getCourse());
		}

		if (null != enquiry.getMode() && !"".equals(enquiry.getMode())) {

			searchCriteria.setMode(enquiry.getMode());
		}

		if (null != enquiry.getStatus() && !"".equals(enquiry.getStatus())) {
			searchCriteria.setStatus(enquiry.getStatus());
		}
		// dynamic query creation
		Example<Enquiry> of = Example.of(searchCriteria);
		return enquiryRepo.findAll(of);

	}

	@Override
	public Enquiry getEnquiry(Integer enqId) {
		return enquiryRepo.findById(enqId).orElseThrow();

	}

}

