package com.abc.service.rules;

import org.springframework.stereotype.Component;

import com.abc.common.Constants;
import com.abc.entity.Instrument;
import com.abc.entity.PriceFactor;

@Component
public class PriceFactorRule {
	
	private Instrument instrument;
	
	private PriceFactor priceFactor;
	
	public PriceFactorRule() {		
	}
	
	public PriceFactorRule(Instrument instrument, PriceFactor priceFactor) {
		
		this.instrument = instrument;
		this.priceFactor = priceFactor;
	}
	
	public Instrument apply() {
		
		if(null!=instrument && null!=instrument.getType()) {
			
			if(instrument.getType().equals(Constants.INSTRUMENT_TYPE_EQUITY)) {
				
				instrument.setPrice(instrument.getPrice()*priceFactor.getEquity());				
			}
			else {
				
				instrument.setPrice(instrument.getPrice()*priceFactor.getOther());
			}
		}
		
		return instrument;
	}
}
