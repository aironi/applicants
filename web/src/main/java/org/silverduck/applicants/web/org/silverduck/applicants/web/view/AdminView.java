package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.web.ApplicantsUI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Simplistic admin view that basically shows data for applicants.
 * <p/>
 * TODO: Add selection support and show data in a form beside the table
 */
@CDIView(AdminView.VIEW)
// If we'd have a real-life application we could use this.
//@RolesAllowed({UserRoles.ROLE_ADMIN})
public class AdminView extends VerticalLayout implements View {
    public static final String VIEW = "AdminView";

    @Inject
    private JPAContainer<Applicant> applicantsContainer;

    @PostConstruct
    protected void init() {
        setSizeFull();
        setSpacing(true);

        Table applicantsTable = new Table(AppResources.getLocalizedString("label.applicants", getUI().getCurrent().getLocale()),
                applicantsContainer);
        applicantsTable.setVisibleColumns("id", "created", "updated", "firstName", "lastName", "gender", "reasons");
        applicantsTable.setImmediate(true);
/*
        applicantsDebug.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                applicantsContainer.getItem(event.getItemId());
            }
        });
*/
        Button backButton = new Button();
        backButton.setCaption(AppResources.getLocalizedString("label.backToStart", getUI().getCurrent().getLocale()));
        backButton.setStyleName(BaseTheme.BUTTON_LINK);
        backButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ApplicantsUI.navigateTo(RootView.VIEW); // To Root View
            }
        });
        addComponent(applicantsTable);
        addComponent(backButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
