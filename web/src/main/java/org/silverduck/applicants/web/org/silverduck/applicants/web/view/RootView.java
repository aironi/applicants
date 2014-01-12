package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;
import org.apache.commons.lang3.StringUtils;
import org.silverduck.applicants.common.localization.AppResources;
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

    private Long applicantId;

    private Button applyButton;

    private Button applyLink;


    @PostConstruct
    public void init() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponent(horizontalLayout);

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        contentLayout.setSpacing(true);

        createCompanyInfoSection(contentLayout);
        createApplyButton(contentLayout); // Button with Image and Link
        createAdminLink(contentLayout); // Button with Image and Link
        horizontalLayout.addComponent(contentLayout);
    }

    private void createAdminLink(VerticalLayout layout) {
        Button adminLink = new Button();
        adminLink.setIcon(new ThemeResource("administrator.png"));
        adminLink.setCaption(AppResources.getLocalizedString("label.adminView", getUI().getCurrent().getLocale()));
        adminLink.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ApplicantsUI.navigateTo(AdminView.VIEW);
            }
        });
        adminLink.addStyleName(BaseTheme.BUTTON_LINK);
        layout.addComponent(adminLink);
        layout.setComponentAlignment(adminLink, Alignment.BOTTOM_CENTER);
        layout.setExpandRatio(adminLink, 2);
    }


    private void createApplyButton(VerticalLayout layout) {
        applyButton = new Button();
        applyButton.setIcon(new ThemeResource("form.png"));
        applyButton.addStyleName(BaseTheme.BUTTON_LINK);
        applyButton.addStyleName("orange-border-button");
        layout.addComponent(applyButton);
        layout.setExpandRatio(applyButton, 5);

        applyLink = new Button();

        // Had to insert a separate link since I didn't manage to get the Label behave nicely with Button in short time.
        applyLink.addStyleName(BaseTheme.BUTTON_LINK);
        applyLink.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ApplicantsUI.navigateTo(ApplicantForm.VIEW, applicantId);
            }
        });

        layout.addComponent(applyLink);
    }

    private void createCompanyInfoSection(VerticalLayout layout) {
        Image silverDuck = new Image();
        silverDuck.setIcon(new ThemeResource("silverduck.png"));

        Label companyInfoLabel = new Label(AppResources.getLocalizedString("label.companyInfo", getUI().getCurrent().getLocale()), ContentMode.HTML);
        HorizontalLayout companyInfoLayout = new HorizontalLayout();

        companyInfoLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        companyInfoLayout.addComponent(silverDuck);
        companyInfoLayout.addComponent(companyInfoLabel);

        companyInfoLayout.setExpandRatio(silverDuck, 1);
        companyInfoLayout.setExpandRatio(companyInfoLabel, 4);

        companyInfoLabel.setWidth(350, Unit.PIXELS);
        companyInfoLayout.setHeight(200, Unit.PIXELS);
        companyInfoLayout.setWidth(550, Unit.PIXELS);
        layout.addComponent(companyInfoLayout);
        layout.setExpandRatio(companyInfoLayout, 3);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!StringUtils.isEmpty(event.getParameters())) {
            applicantId = Long.parseLong(event.getParameters());
        }

        applyButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ApplicantsUI.navigateTo(ApplicantForm.VIEW, applicantId);
            }
        });

        if (applicantId == null) {
            applyLink.setCaption(AppResources.getLocalizedString("label.applyForJob", getUI().getCurrent().getLocale()));
        } else {
            applyLink.setCaption(AppResources.getLocalizedString("label.modifyApplication", getUI().getCurrent().getLocale()));
        }

    }
}
