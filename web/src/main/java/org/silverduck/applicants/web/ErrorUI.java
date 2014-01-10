package org.silverduck.applicants.web;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * A simple error ui. Quite redundant.
 *
 * TODO: Throw away
 */
public class ErrorUI extends Window {

    public ErrorUI(Exception e) {
        setWidth(600, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        Throwable t = e;
        StringBuffer sb = new StringBuffer(1024);
        while (t != null) {
            if (t.getCause() == null) {
                sb.append(t.getClass().getName()).append("<br/>");
            }
            t = t.getCause();
        }
        VerticalLayout layout = new VerticalLayout(new Label("An error occurred. Message: "), new Label(sb.toString(), ContentMode.HTML));

        setContent(layout);
    }


}
