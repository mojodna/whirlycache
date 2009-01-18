/*
 * Created on Aug 20, 2004 by pjacob
 *
 */
package com.whirlycott.cache.test;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;

import com.whirlycott.cache.ManagedCache;

/**
 * @author pjacob
 *
 */
public class EhcacheImpl implements ManagedCache {
	
	protected Cache cache = null;
	
	public EhcacheImpl() {
		CacheManager cacheManager = null;
		try {
			cacheManager = CacheManager.create();
		} catch (CacheException e) {
			e.printStackTrace();
		}
		cache = new Cache("test", Constants.STORE_COUNT, false, false, 3600, 3600);
		try {
			cacheManager.addCache(cache);
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (ObjectExistsException e1) {
			e1.printStackTrace();
		} catch (CacheException e1) {
			e1.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.whirlycott.cache.ManagedCache#setMostlyRead(boolean)
	 */
	public void setMostlyRead(boolean _mostlyRead) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.whirlycott.cache.ManagedCache#store(java.lang.Object, java.lang.Object)
	 */
	public void store(Object k, Object v) {
		Element e = new Element((Serializable)k, (Serializable)v);
		cache.put(e);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	public int size() {
		// TODO Auto-generated method stub
		return cache.getSize();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	public void clear() {
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	public Collection values() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	public Set entrySet() {
		return Collections.EMPTY_SET;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	public Set keySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(Object key) {
		Object retval = null;
		try {
			retval = cache.get((Serializable)key);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retval;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Object remove(Object key) {
		cache.remove((Serializable)key);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
		final Element e = new Element((Serializable)key, (Serializable)value);
		cache.put(e);
		return null;
	}
	
}
