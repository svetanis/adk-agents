package com.svetanis.agents.currency;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Provider;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;
import com.google.common.collect.ImmutableList;
import com.svetanis.agents.AgentConfig;
import com.svetanis.agents.AgentConfigProvider;
import com.svetanis.agents.AgentContext;
import com.svetanis.agents.LlmAgentProvider;

public class CurrencyAgent implements Provider<LlmAgent> {

	private static final String CURRENCY = "currency/currency-agent";

	private static final String ERF_DESC = """
			Utilizes YahooFinance API to look up the exchange rate between two currencies
			Returns:
				Map with status, baseCurrency, targetCurrency and rate information
				Success: {"status" : "success", "baseCurrency" : baseCurrency,
				"targetCurrency" : targetCurrency, "rate" : 0.93}
				Error: {"status" : "error", "errorMessage" : "Unsupported currency pair"}
			""";

	private static final String PMF_DESC = """
			Looks up the transaction fee percentage for a given payment method.
			This tool simulates looking up a company's internal fee structure
			based on the name of the payment method provided by the user.
			Returns:
				Map with status and fee information
				Success: {"status" : "success", "feePercentage" : 0.02}
				Error: {"status" : "error", "errorMessage" : "Payment method not found"}
			""";

	private static final String CURR_DESC = "The ISO 4217 currency code of the currency you are converting ";
	private static final String PM_DESC = """
				The name of the payment method. It should be descriptive,
				e.g., platinum credit card or bank transfer
			""";

	@Override
	public LlmAgent get() {
		AgentContext ctx = ctx();
		return new LlmAgentProvider(ctx).get();
	}

	private AgentContext ctx() {
		FunctionTool pmf = FunctionTool.create(CurrencyAgent.class, "paymentMethodFee");
		FunctionTool exr = FunctionTool.create(CurrencyAgent.class, "exchangeRate");
		AgentTool cat = AgentTool.create(new CalculatorAgent().get());
		AgentConfig config = new AgentConfigProvider(CURRENCY).get();
		return AgentContext//
				.builder()//
				.withConfig(config)//
				.withTools(ImmutableList.of(pmf, exr, cat))//
				.build();
	}

	@Schema(description = PMF_DESC)
	public static Map<String, Object> paymentMethodFee(
			@Schema(description = PM_DESC, name = "paymentMethod") String paymentMethod) {
		// TODO: add validation for user input
		String key = paymentMethod.toLowerCase().replace(" ", ".");
		Map<String, Double> fees = paymentMethodFees();
		if (fees.containsKey(key)) {
			double feePercentage = fees.get(key);
			return Map.of("status", "success", "feePercentage", feePercentage);
		} else {
			String msg = String.format("Payment method [%s] not found", paymentMethod);
			return Map.of("status", "error", "errorMessage", msg);
		}
	}

	private static Map<String, Double> paymentMethodFees() {
		Map<String, Double> map = new HashMap<>();
		map.put("platinum.credit.card", 0.02);
		map.put("gold.debit.card", 0.035);
		map.put("bank.transfer", 0.01);
		return map;

	}

	@Schema(description = ERF_DESC)
	public static Map<String, String> exchangeRate(
			@Schema(description = CURR_DESC + "from (e.g., USD)", name = "baseCurrency") String baseCurrency,
			@Schema(description = CURR_DESC + "to (e.g., EUR)", name = "targetCurrency") String targetCurrency) {
		// TODO: add validation for user input
		Optional<Double> rate = new RateProvider(baseCurrency, targetCurrency).get();
		if (rate.isPresent()) {
			String msg = "Tool: Found live exchange rate from %s to %s : %s ";
			System.out.println(String.format(msg, baseCurrency, targetCurrency, rate));
			Map<String, String> map = new HashMap<>();
			map.put("status", "success");
			map.put("baseCurrency", baseCurrency);
			map.put("targetCurrency", targetCurrency);
			map.put("rate", String.valueOf(rate));
			return map;
		} else {
			String msg = String.format("Unsupported currency pair %s -> %s", baseCurrency, targetCurrency);
			return Map.of("status", "error", "errorMessage", msg);
		}
	}
}