package at.ac.uibk.sesdp.sheet02.composition;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// Fixed version: composition instead of inheritance (decorator pattern).
// The set HAS an inner set and forwards every call to it. Only add and addAll are counted.
public class InstrumentedSet<E> implements Set<E> {

    private final Set<E> inner;
    private int addCount = 0;

    public InstrumentedSet(Set<E> inner) {
        this.inner = inner;
    }

    public int getAddCount() {
        return addCount;
    }

    // --- counted methods ---

    @Override
    public boolean add(E e) {
        addCount++;
        return inner.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return inner.addAll(c);
    }

    // --- everything else is simply forwarded to the inner set ---

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return inner.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return inner.iterator();
    }

    @Override
    public Object[] toArray() {
        return inner.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return inner.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return inner.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return inner.containsAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return inner.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return inner.removeAll(c);
    }

    @Override
    public void clear() {
        inner.clear();
    }

    @Override
    public boolean equals(Object o) {
        return inner.equals(o);
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }

    @Override
    public String toString() {
        return inner.toString();
    }
}
