package ves.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("WorkEvent")
public class WorkEvent extends Event {

    @Enumerated(EnumType.STRING)
    private WorkType workType;

}
