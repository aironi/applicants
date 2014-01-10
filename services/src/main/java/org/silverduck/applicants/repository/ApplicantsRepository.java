package org.silverduck.applicants.repository;


import com.vaadin.addon.jpacontainer.MutableEntityProvider;
import org.silverduck.applicants.domain.Applicant;

import javax.ejb.Local;
import java.util.List;

/**
 * ApplicantsRepository interface.
 * <p/>
 * TODO: Also a generic interface could be introduced if we had several different types of domain objects
 */
@Local
public interface ApplicantsRepository extends MutableEntityProvider<Applicant> {
    public List<Applicant> listApplicants();
}
