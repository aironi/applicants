package org.silverduck.applicants.domain;

import org.silverduck.applicants.common.localization.LocalizedEnum;

/**
 * Enumeration for Genders.
 */
public enum Gender implements LocalizedEnum {
    MALE("label.gender.male"),

    FEMALE("label.gender.female");

    private String message;

    @Override
    public String toString() {
        return super.toString();
    }

    Gender(String message) {
        this.message = message;
    }

    @Override
    public String getResourceKey() {
        return message;
    }
}
