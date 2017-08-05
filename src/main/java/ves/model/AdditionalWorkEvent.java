package ves.model;

import javax.persistence.*;

/**
 * Klasa AdditionalWorkEvent reprezentuje obiekty zdarzeń nie związanych z ruchem
 * pojazdów. Jest to przykładowa klasa. Do każdego zdarzenia nie związanego z
 * ruchem pojazdów powinniśmy podejść indywidualnie i zaimplementować nową klasę
 * na wzór klasy AdditionalWorkEvent, która będzie odzworowywać indywidualne cechy
 * każdego zdarzenia. Na potrzeby symulatora klasa została uogólniona.
 */
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

    @Override
    public String toString() {
        return "Zdarzenie pracy dodatkowej";
    }
}