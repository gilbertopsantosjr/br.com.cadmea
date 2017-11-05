/**
 *
 */
package br.com.cadmea.comuns.util;

import br.com.cadmea.comuns.validator.EmailValidator;

/**
 * @author gilbertosantos
 */
public class ValidatorUtil {


    /**
     * @param email
     * @return
     */
    public static boolean isValidEmail(final String email) {
        final EmailValidator validator = new EmailValidator();
        return validator.validate(email);
    }

    /**
     * @param value
     * @return
     */
    public static boolean isNotBlank(final String value) {
        return value != null && !value.isEmpty();
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(final Object a, final Object b) {
        return a == null && b == null || (a != null && a.equals(b));
    }

    /**
     * @param dataSourceReference
     * @return
     */
    public static String toString(final Object dataSourceReference) {
        return dataSourceReference == null ? "" : dataSourceReference.toString();
    }

    /**
     * @param closeables
     */
    public static void close(final AutoCloseable... closeables) {

        for (final AutoCloseable c : closeables) {
            try {
                if (c == null) {
                    // At info level because this shouldn't happen too often and could be of interest to
                    // a dev.
                    //log.info("Not closing closeable as it's null");
                } else {
                    c.close();
                }
            } catch (final Exception e) {
                //log.error("Failed to close object [{}]", c, e);
            }
        }
    }


    /**
     * @param str
     * @return
     */
    public static boolean isValid(final String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * @param obj
     * @return
     */
    public static boolean isValid(final Object obj) {
        return obj != null;
    }

}
