package com.salesmanager.test.isolated;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.salesmanager.core.business.catalog.category.model.Category;
import com.salesmanager.core.business.catalog.category.model.CategoryDescription;
import com.salesmanager.core.business.catalog.category.service.CategoryService;
import com.salesmanager.core.business.catalog.product.model.Product;
import com.salesmanager.core.business.catalog.product.model.availability.ProductAvailability;
import com.salesmanager.core.business.catalog.product.model.description.ProductDescription;
import com.salesmanager.core.business.catalog.product.model.manufacturer.Manufacturer;
import com.salesmanager.core.business.catalog.product.model.manufacturer.ManufacturerDescription;
import com.salesmanager.core.business.catalog.product.model.price.ProductPrice;
import com.salesmanager.core.business.catalog.product.model.price.ProductPriceDescription;
import com.salesmanager.core.business.catalog.product.model.type.ProductType;
import com.salesmanager.core.business.catalog.product.service.ProductService;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductAttributeService;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductOptionService;
import com.salesmanager.core.business.catalog.product.service.attribute.ProductOptionValueService;
import com.salesmanager.core.business.catalog.product.service.availability.ProductAvailabilityService;
import com.salesmanager.core.business.catalog.product.service.image.ProductImageService;
import com.salesmanager.core.business.catalog.product.service.manufacturer.ManufacturerService;
import com.salesmanager.core.business.catalog.product.service.price.ProductPriceService;
import com.salesmanager.core.business.catalog.product.service.type.ProductTypeService;
import com.salesmanager.core.business.customer.service.CustomerService;
import com.salesmanager.core.business.generic.exception.ServiceException;
import com.salesmanager.core.business.generic.util.EntityManagerUtils;
import com.salesmanager.core.business.merchant.model.MerchantStore;
import com.salesmanager.core.business.merchant.service.MerchantStoreService;
import com.salesmanager.core.business.order.service.OrderService;
import com.salesmanager.core.business.reference.country.model.Country;
import com.salesmanager.core.business.reference.country.model.CountryDescription;
import com.salesmanager.core.business.reference.country.service.CountryService;
import com.salesmanager.core.business.reference.currency.model.Currency;
import com.salesmanager.core.business.reference.currency.service.CurrencyService;
import com.salesmanager.core.business.reference.language.model.Language;
import com.salesmanager.core.business.reference.language.service.LanguageService;
import com.salesmanager.core.business.reference.zone.service.ZoneService;
import com.salesmanager.test.core.SalesManagerCoreTestExecutionListener;


@ContextConfiguration(locations = {
		"classpath:spring/test-spring-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	SalesManagerCoreTestExecutionListener.class
})
public class IsolatedTestCase {
	
	private static final Date date = new Date(System.currentTimeMillis());
	
	@Autowired
	private EntityManagerUtils entityManagerUtils;
	
	@Autowired
	protected ProductService productService;

	
	@Autowired
	protected ProductPriceService productPriceService;
	
	@Autowired
	protected ProductAttributeService productAttributeService;
	
	@Autowired
	protected ProductOptionService productOptionService;
	
	@Autowired
	protected ProductOptionValueService productOptionValueService;
	
	@Autowired
	protected ProductAvailabilityService productAvailabilityService;
	
	@Autowired
	protected ProductImageService productImageService;
	
	@Autowired
	protected CategoryService categoryService;
	
	@Autowired
	protected MerchantStoreService merchantService;
	
	@Autowired
	protected ProductTypeService productTypeService;
	
	@Autowired
	protected LanguageService languageService;
	
	@Autowired
	protected CountryService countryService;
	
	@Autowired
	protected ZoneService zoneService;
	
	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected ManufacturerService manufacturerService;

	
	@Autowired
	protected CurrencyService currencyService;
	
	@Autowired
	protected OrderService orderService;
	
