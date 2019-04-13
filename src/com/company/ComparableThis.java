package com.company;


import java.util.Objects;

public class ComparableThis implements Comparable<ComparableThis> {

    private Long value;

    @Override
    public int compareTo(ComparableThis this, ComparableThis o) { // since Java 8 "this" as first parameter is allowed
        return value.compareTo(o.value);
    }

    @Override
    public boolean equals(ComparableThis this, Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComparableThis that = (ComparableThis) o;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
