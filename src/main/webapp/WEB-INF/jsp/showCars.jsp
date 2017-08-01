<%@ page import="ves.model.Car" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<title>Lista pojazd&#243;w</title>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Lista pojazd&#243;w</h2>
<c:if test="${carList.size() > 0}">
<h3>
    <font color="red">
        Usuni&#281;cie pojazdu usuwa r&#243;wnie&#380; wszystkie przypisane do niego zdarzenia
    </font>
</h3>

<table border="1">
    <tr>
        <td>ID Pojazdu</td>
        <td>Marka</td>
        <td>Model</td>
        <td>Nr rejestracyjny</td>
        <td>Typ</td>
        <td>Akcja</td>
    </tr>
<c:forEach var="car" items="${carList}">
        <tr>
            <td><%=((Car) pageContext.getAttribute("car")).getId()%></td>
            <td><%=((Car) pageContext.getAttribute("car")).getMake()%></td>
            <td><%=((Car) pageContext.getAttribute("car")).getModel()%></td>
            <td><%=((Car) pageContext.getAttribute("car")).getRegistrationNumber()%></td>
            <td><%=((Car) pageContext.getAttribute("car")).getType()%></td>
            <td>
                <form method="post">
                <button type="submit"
                        name="deleteCar"
                        value="<%=((Car) pageContext.getAttribute("car")).getId()%>">
                    Usu&#324
                </button>
                </form>
                <form method="post">
                    <button type="submit"
                            name="editCar"
                            value="<%=((Car) pageContext.getAttribute("car")).getId()%>">
                        Edytuj
                    </button>
                </form>
            </td>
        </tr>
</c:forEach>
    </c:if>
    <c:if test="${carList.size() == 0}">
        Brak element&#243;w do wy&#347;wietlenia<br>
    </c:if>
</table>
<br>
<a href="home.do">Wr&oacute;&#263;</a>
</body>
</html>