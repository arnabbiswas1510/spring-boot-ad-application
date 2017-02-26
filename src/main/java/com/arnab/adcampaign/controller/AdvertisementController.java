package com.arnab.adcampaign.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.arnab.adcampaign.exception.AdvertisementNotFoundException;
import com.arnab.adcampaign.exception.InvalidAdvertisementRequestException;
import com.arnab.adcampaign.model.Advertisement;
import com.arnab.adcampaign.repository.AdvertisementRepository;

/**
 * advertisement Controller exposes a series of RESTful endpoints
 */
@RestController
public class AdvertisementController {

	@Autowired
	private AdvertisementRepository advertisementRepository;

	
	/**
	 * Get advertisement using id. Returns HTTP 404 if advertisement not found
	 * 
	 * @param partnerId
	 * @return retrieved advertisement
	 */
	@RequestMapping(value = "/ad/{partner_id}", method = RequestMethod.GET)
	public Advertisement getadvertisement(@PathVariable("partner_id") String partnerId) {
		
		/* validate advertisement Id parameter */
		if (null==partnerId) {
			throw new InvalidAdvertisementRequestException();
		}
		
		Advertisement advertisement = advertisementRepository.findOne(partnerId);
		
		// check if campaign is valid based on campaign duration
		if(null==advertisement || (advertisement.getCreationTimestamp().toInstant()
				.plusSeconds(advertisement.getDuration())
				.isBefore(new Date().toInstant()))){
			throw new AdvertisementNotFoundException();
		}
		
		return advertisement;
	}

	
	/**
	 * Gets all advertisements.
	 *
	 * @return the advertisements
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public List<Advertisement> getadvertisements() {
		
		return (List<Advertisement>) advertisementRepository.findAll();
	}

	
	/**
	 * Create a new advertisement and return in response with HTTP 201
	 *
	 * @param the advertisement
	 * @return created advertisement
	 */
	@RequestMapping(value = { "/ad" }, method = { RequestMethod.POST })
	public Advertisement createadvertisement(@RequestBody Advertisement advertisement, HttpServletResponse httpResponse, WebRequest request) {

		Advertisement createdadvertisement = null;
		if(!advertisementRepository.exists(advertisement.getPartnerId())){
			createdadvertisement = advertisementRepository.save(advertisement);		
			httpResponse.setStatus(HttpStatus.CREATED.value());
			httpResponse.setHeader("Location", String.format("%s/ad/%s", request.getContextPath(), advertisement.getPartnerId()));
		}
		else{
			httpResponse.setStatus(HttpStatus.CONFLICT.value());	
		}
		
		return createdadvertisement;
	}

	
	/**
	 * Update advertisement with given advertisement id.
	 *
	 * @param advertisement the advertisement
	 */
	@RequestMapping(value = { "/ad/{partner_id}" }, method = { RequestMethod.PUT })
	public void updateadvertisement(@RequestBody Advertisement advertisement, @PathVariable("partner_id") String partnerId,
								   		  HttpServletResponse httpResponse) {

		if(!advertisementRepository.exists(partnerId)){
			httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
		}
		else{
			advertisementRepository.save(advertisement);
			httpResponse.setStatus(HttpStatus.NO_CONTENT.value());	
		}
	}

	
	/**
	 * Deletes the advertisement with given advertisement id if it exists and returns HTTP204.
	 *
	 * @param partnerId the advertisement id
	 */
	@RequestMapping(value = "/ad/{partner_id}", method = RequestMethod.DELETE)
	public void removeadvertisement(@PathVariable("partner_id") String partnerId, HttpServletResponse httpResponse) {

		if(advertisementRepository.exists(partnerId)){
			advertisementRepository.delete(partnerId);	
		}
		
		httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
	}

}