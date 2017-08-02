<%@ page import="ves.model.Event" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<title>Lista zdarze&#324;</title>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Lista zdarze&#324;</h2>
<c:if test="${eventsList.size() > 0}">
<a href="home.do">Wr&oacute;&#263;</a>
<br><br>
<table border="1">
    <tr>
        <td>ID zdarzenia</td>
        <td>Rodzaj zdarzenia</td>
        <td>Czas rozpocz&#281;cia</td>
        <td>Czas zako&#324;czenia</td>
        <td>Pojazd</td>
        <td>Adres startowy</td>
        <td>Adres ko&#324;cowy</td>
        <td>Akcja</td>
    </tr>
    <c:forEach var="event" items="${eventsList}">
        <tr>
            <td>${event.id}</td>
            <td><%=((Event) pageContext.getAttribute("event")).getClass().getSimpleName()%></td>
            <td>${event.startDate.toLocaleString()}</td>
            <td>${event.endDate.toLocaleString()}</td>
            <td>${event.car}</td>
            <td>${event.startPoint.address.toString()}</td>
            <td>${event.endPoint.address.toString()}</td>
            <td>
                <form method="post">
                    <button type="submit"
                            name="deleteEvent"
                            value="<%=((Event) pageContext.getAttribute("event")).getId()%>">
                        Usu&#324;
                    </button>
                </form>
                <form method="post">
                    <button type="submit"
                            name="getJson"
                            value="<%=((Event) pageContext.getAttribute("event")).getId()%>">
                        Pobierz JSON
                    </button>
                </form>
                <form method="post">
                    <button type="submit"
                            name="showOnMap"
                            value="<%=((Event) pageContext.getAttribute("event")).getId()%>">
                        Poka&#380; na mapie
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</c:if>
<c:if test="${eventsList.size() == 0}">
    Brak element&#243;w do wy&#347;wietlenia<br>
</c:if>
<br>
<a href="home.do">Wr&oacute;&#263;</a>
</body>
</html>
