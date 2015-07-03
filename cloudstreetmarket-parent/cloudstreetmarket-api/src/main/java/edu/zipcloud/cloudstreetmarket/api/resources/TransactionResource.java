package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;

@XStreamAlias("resource")
public class TransactionResource extends Resource<Transaction> {

	public static final String ACTIONS_PATH = "/actions";
	public static final String TRANSACTIONS_PATH = "/transactions";
	public static final String TRANSACTIONS = "transactions";
	public static final String TRANSACTION = "transaction";

	public TransactionResource(Transaction content, Link... links) {
		super(content, links);
	}
}