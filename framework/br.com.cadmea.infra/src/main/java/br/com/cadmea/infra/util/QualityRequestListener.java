package br.com.cadmea.infra.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class QualityRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(final ServletRequestEvent servletRequestEvent) {
        final ServletRequest servletRequest = servletRequestEvent.getServletRequest();
    }

    @Override
    public void requestInitialized(final ServletRequestEvent servletRequestEvent) {
        final ServletRequest servletRequest = servletRequestEvent.getServletRequest();
    }
}
