<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title>Dodaj zdarzenie</title>
</head>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Dodaj nowe zdarzenie:</h2>

<form:form modelAttribute="event">

    <table border="0">
        <tr>
            <td>Wybierz pojazd:</td>
            <td>
                <form:select path="car" items="${cars}" />
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Zapisz zdarzenie" />
            </td>
        </tr>

    </table>

    <br>
    <a href="home.do">Wr&oacute;&#263;</a>
</form:form>

</body>
</html>
