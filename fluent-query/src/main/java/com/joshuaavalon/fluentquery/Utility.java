package com.joshuaavalon.fluentquery;

import android.support.annotation.NonNull;

class Utility {
	@NonNull
	static String wildcardString(@NonNull final String str) {
		return "%" + str + "%";
	}

	@NonNull
	static String bracketString(@NonNull final String str) {
		return "(" + str + ")";
	}
}
