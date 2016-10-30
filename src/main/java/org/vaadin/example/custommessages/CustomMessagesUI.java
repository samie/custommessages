package org.vaadin.example.custommessages;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.servlet.ServletException;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class CustomMessagesUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));
        });

        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "CustomeMessagesUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CustomMessagesUI.class, productionMode = false)
    public static class CustomeMessagesUIServlet extends VaadinServlet {

        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().addSessionInitListener(sessionInitEvent -> {

                sessionInitEvent.getService().setSystemMessagesProvider(systemMessagesInfo -> {
                    CustomizedSystemMessages msgs = new CustomizedSystemMessages();
                    msgs.setCommunicationErrorMessage("Yhteys palvelimeen poikki");
                    msgs.setSessionExpiredCaption("Istuntosi vanhentui");
                    return msgs;
                });
            });

        }

    }
}
