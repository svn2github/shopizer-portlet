package com.salesmanager.core.business.catalog.product.dao.price;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.salesmanager.core.business.catalog.product.model.price.ProductPrice;
import com.salesmanager.core.business.catalog.product.model.price.QProductPrice;
import com.salesmanager.core.business.catalog.product.model.price.QProductPriceDescription;
import com.salesmanager.core.business.generic.dao.SalesManagerEntityDaoImpl;

@Repository("productPriceDao")
public class ProductPriceDaoImpl extends SalesManagerEntityDaoImpl<Long, ProductPrice> 
	implements ProductPriceDao {
	
	@Override
	public ProductPrice getById(Long id) {
		QProductPrice qEntity = QProductPrice.productPrice;
		QProductPriceDescription qEntityDescription = QProductPriceDescription.productPriceDescription;
		
		
		JPQLQuery query = new JPAQuery (getEntityManager());
		
		query.from(qEntity)
			.leftJoin(qEntity.descriptions, qEntityDescription).fetch()
			.where(qEntity.id.eq(id));
		
		return query.uniqueResult(qEntity);
	}

}
