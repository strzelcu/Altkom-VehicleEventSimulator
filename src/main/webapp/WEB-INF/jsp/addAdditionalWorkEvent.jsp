<%@ page import="ves.model.Car" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title>Dodaj zdarzenie</title>
</head>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Dodaj nowe zdarzenie pracy dodatkowej:</h2>
<form:form method="post" modelAttribute="additionalWorkEvent">
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
                <input name="endAddress" title="Adres ko&#324;cowy" type="text" placeholder="Brak adresu powiela startowy" >
            </td>
        </tr>
        <tr>
        <tr>
            <td>Wybierz rodzaj pracy dodatkowej:</td>
            <td>
                <form:select path="workType" items="${additionalWorkTypeList}"/>
            </td>
        </tr>
        <tr>
            <td>Wpisz wartko&#347;&#263; dodatkow&#261;:</td>
            <td>
                <form:input path="additionalParameter" placeholder="np. temperatura"/>
            </td>
        </tr>
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