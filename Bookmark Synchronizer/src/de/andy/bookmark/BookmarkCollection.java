package de.andy.bookmark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class BookmarkCollection implements Collection {
	
	private ArrayList data = new ArrayList(500);
	private String name;
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}

	public int size() {
		return data.size();
	}

	public boolean isEmpty() {
		return data.isEmpty();
	}

	public boolean contains(Object o) {
		return data.contains(o);
	}

	public Iterator iterator() {
		return data.iterator();
	}

	public Object[] toArray() {
		return data.toArray();
	}

	public Object[] toArray(Object[] a) {
		return data.toArray(a);
	}
	
	//my
	public boolean add(Bookmark b) {
		return data.add((Object)b);
	}
	
	public boolean add(Object o) {
		if (!(o instanceof Bookmark)) return false;
		data.add(o);
		return true;
	}

	public boolean remove(Object o) {
		return data.remove(o);
	}

	public boolean containsAll(Collection c) {
		return data.containsAll(c);
	}

	public boolean addAll(Collection c) {
		return data.addAll(c);
	}

	public boolean removeAll(Collection c) {
		return data.removeAll(c);
	}

	public boolean retainAll(Collection c) {
		return data.retainAll(c);
	}

	public void clear() {
		data.clear();
	}
	
	//testing to console
	public void printAll() {
		Iterator e = data.iterator();
		while (e.hasNext()) {
			System.out.println(e.next());
		}
	}
	
	
	
	//testing
	public void sort() {
		Collections.sort(data);
	}
	
	//getDupps
	//removeDupps
	//sort

}
