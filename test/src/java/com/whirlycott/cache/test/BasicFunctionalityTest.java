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
package com.whirlycott.cache.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;
import com.whirlycott.cache.CacheDecorator;
import com.whirlycott.cache.CacheManager;
import com.whirlycott.cache.ManagedCache;
import com.whirlycott.cache.CacheMaintenancePolicy;
import com.whirlycott.cache.CacheConfiguration;
import com.whirlycott.cache.policy.NullPolicy;
import com.whirlycott.cache.impl.ConcurrentHashMapImpl;

/**
 * @author <a href="mailto:peter.royal@pobox.com">peter royal </a>
 */
public class BasicFunctionalityTest extends TestCase {
    
    /**
     * Logger.
     */
    private static final Log log = LogFactory.getLog(BasicFunctionalityTest.class);
  
    private CacheDecorator _cache;

    protected void setUp() throws Exception {
        super.setUp();
        ManagedCache managedCache = new ConcurrentHashMapImpl();
        CacheMaintenancePolicy policy = new NullPolicy();
        CacheConfiguration configuration = new CacheConfiguration();

        configuration.setName("test");
        configuration.setTunerSleepTime(60);
        configuration.setMaxSize(100);

        _cache = new CacheDecorator(managedCache, configuration, new CacheMaintenancePolicy[] { policy });
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        _cache.clear();
        _cache.shutdown();
    }

    public void testPutGetRemove() throws Exception {
        Object key = new Object();
        Object value = new Object();

        assertEquals(0, _cache.size());

        _cache.store(key, value);

        assertEquals(1, _cache.size());

        assertEquals(value, _cache.retrieve(key));

        assertEquals(value, _cache.remove(key));

        assertEquals(0, _cache.size());
    }
    
    
    /**
     * Verifies that the functionality to get a list of cache names works.
     * @throws Exception
     */
    public void testGetCacheNames() throws Exception {
        final String[] names = CacheManager.getInstance().getCacheNames();
        assertNotNull(names);
        assertTrue(names.length > 0);
        boolean defaultCacheFound = false;
        for (int i = 0; i < names.length; i++) {
            final String cacheName = names[i];
            log.debug("Cache name: " + cacheName);
            if (cacheName.equals("default")) {
                defaultCacheFound = true;
            }
            assertTrue(defaultCacheFound);
        }
    }
}