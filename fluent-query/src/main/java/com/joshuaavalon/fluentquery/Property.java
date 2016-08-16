package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

public class Property {
	@NonNull
	private final String name;

	Property(@NonNull final String name) {
		this.name = name;
	}

	@NonNull
	private Condition build(@NonNull final Operator operator) {
		return new Condition(name + operator.getExpression(), operator.getArguments());
	}

	@NonNull
	public Condition equal(@NonNull final String argument) {
		return build(Operators.equal(argument));
	}

	@NonNull
	public Condition greaterThan(@NonNull final String argument) {
		return build(Operators.greaterThan(argument));
	}

	@NonNull
	public Condition greaterThanOrEqual(@NonNull final String argument) {
		return build(Operators.greaterThanOrEqual(argument));
	}

	@NonNull
	public Condition lesserThan(@NonNull final String argument) {
		return build(Operators.lesserThan(argument));
	}

	@NonNull
	public Condition lesserThanOrEqual(@NonNull final String argument) {
		return build(Operators.lesserThanOrEqual(argument));
	}

	@NonNull
	public Condition notEqual(@NonNull final String argument) {
		return build(Operators.notEqual(argument));
	}

	@NonNull
	public Condition like(@NonNull final String argument) {
		return like(argument, true);
	}

	@NonNull
	public Condition like(@NonNull final String argument, final boolean wildcard) {
		return build(Operators.like(wildcard ? Utility.wildcardString(argument) : argument));
	}

	@NonNull
	public Condition notLike(@NonNull final String argument) {
		return notLike(argument, true);
	}

	@NonNull
	public Condition notLike(@NonNull final String argument, final boolean wildcard) {
		return build(Operators.notLike(wildcard ? Utility.wildcardString(argument) : argument));
	}

	@NonNull
	public Condition isNull() {
		return new Condition(name + Operators.isNull().getExpression());
	}

	@NonNull
	public Condition isNotNull() {
		return new Condition(name + Operators.isNotNull().getExpression());
	}
}
