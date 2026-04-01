package com.svetanis.agents.currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Provider;

import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

public class RateProvider implements Provider<Optional<Double>> {

	private static final String SYMBOL = "%s%s=X";

	public RateProvider(String base, String target) {
		this.base = base;
		this.target = target;
	}

	private final String base;
	private final String target;

	@Override
	public Optional<Double> get() {
		String symbol = String.format(SYMBOL, base, target);
		Optional<Double> fxRate = fxRate(symbol);
		if (fxRate.isPresent()) {
			return fxRate;
		}
		Map<String, Double> rates = rates();
		if (rates.containsKey(symbol)) {
			return Optional.of(rates.get(symbol));
		} else {
			return Optional.empty();
		}
	}

	private static Optional<Double> fxRate(String symbol) {
		try {
			FxQuote quote = YahooFinance.getFx(symbol);
			if (quote != null && quote.getPrice() != null) {
				BigDecimal rate = quote.getPrice();
				return Optional.of(rate.doubleValue());
			} else {
				return Optional.empty();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return Optional.empty();
		}
	}

	private static Map<String, Double> rates() {
		Map<String, Double> map = new HashMap<>();
		map.put(FxSymbols.USDEUR, 0.93);
		map.put(FxSymbols.USDJPY, 157.50);
		map.put(FxSymbols.USDGBP, 0.76);
		return map;
	}

}
