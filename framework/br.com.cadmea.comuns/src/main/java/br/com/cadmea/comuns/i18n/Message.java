/**
 *
 */
package br.com.cadmea.comuns.i18n;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Objects;

/**
 * @author gilbertopsantosjr
 */
@Slf4j
public class Message {

    private final String text;

    public Message(final String key) {
        text = MessageLoader.getInstance().build().getProperty(key);
    }

    public Message(final Locale locale, final String key) {
        text = MessageLoader.getInstance().with(locale).build().getProperty(key);
    }

    public Message(final String locale, final String key) {
        text = MessageLoader.getInstance().with(new Locale(locale)).build().getProperty(key);
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Message message = (Message) o;
        return text == message.text &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

}
