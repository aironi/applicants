package org.silverduck.applicants.repository.impl;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.openejb.api.LocalClient;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.silverduck.applicants.domain.Applicant;
import org.silverduck.applicants.domain.Gender;
import org.silverduck.applicants.repository.ApplicantsRepository;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
import java.util.Properties;

/**
 * Integration test for ApplicantsRepositoryImpl. Designed to be run in Embedded Container.
 * No mocking.
 * <p/>
 * Created by Iiro on 6.1.2014.
 */

@RunWith(BlockJUnit4ClassRunner.class)
@LocalClient
public class ApplicantsRepositoryImplTest extends TestCase {
    @EJB
    private ApplicantsRepository applicantsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    private static EJBContainer ejbContainer;

    @BeforeClass
    public static void setupBeforeClass() throws NamingException {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        ejbContainer = EJBContainer.createEJBContainer(p);
    }

    @Before
    public void setup() throws Exception {
        Context context = ejbContainer.getContext();
        context.bind("inject", this); // inject LocalClient fields
        userTransaction.begin();
    }

    @Test
    public void testValidation() {
        Applicant applicant = new Applicant();
        try {
            applicantsRepository.addEntity(applicant);
        } catch (EJBTransactionRolledbackException e) {
            if (!ConstraintViolationException.class.equals(e.getCause().getClass())) {
                throw e;
            } else {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
                assertEquals("Wrong amount of validation errors", 4, constraintViolationException.getConstraintViolations().size());
            }
            // Otherise OK
        }
    }

    @Test
    public void testAdd() {
        Applicant applicant = new Applicant();
        populateApplicant(applicant);
        applicantsRepository.addEntity(applicant);
        Assert.assertEquals("Wrong amount of applicants in Db", 1, entityManager.createQuery("SELECT a from Applicant AS a").getResultList().size());
    }

    @Test
    public void testRemove() {
        Applicant applicant = new Applicant();
        populateApplicant(applicant);

        Applicant persistedApplicant = applicantsRepository.addEntity(applicant);
        assertEquals("Entity was not persisted", 1, applicantsRepository.listApplicants().size());

        applicantsRepository.removeEntity(persistedApplicant.getId());
        assertEquals("Entity was not removed", 0, applicantsRepository.listApplicants().size());

    }

    private void populateApplicant(Applicant applicant) {
        applicant.setFirstName("Iiro");
        applicant.setLastName("Hietala");
        applicant.setGender(Gender.MALE);
        applicant.setReasons("Only good things can happen.");
    }

    @After
    public void afterTest() throws Exception {
        userTransaction.rollback(); // Don't actually persist anything.
    }
}
