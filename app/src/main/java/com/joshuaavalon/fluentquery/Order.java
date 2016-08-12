package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

import com.google.common.base.MoreObjects;

public class Order implements Clause {
	@NonNull
	private final String column;
	@NonNull
	private final Ordering ordering;

	private Order(@NonNull final String column, @NonNull final Ordering ordering) {
		this.column = column;
		this.ordering = ordering;
	}

	@NonNull
	public static Order asc(@NonNull final String column) {
		return new Order(column, Ordering.ASC);
	}

	@NonNull
	public static Order desc(@NonNull final String column) {
		return new Order(column, Ordering.DESC);
	}

	@NonNull
	@Override
	public String toClause() {
		return String.format("%s %s", column, ordering.name());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("columns", column)
				.add("ordering", ordering.name())
				.toString();
	}

	private enum Ordering {
		ASC, DESC
	}
}
