<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title>Edytuj pojazd</title>
</head>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Edytuj pojazd (ID: ${car.id}):</h2>
<form:form method="post">
    <table border="0">
        <tr>
            <td>Wpisz mark&#281; pojazdu:</td>
            <td>
                <input type="text" name="make" value="${car.make}"/>
                <form:errors path="make"/>
            </td>
        </tr>
        <tr>
            <td>Wpisz model pojazdu:</td>
            <td>
                <input type="text" name="carModel" value="${car.model}"/>
                <form:errors path="make"/>
            </td>
        </tr>
        <tr>
            <td>Wpisz numer rejestracyjny pojazdu:</td>
            <td>
                <input type="text" name="registrationNumber" value="${car.registrationNumber}"/>
                <form:errors path="make"/>
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