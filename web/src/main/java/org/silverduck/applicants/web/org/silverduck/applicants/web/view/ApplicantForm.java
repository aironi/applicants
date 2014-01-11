package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.domain.Gender;
import org.silverduck.applicants.web.ApplicantsUI;
import org.silverduck.applicants.web.ErrorUI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * UI for the Applicant Form for applying for a job.
 *
 * TODO: Add possibility to add a picture of the applicant
 *
 * @author Iiro Hietala
 */
@CDIView(ApplicantForm.VIEW)
@UIScoped
public class ApplicantForm extends VerticalLayout implements View {

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

    private Label headerLabel;

    private Label infoLabel;

    private HorizontalLayout commandButtons;

    private BeanFieldGroup<Applicant> fieldGroup;

    /**
     * Edit an applicant
     *
     * @param applicant Applicant to Edit
     */
    public void edit(final Applicant applicant) {
        bindFields(applicant);
        setReadOnly(false);
    }

    private void bindFields(Applicant applicant) {
        fieldGroup = new BeanFieldGroup(Applicant.class);
        fieldGroup.setItemDataSource(new BeanItem<Applicant>(applicant));
        fieldGroup.setBuffered(true);
        fieldGroup.bindMemberFields(this);
    }

    /**
     * View an applicant
     *
     * @param applicant Applicant to View
     */
    public void view(final Applicant applicant) {
        bindFields(applicant);
        setReadOnly(true);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);

        firstNameField.setReadOnly(readOnly);
        lastNameField.setReadOnly(readOnly);
        genderBox.setReadOnly(readOnly);
        reasons.setReadOnly(readOnly);
        headerLabel.setVisible(!readOnly);
        infoLabel.setVisible(!readOnly);
        commandButtons.setVisible(!readOnly);
    }

    @PostConstruct
    protected void initComponent() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // Custom initialization for ComboBox to get data localized
        genderBox = new ComboBox(AppResources.getLocalizedString("label.applicantForm.gender", getUI().getCurrent().getLocale()));
        for (Gender gender : Gender.values()) {
            genderBox.addItem(gender);
            genderBox.setItemCaption(gender, AppResources.getLocalizedString(gender.getResourceKey(), getUI().getCurrent().getLocale()));
            genderBox.setImmediate(true);
        }

        firstNameField = new TextField(AppResources.getLocalizedString("label.applicantForm.firstName", getUI().getCurrent().getLocale()));
        lastNameField = new TextField(AppResources.getLocalizedString("label.applicantForm.lastName", getUI().getCurrent().getLocale()));
        reasons = new TextArea(AppResources.getLocalizedString("label.applicantForm.reasons", getUI().getCurrent().getLocale()));

        Button submitButton = new Button(AppResources.getLocalizedString("label.submit", getUI().getCurrent().getLocale()));
        submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    if (fieldGroup.isValid()) {
                        fieldGroup.commit(); // Bind to Bean

                        // Since we're using detached entities up to this point, this may be done
                        // as follows. Still, it's not pretty.
                        // TODO: We should be using MVP pattern instead.

                        Applicant applicant = fieldGroup.getItemDataSource().getBean();
                        applicantsContainer.addEntity(applicant);
                        view(applicant);
                        ApplicantsUI.navigateTo(ApplicantSummary.VIEW);

                        // fireViewEvent(ApplicantPresenter.ADD_APPLICANT, fieldGroup.getItemDataSource().getBean());
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
                ApplicantsUI.navigateTo(RootView.VIEW);
                // fireViewEvent(ApplicantPresenter.CANCEL_ADD, null);
            }
        });

        commandButtons = new HorizontalLayout();
        commandButtons.addComponent(submitButton);
        commandButtons.addComponent(cancelButton);

        headerLabel = new Label(AppResources.getLocalizedString("label.applicantForm.formHeader", getUI().getCurrent().getLocale()));
        headerLabel.setStyleName(Runo.LABEL_H2);

        infoLabel = new Label(AppResources.getLocalizedString("label.applicantForm.formInfo", getUI().getCurrent().getLocale()));
        infoLabel.setStyleName(Runo.LABEL_SMALL);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        FormLayout formLayout = new FormLayout();
        formLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        formLayout.setSizeFull();
        formLayout.addComponent(headerLabel);
        formLayout.addComponent(infoLabel);
        formLayout.addComponent(firstNameField);
        formLayout.addComponent(lastNameField);
        formLayout.addComponent(genderBox);
        formLayout.addComponent(reasons);
        formLayout.addComponent(commandButtons);
        horizontalLayout.addComponent(formLayout);
        addComponent(horizontalLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {


    }
}


