package com.att.idp.foundation.drools.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.att.ajsc.logging.AjscEelfManager;
import com.att.eelf.configuration.EELFLogger;
import com.att.idp.foundation.configuration.ApplicationConstants;
import com.att.idp.foundation.drools.TestApplication;
import com.att.idp.foundation.drools.test.model.Address;
import com.att.idp.foundation.drools.test.model.Order;

// TODO: Auto-generated Javadoc
/**
 * The Class DroolsUsageTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles(profiles = { ApplicationConstants.APPLICATION_PROFILE_DROOLS })
@SpringBootTest(classes = { TestApplication.class })
@SuppressWarnings({"PMD.GuardLogStatementJavaUtil","PMD.GuardLogStatement","PMD.SuspiciousConstantFieldName"})
public class DroolsUsageTest {

	/** The log. */
	private static EELFLogger log = AjscEelfManager.getInstance().getLogger(DroolsUsageTest.class);
 
    /** The kie session. */
    @Autowired
    private KieSession kieSession;
    
    /**
     * Address rule postcode.
     */
    @Test
	public void adressRulePostcode() {
        // Given
        final Address address = new Address();
        address.setPostcode("99425");
        
        // When
        // Let´s give the Drools Knowledge-Base an Object, we can then apply rules on
        kieSession.insert(address);
        final int ruleFiredCount = kieSession.fireAllRules();
                
        // Then     
        assertEquals("there´s 1 rule, thats meets the condition, so there should be 1 applied", 1, ruleFiredCount);
        log.info("Rules checked: {}" + ruleFiredCount);
    }
    
    /**
     * Adress rule postcode not met.
     */
    @Test
    public void adressRulePostcodeNotMet() {
        // Given
        final Address address = new Address();
        address.setPostcode("994259");
        
        // When
        // Let´s give the Drools Knowledge-Base an Object, we can then apply rules on
        kieSession.insert(address);
        final int ruleFiredCount = kieSession.fireAllRules();
                
        // Then     
        assertEquals("there´s 0 rule, thats meets the condition, so there should be 0 applied", 0, ruleFiredCount);
        log.info("Rules checked: {}" + ruleFiredCount);
    }
    
    
    /**
     * Adress rule order.
     */
    @Test
    public void adressRuleOrder() {
        // Given
        final Address address = new Address();
        address.setPostcode("99425");
        address.setState("GERMANY");
        
        final Order order = new Order();
        order.setAmount(2);
        order.setState2ship2("GERMANY");
        
        // When
        // Let´s give the Drools Knowledge-Base an Object, we can then apply rules on
        kieSession.insert(address);
        kieSession.insert(order);
        final int ruleFiredCount = kieSession.fireAllRules();
                
        // Then     
        assertEquals("there are 2 rules, which meet the condition, so there should be 2 applied", 2, ruleFiredCount);
        log.info("Rules checked: {}" + ruleFiredCount);
    }
    
    

}
