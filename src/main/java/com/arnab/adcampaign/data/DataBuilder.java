package com.arnab.adcampaign.data;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.arnab.adcampaign.model.Advertisement;

@Component
public class DataBuilder { 

	
	public List<Advertisement> createAdvertisements() {

		Advertisement ad1 = new Advertisement("Joe", 10001, "Sample content for ad1");

		Advertisement ad2 = new Advertisement("Paul", 10002, "Sample content for ad2");

		Advertisement ad3 = new Advertisement("Steve", 10003, "Sample content for ad3");
		
		return Arrays.asList(ad1, ad2, ad3);
	}
}