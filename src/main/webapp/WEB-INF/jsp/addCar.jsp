<%@ include file="/WEB-INF/jsp/includes/common.jsp" %>
<html>
<head>
    <title>Dodaj nowy pojazd</title>
</head>
<body>
<h1>Vehicle Event Simulator 1.0</h1>
<h2>Dodaj nowy pojazd:</h2>

    <form:form modelAttribute="car">

        <table border="0">
            <tr>
                <td>Wpisz mark&#281; pojazdu:</td>
                <td>
                    <form:input path="make"/>
                    <form:errors path="make"/>
                </td>
            </tr>
            <tr>
                <td>Wpisz model pojazdu:</td>
                <td>
                    <form:input path="model"/>
                    <form:errors path="model"/>
                </td>
            </tr>
            <tr>
                <td>Wpisz numer rejestracyjny pojazdu:</td>
                <td>
                    <form:input path="registrationNumber"/>
                    <form:errors path="registrationNumber"/>
                </td>
            </tr>
            <tr>
                <td>Wybierz typ pojazdu:</td>
                <td>
                    <form:select path="type" items="${carTypes}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Zapisz pojazd" colspan="2" />
                </td>
            </tr>

        </table>

        <br>
        <a href="home.do">Wr&oacute;&#263;</a>

    </form:form>
</body>
</html>
