package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

import java.util.List;

public class Column {
	@NonNull
	private final List<String> columns;

	Column(@NonNull final List<String> columns) {
		this.columns = columns;
	}

	@NonNull
	public Query from(@NonNull final String table) {
		return new Query(columns, new Table(table));
	}

	@NonNull
	public Query from(@NonNull final Table table) {
		return new Query(columns, table);
	}
}
