/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.core.converters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.social.yahoo.module.YahooQuote;
import org.springframework.social.yahoo.module.QuoteWrapper;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class YahooHistoMessageConverter extends AbstractHttpMessageConverter<QuoteWrapper> {

    public YahooHistoMessageConverter() {
    }

    public YahooHistoMessageConverter(MediaType supportedMediaType) {
        super(supportedMediaType);
    }

    public YahooHistoMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return QuoteWrapper.class.equals(clazz);
    }

    @Override
    protected QuoteWrapper readInternal(Class<? extends QuoteWrapper> clazz, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        CSVReader reader = new CSVReader(new InputStreamReader(httpInputMessage.getBody()));
        List<String[]> rows = reader.readAll();
        QuoteWrapper quoteWrapper = new QuoteWrapper();
        for (String[] row : rows) {

        	quoteWrapper.add(new YahooQuote(row[0], 
        								row[1], 
        								parseDouble(row[2]), 
        								parseDouble(row[3]), 
        								parseDouble(row[4]), 
        								parseDouble(row[5]), 
        								parsePercent(row[6]), 
        								parseDouble(row[7]), 
        								parseDouble(row[8]), 
        								parseDouble(row[9]), 
        								parseDouble(row[10]), 
        								parseInt(row[11]), 
        								row[12], 
        								row[13]));
        }

        return quoteWrapper;
    }
    
    private static double parseDouble(String s){
    	try{
    		return Double.parseDouble(s);
    	}
    	catch(NumberFormatException e){
    		return 0;
    	}
    }
    
    private static int parseInt(String s){
    	try{
    		return Integer.parseInt(s);
    	}
    	catch(NumberFormatException e){
    		return 0;
    	}
    }
    
    private static double parsePercent(String s){
    	return parseDouble(s.endsWith("%") ? s.substring(0, s.length()-1) : s);
    }

    @Override
    protected void writeInternal(QuoteWrapper quotes, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(httpOutputMessage.getBody()));
        for (YahooQuote quote : quotes) {
            writer.writeNext(
            		new String[]{	quote.getId(),
            						quote.getName(),
            						String.valueOf(quote.getOpen()),
            						String.valueOf(quote.getPreviousClose()),
                    				String.valueOf(quote.getLast()),
                            		String.valueOf(quote.getLastChange()),
                            		String.valueOf(quote.getLastChangePercent()),
                            		String.valueOf(quote.getHigh()),
                            		String.valueOf(quote.getLow()),
                            		String.valueOf(quote.getBid()),
                            		String.valueOf(quote.getAsk()),
                            		String.valueOf(quote.getVolume()),
            						quote.getExchange(),
            						quote.getCurrency()
            		});
        }

        writer.close();
    }
}

