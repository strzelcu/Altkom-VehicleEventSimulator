<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<title>Vehicle Event Simulator</title>
<body>
	<h1>Vehicle Event Simulator 1.0</h1>
    <h2>Menu</h2>
    <ul>
        <li><a href="<c:url value="/addCar.do" />" >Dodaj pojazd</a></li>
        <li><a href="<c:url value="/showCars.do" />" >Poka&#380; list&#281; pojazd&#243;w</a></li><br><br>
        <li><a href="<c:url value="/addDriveEvent.do" />" >Dodaj zdarzenie jazdy</a></li>
        <li><a href="<c:url value="/addParkingEvent.do" />" >Dodaj zdarzenie postojowe</a></li>
        <li><a href="<c:url value="/addAdditionalWorkEvent.do" />" >Dodaj zdarzenie dodatkowe</a></li>
        <li><a href="<c:url value="/showEvents.do" />" >Poka&#380; list&#281; zdarze&#324;</a></li>
    </ul>
</body>
</html>