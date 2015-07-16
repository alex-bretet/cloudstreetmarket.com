package edu.zipcloud.cloudstreetmarket.api.controllers;

import static edu.zipcloud.cloudstreetmarket.api.resources.TransactionResource.ACTIONS_PATH;
import static edu.zipcloud.cloudstreetmarket.api.resources.TransactionResource.TRANSACTIONS;
import static edu.zipcloud.cloudstreetmarket.api.resources.TransactionResource.TRANSACTIONS_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import edu.zipcloud.cloudstreetmarket.api.services.CurrencyExchangeService;
import edu.zipcloud.cloudstreetmarket.core.entities.CurrencyExchange;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;
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
	
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	@RequestMapping(method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get the user transactions", notes = "Return the transactions of a user")
	public PagedResources<TransactionResource> search(
			@ApiParam(value="User id: WHATEVERKEY") @RequestParam(value="user", required=false) String userName,
			@ApiParam(value="Quote id: 123L") @RequestParam(value="quote:[\\d]+", required=false) Long quoteId,
			@ApiParam(value="Product ticker: FB") @RequestParam(value="ticker:[a-zA-Z0-9-:]+", required=false) String ticker,
			@ApiIgnore @PageableDefault(size=10, page=0, sort={"lastUpdate"}, direction=Direction.DESC) Pageable pageable
			){
		return pagedAssembler.toResource(transactionService.findBy(pageable, userName, quoteId, ticker), assembler);
	}
	
	@RequestMapping(value="/{id}", method=GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a transaction", notes = "Return one trannsaction")
	public TransactionResource get(@ApiParam(value="Transaction id: 24") @PathVariable(value="id") Long transactionId){
		return assembler.toResource(transactionService.get(transactionId));
	}
	
	@RequestMapping(method=POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Processes a transaction")
	public TransactionResource post(@RequestBody Transaction transaction) {

		transactionService.hydrate(transaction);
		
		if(!transaction.getUser().getUsername().equals(getPrincipal().getUsername())){
			throw new AccessDeniedException("Wrong targetted user!");
		}
		
		CurrencyExchange currencyExchange = null;
		
		if(!getAuthenticated().getCurrency().equals(transaction.getQuote().getSupportedCurrency())){
			currencyExchange = currencyExchangeService.gather(transaction.getQuote().getSupportedCurrency().name()+ getAuthenticated().getCurrency().name() + "=X");
		}

		if(transaction.getType().equals(UserActivityType.BUY)){
			if(communityService.isAffordableToUser(transaction.getQuantity(), transaction.getQuote(), transaction.getUser(), currencyExchange)){
				communityService.alterUserBalance(transaction.getQuantity(), transaction.getQuote(), transaction.getUser(), transaction.getType(), currencyExchange);
				transaction = transactionService.save(transaction);
			}
			else{
				throw new AccessDeniedException("You can't afford the transaction!");
			}
		}
		else if(transaction.getType().equals(UserActivityType.SELL)){
			if(transactionService.isOwnedByUser(transaction.getUser(), transaction.getQuantity(), transaction.getQuote().getStock())){
				communityService.alterUserBalance(transaction.getQuantity(), transaction.getQuote(), transaction.getUser(), transaction.getType(), currencyExchange);
				transaction = transactionService.save(transaction);
			}
			else{
				throw new AccessDeniedException("You actually don't own enough products that you want to sell!");
			}
		}

		return assembler.toResource(transaction);
	}
}