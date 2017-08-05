package ves.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Klasa Event jest podstawą każdego zdarzenia. Wszystkie klasy reprezentujące
 * obiekty zdarzeń powinny być rozszerzone o klasę Event. Klasa Event jest klasą
 * abstrakcyjną aby zapobiec utworzeniu obiektu tej klasy.
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "eve_type",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Event implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "eve_id")
    private Integer id;

    @Column(name = "eve_startDate")
    private Date startDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private GeoPoint startPoint;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private GeoPoint endPoint;

    @Column(name = "eve_endDate")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public GeoPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(GeoPoint startPoint) {
        this.startPoint = startPoint;
    }

    public GeoPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(GeoPoint endPoint) {
        this.endPoint = endPoint;
    }
}