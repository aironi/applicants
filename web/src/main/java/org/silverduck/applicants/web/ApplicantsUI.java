package org.silverduck.applicants.web;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.repository.ApplicantsRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;


@Theme("mytheme")
@SuppressWarnings("serial")
@CDIUI
public class ApplicantsUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    @Produces
    private JPAContainer<Applicant> applicantsContainer;

    @Inject
    private ApplicantsRepository applicantsRepository;

    @Override
    protected void init(VaadinRequest request) {
        initializeLayout(request);
    }

    @PostConstruct
    public void initJPAContainer() {
        applicantsContainer = new JPAContainer<Applicant>(Applicant.class);
        applicantsContainer.setEntityProvider(applicantsRepository);
    }

    private void initializeLayout(VaadinRequest request) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        Navigator navigator = new Navigator(this, horizontalLayout);
        navigator.addProvider(viewProvider);
        setContent(horizontalLayout);
    }

}
