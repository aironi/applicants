package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Locale;

/**
 * Summary page for Applicant that is shown after applying has been completed
 */
@CDIView(ApplicantSummary.VIEW)
public class ApplicantSummary extends CustomComponent implements View {
    public static final String VIEW = "ApplicantSummary";

    @Inject
    private Locale requestLocale;


    @PostConstruct
    public void init() {
        // TODO: Implement
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
