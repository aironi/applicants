package org.silverduck.applicants.web;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.silverduck.applicants.domain.Applicant;

import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@SuppressWarnings("serial")
public class ApplicantsUI extends UI {

    //@EJB
    //private ApplicantsRepo applicantsRepo;


    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ApplicantsUI.class, widgetset = "org.silverduck.applicants.web.AppWidgetSet")
    @PersistenceContext(name = "persistence/em", unitName = "applicants-unit")
    public static class Servlet extends VaadinServlet {
    }

    public ApplicantsUI() {
    }

    @Override
    protected void init(VaadinRequest request) {
        initializeLayout(request);
    }

    private void initializeLayout(VaadinRequest request) {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        verticalLayout.setWidth(500, Unit.PIXELS);

        verticalLayout.addComponent(new Label("This is a TEST label to see if anything gets rendered"));
        verticalLayout.addComponent(new ApplicantForm(new BeanItem<Applicant>(new Applicant().resetFields()), request.getLocale()));


//        Table applicantsDebug = new Table("DEBUG Table", applicantsRepo.// TODO: do it.

        //      verticalLayout.addComponent(applicantsDebug);
        horizontalLayout.addComponent(verticalLayout);
        setContent(horizontalLayout);
    }

}
