package com.xujl.etl;

import java.util.function.Function;

public interface Mapping<T, R> extends Function<T, R> {
	default R apply(T t){
		return map(t);
	};
	R map(T in);

}
