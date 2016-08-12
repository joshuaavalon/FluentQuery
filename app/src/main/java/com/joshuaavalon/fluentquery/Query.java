package com.joshuaavalon.fluentquery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.CheckResult;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query {
	@NonNull
	private List<String> columns;
	@NonNull
	private Table table;
	@NonNull
	private Optional<Condition> condition;
	@NonNull
	private List<String> groupBy;
	@NonNull
	private Optional<Condition> having;
	@NonNull
	private List<Order> sortOrder;
	@IntRange(from = 0)
	private int limit;
	@IntRange(from = 0)
	private int offset;
	private boolean distinct;

	private Query() {
		columns = new ArrayList<>();
		table = Table.from("");
		condition = Optional.absent();
		groupBy = new ArrayList<>();
		having = Optional.absent();
		sortOrder = new ArrayList<>();
		limit = 0;
		offset = 0;
	}

	@NonNull
	public static Query select(@NonNull final String... columns) {
		final Query query = new Query();
		query.columns = Arrays.asList(columns);
		return query;
	}

	@NonNull
	public static Query select(@NonNull final List<String> columns) {
		final Query query = new Query();
		query.columns = columns;
		return query;
	}

	@NonNull
	public Query from(@NonNull final String table) {
		this.table = new Table(table);
		return this;
	}

	@NonNull
	public Query from(@NonNull final Table table) {
		this.table = table;
		return this;
	}

	@NonNull
	public Query where(@NonNull final Condition conditions) {
		this.condition = Optional.of(conditions);
		return this;
	}

	@NonNull
	public Query having(@NonNull final Condition conditions) {
		this.condition = Optional.of(conditions);
		return this;
	}

	@NonNull
	public Query groupBy(@NonNull final String... columns) {
		this.groupBy = Arrays.asList(columns);
		return this;
	}

	@NonNull
	public Query groupBy(@NonNull final List<String> columns) {
		this.groupBy = columns;
		return this;
	}

	@NonNull
	public Query orderBy(@NonNull final Order... columns) {
		this.sortOrder = Arrays.asList(columns);
		return this;
	}

	@NonNull
	public Query orderBy(@NonNull final List<Order> columns) {
		this.sortOrder = columns;
		return this;
	}

	@NonNull
	public Query limit(@IntRange(from = 1) int limit) {
		this.limit = limit;
		return this;
	}

	@NonNull
	public Query offset(@IntRange(from = 1) int offset) {
		this.offset = offset;
		return this;
	}

	@NonNull
	public Query distinct() {
		distinct = true;
		return this;
	}

	@NonNull
	@CheckResult
	public Cursor commit(@NonNull final SQLiteOpenHelper helper) {
		return commit(helper.getReadableDatabase());
	}

	@NonNull
	@CheckResult
	public Cursor commit(@NonNull final SQLiteDatabase db) {
		final String[] columns = this.columns.size() != 0 ?
				Iterables.toArray(this.columns, String.class) : new String[]{"*"};
		final String selection = condition.isPresent() ? condition.get().toExpression() : null;
		final String[] selectionArgs = condition.isPresent() ?
				Iterables.toArray(condition.get().getArguments(), String.class) : null;
		final Iterable<String> orderByClauses = Iterables.transform(sortOrder, new Function<Order,
				String>() {
			@Override
			public String apply(Order input) {
				return input.toClause();
			}
		});
		final String orderBy = Joiner.on(", ").join(orderByClauses);
		final String limitClause = limit == 0 ? null : (offset == 0 ? String.valueOf(limit) :
				limit + "," + offset);
		return db.query(distinct,
				table.toClause(),
				columns,
				selection,
				selectionArgs,
				Joiner.on(", ").join(groupBy),
				having.isPresent() ? having.get().toClause() : null,
				orderBy,
				limitClause);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("columns", columns.toString())
				.add("table", table.toClause())
				.add("condition", condition.toString())
				.add("groupBy", groupBy.toString())
				.add("having", having.toString())
				.add("sortOrder", sortOrder.toString())
				.add("limit", limit)
				.add("offset", offset)
				.add("distinct", distinct).toString();
	}
}
