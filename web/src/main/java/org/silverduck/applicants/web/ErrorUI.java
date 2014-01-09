package org.silverduck.applicants.web;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * Created by Iiro on 5.1.2014.
 */
public class ErrorUI extends Window {

    public ErrorUI(Exception e) {
        setWidth(400, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        HorizontalLayout layout = new HorizontalLayout(new Label("An error occurred. Message: "), new Label(e.getMessage()));

        setContent(layout);
    }


}
