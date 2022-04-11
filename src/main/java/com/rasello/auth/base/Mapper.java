package com.rasello.auth.base;

public interface Mapper <S,T> {
    T mapToTarget(S source);
    S mapToSource(T target);
}
