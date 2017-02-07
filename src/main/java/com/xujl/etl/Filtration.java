package com.xujl.etl;

import java.util.function.Predicate;

public interface Filtration<T> extends Predicate<T> {
	default boolean test(T in){
		return filter(in);
	}
	boolean filter(T in);
}
