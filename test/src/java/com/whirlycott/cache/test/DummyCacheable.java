/*
 * Created on Oct 15, 2004 by pjacob
 *
 */
package com.whirlycott.cache.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.whirlycott.cache.Cacheable;

/**
 * Dummy test class.
 * @author pjacob
 *
 */
public class DummyCacheable implements Cacheable {
    
    private static final Log log = LogFactory.getLog(DummyCacheable.class);

    /**
     * 
     */
    public DummyCacheable() {
        super();
    }

    /* (non-Javadoc)
     * @see com.whirlycott.cache.Cacheable#onRetrieve(java.lang.Object)
     */
    public void onRetrieve(Object _value) {
        log.debug("Executing onRetrieve()");
    }

    /* (non-Javadoc)
     * @see com.whirlycott.cache.Cacheable#onStore(java.lang.Object)
     */
    public void onStore(Object _value) {
        log.debug("Executing onStore()");
    }

    /* (non-Javadoc)
     * @see com.whirlycott.cache.Cacheable#onRemove(java.lang.Object)
     */
    public void onRemove(Object _value) {
        log.debug("Executing onRemove()");
    }

}
