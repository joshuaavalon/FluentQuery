package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class Operator {
	@NonNull
	private final String expression;

	@NonNull
	private final List<String> arguments;

	protected Operator(@NonNull final String expression, @NonNull final String... arguments) {
		this.expression = expression;
		this.arguments = Arrays.asList(arguments);
	}


	protected Operator(@NonNull final String expression, @NonNull final List<String> arguments) {
		this.expression = expression;
		this.arguments = new ArrayList<>(arguments);
	}

	@NonNull
	String getExpression() {
		return expression;
	}

	@NonNull
	List<String> getArguments() {
		return arguments;
	}
}
