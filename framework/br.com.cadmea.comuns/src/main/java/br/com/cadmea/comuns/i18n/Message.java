/**
 *
 */
package br.com.cadmea.comuns.exceptions;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * @author gilbertopsantosjr
 */
@Slf4j
public class Message {

    public static final Message NOT_FOUND = new Message(Locale.UK, "not.found");
    public static final Message FOUND = new Message(Locale.UK, "found");

    private Locale locale;
    private String text;
    private final String key;
    private final Properties prop = new Properties();

    public Message(final String key) {
        this.key = key;
        build();
    }

    public Message(final Locale locale, final String key) {
        this.locale = locale;
        this.key = key;
        build();
    }

    public Message(final String locale, final String key) {
        this.locale = new Locale(locale);
        this.key = key;
        build();
    }

    public String getText() {
        return prop.getProperty(key);
    }

    private void build() {
        final String validName = locale == null || locale.equals(Locale.UK) ? "" : "_" + locale.getLanguage();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("i18n/CadmeaMessages" + validName + ".properties")) {
            prop.load(input);
        } catch (final IOException e) {
            log.error("error when loading CadmeaMessages " + validName + " properties ");
        }
    }
}
