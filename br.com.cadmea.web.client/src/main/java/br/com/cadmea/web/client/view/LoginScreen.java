package br.com.cadmea.web.client.view;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@SpringUI(path = "/login")
@SuppressWarnings("serial")
public class LoginScreen extends UI {

  @Override
  protected void init(VaadinRequest request) {
    final CustomLayout layout = new CustomLayout();
    setContent(layout);

    final TextField username = new TextField();
    username.setWidth(100.0f, Unit.PERCENTAGE);
    layout.addComponent(username, "username");

    final PasswordField password = new PasswordField();
    password.setWidth(100.0f, Unit.PERCENTAGE);
    layout.addComponent(password, "password");

    Button button = new Button("Log in");
    button.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(ClickEvent event) {
        layout.addComponent(new Label("Thank you for clicking"));
      }
    });
    layout.addComponent(button);
  }

}
