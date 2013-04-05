package com.salesmanager.core.modules.integration.shipping.model;

import java.util.List;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class CustomShippingQuotesRegion implements JSONAware {
	
	private String customRegionName;//a name given by the merchant for this custom region
	private List<String> countries;//a list of country code for this region
	
	private List<CustomShippingQuoteWeightItem> quoteItems;//price max weight

	public void setQuoteItems(List<CustomShippingQuoteWeightItem> quoteItems) {
		this.quoteItems = quoteItems;
	}

	public List<CustomShippingQuoteWeightItem> getQuoteItems() {
		return quoteItems;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void setCustomRegionName(String customRegionName) {
		this.customRegionName = customRegionName;
	}

	public String getCustomRegionName() {
		return customRegionName;
	}
	
	@SuppressWarnings("unchecked")
	public String toJSONString() {
		//JSONObject data = new JSONObject();
		
		StringBuilder returnString = new StringBuilder();
		returnString.append("{\"customRegionName\"").append(":\"").append(this.getCustomRegionName()).append("\"");
		//data.put("customRegionName", this.getCustomRegionName());
		if(countries!=null) {
			returnString.append(",");
			StringBuilder coutriesList = new StringBuilder();
			int countCountry = 0;
			coutriesList.append("[");
			for(String country : countries) {
				coutriesList.append("\"").append(country).append("\"");
				countCountry ++;
				if(countCountry<countries.size()) {
					coutriesList.append(",");
				}
			}
			coutriesList.append("]");
			returnString.append("\"countries\"").append(":").append(coutriesList.toString());
		}
		
		if(quoteItems!=null) {
			returnString.append(",");
			StringBuilder quotesList = new StringBuilder();
			int countQuotes = 0;
			quotesList.append("[");
			for(CustomShippingQuoteWeightItem quote : quoteItems) {
				quotesList.append(quote.toJSONString());
				countQuotes ++;
				if(countQuotes<quoteItems.size()) {
					quotesList.append(",");
				}
			}
			quotesList.append("]");
			//data.put("quoteItems", quotesList.toString());
			returnString.append("\"quoteItems\"").append(":").append(quotesList.toString());
		}
		
		//return data.toJSONString();
		returnString.append("}");
		return returnString.toString();
		
		
	}


}
