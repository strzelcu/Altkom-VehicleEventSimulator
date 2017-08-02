<%@ page import="ves.model.Car" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title>Dodaj zdarzenie</title>
</head>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Dodaj nowe zdarzenie jazdy:</h2>
<form:form method="post" modelAttribute="drivingEvent">
    <table border="0">
        <tr>
            <td>Wybierz pojazd:</td>
            <td>
                <select name="carId">
                <c:forEach varStatus="" var="car" items="${carList}">
                    <option value="<%=((Car) pageContext.getAttribute("car")).getId()%>">${car}</option>
                </c:forEach>
                </select>
            </td>
        </tr>
        <tr>

            <td>Wpisz adres startowy:</td>
            <td>
                <input name="startAddress" title="Adres startowy" type="text" >
            </td>
        </tr>
        <tr>

            <td>Wpisz adres ko&#324;cowy:</td>
            <td>
                <input name="endAddress" title="Adres ko&#324;cowy" type="text" >
            </td>
        </tr>
        <tr>
            <td>Wybierz typ jazdy:</td>
            <td>
                <form:select path="driveType" items="${driveTypeList}"/>
            </td>
        </tr>
        <tr>
            <td>Adresy po&#347;rednie:</td>
            <td>
                <input type="radio" name="loadAddresses" value="true" checked/> Pobierz (Mo&#380;e to d&#322;u&#380;ej potrwa&#263;)<br>
                <input type="radio" name="loadAddresses" value="false"/> Nie pobieraj (Szybkie)
            </td>
        </tr>
        <tr>
            <td>Wpisz dat&#281; pocz&#261;tku:</td>
            <td>
            <form:input path="startDate" placeholder="dd/MM/yyyy gg:hh"/>
        </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Zapisz zdarzenie" />
            </td>
        </tr>

    </table>
</form:form>
    <br>
    <a href="home.do">Wr&oacute;&#263;</a>
</body>
</html>