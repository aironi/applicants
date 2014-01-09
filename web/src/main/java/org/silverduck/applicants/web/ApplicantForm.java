package org.silverduck.applicants.web;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.*;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.domain.Gender;

import java.util.Locale;

/**
 * UI for the Applicant Form
 *
 * @author Iiro Hietala
 */
public class ApplicantForm extends CustomComponent {

    @PropertyId("firstName")
    private TextField firstNameField;

    @PropertyId("lastName")
    private TextField lastNameField;

    @PropertyId("gender")
    private ComboBox genderBox;

    @PropertyId("reasons")
    private TextArea reasons;

    Button submitButton;

    public ApplicantForm(Item item, Locale locale) {
        FormLayout formLayout = new FormLayout();

        final FieldGroup fieldGroup = new FieldGroup(item);

        // Custom initialization for ComboBox to get data localized
        genderBox = new ComboBox(AppResources.getLocalizedString("label.gender", locale));
        for (Gender gender : Gender.values()) {
            genderBox.addItem(gender);
            genderBox.setItemCaption(gender, AppResources.getLocalizedString(gender.getResourceKey(), locale));
        }

        fieldGroup.buildAndBindMemberFields(this); // Instantiate fields and bind them to domain object
        fieldGroup.setBuffered(true);

        // Set localized captions
        for (Object propertyId : fieldGroup.getBoundPropertyIds()) {
            Field<?> field = fieldGroup.getField(propertyId);
            field.setCaption(AppResources.getLocalizedString("label." + propertyId, locale));
            field.setRequired(true);
        }

        Button submitButton = new Button(AppResources.getLocalizedString("label.submit", locale));
        submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    fieldGroup.commit();
                } catch (FieldGroup.CommitException e) {
                    getUI().addWindow(new ErrorUI(e));
                    e.printStackTrace(); // TODO: implement some sane logging such as LOG4J
                }

            }
        });

        Button cancelButton = new Button(AppResources.getLocalizedString("label.cancel", locale));
        cancelButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                fieldGroup.discard();

            }
        });

        HorizontalLayout commandButtons = new HorizontalLayout();
        commandButtons.addComponent(submitButton);
        commandButtons.addComponent(cancelButton);

        formLayout.addComponent(firstNameField);
        formLayout.addComponent(lastNameField);
        formLayout.addComponent(genderBox);
        formLayout.addComponent(reasons);
        formLayout.addComponent(commandButtons);

        setCompositionRoot(formLayout);

        /*
        TextField firstNameField = new TextField(AppResources.getLocalizedString("label.firstName", request.getLocale()));
        firstNameField.setRequired(true);
        verticalLayout.addComponent(firstNameField);

        TextField lastNameField = new TextField(AppResources.getLocalizedString("label.surname", request.getLocale()));
        lastNameField.setRequired(true);
        verticalLayout.addComponent(lastNameField);

        ComboBox genderBox = new ComboBox(AppResources.getLocalizedString("label.gender", request.getLocale()));
        for (Gender gender : Gender.values()) {
            genderBox.addItem(gender);
            genderBox.setItemCaption(gender, AppResources.getLocalizedString(gender.getResourceKey(), request.getLocale()));
        }

        genderBox.setRequired(true);
        verticalLayout.addComponent(genderBox);

        TextArea reasons = new TextArea(AppResources.getLocalizedString("label.reasons", request.getLocale()));
        reasons.setRequired(true);
        verticalLayout.addComponent(reasons);

        Button submitButton = new Button(AppResources.getLocalizedString("label.submit", request.getLocale()));
        submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                verticalLayout.addComponent(new Label("Submitted"));
            }
        });
        verticalLayout.addComponent(submitButton);
         */

    }

}


