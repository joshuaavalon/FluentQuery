package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Condition {
	@NonNull
	private final String expression;

	@NonNull
	private final List<String> arguments;

	Condition(@NonNull final String expression, @NonNull final String... arguments) {
		this.expression = expression;
		this.arguments = Arrays.asList(arguments);
	}

	Condition(@NonNull final String expression, @NonNull final List<String> arguments) {
		this.expression = expression;
		this.arguments = new ArrayList<>(arguments);
	}

	@NonNull
	public static Property property(@NonNull final String name) {
		return new Property(name);
	}

	@NonNull
	String getExpression() {
		return expression;
	}

	@NonNull
	String getFullExpression() {
		return String.format(expression.replaceAll("\\?", "%s"), arguments);
	}

	@NonNull
	List<String> getArguments() {
		return arguments;
	}

	@NonNull
	public Condition and(@NonNull final Condition condition) {
		return combine(DefaultOperators.AND, condition);
	}

	@NonNull
	public Condition or(@NonNull final Condition condition) {
		return combine(DefaultOperators.OR, condition);
	}

	@NonNull
	public Condition not() {
		return new Condition(DefaultOperators.NOT.getSymbol() + Utility.bracketString(expression),
				arguments);
	}

	@NonNull
	private Condition combine(@NonNull final DefaultOperators operator,
							  @NonNull final Condition condition) {
		final String newExpression = Utility.bracketString(expression) +
				operator.getSymbol() +
				Utility.bracketString(expression);
		final List<String> newArguments = new ArrayList<>(arguments);
		newArguments.addAll(condition.arguments);
		return new Condition(newExpression, newArguments);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("expression", expression)
				.add("arguments", arguments)
				.toString();
	}

	private enum DefaultOperators {
		AND(" AND "),
		OR(" OR "),
		NOT(" NOT ");

		@NonNull
		private final String symbol;

		DefaultOperators(@NonNull final String symbol) {
			this.symbol = symbol;
		}

		@NonNull
		public String getSymbol() {
			return symbol;
		}
	}
}