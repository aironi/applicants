package org.silverduck.applicants.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Applicant domain object. Represents an Applicant that applies for a job.
 */
@Entity
public class Applicant extends AbstractDomainObject {

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "FirstName")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "LastName")
    private String lastName;

    @NotNull
    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Column(name = "Reasons")
    private String reasons;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    /**
     * Helper for UI that resets the fields.
     *
     * @return this
     */
    public Applicant resetFields() {
        firstName = "";
        lastName = "";
        gender = Gender.MALE;
        reasons = "";
        return this;
    }
}
