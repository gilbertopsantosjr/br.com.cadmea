package br.com.cadmea.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * EJBUtil
 * @version 1.0
 */
public class EJBUtil {

    /**
     *
     * @param name
     * @param hostname
     * @param contextFactory e.g websphere "com.ibm.websphere.naming.WsnInitialContextFactory"
     * @return
     */
    private <T> T lookupService(final String name, final String hostname, final String contextFactory)
    {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
        props.put(javax.naming.Context.PROVIDER_URL, "iiop://" + hostname + ":2810");

        Object obj;
        try
        {
            InitialContext ctx = new InitialContext(props);
            obj = ctx.lookup(name);
            return (T) obj;
        }
        catch (NamingException e)
        {
            throw new RuntimeException("Cannot lookup following service: " + name);
        }
    }

}