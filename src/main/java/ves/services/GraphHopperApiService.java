package ves.services;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.api.GraphHopperWeb;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.GeocodingApi;
import com.graphhopper.directions.api.client.model.GeocodingLocation;
import com.graphhopper.directions.api.client.model.GeocodingResponse;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.util.shapes.GHPoint3D;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ves.model.Address;
import ves.model.GeoPoint;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class GraphHopperApiService {

    @Value( "${graphhopper.api.key}" )
    private String KEY;

    private final String LOCALE = "en";

    private GeocodingApi geocodingApiInstance = new GeocodingApi();

    public GeoPoint getGeoPoint(String queryAddress) {

        GeocodingLocation location = null;
        GeoPoint geoPoint = new GeoPoint();

        try {
            location = geocodingApiInstance.geocodeGet(KEY, queryAddress, LOCALE, 5, false, null, "default").getHits().get(0);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        if(null != location) {
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

    public GeoPoint getGeoPoint(double latitude, double longitude, boolean addAddresses) {

        GeocodingLocation location = null;
        GeoPoint geoPoint = new GeoPoint();
        Address address = new Address();

        if(addAddresses) {
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

    public Object[] getViaPoints(GeoPoint start, GeoPoint end, boolean addAddresses){

        ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>();

        GraphHopperWeb graphHopperWeb = new GraphHopperWeb();
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
            geoPoints.add(getGeoPoint(geo.getLat(),geo.getLon(), addAddresses));

        }
        Object[] result = {geoPoints, timeLenght};
        return result;
    }

    // TODO Usunąć metodę przed wysłaniem
    private void getGeocodingApiTest(){
        String q = "Zawadzkie, Opolska 61c/6"; // String | If you do forward geocoding, then this would be a textual description of the adress you are looking for. If you do reverse geocoding this would be in lat,lon.
        Integer limit = 5; // Integer | Specify the maximum number of returned results
        Boolean reverse = true; // Boolean | Set to true to do a reverse Geocoding request
        String point = "50.046325,19.956465"; // String | The location bias in the format 'latitude,longitude' e.g. point=45.93272,11.58803
        String provider = "default"; // String | Can be either, default, nominatim, opencagedata
        try {
            GeocodingResponse result = geocodingApiInstance.geocodeGet("a982c2a1-833b-4d22-9bbb-15aacbf139a6", null, LOCALE, limit, reverse, point, provider);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling GeocodingApi#geocodeGet");
            e.printStackTrace();
        }
    }


    // TODO Usunąć metodę przed wysłaniem
    public void getDirectionsApiTest(){

        // Hint: create this thread safe instance only once in your application to allow the underlying library to cache the costly initial https handshake
        GraphHopper graphHopper = new GraphHopper();

        GraphHopperWeb gh = new GraphHopperWeb();
        // insert your key here
        gh.setKey("a982c2a1-833b-4d22-9bbb-15aacbf139a6");
        // change timeout, default is 5 seconds
        gh.setDownloader(new OkHttpClient.Builder().
                connectTimeout(5, TimeUnit.SECONDS).
                readTimeout(5, TimeUnit.SECONDS).build());

        // specify at least two coordinates
        GHRequest req = new GHRequest().
                addPoint(new GHPoint(49.6724, 11.3494)).
                addPoint(new GHPoint(49.6550, 11.4180));
        // Set vehicle like car, bike, foot, ...
        req.setVehicle("car");
        // Optionally enable/disable elevation in output PointList, currently bike and foot support elevation, default is false
        req.getHints().put("elevation", false);
        // Optionally enable/disable turn instruction information, defaults is true
        req.getHints().put("instructions", false);
        // Optionally enable/disable path geometry information, default is true
        req.getHints().put("calc_points", true);
        // note: turn off instructions and calcPoints if you just need the distance or time
        // information to make calculation and transmission faster
        //
        // Optionally set specific locale for instruction information, supports already over 25 languages,
        // defaults to English
        req.setLocale(Locale.ENGLISH);

        GHResponse fullRes = gh.route(req);

        PathWrapper res = fullRes.getBest();
        // get path geometry information (latitude, longitude and optionally elevation)
        PointList pl = res.getPoints();
        // distance of the full path, in meter
        double distance = res.getDistance();
        // time of the full path, in milliseconds
        long millis = res.getTime();
        // get information per turn instruction
        InstructionList il = res.getInstructions();

        for (GHPoint3D point :
                pl) {
            System.out.println(point.toString());
        }
        System.out.println(distance);
        System.out.println(millis);

    }
}