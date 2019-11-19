package com.atu.service.impl;

import org.springframework.stereotype.Service;

import com.atu.service.CommonService;
@Service("service")
public class CommonServiceImpl implements CommonService {
	@com.googlecode.ehcache.annotations.TriggersRemove(cacheName="myQueryCache",removeAll=true)
	@Override
	public void clearQueryCache() {
		// TODO Auto-generated method stub
		
	}

}
