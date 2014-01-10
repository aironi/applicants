package org.silverduck.applicants.repository.impl;

import com.vaadin.addon.jpacontainer.provider.MutableLocalEntityProvider;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.repository.ApplicantsRepository;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Repository methods that provide Applicant-operations with JTA transcations.
 */
@Stateless(name = "applicantsRepository")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ApplicantsRepositoryImpl extends
        MutableLocalEntityProvider<Applicant> implements ApplicantsRepository {

    // Implementation Note:
    // Intentionally named 'poorly'. This has to be something else than 'entityManager'. Otherwise it overrides the
    // superclass field and PersistenceContext won't get injected.
    @PersistenceContext(unitName = "applicants-unit", type = PersistenceContextType.TRANSACTION)
    protected EntityManager em;

    public ApplicantsRepositoryImpl() {
        super(Applicant.class);
        setTransactionsHandledByProvider(false);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    protected void runInTransaction(Runnable operation) {
        super.runInTransaction(operation);
    }

    @PostConstruct
    public void init() {
        setEntityManager(em);
        setEntitiesDetached(false);
    }

    @PrePersist
    public void prePersist(Applicant applicant) {
        // TODO: In real world use Joda time and store in UTC.
        applicant.setCreated(new Date());
    }

    @PreUpdate
    public void preUpdate(Applicant applicant) {
        // TODO: In real world use Joda time and store in UTC.
        applicant.setUpdated(new Date());
    }

    @Override
    public List<Applicant> listApplicants() {
        return em.createQuery("SELECT applicant FROM Applicant as applicant").getResultList();
    }
}
