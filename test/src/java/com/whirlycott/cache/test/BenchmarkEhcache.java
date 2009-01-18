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

/*
 * Created on May 16, 2004
 *
 */
package com.whirlycott.cache.test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author phil
 */
public class BenchmarkEhcache {
    
    private static Log log = LogFactory.getLog(BenchmarkEhcache.class);

    public static void main(String[] args) throws CacheException {
               
        log.debug("Setting up the cache...");
        CacheManager cacheManager = CacheManager.create();
        
        Cache cache = new Cache("test", Constants.STORE_COUNT, false, false, 3600, 3600);
        cacheManager.addCache(cache);
                
        //BEGIN PUT TEST
        long start = System.currentTimeMillis();
        //Do puts.
        for (int i = 0; i < Constants.STORE_COUNT; i++) {
            Element e = new Element(new Integer(i).toString(), "value" + i);
            cache.put(e);
        }     
        long end = System.currentTimeMillis();       
        log.debug("Total PUT time was: " + (end - start));
        //END PUT TEST
                
        //---------------------------------------------
        
        //BEGIN GET TEST
        long startGet = System.currentTimeMillis();
        
        for (int i = 0; i < Constants.RETRIEVE_COUNT; i++) {
        	for (int loop=0; loop < Constants.STORE_COUNT; loop++) {
        		Element e = cache.get(new Integer(loop).toString());
        		Object o = e.getValue();
        	}
        }   
        
        long endGet = System.currentTimeMillis();
        log.debug("Total GET time: " + (endGet - startGet));
        //END GET TEST

        //BEGIN MULTITHREAD GET
        long startMGet = System.currentTimeMillis();
        
        long endMGet = System.currentTimeMillis();
        //END MULTITHREAD GET
        
        
        //BEGIN MULTITHREAD GET/PUT
        long startMG = System.currentTimeMillis();
        
        long endMG = System.currentTimeMillis();
        //END MULTITHREAD GET/PUT
        
        log.debug("Shutting down the cache...");
        cacheManager.shutdown();
        log.debug("Done.");
        
    }
}
