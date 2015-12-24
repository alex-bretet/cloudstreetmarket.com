package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import net.kaczmarzyk.spring.data.jpa.domain.EqualEnum;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.core.dtos.StockProductOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;
import edu.zipcloud.cloudstreetmarket.core.services.IProductService;

@Api(value = "stocks", description = "Financial stocks") // Swagger annotation
@RestController
@RequestMapping(value=ProductController.PRODUCT_PATH + "/stocks", produces={"application/xml", "application/json"})
public class StockProductController extends ProductController{

	@Autowired
	private WebApplicationContext webAppContext;

	@Autowired
	private IProductService<StockProduct, StockProductOverviewDTO> productService;
	
	@RequestMapping(method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get overviews of stocks", notes = "Return a page of stock-overviews")
	public Page<StockProductOverviewDTO> search(
			@And(value = { @Spec(params = "mkt", path="market.code",spec = EqualEnum.class)},
            and = { @Or({
                @Spec(params="cn", path="code", spec=LikeIgnoreCase.class),
                @Spec(params="cn", path="name", spec=LikeIgnoreCase.class)})}	
			) @ApiIgnore Specification<StockProduct> spec,
            @RequestParam(value="mkt", required=false) MarketCode market, 
            @ApiParam(value="Starts with filter") @RequestParam(value="sw", defaultValue="") String startWith, 
            @ApiParam(value="Contains filter") @RequestParam(value="cn", defaultValue="") String contain, 
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"dailyLatestValue"}, direction=Direction.DESC) Pageable pageable){
		return productService.getProductsOverview(startWith, spec, pageable);
	}
	
	@RequestMapping(value="/{code}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one stock-overview", notes = "Return one stock-overview")
	public StockProductOverviewDTO getByCode(@ApiParam(value="Stock code: CCH.L") @PathVariable(value="code") StockProduct stock){
		return StockProductOverviewDTO.build(stock);
	}
}