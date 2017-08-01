<%@ page import="ves.model.Event" %>
<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<title>Lista zdarze&#324;</title>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Lista zdarze&#324;</h2>
<c:if test="${eventsList.size() > 0}">
<table border="1">
    <tr>
        <td>ID zdarzenia</td>
        <td>Rodzaj zdarzenia</td>
        <td>Czas rozpoczecia</td>
        <td>Czas zakonczenia</td>
        <td>Pojazd</td>
        <td>Adres startowy</td>
        <td>Adres koncowy</td>
        <td>Akcja</td>
    </tr>
    <c:forEach var="event" items="${eventsList}">
        <tr>
            <td><%=((Event) pageContext.getAttribute("event")).getId()%></td>
            <td><%=((Event) pageContext.getAttribute("event")).getClass().getSimpleName()%></td>
            <td><%=((Event) pageContext.getAttribute("event")).getStartDate()%></td>
            <td><%=((Event) pageContext.getAttribute("event")).getEndDate()%></td>
            <td><%=((Event) pageContext.getAttribute("event")).getCar().toString()%></td>
            <td><%=((Event) pageContext.getAttribute("event")).getStartPoint().getAddress().toString()%></td>
            <td><%=((Event) pageContext.getAttribute("event")).getEndPoint().getAddress().toString()%></td>
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
