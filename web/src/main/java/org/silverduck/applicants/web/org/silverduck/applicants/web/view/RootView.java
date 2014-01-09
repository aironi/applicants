package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.BaseTheme;
import org.silverduck.applicants.common.localization.AppResources;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Locale;

/**
 * Created by Iiro on 9.1.2014.
 */
@CDIView
public class RootView extends HorizontalLayout implements View {

    @Inject
    private Locale requestLocale;

    @PostConstruct
    public void init() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Button applyButton = new Button();
        applyButton.setIcon(new ThemeResource("form.png"));
        applyButton.setStyleName(BaseTheme.BUTTON_LINK);
        applyButton.setCaption(AppResources.getLocalizedString("label.applyForJob", requestLocale));

        applyButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // Redirect to ApplicantForm.
                Page.getCurrent().setUriFragment("!" + ApplicantForm.VIEW);
            }
        });

        Button adminLink = new Button();
        adminLink.setStyleName(BaseTheme.BUTTON_LINK);
        adminLink.setCaption(AppResources.getLocalizedString("label.adminView", requestLocale));


        addComponent(applyButton);
        addComponent(adminLink);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
