package ves.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "eve_type",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Event")
public class Event {

    @Id
    @Column(name = "eve_id")
    private Long id;

    @Column(name = "eve_startDate")
    private Date startDate;

    @Column(name = "eve_endDate")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "eve_startLatitude")
    private double startLatitude;

    @Column(name = "eve_startLongitude")
    private double startLongitude;

    @Column(name = "eve_startAddress")
    private String startAddress;

    @Column(name = "eve_endLatitude")
    private double endLatitude;

    @Column(name = "eve_endLongitude")
    private double endLongitude;

    @Column(name = "eve_endAddress")
    private String endAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }
}
