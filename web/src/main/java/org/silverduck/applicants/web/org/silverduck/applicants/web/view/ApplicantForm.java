package org.silverduck.applicants.web.org.silverduck.applicants.web.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;
import org.apache.commons.lang3.StringUtils;
import org.silverduck.applicants.common.localization.AppResources;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.repository.ApplicantsRepository;
import org.silverduck.applicants.web.ApplicantsUI;
import org.silverduck.applicants.web.org.silverduck.applicants.web.component.ApplicantComponent;

import javax.ejb.EJB;

/**
 * UI for the Applicant Form for applying for a job.
 * <p/>
 * TODO: Add possibility to add a picture of the applicant
 *
 * @author Iiro Hietala
 */
@CDIView(ApplicantForm.VIEW)
public class ApplicantForm extends VerticalLayout implements View {

    public static final String VIEW = "ApplicantForm";

    @EJB
    private ApplicantsRepository applicantsRepository;

    private Label headerLabel;

    private Label infoLabel;

    private HorizontalLayout commandButtons;

    private ApplicantComponent applicantComponent;

    public ApplicantForm() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(horizontalLayout);

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);

        createHeaderLabels(contentLayout);
        createApplicantComponent(contentLayout);
        createCommandButtons(contentLayout);

        horizontalLayout.addComponent(contentLayout);
    }

    private void createApplicantComponent(VerticalLayout contentLayout) {
        applicantComponent = new ApplicantComponent();
        contentLayout.addComponent(applicantComponent);
    }

    private void createHeaderLabels(VerticalLayout contentLayout) {
        headerLabel = new Label(AppResources.getLocalizedString("label.applicantForm.formHeader", getUI().getCurrent().getLocale()));
        headerLabel.setStyleName(Runo.LABEL_H2);
        contentLayout.addComponent(headerLabel);
        infoLabel = new Label(AppResources.getLocalizedString("label.applicantForm.formInfo", getUI().getCurrent().getLocale()));
        infoLabel.setStyleName(Runo.LABEL_SMALL);
        contentLayout.addComponent(infoLabel);

    }

    private void createCommandButtons(VerticalLayout contentLayout) {
        Button submitButton = new Button(AppResources.getLocalizedString("label.submit", getUI().getCurrent().getLocale()));
        submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (applicantComponent.isValid()) {
                    Applicant applicant = applicantComponent.commit();

                    // Since we're using transient entity up to this point (only adding), this may be done
                    // as follows. Still, it's not pretty...
                    // TODO: We should be using MVP pattern instead. See patch.
                    if (applicant.getId() == null) {
                        applicantsRepository.addApplicant(applicant);
                    } else {
                        applicantsRepository.updateApplicant(applicant);
                    }

                    ApplicantsUI.navigateTo(ApplicantSummary.VIEW, applicant.getId());

                    // fireViewEvent(ApplicantPresenter.ADD_APPLICANT, fieldGroup.getItemDataSource().getBean());
                } else {
                    Notification.show(AppResources.getLocalizedString("applicantForm.validationErrorsNotification",
                            getUI().getCurrent().getLocale()), Notification.Type.TRAY_NOTIFICATION);
                }
            }
        });

        Button cancelButton = new Button(AppResources.getLocalizedString("label.cancel", getUI().getCurrent().getLocale()));
        cancelButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                applicantComponent.discard();
                ApplicantsUI.navigateTo(RootView.VIEW);
                // fireViewEvent(ApplicantPresenter.CANCEL_ADD, null);
            }
        });
        commandButtons = new HorizontalLayout();
        commandButtons.addComponent(submitButton);
        commandButtons.addComponent(cancelButton);
        contentLayout.addComponent(commandButtons);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Applicant applicant = null;
        if (!StringUtils.isEmpty(event.getParameters())) {
            applicantsRepository.findApplicant(Long.parseLong(event.getParameters()));
        } else {
            applicantComponent.edit(new Applicant().resetFields());
        }
    }
}


