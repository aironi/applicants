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
    /**
     * List all Applicants
     *
     * @return All Applicants.
     */
    public List<Applicant> listApplicants();

    /**
     * Returns Applicant associated with the given ID
     *
     * @param applicantId ID of the applicant
     * @return The Applicant object or null if applicant cannot be found with given ID.
     */
    public Applicant findApplicant(Long applicantId);

    /**
     * Add Applicant to Repository
     *
     * @param applicant Applicant to Add
     */
    void addApplicant(Applicant applicant);

    /**
     * Update pre-existing Applicant in repository
     *
     * @param applicant Previously persisted Applicant
     */
    void updateApplicant(Applicant applicant);

    /**
     * Remove an existing Applicant from repository
     *
     * @param applicant Applicant to Remove
     */
    void removeApplicant(Applicant applicant);
}
