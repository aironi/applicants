package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.domain.Gender;
import org.silverduck.applicants.repository.ApplicantsRepo;
import org.silverduck.applicants.web.ErrorUI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Locale;

/**
 * UI for the Applicant Form
 *
 * @author Iiro Hietala
 */
@CDIView(ApplicantForm.VIEW)
public class ApplicantForm extends CustomComponent implements View {

    public static final String VIEW = "ApplicantForm";

    private JPAContainer<Applicant> applicantsContainer;

    @Inject
    private ApplicantsRepo applicantsRepo;

    @Inject
    private Locale requestLocale;

    @PropertyId("firstName")
    private TextField firstNameField;

    @PropertyId("lastName")
    private TextField lastNameField;

    @PropertyId("gender")
    private ComboBox genderBox;

    @PropertyId("reasons")
    private TextArea reasons;

    Button submitButton;

    @PostConstruct
    protected void init() {
        applicantsContainer = new JPAContainer<Applicant>(Applicant.class);
        applicantsContainer.setEntityProvider(applicantsRepo);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        final BeanFieldGroup<Applicant> fieldGroup = new BeanFieldGroup(Applicant.class);
        final Applicant applicant = new Applicant().resetFields();
        fieldGroup.setItemDataSource(applicant);

        // Custom initialization for ComboBox to get data localized
        genderBox = new ComboBox(AppResources.getLocalizedString("label.gender", requestLocale));
        for (Gender gender : Gender.values()) {
            genderBox.addItem(gender);
            genderBox.setItemCaption(gender, AppResources.getLocalizedString(gender.getResourceKey(), requestLocale));
            genderBox.setImmediate(true);
        }

        // Instantiate rest of the fields and bind them to domain object
        fieldGroup.buildAndBindMemberFields(this);
        fieldGroup.setBuffered(true);

        // Set localized captions
        for (Object propertyId : fieldGroup.getBoundPropertyIds()) {
            Field<?> field = fieldGroup.getField(propertyId);
            field.setCaption(AppResources.getLocalizedString("label." + propertyId, requestLocale));
        }

        Button submitButton = new Button(AppResources.getLocalizedString("label.submit", requestLocale));
        submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    if (fieldGroup.isValid()) {
                        fieldGroup.commit();
                        applicantsContainer.addEntity(applicant);
                        Notification.show("Committed! Values: " + applicant.toHumanReadable());
                        Page.getCurrent().setUriFragment("!" + ApplicantSummary.VIEW); // To Summary View
                    }
                } catch (FieldGroup.CommitException e) {
                    getUI().addWindow(new ErrorUI(e));
                    e.printStackTrace(); // TODO: implement some sane logging such as LOG4J
                    Page.getCurrent().setUriFragment("!"); // To RootView
                }
            }
        });

        Button cancelButton = new Button(AppResources.getLocalizedString("label.cancel", requestLocale));
        cancelButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                fieldGroup.discard();
                Page.getCurrent().setUriFragment("!"); // To RootView
            }
        });

        HorizontalLayout commandButtons = new HorizontalLayout();
        commandButtons.addComponent(submitButton);
        commandButtons.addComponent(cancelButton);

        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(firstNameField);
        formLayout.addComponent(lastNameField);
        formLayout.addComponent(genderBox);
        formLayout.addComponent(reasons);
        formLayout.addComponent(commandButtons);

        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        verticalLayout.setWidth(600, Unit.PIXELS);
        Table applicantsDebug = new Table("DEBUG Table", applicantsContainer);
        applicantsDebug.setImmediate(true);
        formLayout.addComponent(applicantsDebug);
        verticalLayout.addComponent(formLayout);
        setCompositionRoot(verticalLayout);
    }
}


