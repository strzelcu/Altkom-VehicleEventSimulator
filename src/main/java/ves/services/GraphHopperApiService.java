package ves.services;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.PathWrapper;
import com.graphhopper.api.GraphHopperWeb;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.GeocodingApi;
import com.graphhopper.directions.api.client.model.GeocodingLocation;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.util.shapes.GHPoint3D;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ves.exceptions.AddressNotFoundException;
import ves.model.Address;
import ves.model.GeoPoint;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Klasa GraphHopperApiService jest prostą fasadą wykorzystującą obiekty GeocodingApi
 * i GraphHopperWeb dzięki której wywołujemy proste metody zwracające potrzebne w
 * projekcie obiekty.
 */
@Service
public class GraphHopperApiService {

    private final String LOCALE = "en";
    @Value("${graphhopper.api.key}")
    private String KEY;
    private GeocodingApi geocodingApiInstance = new GeocodingApi();
    private GraphHopperWeb graphHopperWeb = new GraphHopperWeb();


    /**
     * Metoda getGeoPoint na podstawie parametru queryAddress pobiera dane z GeocodingApi
     * i zwraca nowy obiekt GeoPoint uzupełniony danymi z pierwszego adresu zwróconego przez
     * zapytanie do API.
     *
     * @param queryAddress zapytanie adresowe np "Wroclaw"
     * @return GeoPoint uzupełniony danymi
     * @throws AddressNotFoundException wyjątek z informacja o nieznalezionym adresie
     */
    public GeoPoint getGeoPoint(String queryAddress) throws AddressNotFoundException {

        GeocodingLocation location = null;
        GeoPoint geoPoint = new GeoPoint();

        try {
            location = geocodingApiInstance.geocodeGet(KEY, queryAddress, LOCALE, 5, false, null, "default").getHits().get(0);
        } catch (ApiException ae) {
            ae.printStackTrace();
        } catch (Exception e) {
            throw new AddressNotFoundException("Address " + queryAddress + " not found. Try again.");
        }

        if (null != location) {
            Address address = new Address();
            if (location.getStreet() != null) address.setStreet(location.getStreet());
            if (location.getHousenumber() != null) address.setHouseNumber(location.getHousenumber());
            if (location.getPostcode() != null) address.setPostalCode(location.getPostcode());
            if (location.getCountry() != null) address.setCountry(location.getCountry());
            if (location.getCity() != null) address.setCity(location.getCity());
            geoPoint.setAddress(address);
            geoPoint.setLatitude(location.getPoint().getLat());
            geoPoint.setLongitude(location.getPoint().getLng());
        }
        return geoPoint;
    }

    /**
     * Metoda getGeoPoint przyjmująca niżej wymienione parametry zwraca GeoPoint
     * zawierający adres. Wykorzystana jest do pobierania punktów pośrednich.
     *
     * @param latitude     szerokość geograficzna
     * @param longitude    długość geograficzna
     * @param addAddresses true/false czy pobierać adresy z API
     * @return GeoPoint
     */
    private GeoPoint getGeoPoint(double latitude, double longitude, boolean addAddresses) {

        GeocodingLocation location;
        GeoPoint geoPoint = new GeoPoint();
        Address address = new Address();

        if (addAddresses) {
            try {
                location = geocodingApiInstance.geocodeGet(KEY, null, LOCALE, 5, true, latitude + "," + longitude, "default").getHits().get(0);
                address.setStreet(location.getStreet());
                address.setHouseNumber(location.getHousenumber());
                address.setPostalCode(location.getPostcode());
                address.setCity(location.getCity());
                address.setCountry(location.getCountry());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        geoPoint.setAddress(address);
        geoPoint.setLatitude(latitude);
        geoPoint.setLongitude(longitude);
        return geoPoint;
    }

    /**
     * Metoda getViaPoints pobiera trasę z GraphHopperApi i zwraca tablicę z dwoma
     * obiektami. Pierwszy obiekt to lista punktów pośrednich trasy GeoPoint, natomiast
     * drugi obiekt to czas potrzebny na przebycie trasy. GraphHopperApi udostępnia również
     * długość trasy, która nie została wykorzystana w projekcie.
     *
     * @param start        GeoPoint wraz z długością i szerokością geograficzną punktu początkowego
     * @param end          GeoPoint wraz z długością i szerokością geograficzną punktu końcowego
     * @param addAddresses true/false czy pobrać adres dla każdego punktu z osobna
     * @return Object[] wraz z listą punktów pośrednich i długość trasy
     */
    public Object[] getViaPoints(GeoPoint start, GeoPoint end, boolean addAddresses) {

        ArrayList<GeoPoint> geoPoints = new ArrayList<>();

        graphHopperWeb.setKey(KEY);
        graphHopperWeb.setDownloader(new OkHttpClient.Builder().
                connectTimeout(5, TimeUnit.SECONDS).
                readTimeout(5, TimeUnit.SECONDS).build());
        GHRequest req = new GHRequest().
                addPoint(new GHPoint(start.getLatitude(), start.getLongitude())).
                addPoint(new GHPoint(end.getLatitude(), end.getLongitude()));
        req.setVehicle("car");
        req.getHints().put("elevation", false);
        req.getHints().put("calc_points", true);
        req.setLocale(Locale.ENGLISH);
        GHResponse fullRes = graphHopperWeb.route(req);
        PathWrapper res = fullRes.getBest();
        PointList pl = res.getPoints();
        Long timeLenght = res.getTime();
        for (GHPoint3D geo :
                pl) {
            geoPoints.add(getGeoPoint(geo.getLat(), geo.getLon(), addAddresses));

        }
        return new Object[]{geoPoints, timeLenght};
    }
}