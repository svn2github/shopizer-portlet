package com.salesmanager.web.images;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesmanager.core.business.catalog.product.service.image.ProductImageService;
import com.salesmanager.core.business.content.model.image.ImageContentType;
import com.salesmanager.core.business.content.model.image.OutputContentImage;
import com.salesmanager.core.business.content.service.ContentService;

@Controller
public class ImagesController {

	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private ProductImageService productImageService;
	
	/**
	 * Logo, content image
	 * @param storeId
	 * @param imageType
	 * @param imageName
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/static/{storeId}/{imageType}/{imageName}")
	public @ResponseBody byte[] printImage(@PathVariable final Integer storeId, @PathVariable final String imageType, @PathVariable final String imageName) throws IOException {
	    //For testing
		//InputStream in = servletContext.getResourceAsStream("/images/test.jpg");
	    //return IOUtils.toByteArray(in);
		
		// example -> /static/1/CONTENT/myImage.png
		
		ImageContentType imgType = null;
		
		if(imageType.equals(ImageContentType.LOGO)) {
			imgType = ImageContentType.LOGO;
		}
		
		if(imageType.equals(ImageContentType.CONTENT)) {
			imgType = ImageContentType.CONTENT;
		}
		
		OutputContentImage image = null;//contentService.getContentImage(storeId, imageName);
		if(image!=null) {
			return image.getImage().toByteArray();
		} else {
			//empty image placeholder
			return null;
		}

	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/static/{storeId}/{imageType}/{productId}/{imageName}")
	public @ResponseBody byte[] printImage(@PathVariable final Integer storeId, @PathVariable final Long productId, @PathVariable final String imageType, @PathVariable final String imageName) throws IOException {
	    //For testing
		//InputStream in = servletContext.getResourceAsStream("/images/test.jpg");
	    //return IOUtils.toByteArray(in);
		
		// example -> /static/1/PRODUCT/120/product1.jpg
		
		ImageContentType imgType = null;
		
		if(imageType.equals(ImageContentType.PRODUCT)) {
			imgType = ImageContentType.PRODUCT;
		}
		
		if(imageType.equals(ImageContentType.PROPERTY)) {
			imgType = ImageContentType.PROPERTY;
		}
		
		OutputContentImage image = null;//productImageService.getProductImage(storeId, productId, imageName);//TODO service required for property
		if(image!=null) {
			return image.getImage().toByteArray();
		} else {
			//empty image placeholder
			return null;
		}

	}

}
