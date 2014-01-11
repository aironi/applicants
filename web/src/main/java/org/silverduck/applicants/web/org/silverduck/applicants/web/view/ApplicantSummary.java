package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.web.ApplicantsUI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Summary page for Applicant that is shown after applying has been completed
 */
@CDIView(ApplicantSummary.VIEW)
public class ApplicantSummary extends VerticalLayout implements View {
    public static final String VIEW = "ApplicantSummary";

    @Inject
    private ApplicantForm applicantForm;

    @PostConstruct
    protected void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setSizeFull();

        Image thumbsUpImage = new Image();
        thumbsUpImage.setIcon(new ThemeResource("thumbsup.png"));
        addComponent(thumbsUpImage);

        Label thankYouLabel = new Label(AppResources.getLocalizedString("applicantSummary.thanks", getUI().getCurrent().getLocale()));

        Button backButton = new Button();
        backButton.setCaption(AppResources.getLocalizedString("label.backToStart", getUI().getCurrent().getLocale()));
        backButton.setStyleName(BaseTheme.BUTTON_LINK);
        backButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ApplicantsUI.navigateTo(RootView.VIEW);
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(thumbsUpImage);
        horizontalLayout.addComponent(thankYouLabel);
        addComponent(horizontalLayout);
        addComponent(applicantForm);
        addComponent(backButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}

