<%@ page import="ves.model.Car" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title>Dodaj zdarzenie</title>
</head>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Dodaj nowe zdarzenie postojowe:</h2>
<form:form method="post" modelAttribute="parkingEvent">
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
            <td>Wpisz adres zdarzenia:</td>
            <td>
                <input name="startAddress" title="Adres zdarzenia" type="text" >
            </td>
        </tr>
        <tr>
        <tr>
            <td>Wybierz rodzaj postoju:</td>
            <td>
                <form:select path="parkingEventType" items="${parkingTypeList}"/>
            </td>
        </tr>
        <tr>
            <td>Wpisz dat&#281; pocz&#261;tku:</td>
            <td>
                <form:input path="startDate" placeholder="dd/MM/yyyy gg:hh"/>
            </td>
        </tr>
        <tr>
            <td>Wpisz dat&#281; ko&#324;ca:</td>
            <td>
                <form:input path="endDate" placeholder="dd/MM/yyyy gg:hh"/>
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
