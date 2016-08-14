package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

interface Clause {
	@NonNull
	String toClause();
}
