package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Table{
	@NonNull
	private final String table;
	@NonNull
	private final List<JoinClause> joins;

	public Table(@NonNull final String table) {
		this.table = table;
		joins = new ArrayList<>();
	}

	public static Table from(@NonNull final String table) {
		return new Table(table);
	}

	@NonNull
	public Table innerJoin(@NonNull final String rightTable, @NonNull final Condition condition) {
		joins.add(new JoinClause(Join.INNER_JOIN, rightTable, condition));
		return this;
	}

	@NonNull
	public Table leftJoin(@NonNull final String rightTable, @NonNull final Condition condition) {
		joins.add(new JoinClause(Join.LEFT_JOIN, rightTable, condition));
		return this;
	}

	@NonNull
	public Table rightJoin(@NonNull final String rightTable, @NonNull final Condition condition) {
		joins.add(new JoinClause(Join.RIGHT_JOIN, rightTable, condition));
		return this;
	}

	@NonNull
	public Table fullJoin(@NonNull final String rightTable, @NonNull final Condition condition) {
		joins.add(new JoinClause(Join.FULL_JOIN, rightTable, condition));
		return this;
	}

	@NonNull
	String getFullExpression() {
		final StringBuilder stringBuilder = new StringBuilder(table);
		for (JoinClause clause : joins) {
			stringBuilder.append(clause.getFullExpression());
		}
		return stringBuilder.toString();
	}

	private enum Join {
		INNER_JOIN(" INNER JOIN "),
		LEFT_JOIN(" LEFT JOIN "),
		RIGHT_JOIN(" RIGHT JOIN "),
		FULL_JOIN(" FULL JOIN ");

		@NonNull
		private final String symbol;

		Join(@NonNull final String symbol) {
			this.symbol = symbol;
		}

		@NonNull
		public String getSymbol() {
			return symbol;
		}
	}

	private static class JoinClause {
		@NonNull
		private final Join join;
		@NonNull
		private final String rightTable;
		@NonNull
		private final Condition condition;

		private JoinClause(@NonNull final Join join, @NonNull final String rightTable, @NonNull Condition condition) {
			this.join = join;
			this.rightTable = rightTable;
			this.condition = condition;
		}

		@NonNull
		Join getJoin() {
			return join;
		}

		@NonNull
		String getRightTable() {
			return rightTable;
		}

		@NonNull
		Condition getCondition() {
			return condition;
		}

		@NonNull
		String getFullExpression() {
			return String.format("%s%s ON %s", join.getSymbol(), rightTable, condition.getFullExpression());
		}
	}
}
