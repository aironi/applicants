package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.web.ApplicantsUI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The root view of the application.
 */
@CDIView
public class RootView extends VerticalLayout implements View {

    public static final String VIEW = "";

    @Inject
    private ApplicantForm applicantForm;

    @Inject
    private JPAContainer<Applicant> applicantsContainer;

    @PostConstruct
    public void init() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        createCompanyInfoSection(); // Horizontal Layout in the top
        createApplyButton(); // Button with Image and Link
        createAdminLink(); // Button with Image and Link
    }

    private void createAdminLink() {
        Button adminLink = new Button();
        adminLink.setIcon(new ThemeResource("administrator.png"));
        adminLink.setCaption(AppResources.getLocalizedString("label.adminView", getUI().getCurrent().getLocale()));
        adminLink.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ApplicantsUI.navigateTo(AdminView.VIEW);
            }
        });
        addComponent(adminLink);
        adminLink.addStyleName(BaseTheme.BUTTON_LINK);
        setComponentAlignment(adminLink, Alignment.BOTTOM_CENTER);
    }

    private void createApplyButton() {
        Button applyButton = new Button();
        applyButton.setIcon(new ThemeResource("form.png"));
        applyButton.setCaption(AppResources.getLocalizedString("label.applyForJob", getUI().getCurrent().getLocale()));

        applyButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // Redirect to ApplicantForm.
                applicantForm.edit(new Applicant().resetFields());
                ApplicantsUI.navigateTo(ApplicantForm.VIEW);
            }
        });
        applyButton.addStyleName(BaseTheme.BUTTON_LINK);
        applyButton.addStyleName("orange-border-button");
        addComponent(applyButton);
    }

    private HorizontalLayout createCompanyInfoSection() {
        Image silverDuck = new Image();
        //silverDuck.addStyleName("orange-border-icon");
        silverDuck.setIcon(new ThemeResource("silverduck.png"));
        silverDuck.addStyleName(BaseTheme.BUTTON_LINK);
        silverDuck.addStyleName("orange-border-button");

        Label companyInfoLabel = new Label(AppResources.getLocalizedString("label.companyInfo", getUI().getCurrent().getLocale()), ContentMode.HTML);
        HorizontalLayout companyInfoLayout = new HorizontalLayout();

        companyInfoLayout.addComponent(silverDuck);
        companyInfoLayout.addComponent(companyInfoLabel);
        companyInfoLayout.setWidth(55, Unit.PERCENTAGE);
        companyInfoLayout.setMargin(true);
        companyInfoLayout.setExpandRatio(silverDuck, 1);
        companyInfoLayout.setExpandRatio(companyInfoLabel, 5);
        addComponent(companyInfoLayout);
        return companyInfoLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
