package com.abc.service;

import java.net.URI;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.abc.common.Constants;
import com.abc.entity.Instrument;
import com.abc.entity.PriceFactor;
import com.abc.repository.InstrumentsRepository;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@Component
public class InstrumentsService {
	
	@Autowired
	InstrumentsRepository instrumentsRepository;
	
	@Autowired
	RulesService rulesService;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	@Autowired
	LoadBalancerClient loadBalancerClient;
	
	public Instrument getInstrument(Integer id) {
		
		return instrumentsRepository.findOne(id);
	}
	
	public Instrument saveInstrument(Instrument instrument, PriceFactor priceFactor) {
		
		Instrument saveInstrument = rulesService.applyRules(instrument, priceFactor);
		
		instrumentsRepository.save(saveInstrument);
		
		rabbitTemplate.convertAndSend(Constants.QUEUE_NAME, saveInstrument.toString());
		
		/* Choice 1 - Use DiscoveryClient */
		// propagateUsingDiscoveryClient(saveInstrument);
		/* Choice 2 - Use LoadBalancer */
		propagateUsingRibbon(saveInstrument);		
		
		return saveInstrument;
	}
	
	public Instrument saveInstrument(Integer id, Double newprice) {
		
		Instrument instrument = getInstrument(id);
		
		PriceFactor priceFactor = new PriceFactor();
		priceFactor.setEquity(1.0);
		priceFactor.setOther(1.0);
		
		instrument.setPrice(newprice);
		
		return instrumentsRepository.save(rulesService.applyRules(instrument, priceFactor));
	}
	
	private void propagateUsingDiscoveryClient(Instrument instrument) {
		
		try {

			List<ServiceInstance> list = discoveryClient.getInstances("IPRPGNSRVC");
			
		    if (list != null && list.size() > 0 ) {
		    	
		    	URI uri = list.get(0).getUri();
		    	System.out.println("URI:::::::::::"+uri.toString());
		    	
			    if (uri !=null ) {
			    	new RestTemplate().postForObject(uri+"/instrument", instrument, Instrument.class);
			    }
		    }
		}
		catch(Exception ex) {
			
			System.out.println("Propagation failure..."+ex.getMessage());
			ex.printStackTrace();
		}
    }	    
	
	private void propagateUsingRibbon(Instrument instrument) {
		
		try {

			ServiceInstance instance = loadBalancerClient.choose("IPRPGNSRVC");
			
			if(null!=instance) {
		    	URI uri = instance.getUri();
		    	System.out.println("URI:::::::::::"+uri.toString());
		
		    	if (uri !=null ) {
		    		new RestTemplate().postForObject(uri+"/instrument", instrument, Instrument.class);
			    }
			}
		}
		catch(Exception ex) {
			
			System.out.println("Propagation failure..."+ex.getMessage());
			ex.printStackTrace();
		}
    }
}