	@Test
	public void test1CreateReferences() throws ServiceException {
		
		
		
		Date date = new Date(System.currentTimeMillis());
		
		Language en = new Language();
		en.setCode("en");
		languageService.create(en);
		
		Language fr = new Language();
		fr.setCode("fr");
		languageService.create(fr);
		

		
		//create country
		Country ca = new Country();
		ca.setIsoCode("CA");
		
		CountryDescription caden = new CountryDescription();
		caden.setCountry(ca);
		caden.setLanguage(en);
		caden.setName("Canada");
		caden.setDescription("Canada Country");
		
		CountryDescription cadfr = new CountryDescription();
		cadfr.setCountry(ca);
		cadfr.setLanguage(fr);
		cadfr.setName("Canada");
		cadfr.setDescription("Pays Canada");
		
		List<CountryDescription> descriptionsca = new ArrayList<CountryDescription>();
		descriptionsca.add(caden);
		descriptionsca.add(cadfr);
		ca.setDescriptions(descriptionsca);
		
		countryService.create(ca);
		
		//create a currency
		Currency currency = new Currency();
		currency.setCurrency(java.util.Currency.getInstance(Locale.CANADA));
		currency.setSupported(true);
		currencyService.create(currency);
		
		//create a merchant
		MerchantStore store = new MerchantStore();
		store.setCountry(ca);
		store.setCurrency(currency);
		store.setDefaultLanguage(en);
		store.setInBusinessSince(date);
		store.setStorename("store name");
		store.setCode(MerchantStore.DEFAULT_STORE);
		store.setStoreEmailAddress("test@test.com");
		
		merchantService.create(store);
		
		ProductType generalType = new ProductType();
		generalType.setCode(ProductType.GENERAL_TYPE);
		productTypeService.create(generalType);
		
		
	}
	
	
	@Test
	public void testCreateProducts() throws ServiceException {
		
		

		
		Language en = languageService.getByCode("en");
		Language fr = languageService.getByCode("fr");
		Country ca = countryService.getByCode("CA");
		Currency currency = currencyService.getByCode("CAD");
		MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
		ProductType generalType = productTypeService.getProductType(ProductType.GENERAL_TYPE);
		
		
		Category book = new Category();
		book.setDepth(0);
		book.setLineage("/");
		book.setMerchantSore(store);
		
		CategoryDescription bookEnglishDescription = new CategoryDescription();
		bookEnglishDescription.setName("Book");
		bookEnglishDescription.setCategory(book);
		bookEnglishDescription.setLanguage(en);
		
		CategoryDescription bookFrenchDescription = new CategoryDescription();
		bookFrenchDescription.setName("Livre");
		bookFrenchDescription.setCategory(book);
		bookFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> descriptions = new ArrayList<CategoryDescription>();
		descriptions.add(bookEnglishDescription);
		descriptions.add(bookFrenchDescription);
		
		book.setDescriptions(descriptions);
		
		categoryService.create(book);
		
		
		Category music = new Category();
		music.setDepth(0);
		music.setLineage("/");
		music.setMerchantSore(store);
		
		CategoryDescription musicEnglishDescription = new CategoryDescription();
		musicEnglishDescription.setName("Music");
		musicEnglishDescription.setCategory(music);
		musicEnglishDescription.setLanguage(en);
		
		CategoryDescription musicFrenchDescription = new CategoryDescription();
		musicFrenchDescription.setName("Musique");
		musicFrenchDescription.setCategory(music);
		musicFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> descriptions2 = new ArrayList<CategoryDescription>();
		descriptions2.add(musicEnglishDescription);
		descriptions2.add(musicFrenchDescription);
		
		music.setDescriptions(descriptions2);
		
		categoryService.create(music);
		
		Category novell = new Category();
		novell.setDepth(1);
		novell.setLineage("/" + book.getId() + "/");
		novell.setMerchantSore(store);
		
		CategoryDescription novellEnglishDescription = new CategoryDescription();
		novellEnglishDescription.setName("Novell");
		novellEnglishDescription.setCategory(novell);
		novellEnglishDescription.setLanguage(en);
		
		CategoryDescription novellFrenchDescription = new CategoryDescription();
		novellFrenchDescription.setName("Roman");
		novellFrenchDescription.setCategory(novell);
		novellFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> descriptions3 = new ArrayList<CategoryDescription>();
		descriptions3.add(novellEnglishDescription);
		descriptions3.add(novellFrenchDescription);
		
		novell.setDescriptions(descriptions3);
		
		categoryService.create(novell);
		categoryService.addChild(book, novell);
		
		Category tech = new Category();
		tech.setDepth(1);
		tech.setLineage("/" + book.getId() + "/");
		tech.setMerchantSore(store);
		
		CategoryDescription techEnglishDescription = new CategoryDescription();
		techEnglishDescription.setName("Technology");
		techEnglishDescription.setCategory(tech);
		techEnglishDescription.setLanguage(en);
		
		CategoryDescription techFrenchDescription = new CategoryDescription();
		techFrenchDescription.setName("Technologie");
		techFrenchDescription.setCategory(tech);
		techFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> descriptions4 = new ArrayList<CategoryDescription>();
		descriptions4.add(techFrenchDescription);
		descriptions4.add(techFrenchDescription);
		
		tech.setDescriptions(descriptions4);
		
		categoryService.create(tech);
		categoryService.addChild(book, tech);
		
		
		Category fiction = new Category();
		fiction.setDepth(2);
		fiction.setLineage("/" + book.getId() + "/" + novell.getId() + "/");
		fiction.setMerchantSore(store);
		
		CategoryDescription fictionEnglishDescription = new CategoryDescription();
		fictionEnglishDescription.setName("Fiction");
		fictionEnglishDescription.setCategory(fiction);
		fictionEnglishDescription.setLanguage(en);
		
		CategoryDescription fictionFrenchDescription = new CategoryDescription();
		fictionFrenchDescription.setName("Sc Fiction");
		fictionFrenchDescription.setCategory(fiction);
		fictionFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> fictiondescriptions = new ArrayList<CategoryDescription>();
		fictiondescriptions.add(fictionEnglishDescription);
		fictiondescriptions.add(fictionFrenchDescription);
		
		fiction.setDescriptions(fictiondescriptions);
		
		categoryService.create(fiction);
		categoryService.addChild(book, fiction);
		
		//Add products
		//ProductType generalType = productTypeService.
		
		Manufacturer oreilley = new Manufacturer();
		oreilley.setMerchantSore(store);
		
		ManufacturerDescription oreilleyd = new ManufacturerDescription();
		oreilleyd.setLanguage(en);
		oreilleyd.setName("O\'reilley");
		oreilleyd.setManufacturer(oreilley);
		oreilley.getDescriptions().add(oreilleyd);
		
		manufacturerService.create(oreilley);
		
		Manufacturer packed = new Manufacturer();
		packed.setMerchantSore(store);
		
		ManufacturerDescription packedd = new ManufacturerDescription();
		packedd.setLanguage(en);
		packedd.setManufacturer(packed);
		packedd.setName("Packed publishing");
		packed.getDescriptions().add(packedd);
		
		manufacturerService.create(packed);
		
		Manufacturer novells = new Manufacturer();
		novells.setMerchantSore(store);
		
		ManufacturerDescription novellsd = new ManufacturerDescription();
		novellsd.setLanguage(en);
		novellsd.setManufacturer(novells);
		novellsd.setName("Novells publishing");
		novells.getDescriptions().add(novellsd);
		
		manufacturerService.create(novells);
		
		//PRODUCT 1
		
		Product product = new Product();
		product.setProductHeight(new BigDecimal(4));
		product.setProductLength(new BigDecimal(3));
		product.setProductWidth(new BigDecimal(1));
		product.setSku("TB12345");
		product.setManufacturer(oreilley);
		product.setType(generalType);
		product.setMerchantSore(store);
		
		
		//Product description
		ProductDescription description = new ProductDescription();
		description.setName("Spring in Action");
		description.setLanguage(en);
		description.setProduct(product);
		
		product.getDescriptions().add(description);
		
		product.getCategories().add(tech);
		
		productService.create(product);
		
		//Availability
		ProductAvailability availability = new ProductAvailability();
		availability.setProductDateAvailable(date);
		availability.setProductQuantity(100);
		availability.setRegion("*");
		availability.setProduct(product);//associate with product
		
		productAvailabilityService.create(availability);
		
		ProductPrice dprice = new ProductPrice();
		dprice.setDefaultPrice(true);
		dprice.setProductPriceAmount(new BigDecimal(29.99));
		dprice.setProductPriceAvailability(availability);

		ProductPriceDescription dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice);
		dpd.setLanguage(en);
		
