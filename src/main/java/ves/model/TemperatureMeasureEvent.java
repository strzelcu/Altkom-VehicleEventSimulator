package ves.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TemperatureMeasureEvent")
public class TemperatureMeasureEvent extends AdditionalWorkEvent {
    
    public TemperatureMeasureEvent(){
        this.setAdditionalWorkType(AdditionalWorkType.TEMPERATURE_MEASURMENT);
    }
    
    @Column(name = "awe_temperature")
    private int temperature;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
