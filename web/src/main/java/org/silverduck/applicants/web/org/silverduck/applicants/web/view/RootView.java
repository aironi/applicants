package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import org.silverduck.applicants.common.localization.AppResources;

import javax.annotation.PostConstruct;

/**
 * The root view of the application.
 */
@CDIView
public class RootView extends VerticalLayout implements View {

    @PostConstruct
    public void init() {
        setSizeFull();

        Button applyButton = new Button();
        applyButton.setIcon(new ThemeResource("form.png"));
        applyButton.setStyleName(BaseTheme.BUTTON_LINK);
        applyButton.setIconAlternateText(AppResources.getLocalizedString("label.applyForJob", getUI().getCurrent().getLocale()));

        applyButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // Redirect to ApplicantForm.
                Page.getCurrent().setUriFragment("!" + ApplicantForm.VIEW);
            }
        });

        Button adminLink = new Button();
        adminLink.setStyleName(BaseTheme.BUTTON_LINK);
        adminLink.setCaption(AppResources.getLocalizedString("label.adminView", getUI().getCurrent().getLocale()));
        adminLink.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Page.getCurrent().setUriFragment("!" + AdminView.VIEW);
            }
        });

        addComponent(applyButton);
        addComponent(adminLink);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
