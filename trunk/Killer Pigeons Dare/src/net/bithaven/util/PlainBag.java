package net.bithaven.util;

import java.lang.reflect.Array;
import java.util.Collection;

public class PlainBag<T> implements Collection<T> {
	private class Link {
		T o;
		Link next;
		
		Link (T o) {
			this.o = o;
		}
	}
	Link first = null;
	Link last = null;
	int size = 0;

	public boolean add(T o) {
		if (last == null) {
			last = first = new Link(o);
		} else {
			last = last.next = new Link(o);
		}
		size++;
		return true;
	}

	private void add(Link l) {
		if (last == null) {
			last = first = l;
		} else {
			last = last.next = l;
		}
		size++;
	}

	public boolean addAll(Collection<? extends T> collection) {
		if (collection.isEmpty()) {
			return false;
		}
		java.util.Iterator<? extends T> it = collection.iterator();
		Link l = new Link(it.next());
		add(l);
		while (it.hasNext()) {
			l.next = new Link(it.next());
			l = l.next;
		}
		last = l;
		return true;
	}
	
	public void clear() {
		first = last = null;
		size = 0;
	}
	
	public boolean contains(Object o) {
		Link l = first;
		while (l != null) {
			if (l.o.equals(o)) {
				return true;
			}
			l = l.next;
		}
		return false;
	}

	public boolean containsAll(Collection<?> collection) {
		for (Object o : collection) {
			if (!contains(o)) return false;
		}
		return true;
	}
	
	public boolean isEmpty() {
		return (size == 0);
	}
	
	public Iterator iterator() {
		Iterator out = new Iterator();
		out.at = first;
		out.remove = null;
		out.remove2 = null;
		return out;
	}
	
	public boolean remove(Object o) {
		Link l = first;
		Link prev = null;
		while (l != null) {
			if (l.o.equals(o)) {
				if (prev != null) {
					prev.next = l.next;
				} else {
					first = l.next;
				}
				size--;
				return true;
			}
			l = l.next;
		}
		return false;
	}

	public boolean removeAll(Collection<?> collection) {
		boolean out = false;
		for (Object o : collection) {
			if (remove(o)) out = true;
		}
		return out;
	}

	public boolean retainAll(Collection<?> collection) {
		boolean out = false;
		Link l = first;
		Link prev = null;
		while (l != null) {
			if (!collection.contains(l.o)) {
				if (prev != null) {
					prev.next = l.next;
				} else {
					first = l.next;
				}
				size--;
				out = true;
			}
			l = l.next;
		}
		return out;
	}

	public int size() {
		return size;
	}

	public Object[] toArray() {
		Object[] out = new Object[size];
		Link l = first;
		int i = 0;
		while (l != null) {
			out[i] = l.o;
			i++;
			l = l.next;
		}
		return out;
	}

	public <O> O[] toArray(O[] array) {
		if (array.length < size) {
			O[] out = (O[])Array.newInstance(array.getClass().getComponentType(), size);
			Link l = first;
			int i = 0;
			while (l != null) {
				if (array.getClass().isInstance(l.o)) {
					out[i] = (O)l.o;
				} else {
					throw new ArrayStoreException();
				}
				i++;
				l = l.next;
			}
			return out;
		} else {
			Link l = first;
			int i = 0;
			while (l != null) {
				if (array.getClass().isInstance(l.o)) {
					array[i] = (O)l.o;
				} else {
					throw new ArrayStoreException();
				}
				i++;
				l = l.next;
			}
			if (array.length > size) {
				array[i] = null;
			}
			return array;
		}
	}
	
	public class Iterator implements java.util.Iterator<T> {
		Link at;
		Link remove;
		Link remove2;

		public boolean hasNext() {
			return (at != null);
		}

		public T next() {
			T out = at.o;
			remove2 = remove;
			remove = at;
			at = at.next;
			return out;
		}

		public void remove() {
			remove2.next = at;
			remove = remove2;
		}
		
	}
}
