package edu.zipcloud.cloudstreetmarket.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import edu.zipcloud.cloudstreetmarket.core.daos.TransactionRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Quote;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.entities.User;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CommunityServiceImpl communityService;
	
	@Autowired
	private StockQuoteService stockQuoteService;
	
	@Override
	public Transaction get(Long transactionId) {
		return transactionRepository.findOne(transactionId);
	}

	@Override
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	@Override
	public Transaction create(Transaction transaction) {
		if(!transactionRepository.findByUserAndQuote(transaction.getUser(), transaction.getQuote()).isEmpty()){
			throw new DataIntegrityViolationException("A transaction for the quote and the user already exists!");
		}
		return transactionRepository.save(transaction);
	}

	@Override
	public Page<Transaction> findBy(Pageable pageable, String userName, Long quoteId) {
		Preconditions.checkArgument(userName != null || quoteId !=null, "The user AND the quote cannot be both null!");

		//TODO Handle multiple quote types
		Quote quote = stockQuoteService.get(quoteId);
		
		User user = communityService.findByUserName(userName);
		
		if(quote==null){
			return transactionRepository.findByUser(pageable, user);
		}
		if(user==null){
			return transactionRepository.findByQuote(pageable, quote);
		}
		return transactionRepository.findByUserAndQuote(pageable, user, quote);
	}
}
