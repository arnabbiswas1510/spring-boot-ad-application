package com.arnab.adcampaign.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.arnab.adcampaign.model.Advertisement;

public interface AdvertisementRepository extends CrudRepository<Advertisement, String> {

    public List<Advertisement> findBypartnerId(String partnerId); 
}