		dprice.getDescriptions().add(dpd);
		
		productPriceService.create(dprice);
		

		
		//PRODUCT 2
		
		Product product2 = new Product();
		product2.setProductHeight(new BigDecimal(4));
		product2.setProductLength(new BigDecimal(3));
		product2.setProductWidth(new BigDecimal(1));
		product2.setSku("TB2468");
		product2.setManufacturer(packed);
		product2.setType(generalType);
		product2.setMerchantSore(store);
		
		
		//Product description
		description = new ProductDescription();
		description.setName("This is Node.js");
		description.setLanguage(en);
		description.setProduct(product2);
		
		product2.getDescriptions().add(description);
		
		product2.getCategories().add(tech);
		productService.create(product2);
		
		//Availability
		ProductAvailability availability2 = new ProductAvailability();
		availability2.setProductDateAvailable(date);
		availability2.setProductQuantity(100);
		availability2.setRegion("*");
		availability2.setProduct(product2);//associate with product
		
		productAvailabilityService.create(availability2);
		
		ProductPrice dprice2 = new ProductPrice();
		dprice2.setDefaultPrice(true);
		dprice2.setProductPriceAmount(new BigDecimal(39.99));
		dprice2.setProductPriceAvailability(availability2);

		dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice2);
		dpd.setLanguage(en);
		
		dprice2.getDescriptions().add(dpd);
		
		productPriceService.create(dprice2);
		
		//PRODUCT 3
		
		Product product3 = new Product();
		product3.setProductHeight(new BigDecimal(4));
		product3.setProductLength(new BigDecimal(3));
		product3.setProductWidth(new BigDecimal(1));
		product3.setSku("NB1111");
		product3.setManufacturer(packed);
		product3.setType(generalType);
		product3.setMerchantSore(store);
		
		
		//Product description
		description = new ProductDescription();
		description.setName("A nice book for you");
		description.setLanguage(en);
		description.setProduct(product3);
		
		product3.getDescriptions().add(description);
		
		product3.getCategories().add(novell);
		productService.create(product3);
		
		//Availability
		ProductAvailability availability3 = new ProductAvailability();
		availability3.setProductDateAvailable(date);
		availability3.setProductQuantity(100);
		availability3.setRegion("*");
		availability3.setProduct(product3);//associate with product
		
		productAvailabilityService.create(availability3);
		
		ProductPrice dprice3 = new ProductPrice();
		dprice3.setDefaultPrice(true);
		dprice3.setProductPriceAmount(new BigDecimal(19.99));
		dprice3.setProductPriceAvailability(availability3);

		dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice3);
		dpd.setLanguage(en);
		
		dprice3.getDescriptions().add(dpd);
		
		productPriceService.create(dprice3);
		
		
		
		//PRODUCT 4
		
		Product product4 = new Product();
		product4.setProductHeight(new BigDecimal(4));
		product4.setProductLength(new BigDecimal(3));
		product4.setProductWidth(new BigDecimal(1));
		product4.setSku("SF333345");
		product4.setManufacturer(packed);
		product4.setType(generalType);
		product4.setMerchantSore(store);
				
				
		//Product description
		description = new ProductDescription();
		description.setName("Battle of the worlds");
		description.setLanguage(en);
		description.setProduct(product4);
		
		product4.getDescriptions().add(description);
				
		product4.getCategories().add(fiction);
		productService.create(product4);
				
		//Availability
		ProductAvailability availability4 = new ProductAvailability();
		availability4.setProductDateAvailable(date);
		availability4.setProductQuantity(100);
		availability4.setRegion("*");
		availability4.setProduct(product4);//associate with product
				
		productAvailabilityService.create(availability4);
				
		ProductPrice dprice4 = new ProductPrice();
		dprice4.setDefaultPrice(true);
		dprice4.setProductPriceAmount(new BigDecimal(18.99));
		dprice4.setProductPriceAvailability(availability4);

		dpd = new ProductPriceDescription();
		dpd.setName("Base price");
		dpd.setProductPrice(dprice4);
		dpd.setLanguage(en);
		
		dprice4.getDescriptions().add(dpd);
				
		productPriceService.create(dprice4);
	
		
		
		
	}
	
	@Test
	public void testGetProduct() throws ServiceException {
		
		Language language = languageService.getByCode("en");
		
		Locale locale = new Locale("en","CA");
		
		Product p = productService.getProductForLocale(1L, language, locale);
		
		if(p!=null) {
			
			System.out.println(p.getDescriptions().size());
			
		}
		
	}
	
	@Test
	public void testGetProducts() throws ServiceException {
		
		Language language = languageService.getByCode("en");
		
		Locale locale = new Locale("en","CA");
		
		Category category = categoryService.getByName("Novell");
		
		List<Product> products = productService.getProductsForLocale(category, language, locale);
		
		for(Product product : products) {
			
			System.out.println(product.getId());
			
		}
		
	}
	
	
	@Test
	public void testCategory() throws ServiceException {
		
		
		/**
		 * Creates a category hierarchy
		 * Music
		 * Books
		 * 		Novell
		 * 			Science-Fiction
		 * 		Technology
		 * 		Business
		 */
		
		Language en = new Language();
		en.setCode("en");
		languageService.create(en);
		
		Language fr = new Language();
		fr.setCode("fr");
		languageService.create(fr);
		

		
		//create country
		Country ca = new Country();
		ca.setIsoCode("CA");
		
		CountryDescription caden = new CountryDescription();
		caden.setCountry(ca);
		caden.setLanguage(en);
		caden.setName("Canada");
		caden.setDescription("Canada Country");
		
		CountryDescription cadfr = new CountryDescription();
		cadfr.setCountry(ca);
		cadfr.setLanguage(fr);
		cadfr.setName("Canada");
		cadfr.setDescription("Pays Canada");
		
		List<CountryDescription> descriptionsca = new ArrayList<CountryDescription>();
		descriptionsca.add(caden);
		descriptionsca.add(cadfr);
		ca.setDescriptions(descriptionsca);
		
		countryService.create(ca);
		
		//create a currency
		Currency currency = new Currency();
		currency.setCurrency(java.util.Currency.getInstance(Locale.CANADA));
		currency.setSupported(true);
		currencyService.create(currency);
		
		//create a merchant
		MerchantStore store = new MerchantStore();
		store.setCountry(ca);
		store.setCurrency(currency);
		store.setDefaultLanguage(en);
		store.setInBusinessSince(date);
		store.setStorename("store name");
		store.setCode(MerchantStore.DEFAULT_STORE);
		store.setStoreEmailAddress("test@test.com");
		
		merchantService.create(store);
		
		
		Category book = new Category();
		book.setDepth(0);
		book.setLineage("/");
		book.setMerchantSore(store);
		
		CategoryDescription bookEnglishDescription = new CategoryDescription();
		bookEnglishDescription.setName("Book");
		bookEnglishDescription.setCategory(book);
		bookEnglishDescription.setLanguage(en);
		
		CategoryDescription bookFrenchDescription = new CategoryDescription();
		bookFrenchDescription.setName("Livre");
		bookFrenchDescription.setCategory(book);
		bookFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> descriptions = new ArrayList<CategoryDescription>();
		descriptions.add(bookEnglishDescription);
		descriptions.add(bookFrenchDescription);
		
		book.setDescriptions(descriptions);
		
		categoryService.create(book);
		
		
		Category music = new Category();
		music.setDepth(0);
		music.setLineage("/");
		music.setMerchantSore(store);
		
		CategoryDescription musicEnglishDescription = new CategoryDescription();
		musicEnglishDescription.setName("Music");
		musicEnglishDescription.setCategory(music);
		musicEnglishDescription.setLanguage(en);
		
		CategoryDescription musicFrenchDescription = new CategoryDescription();
		musicFrenchDescription.setName("Musique");
		musicFrenchDescription.setCategory(music);
		musicFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> descriptions2 = new ArrayList<CategoryDescription>();
		descriptions2.add(musicEnglishDescription);
		descriptions2.add(musicFrenchDescription);
		
		music.setDescriptions(descriptions2);
		
		categoryService.create(music);
		
		Category novell = new Category();
		novell.setDepth(1);
		novell.setLineage("/" + book.getId() + "/");
		novell.setParent(book);
		novell.setMerchantSore(store);
		
		CategoryDescription novellEnglishDescription = new CategoryDescription();
		novellEnglishDescription.setName("Novell");
		novellEnglishDescription.setCategory(novell);
		novellEnglishDescription.setLanguage(en);
		
		CategoryDescription novellFrenchDescription = new CategoryDescription();
		novellFrenchDescription.setName("Roman");
		novellFrenchDescription.setCategory(novell);
		novellFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> descriptions3 = new ArrayList<CategoryDescription>();
		descriptions3.add(novellEnglishDescription);
		descriptions3.add(novellFrenchDescription);
		
		novell.setDescriptions(descriptions3);
		
		categoryService.create(novell);
		
		Category tech = new Category();
		tech.setDepth(1);
		tech.setParent(book);
		tech.setLineage("/" + book.getId() + "/");
		tech.setMerchantSore(store);
		
		CategoryDescription techEnglishDescription = new CategoryDescription();
		techEnglishDescription.setName("Technology");
		techEnglishDescription.setCategory(tech);
		techEnglishDescription.setLanguage(en);
		
		CategoryDescription techFrenchDescription = new CategoryDescription();
		techFrenchDescription.setName("Technologie");
		techFrenchDescription.setCategory(tech);
		techFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> descriptions4 = new ArrayList<CategoryDescription>();
		descriptions4.add(techFrenchDescription);
		descriptions4.add(techFrenchDescription);
		
		tech.setDescriptions(descriptions4);
		
		categoryService.create(tech);
		
		
		Category fiction = new Category();
		fiction.setDepth(2);
		fiction.setParent(novell);
		fiction.setLineage("/" + book.getId() + "/" + novell.getId() + "/");
		fiction.setMerchantSore(store);
		
		CategoryDescription fictionEnglishDescription = new CategoryDescription();
		fictionEnglishDescription.setName("Fiction");
		fictionEnglishDescription.setCategory(fiction);
		fictionEnglishDescription.setLanguage(en);
		
		CategoryDescription fictionFrenchDescription = new CategoryDescription();
		fictionFrenchDescription.setName("Sc Fiction");
		fictionFrenchDescription.setCategory(fiction);
		fictionFrenchDescription.setLanguage(fr);
		
		List<CategoryDescription> fictiondescriptions = new ArrayList<CategoryDescription>();
		fictiondescriptions.add(fictionEnglishDescription);
		fictiondescriptions.add(fictionFrenchDescription);
		
		fiction.setDescriptions(fictiondescriptions);
		
		categoryService.create(fiction);
		
		
		categoryService.delete(novell);
		
		
	}
	
	
	@Test
	public void testGetCategory() throws ServiceException {
		
		
		Category category = categoryService.getByName("Novell");
		System.out.println("Done");
		
	}

	
	
	
	@Test
	public void testCreateManufacturer() throws ServiceException {
		
		
		Language english = new Language();
		english.setCode("en");
		languageService.create(english);
		
		Language french = new Language();
		french.setCode("fr");
		languageService.create(french);
		
		Currency euro = new Currency();
		euro.setCurrency(java.util.Currency.getInstance("EUR"));
		currencyService.create(euro);
		
		Currency cad = new Currency();
		cad.setCurrency(java.util.Currency.getInstance("CAD"));
		currencyService.create(cad);
		
		Country fr = new Country("FR");
		countryService.create(fr);
		
		Country ca = new Country("CA");
		countryService.create(ca);
		
		
		Language DEFAULT_LANGUAGE = languageService.getByCode("en");
		Language FRENCH = languageService.getByCode("fr");
		Currency currency = currencyService.getByCode("CAD");

		
		
		//create a merchant
		MerchantStore store = new MerchantStore();
		store.setCountry(ca);
		store.setCurrency(currency);
		store.setDefaultLanguage(DEFAULT_LANGUAGE);
		store.setInBusinessSince(date);
		store.setStorename("store name");
		store.setStoreEmailAddress("test@test.com");
		merchantService.create(store);
		
		Manufacturer manufacturer = new Manufacturer();
		//store.getManufacturers().add(manufacturer);
		
		//merchantService.update(store);
		
		//Manufacturer manufacturer = new Manufacturer();
		manufacturer.setMerchantSore(store);
		
		ManufacturerDescription fd = new ManufacturerDescription();
		fd.setLanguage(FRENCH);
		fd.setName("Sony french");
		fd.setManufacturer(manufacturer);
		
		ManufacturerDescription ed = new ManufacturerDescription();
		ed.setLanguage(DEFAULT_LANGUAGE);
		ed.setName("Sony english");
		ed.setManufacturer(manufacturer);
		
		Set descriptions = new HashSet();
		descriptions.add(fd);
		descriptions.add(ed);
		
		manufacturer.setDescriptions(descriptions);
		
		
		
		
		manufacturerService.create(manufacturer);
		
		//manufacturerService.delete(manufacturer);
		//merchantService.delete(store);
		
	}
	
	@Test
	public void testDeleteManufacturerService() throws ServiceException {
		
		Manufacturer manufacturer = manufacturerService.getById(1L);
		manufacturerService.delete(manufacturer);
		
	}

}