package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.api.resources.TransactionResource.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.api.assemblers.TransactionResourceAssembler;
import edu.zipcloud.cloudstreetmarket.api.resources.TransactionResource;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.services.TransactionService;

@Api(value = TRANSACTIONS, description = "Transactions") // Swagger annotation
@RestController
@ExposesResourceFor(Transaction.class)
@RequestMapping(value=ACTIONS_PATH + TRANSACTIONS_PATH, produces={"application/xml", "application/json"})
public class TransactionController extends CloudstreetApiWCI<Transaction> {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionResourceAssembler assembler;
	
	@RequestMapping(method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get the user transactions", notes = "Return the transactions of a user")
	public PagedResources<TransactionResource> search(
			@ApiParam(value="User id: WHATEVERKEY") @RequestParam(value="user", required=false) String userName,
			@ApiParam(value="Quote id: 123L") @RequestParam(value="quote:[\\d]+", required=false) Long quoteId,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"lastUpdate"}, direction=Direction.DESC) Pageable pageable
			){
		return pagedAssembler.toResource(transactionService.findBy(pageable, userName, quoteId), assembler);
	}
	
	@RequestMapping(value="/{id}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a transaction", notes = "Return one trannsaction")
	public TransactionResource get(@ApiParam(value="Transaction id: 24") @PathVariable(value="id") Long transactionId){
		return assembler.toResource(transactionService.get(transactionId));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Processes a transaction")
	public TransactionResource post(Transaction transaction) {
		return assembler.toResource(transactionService.save(transaction));
	}
}