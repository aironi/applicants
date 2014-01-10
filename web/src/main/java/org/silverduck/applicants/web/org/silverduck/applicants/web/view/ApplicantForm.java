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
import org.silverduck.applicants.web.ErrorUI;

import javax.inject.Inject;

/**
 * UI for the Applicant Form for applying for a job.
 *
 * TODO: Add possibility to add a picture of the applicant
 *
 * @author Iiro Hietala
 */
@CDIView(ApplicantForm.VIEW)
public class ApplicantForm extends FormLayout implements View {

    public static final String VIEW = "ApplicantForm";

    @Inject
    private JPAContainer<Applicant> applicantsContainer;

    @PropertyId("firstName")
    private TextField firstNameField;

    @PropertyId("lastName")
    private TextField lastNameField;

    @PropertyId("gender")
    private ComboBox genderBox;

    @PropertyId("reasons")
    private TextArea reasons;

    Button submitButton;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        final BeanFieldGroup<Applicant> fieldGroup = new BeanFieldGroup(Applicant.class);
        final Applicant applicant = new Applicant().resetFields();
        fieldGroup.setItemDataSource(applicant);

        // Custom initialization for ComboBox to get data localized
        genderBox = new ComboBox(AppResources.getLocalizedString("label.gender", getUI().getCurrent().getLocale()));
        for (Gender gender : Gender.values()) {
            genderBox.addItem(gender);
            genderBox.setItemCaption(gender, AppResources.getLocalizedString(gender.getResourceKey(), getUI().getCurrent().getLocale()));
            genderBox.setImmediate(true);
        }

        // Instantiate rest of the fields and bind them to domain object
        fieldGroup.buildAndBindMemberFields(this);
        fieldGroup.setBuffered(true);

        // Set localized captions
        for (Object propertyId : fieldGroup.getBoundPropertyIds()) {
            Field<?> field = fieldGroup.getField(propertyId);
            field.setCaption(AppResources.getLocalizedString("label." + propertyId, getUI().getCurrent().getLocale()));
        }

        Button submitButton = new Button(AppResources.getLocalizedString("label.submit", getUI().getCurrent().getLocale()));
        submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    if (fieldGroup.isValid()) {
                        fieldGroup.commit();
                        applicantsContainer.addEntity(applicant);
                        Page.getCurrent().setUriFragment("!" + ApplicantSummary.VIEW); // To Summary View
                    } else {
                        Notification.show(AppResources.getLocalizedString("applicantForm.validationErrorsNotification",
                                getUI().getCurrent().getLocale()), Notification.Type.TRAY_NOTIFICATION);
                    }
                } catch (FieldGroup.CommitException e) {
                    getUI().addWindow(new ErrorUI(e));
                    e.printStackTrace(); // TODO: implement some sane logging such as LOG4J
                }
            }
        });

        Button cancelButton = new Button(AppResources.getLocalizedString("label.cancel", getUI().getCurrent().getLocale()));
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

        Label infoLabel = new Label(AppResources.getLocalizedString("label.applicantForm.formInfo", getUI().getCurrent().getLocale()));

        addComponent(infoLabel);
        addComponent(firstNameField);
        addComponent(lastNameField);
        addComponent(genderBox);
        addComponent(reasons);
        addComponent(commandButtons);
    }
}


