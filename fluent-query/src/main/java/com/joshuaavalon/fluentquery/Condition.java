package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;

public class Condition implements Expressible {
	@NonNull
	private final Expressible property;
	@NonNull
	private Optional<? extends Expressible> value;
	@NonNull
	private Optional<? extends Operator> operator;

	public Condition(@NonNull final String property) {
		this(Value.property(property));
	}

	private Condition(@NonNull final Expressible property) {
		this.property = property;
		value = Optional.absent();
		operator = Optional.absent();
	}

	@NonNull
	public static Condition property(@NonNull final String property) {
		return new Condition(property);
	}

	@NonNull
	@Override
	public String toExpression() {
		final String valueStr = value.isPresent() ? ((value.get() instanceof Condition) ? value
				.get().toExpression() : "?") : "";
		return String.format("(%s%s%s)", property.toExpression(), operator.isPresent() ? operator
				.get().getSymbol() : "", valueStr);
	}

	@NonNull
	@Override
	public String toClause() {
		final String valueStr = value.isPresent() ? value.get().toExpression() : "";
		return String.format("(%s%s%s)", property.toExpression(), operator.isPresent() ? operator
				.get().getSymbol() : "", valueStr);
	}

	@NonNull
	@Override
	public List<String> getArguments() {
		List<String> args = new ArrayList<>();
		args.addAll(property.getArguments());
		if (value.isPresent()) args.addAll(value.get().getArguments());
		return args;
	}

	@NonNull
	public Condition equal(@NonNull final String argument) {
		return set(Operators.EQUALS, argument);
	}

	@NonNull
	public Condition greaterThan(@NonNull final String argument) {
		return set(Operators.GREATER_THAN, argument);
	}

	@NonNull
	public Condition lesserThan(@NonNull final String argument) {
		return set(Operators.LESSER_THAN, argument);
	}

	@NonNull
	public Condition notEqual(@NonNull final String argument) {
		return set(Operators.NOT_EQUALS, argument);
	}

	@NonNull
	public Condition like(@NonNull final String argument) {
		return set(Operators.LIKE, "%" + argument + "%");
	}

	@NonNull
	public Condition notLike(@NonNull final String argument) {
		return set(Operators.NOT_LIKE, "%" + argument + "%");
	}

	@NonNull
	public Condition isNull() {
		return set(Operators.IS_NULL, null);
	}

	@NonNull
	public Condition isNotNull() {
		return set(Operators.IS_NOT_NULL, null);
	}

	@NonNull
	public Condition and(@NonNull final Condition condition) {
		return combine(Operators.AND, condition);
	}

	@NonNull
	public Condition or(@NonNull final Condition condition) {
		return combine(Operators.OR, condition);
	}

	@NonNull
	private Condition combine(@NonNull final Operator op, @NonNull final Condition condition) {
		final Condition result = new Condition(this);
		result.operator = Optional.of(op);
		result.value = Optional.of(condition);
		return result;
	}

	@NonNull
	private Condition set(@NonNull final Operator op, @Nullable final String argument) {
		operator = Optional.of(op);
		value = Optional.fromNullable(argument == null ? null : Value.argument(argument));
		return this;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("property", property).add("operator", operator
				.orNull()).add("value", value.orNull()).toString();
	}

	private enum Operators implements Operator {
		EQUALS(" = "),
		GREATER_THAN(" > "),
		LESSER_THAN(" < "),
		NOT_EQUALS(" != "),
		LIKE(" LIKE "),
		NOT_LIKE(" NOT LIKE "),
		IS_NULL(" IS NULL "),
		IS_NOT_NULL(" IS NOT NULL "),
		AND(" AND "),
		OR(" OR ");

		@NonNull
		private final String symbol;

		Operators(@NonNull final String symbol) {
			this.symbol = symbol;
		}

		@NonNull
		@Override
		public String getSymbol() {
			return symbol;
		}
	}

	private static class Value implements Expressible {
		@NonNull
		private Optional<String> property;
		@NonNull
		private Optional<String> argument;

		private Value() {
			property = Optional.absent();
			argument = Optional.absent();
		}

		@NonNull
		public static Value property(@NonNull final String property) {
			final Value value = new Value();
			value.property = Optional.of(property);
			return value;
		}

		@NonNull
		public static Value argument(@NonNull final String argument) {
			final Value value = new Value();
			value.argument = Optional.of(argument);
			return value;
		}

		@NonNull
		@Override
		public String toExpression() {
			return property.or("");
		}

		@NonNull
		@Override
		public String toClause() {
			return property.or(argument).or("");
		}

		@NonNull
		@Override
		public List<String> getArguments() {
			List<String> args = new ArrayList<>();
			if (argument.isPresent()) args.add(argument.get());
			return args;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("property", property)
					.add("argument", argument.orNull())
					.toString();
		}
	}
}