package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import net.kaczmarzyk.spring.data.jpa.domain.EqualEnum;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.Api;

import edu.zipcloud.cloudstreetmarket.core.dtos.ProductOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.params.MarketCodeParam;
import edu.zipcloud.cloudstreetmarket.core.params.SortDirectionParam;
import edu.zipcloud.cloudstreetmarket.core.params.SortFieldParam;
import edu.zipcloud.cloudstreetmarket.core.services.IProductService;
import edu.zipcloud.util.SortUtil;

@Api(value = "stocks", description = "Financial stocks") // Swagger annotation
@RestController
@RequestMapping(value=ProductController.PRODUCT_PATH + "/stocks", produces={"application/xml", "application/json"})
public class StockProductController extends ProductController{

	@Autowired
	private WebApplicationContext webAppContext;

	@Autowired
	private IProductService<StockProduct> productService;
	
	@RequestMapping(method=GET)
	@ResponseStatus(HttpStatus.OK)
	public Page<ProductOverviewDTO> search(
			@And(value = { @Spec(params = "mkt", path="market.code",spec = EqualEnum.class)},
            and = { @Or({
                @Spec(params="cn", path="code", spec=LikeIgnoreCase.class),
                @Spec(params="cn", path="name", spec=LikeIgnoreCase.class)})}	
			) Specification<StockProduct> spec,
            @RequestParam(value="mkt", required=false) MarketCodeParam market, 
			@RequestParam(value="sw", defaultValue="") String startWith, 
			@RequestParam(value="cn", defaultValue="") String contain, 
			@RequestParam(value="sf", defaultValue="dailyLatestValue") SortFieldParam sortFields, 
			@RequestParam(value="sd", defaultValue="desc") SortDirectionParam sortDirections, 
			@RequestParam(value="pn", defaultValue="0") int pageNumber, 
			@RequestParam(value="ps", defaultValue="10") int pageSize){
		return productService.getProductsOverview(
			startWith, spec, new PageRequest(
				pageNumber, 
				pageSize, 
				SortUtil.buildSort(sortFields.getFields(), sortDirections.getDirections()))
		);
	}
}