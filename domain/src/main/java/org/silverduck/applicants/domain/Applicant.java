package org.silverduck.applicants.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Applicant domain object. Represents an Applicant that applies for a job.
 */
@Entity
public class Applicant extends AbstractDomainObject implements Serializable {

    @Size(min = 1, max = 30, message = "{applicant.firstName.size}")
    @Column(name = "FirstName")
    private String firstName;

    @Size(min = 1, max = 30, message = "{applicant.lastName.size}")
    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Size(max = 100000, message = "{applicant.reasons.size}")
    @Column(name = "Reasons", length = 100000)
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

    public String toHumanReadable() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("First Name '").append(firstName).append("' Last Name '").append(lastName).append("' Gender '")
                .append(gender).append("' Reasons '").append(reasons).append("'");
        return sb.toString();
    }
}
