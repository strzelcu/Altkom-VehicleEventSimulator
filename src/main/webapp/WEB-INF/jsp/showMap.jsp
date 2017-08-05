<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<c:set var="now" value="<%= new java.util.Date()%>"/>
<html>
<head>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.1.0/dist/leaflet.css"
          integrity="sha512-wcw6ts8Anuw10Mzh9Ytw4pylW8+NAD4ch3lqm9lzAsTxg0GFeJgoAtxuCLREZSC5lUXdVyo/7yfsqFjQ4S+aKw=="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.1.0/dist/leaflet.js"
            integrity="sha512-mNqn2Wg7tSToJhvHcqfzLMU6J4mkOImSPTxVZAdo+lcPlk+GhZmYgACEe0x35K7YzW1zJ7XyJV/TT1MrdXvMcA=="
            crossorigin=""></script>
    <title>Mapa zdarzenia numer ${event.id}</title>
</head>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>${event} (ID ${event.id}) z dnia ${event.startDate.toLocaleString()} dla pojazdu ${event.car}</h2>
<div id="mapid" style="width: 100%; height: 400px;"></div>
<script>

    var mymap = L.map('mapid').setView([51.505, -0.09], 13);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
        '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="http://mapbox.com">Mapbox</a>',
        id: 'mapbox.streets'
    }).addTo(mymap);

    var geojsonFeature = ${geoJson};

    function onEachFeature(feature, layer) {
        var popupContent = "";
        if (feature.geometry.type != "LineString") {
            if (feature.properties && feature.properties.popupContent) {
                popupContent += "<p><b>" + feature.properties.date + "</b><br>";
                popupContent += feature.properties.popupContent + "<br>";
                popupContent += "Pojazd: " + feature.properties.car + "<br>";
                popupContent += "Adres: " + feature.properties.address + "<br>";
                popupContent += "Typ: " + feature.properties.type + "<br>";
                if (feature.properties.additionalWorkValue != null) {
                    popupContent += "Warto&#347;&#263;: " + feature.properties.additionalWorkValue + "<br>";
                }
                popupContent += "</p>";
            }

            layer.bindPopup(popupContent);
        }
    }

    L.geoJSON(geojsonFeature, {

        filter: function (feature, layer) {

            if (feature.properties) {
                // If the property "underConstruction" exists and is true, return false (don't render features under construction)
                return feature.properties.underConstruction !== undefined ? !feature.properties.underConstruction : true;
            }
            return false;
        },

        onEachFeature: onEachFeature
    }).addTo(mymap);

    mymap.fitBounds([[${event.startPoint.latitude}, ${event.startPoint.longitude}], [${event.endPoint.latitude}, ${event.endPoint.longitude}]]);

</script>
<br>
<a href="showEvents.do">Wr&oacute;&#263;</a>
</body>
</html>