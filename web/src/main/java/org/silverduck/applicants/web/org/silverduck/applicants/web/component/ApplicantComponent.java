package org.silverduck.applicants.web.org.silverduck.applicants.web.component;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.domain.Gender;
import org.silverduck.applicants.web.ErrorUI;

/**
 * Common component for showing Applicant data in a FormLayout.
 */
public class ApplicantComponent extends CustomComponent {

    @PropertyId("firstName")
    private TextField firstNameField;

    @PropertyId("lastName")
    private TextField lastNameField;

    @PropertyId("gender")
    private ComboBox genderBox;

    @PropertyId("reasons")
    private TextArea reasons;

    private BeanFieldGroup<Applicant> fieldGroup;


    public ApplicantComponent() {
        // Custom initialization for ComboBox to get data localized
        genderBox = new ComboBox(AppResources.getLocalizedString("label.applicantForm.gender", getUI().getCurrent().getLocale()));
        for (Gender gender : Gender.values()) {
            genderBox.addItem(gender);
            genderBox.setItemCaption(gender, AppResources.getLocalizedString(gender.getResourceKey(), getUI().getCurrent().getLocale()));
            genderBox.setImmediate(true);
        }

        firstNameField = new TextField(AppResources.getLocalizedString("label.applicantForm.firstName", getUI().getCurrent().getLocale()));
        firstNameField.setImmediate(true);
        lastNameField = new TextField(AppResources.getLocalizedString("label.applicantForm.lastName", getUI().getCurrent().getLocale()));
        lastNameField.setImmediate(true);
        reasons = new TextArea(AppResources.getLocalizedString("label.applicantForm.reasons", getUI().getCurrent().getLocale()));
        reasons.setImmediate(true);
        reasons.addStyleName("vertical-align-top");

        FormLayout formLayout = new FormLayout();
        formLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        formLayout.setSizeFull();
        formLayout.addComponent(firstNameField);
        formLayout.addComponent(lastNameField);
        formLayout.addComponent(genderBox);
        formLayout.addComponent(reasons);

        setCompositionRoot(formLayout);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);

        firstNameField.setReadOnly(readOnly);
        lastNameField.setReadOnly(readOnly);
        genderBox.setReadOnly(readOnly);
        reasons.setReadOnly(readOnly);
    }
//        bindFields(applicant);

    private void bindFields(Applicant applicant) {
        fieldGroup = new BeanFieldGroup(Applicant.class);
        fieldGroup.setItemDataSource(new BeanItem<Applicant>(applicant));
        fieldGroup.setBuffered(true);
        fieldGroup.bindMemberFields(this);
    }

    /**
     * Edit an applicant
     *
     * @param applicant Applicant to Edit
     */
    public void edit(final Applicant applicant) {
        bindFields(applicant);
        setReadOnly(false);
    }

    /**
     * View an applicant
     *
     * @param applicant Applicant to View
     */
    public void view(final Applicant applicant) {
        if (applicant != null) {
            bindFields(applicant);
        }
        setReadOnly(true);
    }

    /**
     * @return True, if the given data in the Form valid. Otherwise false.
     */
    public boolean isValid() {
        return fieldGroup.isValid();
    }

    /**
     * Commits the changes in the component and returns the Applicant object.
     *
     * @return Applicant object if bound. Otherwise null.
     */
    public Applicant commit() {
        if (fieldGroup.getItemDataSource() != null) {
            try {
                fieldGroup.commit();
            } catch (FieldGroup.CommitException e) {
                getUI().addWindow(new ErrorUI(e));
                e.printStackTrace(); // TODO: implement some sane logging such as LOG4J
            }
            return fieldGroup.getItemDataSource().getBean();
        }
        return null;
    }

    /**
     * Discard given changes in Form to the bound Applicant
     */
    public void discard() {
        if (fieldGroup.getItemDataSource() != null) {
            fieldGroup.discard();
        }
    }
}
