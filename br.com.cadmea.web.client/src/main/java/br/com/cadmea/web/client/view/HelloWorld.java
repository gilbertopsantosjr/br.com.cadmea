package br.com.cadmea.web.client.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@SpringUI(path = "/helloWorld")
@Theme("SpringBootHello")
public class HelloWorld extends UI {

  /**
   * Init is invoked on application load (when a user accesses the application
   * for the first time).
   */
  @Override
  public void init(VaadinRequest request) {
    Label lbl = new Label("Hello Vaadin");
    setContent(lbl);
  }

}
