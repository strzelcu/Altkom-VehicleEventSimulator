<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title>Edytuj pojazd</title>
</head>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Edytuj pojazd (Id: ${carId}):</h2>
<form:form modelAttribute="car" method="post">
    <table border="0">
        <tr>
            <td>Wpisz mark&#281; pojazdu:</td>
            <td>
                <form:input path="make" value="${car.make}"/>
                <form:errors path="make"/>
            </td>
        </tr>
        <tr>
            <td>Wpisz model pojazdu:</td>
            <td>
                <form:input path="model" value="${car.model}"/>
                <form:errors path="model"/>
            </td>
        </tr>
        <tr>
            <td>Wpisz numer rejestracyjny pojazdu:</td>
            <td>
                <form:input path="registrationNumber" value="${car.registrationNumber}"/>
                <form:errors path="registrationNumber"/>
            </td>
        </tr>
        <tr>
            <td>Wybierz typ pojazdu:</td>
            <td>
                <select name="type" >
                    <c:forEach var="type" items="${carTypes}">
                        <option<c:if test="${car.type.equals(type)}"> selected</c:if>>${type}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Zapisz pojazd"/>
            </td>
        </tr>

    </table>
    <br>
    <a href="showCars.do">Anuluj</a>
</form:form>
</body>
</html>