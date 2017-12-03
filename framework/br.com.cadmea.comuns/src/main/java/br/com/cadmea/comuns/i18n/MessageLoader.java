package br.com.cadmea.comuns.i18n;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;


/**
 * @author gilbertopsantosjr
 */
@Slf4j
public class MessageLoader {

    private Locale locale;
    private final Properties prop = new Properties();
    private static final MessageLoader singleton;

    static {
        singleton = new MessageLoader();
    }

    public MessageLoader with(final Locale locale) {
        if (getInstance().locale == null || !getInstance().locale.equals(locale)) {
            getInstance().prop.clear();
        }
        getInstance().locale = locale;
        return getInstance();
    }

    public String getProperty(final String key) {
        return getInstance().prop.getProperty(key);
    }

    public MessageLoader build() {
        if (getInstance().prop.isEmpty()) {
            log.info(" MessageLoader built ");
            final String validName = getInstance().locale == null || getInstance().locale.equals(Locale.UK) ? "" : "_" + getInstance().locale.getLanguage();
            try (InputStream input = MessageLoader.class.getClassLoader().getResourceAsStream("i18n/CadmeaMessages" + validName + ".properties")) {
                getInstance().prop.load(input);
            } catch (final IOException e) {
                log.error("error when loading CadmeaMessages " + validName + " properties ");
            }
        }
        return getInstance();
    }

    private MessageLoader() {
    }


    public static MessageLoader getInstance() {
        return singleton;
    }
}
