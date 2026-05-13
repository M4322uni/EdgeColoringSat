package utils;

import java.util.Objects;

public class Pair<K> {
    private final K left;
    private final K right;

    public Pair(K left, K right) {
        this.left = left;
        this.right = right;
    }

    public K getLeft() {
        return left;
    }

    public K getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Pair<?> other = (Pair<?>) obj;
        return left == other.left && right == other.right;
    }
}
