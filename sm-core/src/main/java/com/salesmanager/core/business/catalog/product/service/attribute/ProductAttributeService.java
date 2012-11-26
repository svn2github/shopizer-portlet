package com.salesmanager.core.business.catalog.product.service.attribute;

import java.util.List;

import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductAttribute;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductOption;
import com.salesmanager.core.business.catalog.product.model.attribute.ProductOptionValue;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.service.SalesManagerEntityService;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.reference.language.model.Language;

public interface ProductAttributeService extends
		SalesManagerEntityService<Long, ProductAttribute> {

	void saveOrUpdate(ProductAttribute productAttribute)
			throws ServiceException;
	
	List<ProductAttribute> getById(MerchantStore store,
			ProductOption option) throws ServiceException;

	List<ProductAttribute> getByOptionValueId(MerchantStore store,
			ProductOptionValue optionValue) throws ServiceException;

	List<ProductAttribute> getByProductId(MerchantStore store, Product product, Language language)
			throws ServiceException;
}
