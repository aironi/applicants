package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Runo;
import org.apache.commons.lang3.StringUtils;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.repository.ApplicantsRepository;
import org.silverduck.applicants.web.ApplicantsUI;
import org.silverduck.applicants.web.org.silverduck.applicants.web.component.ApplicantComponent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 * Summary page for Applicant that is shown after applying has been completed
 */
@CDIView(ApplicantSummary.VIEW)
public class ApplicantSummary extends VerticalLayout implements View {
    public static final String VIEW = "ApplicantSummary";

    @EJB
    private ApplicantsRepository applicantsRepository;

    private ApplicantComponent applicantComponent;

    private Button backButton;

    @PostConstruct
    protected void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSizeFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(horizontalLayout);

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        horizontalLayout.addComponent(contentLayout);

        Image thumbsUpImage = new Image();
        thumbsUpImage.setIcon(new ThemeResource("thumbsup.png"));

        Label thankYouLabel = new Label(AppResources.getLocalizedString("applicantSummary.thanks", getUI().getCurrent().getLocale()));
        thankYouLabel.setStyleName(Runo.LABEL_H2);

        backButton = new Button();
        backButton.setCaption(AppResources.getLocalizedString("label.backToStart", getUI().getCurrent().getLocale()));
        backButton.setStyleName(BaseTheme.BUTTON_LINK);

        VerticalLayout rightSideLayout = new VerticalLayout();
        rightSideLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        rightSideLayout.addComponent(thankYouLabel);

        applicantComponent = new ApplicantComponent();
        rightSideLayout.addComponent(applicantComponent);

        HorizontalLayout thumbsAndDataLayout = new HorizontalLayout();
        thumbsAndDataLayout.addComponent(thumbsUpImage);
        thumbsAndDataLayout.addComponent(rightSideLayout);
        thumbsAndDataLayout.setExpandRatio(thumbsUpImage, 4);
        thumbsAndDataLayout.setExpandRatio(rightSideLayout, 6);

        contentLayout.addComponent(thumbsAndDataLayout);
        contentLayout.addComponent(backButton);

        contentLayout.setComponentAlignment(backButton, Alignment.BOTTOM_CENTER);
        contentLayout.setExpandRatio(thumbsAndDataLayout, 9);
        contentLayout.setExpandRatio(backButton, 1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if (!StringUtils.isEmpty(event.getParameters())) {
            String id = event.getParameters();
            final Applicant applicant = applicantsRepository.findApplicant(Long.parseLong(id));
            applicantComponent.view(applicant);

            backButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    ApplicantsUI.navigateTo(RootView.VIEW, applicant.getId());
                }
            });
        }
    }
}

