package org.springframework.social.yahoo.module;

import java.util.ArrayList;
import java.util.Collection;

public class QuoteWrapper extends ArrayList<YahooQuote> {
	
	private static final long serialVersionUID = -3924648737519678218L;

	public QuoteWrapper() {
    }

    public QuoteWrapper(Collection<? extends YahooQuote> c) {
        super(c);
    }

	@Override
	public String toString() {
		return "QuoteWrapper [" + super.toString() + "]";
	}

}
