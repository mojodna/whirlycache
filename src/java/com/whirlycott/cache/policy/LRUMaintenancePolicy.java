/*
Copyright 2004 		Philip Jacob <phil@whirlycott.com>
				  	Seth Fitzsimmons <seth@note.amherst.edu>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.whirlycott.cache.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.whirlycott.cache.CacheConfiguration;
import com.whirlycott.cache.CacheMaintenancePolicy;
import com.whirlycott.cache.Cacheable;
import com.whirlycott.cache.ManagedCache;
import com.whirlycott.cache.Messages;

/**
 * This policy removes cached items, biased towards least recently used (LRU)
 * Items.
 * 
 * @author Seth Fitzsimmons
 */
public class LRUMaintenancePolicy implements CacheMaintenancePolicy {

	private static final Logger log = Logger.getLogger(LRUMaintenancePolicy.class);

	protected ManagedCache managedCache = null;

	protected int maxSize;

	public void performMaintenance() {
		log.debug(Messages.getString("LRUMaintenancePolicy.performing_lru_maintenance")); //$NON-NLS-1$

		final Object[] args = { new Integer(maxSize), new Integer(managedCache.size()) };
		log.debug(Messages.getCompoundString("CacheMaintenancePolicy.report_items", args)); //$NON-NLS-1$

		// Sort the entries in the cache.
		final List entries = new ArrayList(managedCache.entrySet());
		final int currentSize = managedCache.size();
		if (maxSize < currentSize) {
			final Object[] args1 = { new Integer(currentSize - maxSize) };
			log.debug(Messages.getCompoundString("CacheMaintenancePolicy.clearing_approximately", args1)); //$NON-NLS-1$
			Collections.sort(entries, new UsedComparator());
			final List removeThese = entries.subList(0, currentSize - maxSize);
			for (final Iterator i = removeThese.iterator(); i.hasNext();) {
				final Map.Entry entry = (Entry) i.next();
				if (entry != null) {
					// final Item evictee = (Item) entry.getValue();
					// log.trace("Removing: " + entry.getKey() + " (" +
					// evictee.added + ", " + evictee.used + ", " +
					// evictee.count +
					// ")");
					final Object key = entry.getKey();
					final Object val = this.managedCache.remove(key);
					if (val != null && val instanceof Cacheable) {
						final Cacheable c = (Cacheable) key;
						c.onRemove(val);
						this.managedCache.remove(c);
					}
				}
			}
			log.debug(Messages.getString("LRUMaintenancePolicy.new_size") + managedCache.size()); //$NON-NLS-1$
		}

	}

	public void setCache(final ManagedCache _cache) {
		managedCache = _cache;
	}

	public void setConfiguration(final CacheConfiguration _configuration) {
		maxSize = _configuration.getMaxSize();
	}
}