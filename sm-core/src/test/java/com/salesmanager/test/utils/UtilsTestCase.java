package com.salesmanager.test.utils;



import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.currency.service.CurrencyService;
import com.salesmanager.core.business.system.model.IntegrationConfiguration;
import com.salesmanager.core.utils.CacheUtils;
import com.salesmanager.test.core.AbstractSalesManagerCoreTestCase;
import com.salesmanager.test.core.SalesManagerCoreTestExecutionListener;

public class UtilsTestCase extends AbstractSalesManagerCoreTestCase {
	
	
	@Autowired
	private CountryService countryService;
	
	
	
	@Autowired
	private CurrencyService currencyService;
	
	
	@Test
	public void testCache() throws Exception {
		
		@SuppressWarnings("rawtypes")
		List countries = countryService.list();
		


		
		CacheUtils cache = CacheUtils.getInstance();
		cache.putInCache(countries, "COUNTRIES");
		
		@SuppressWarnings("rawtypes")
		List objects = (List) cache.getFromCache("COUNTRIES");
		
		Assert.assertNotNull(objects);
		
	}
	
	@Test
	public void testCurrency() throws Exception {
		
		Currency currency = currencyService.getByCode("BGN");
		
		java.util.Currency c = currency.getCurrency();
		
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		numberFormat.setCurrency(c);
		
		System.out.println("Done");
		
	}
	
	@Test
	public void testEncryption() throws Exception {
		
		MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
		

		IntegrationConfiguration paymentConfiguration = new IntegrationConfiguration();
		
		paymentConfiguration.setActive(true);
		paymentConfiguration.setEnvironment(IntegrationConfiguration.TEST_ENVIRONMENT);
		paymentConfiguration.setModuleCode("beanstream");
		
		Map<String,String> integrationKeys = new HashMap<String,String>();
		integrationKeys.put("merchantid", "123456");
		integrationKeys.put("username", "accnt");
		integrationKeys.put("password", "pass123");
		integrationKeys.put("transaction", "CAPTURE");
		
		paymentConfiguration.setIntegrationKeys(integrationKeys);
		
		System.out.println(paymentConfiguration.toJSONString());
		
		paymentService.savePaymentModuleConfiguration(paymentConfiguration, store);
		
		paymentConfiguration = paymentService.getPaymentConfiguration("beanstream", store);
		
		System.out.println(paymentConfiguration.toJSONString());
		
	}

}
