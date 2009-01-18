/*
 * Created on Dec 19, 2004 by pjacob
 *
 */
package com.whirlycott.cache.hibernate;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.whirlycott.cache.Messages;

import net.sf.hibernate.cache.Cache;
import net.sf.hibernate.cache.CacheException;
import net.sf.hibernate.cache.CacheProvider;
import net.sf.hibernate.cache.Timestamper;

/**
 * @author pjacob
 *  
 */
public class WhirlycacheProvider implements CacheProvider {

    /**
     * Logger.
     */
    private static final Log log = LogFactory.getLog(WhirlycacheProvider.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.CacheProvider#buildCache(java.lang.String,
     *      java.util.Properties)
     */
    public Cache buildCache(final String _name, final Properties _props) throws CacheException {
        log.debug(Messages.getString("WhirlycacheProvider.building_cache") + _name); //$NON-NLS-1$
        return new WhirlycachePlugin(_name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.hibernate.cache.CacheProvider#nextTimestamp()
     */
    public long nextTimestamp() {
        return Timestamper.next();
    }

}

