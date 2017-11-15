package br.com.cadmea.comuns.validator;

import br.com.cadmea.comuns.exceptions.SystemException;
import br.com.cadmea.comuns.util.ValidatorUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class Validator {

    // only word characters: [a-zA-Z_0-9] and file extension
    private static final String FILENAME_PATTERN = "\\w+|\\w+\\.?\\w+";

    //only word characters: [a-zA-Z_0-9] and start with letter
    private static final String USERNAME_PATTERN = "^[a-zA-Z]+\\w+";

    //only word characters: [a-zA-Z_0-9]
    private static final String COLUMN_NAME_PATTERN = "\\w+";

    //only word characters: [a-zA-Z_0-9-]
    private static final String DB_ID_PATTERN = "[a-zA-Z_0-9-]+";

    private final List<SystemException> exceptions = new ArrayList<>();

    private Locale locale;

    private Validator() {
        locale = Locale.UK;
    }

    /**
     * @param locale
     */
    public void addLocale(final String locale) {
        getInstance().locale = new Locale(locale);
    }

    /**
     * Asserts that comparable A is less thant or equal to comparable b. Fails if either value is
     * null.
     */
    public static void assertLessThanOrEquals(final Comparable a, final Comparable b, final String message,
                                              final Object... params) {
        getInstance().addExceptionIfFalse(a != null && b != null && a.compareTo(b) <= 0, message, params);
    }

    /**
     * @param email
     */
    public static void assertEmailValid(final String email) {
        getInstance().addExceptionIfFalse(ValidatorUtil.isValidEmail(email), "Email doesn't look good. ", new String[]{email});
    }

    /**
     * @param a
     * @param b
     * @param message
     * @param params
     */
    public static void assertNotIn(final Object a, final Collection<?> b, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(b != null && !b.contains(a), message, params);
    }

    /**
     * @param a
     * @param b
     * @param message
     * @param params
     */
    public static void assertIn(final Object a, final Collection<?> b, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(b != null && b.contains(a), message, params);
    }

    /**
     * @param array
     * @param minSize
     * @param message
     * @param params
     */
    public static void assertMinSize(final Object[] array, final int minSize, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(array != null && array.length >= minSize, message, params);
    }

    /**
     * @param toCheck
     * @param minSize
     * @param message
     * @param params
     */
    public static void assertMinSize(final Collection toCheck, final int minSize, final String message,
                                     final Object... params) {
        getInstance().addExceptionIfFalse(toCheck != null && toCheck.size() >= minSize, message, params);
    }

    /**
     * @param toCheck
     * @param maxSize
     * @param message
     * @param params
     */
    public static void assertMaxSize(final Collection toCheck, final int maxSize, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(toCheck != null && toCheck.size() <= maxSize, message, params);
    }

    /**
     * @param a
     * @param b
     * @param message
     * @param params
     */
    public static void assertEquals(final Object a, final Object b, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(ValidatorUtil.equals(a, b), message, params);
    }

    /**
     * @param a
     * @param b
     * @param message
     * @param params
     */
    public static void assertNotEquals(final Object a, final Object b, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(!ValidatorUtil.equals(a, b), message, params);
    }

    /**
     * @param toCheck
     * @param message
     * @param params
     */
    public static void assertTrue(final Boolean toCheck, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(toCheck != null && toCheck, message, params);
    }

    /**
     * @param value
     * @param message
     * @param params
     */
    public static void assertNotNull(final Object value, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(value != null, message, params);
    }

    /**
     * @param value
     * @param message
     * @param params
     */
    public static void assertNull(final Object value, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(value == null, message, params);
    }

    /**
     * @param value
     * @param message
     * @param params
     */
    public static void assertNotBlank(final String value, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(ValidatorUtil.isNotBlank(value), message, params);
    }


    /**
     * @param message
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T throwException(final String message, final Object... params) {
        getInstance().addExceptionIfFalse(false, message, params);
        return null;
    }

    /**
     * @param toCheck
     * @param message
     * @param params
     */
    public static void assertFalse(final Boolean toCheck, final String message, final Object... params) {
        getInstance().addExceptionIfFalse(toCheck != null && !toCheck, message, params);
    }

    /**
     * Only word characters allowed i.e.[a-zA-Z_0-9]. The filename extension is optional but must when
     * provided it can't be empty <code>
     * file.txt
     * file
     * <code>
     */
    public static void assertValidFilename(final String toCheck, final String message, final Object... params) {
        assertNotBlank(toCheck, message, params);
        getInstance().addExceptionIfFalse(toCheck.matches(FILENAME_PATTERN), message, params);
    }

    /**
     * Only letters, numbers and _ allowed. It should start with letter.
     *
     * @param userName
     * @param message
     * @param params
     */
    public static void assertValidUserName(final String userName, final String message, final Object... params) {

        assertNotBlank(userName, message, params);
        getInstance().addExceptionIfFalse(userName.matches(USERNAME_PATTERN), message, params);
    }

    /**
     * DB column name should contain letters, numbers and _
     *
     * @param colName
     * @param message
     * @param params
     */
    public static void assertValidDBColumnName(final String colName, final String message, final Object... params) {

        assertNotBlank(colName, message, params);
        getInstance().addExceptionIfFalse(colName.matches(COLUMN_NAME_PATTERN), message, params);
    }

    /**
     * Whitelist: only two values allowed as a part of this validation.
     *
     * @param sortOrder
     * @param message
     * @param params
     */
    public static void assertValidDBSortOrder(final String sortOrder, final String message, final Object... params) {

        assertNotBlank(sortOrder, message, params);
        getInstance().addExceptionIfFalse(
                ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)), message, params);
    }

    /**
     * We are just checking any invalid characters are not added as a part of Id.
     *
     * @param id
     * @param message
     * @param params
     */
    public static void assertValidDBId(final String id, final String message, final Object... params) {

        assertNotBlank(id, message, params);
        getInstance().addExceptionIfFalse(id.matches(DB_ID_PATTERN), message, params);
    }

    /**
     * @param asserted
     * @param message
     * @param params
     */
    private void addExceptionIfFalse(final boolean asserted, final String message, final Object[] params) {
        if (!asserted) {
            final SystemException e = new SystemException(String.format(message, params), getInstance().locale);
            exceptions.add(e);
        }
    }

    /**
     * @param condictional
     * @param message
     */
    public static void throwIfFail(final Boolean condictional, final String message) {
        if (condictional) {
            throw new SystemException(String.format(message), getInstance().locale);
        }
    }

    public static void failIfAnyExceptionsFound() {
        if (getInstance().exceptions.size() > 0) {
            throw new SystemException(getInstance().exceptions);
        }
        getInstance().exceptions.clear();
    }

    public static Validator getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        public static Validator instance = new Validator();
    }
}
