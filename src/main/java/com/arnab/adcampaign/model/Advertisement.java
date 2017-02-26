package com.arnab.adcampaign.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Advertisement{

	public Advertisement(){}
	
	public Advertisement(String partnerId, long duration, String adContent) {
		super();
		this.partnerId = partnerId;
		this.duration = duration;
		this.adContent = adContent;
	}
	
	@Id
	@Setter
	@Getter
	@Column(unique=true)
	private String partnerId;
	
	@Setter
	@Getter
	private long duration;
	
	@Setter	
	@Getter
	private String adContent;
	
	@Setter	
	@Getter
	@CreationTimestamp
	private Date creationTimestamp;
	
	@Setter	
	@Getter
	@UpdateTimestamp
	private Date updateTimestamp;
}