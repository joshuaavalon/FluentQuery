package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

class Operators {

	static Operator equal(@NonNull final String argument) {
		return new SingleArgumentOperator(DefaultOperators.EQUAL, argument);
	}

	static Operator greaterThan(@NonNull final String argument) {
		return new SingleArgumentOperator(DefaultOperators.GREATER_THAN, argument);
	}

	static Operator greaterThanOrEqual(@NonNull final String argument) {
		return new SingleArgumentOperator(DefaultOperators.GREATER_THAN_OR_EQUAL, argument);
	}

	static Operator lesserThan(@NonNull final String argument) {
		return new SingleArgumentOperator(DefaultOperators.LESSER_THAN, argument);
	}

	static Operator lesserThanOrEqual(@NonNull final String argument) {
		return new SingleArgumentOperator(DefaultOperators.LESSER_THAN_OR_EQUAL, argument);
	}

	static Operator notEqual(@NonNull final String argument) {
		return new SingleArgumentOperator(DefaultOperators.NOT_EQUAL, argument);
	}

	static Operator like(@NonNull final String argument) {
		return new SingleArgumentOperator(DefaultOperators.LIKE, argument);
	}

	static Operator notLike(@NonNull final String argument) {
		return new SingleArgumentOperator(DefaultOperators.NOT_LIKE, argument);
	}

	static Operator isNull() {
		return new NoArgumentOperator(DefaultOperators.IS_NULL);
	}

	static Operator isNotNull() {
		return new NoArgumentOperator(DefaultOperators.IS_NOT_NULL);
	}

	private static class SingleArgumentOperator extends Operator {
		protected SingleArgumentOperator(@NonNull final DefaultOperators operator,
										 @NonNull final String argument) {
			super(operator.getSymbol() + "?", argument);
		}
	}

	private static class NoArgumentOperator extends Operator {
		protected NoArgumentOperator(@NonNull final DefaultOperators operator) {
			super(operator.getSymbol());
		}
	}

	private enum DefaultOperators {
		EQUAL(" = "),
		GREATER_THAN(" > "),
		LESSER_THAN(" < "),
		GREATER_THAN_OR_EQUAL(" >= "),
		LESSER_THAN_OR_EQUAL(" <= "),
		NOT_EQUAL(" != "),
		LIKE(" LIKE "),
		NOT_LIKE(" NOT LIKE "),
		IS_NULL(" IS NULL "),
		IS_NOT_NULL(" IS NOT NULL ");
		//AND(" AND "),
		//OR(" OR "),
		//NOT(" NOT ");

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
