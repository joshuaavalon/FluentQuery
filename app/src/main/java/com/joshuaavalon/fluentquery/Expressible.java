package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

import java.util.List;

interface Expressible extends Clause {
	@NonNull
	String toExpression();

	@NonNull
	List<String> getArguments();
}
