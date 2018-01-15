package mh.sanwix.com.GenericAdapter;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * Created by m.hoseini on 1/15/2018.
 */

public class MH_Map<Key,Value> extends ArrayList<Pair<Key,Value>>
{
    public MH_Map(int initialCapacity)
    {
        super(initialCapacity);
    }

    public MH_Map()
    {
        super();
    }

    public MH_Map(@NonNull Collection<? extends Pair<Key, Value>> c)
    {
        super(c);
    }

    @Override
    public void trimToSize()
    {
        super.trimToSize();
    }

    @Override
    public void ensureCapacity(int minCapacity)
    {
        super.ensureCapacity(minCapacity);
    }

    @Override
    public int size()
    {
        return super.size();
    }

    @Override
    public boolean isEmpty()
    {
        return super.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return super.contains(o);
    }

    @Override
    public int indexOf(Object o)
    {
        return super.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return super.lastIndexOf(o);
    }

    @Override
    public Object clone()
    {
        return super.clone();
    }

    @Override
    public Object[] toArray()
    {
        return super.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return super.toArray(a);
    }

    @Override
    public Pair<Key, Value> get(int index)
    {
        return super.get(index);
    }

    @Override
    public Pair<Key, Value> set(int index, Pair<Key, Value> element)
    {
        return super.set(index, element);
    }

    @Override
    public boolean add(Pair<Key, Value> keyValuePair)
    {
        return super.add(keyValuePair);
    }

    @Override
    public void add(int index, Pair<Key, Value> element)
    {
        super.add(index, element);
    }

    @Override
    public Pair<Key, Value> remove(int index)
    {
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o)
    {
        return super.remove(o);
    }

    @Override
    public void clear()
    {
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends Pair<Key, Value>> c)
    {
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Pair<Key, Value>> c)
    {
        return super.addAll(index, c);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex)
    {
        super.removeRange(fromIndex, toIndex);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return super.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return super.retainAll(c);
    }

    @NonNull
    @Override
    public ListIterator<Pair<Key, Value>> listIterator(int index)
    {
        return super.listIterator(index);
    }

    @NonNull
    @Override
    public ListIterator<Pair<Key, Value>> listIterator()
    {
        return super.listIterator();
    }

    @NonNull
    @Override
    public Iterator<Pair<Key, Value>> iterator()
    {
        return super.iterator();
    }

    @Override
    public List<Pair<Key, Value>> subList(int fromIndex, int toIndex)
    {
        return super.subList(fromIndex, toIndex);
    }


    @Override
    public Spliterator<Pair<Key, Value>> spliterator()
    {
        return super.spliterator();
    }


    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return super.containsAll(c);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
    }

    @Override
    public Stream<Pair<Key, Value>> stream()
    {
        return null;
    }

    @Override
    public Stream<Pair<Key, Value>> parallelStream()
    {
        return null;
    }
}
