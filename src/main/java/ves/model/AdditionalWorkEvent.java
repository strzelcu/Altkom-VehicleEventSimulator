package ves.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Klasa AdditionalWorkEvent odpowiada dodatkowym pracom pojazdów.
 * Dla każdego rodzaju pracy możemy utworzyć nową klasę rozszerzoną
 * o klasę AdditionalWorkEvent, która będzie zawierała dane specyficzne
 * dla dodatkowej pracy np. mierzenie temperatury w chłodni
 */

@Entity
@DiscriminatorValue("AdditionalWorkEvent")
public class AdditionalWorkEvent extends Event {

    @Enumerated(EnumType.STRING)
    private AdditionalWorkType additionalWorkType;

    void setAdditionalWorkType(AdditionalWorkType additionalWorkType) {
        this.additionalWorkType = additionalWorkType;
    }

}
