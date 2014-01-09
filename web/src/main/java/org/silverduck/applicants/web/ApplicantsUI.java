package org.silverduck.applicants.web;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Locale;


@Theme("mytheme")
@SuppressWarnings("serial")
@CDIUI
public class ApplicantsUI extends UI {

    public static final String APPLICANT_FORM_VIEW = "ApplicantForm";

    private Navigator navigator;

    @Inject
    private CDIViewProvider viewProvider;

    /**
     * Used in other views for localization. Simplistic approach.
     */
    @Produces
    private Locale requestLocale;

    @Override
    protected void init(VaadinRequest request) {
        requestLocale = request.getLocale();
        initializeLayout(request);
    }

    private void initializeLayout(VaadinRequest request) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        navigator = new Navigator(this, horizontalLayout);
        navigator.addProvider(viewProvider);
        setContent(horizontalLayout);
    }

}
