package ves.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AdditionalWorkEvent")
public class AdditionalWorkEvent extends Event {

    @Column(name = "awe_workType")
    @Enumerated(value = EnumType.STRING)
    private AdditionalWorkType workType;

    @Column(name = "awe_additionalParameter")
    private String additionalParameter;

    public AdditionalWorkType getWorkType() {
        return workType;
    }

    public void setWorkType(AdditionalWorkType workType) {
        this.workType = workType;
    }

    public String getAdditionalParameter() {
        return additionalParameter;
    }

    public void setAdditionalParameter(String additionalParameter) {
        this.additionalParameter = additionalParameter;
    }
}