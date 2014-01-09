package org.silverduck.applicants.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Base class for all domain objects in the project. Contains mandatory fields for all domain objects.
 */
@MappedSuperclass
public abstract class AbstractDomainObject implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "Created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "Updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
