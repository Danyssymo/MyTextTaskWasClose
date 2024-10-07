<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>
<head>
    <title>Error Free Text</title>
</head>
<body>
    <h2>Input Text</h2>
    <form action="/addNewText" method="post">
        <textarea id="Textarea" name="Textarea" rows="10" cols="50"></textarea>
        <br><br>
        <label for="dropdown">Select lang:</label>
        <select id="dropdown" name="dropdown">
            <option value="RU">ru</option>
            <option value="ENG">en</option>
        </select>
            <label>ID:</label>
            <input type="number" id="id" name="id">
        <br><br>
        <input type="submit" value="Send">
    </form>

    <c:choose>
                <c:when test="${not empty sessionScope.error}">
                    <div style="margin-top: 20px;">
                         <label for="retrievedText">Status: ${sessionScope.fixedText.status}</label>
                         <br>
                         <textarea rows="10" cols="50" readonly>${sessionScope.error}</textarea>
                         </div>
                </c:when>
            <c:when test="${not empty sessionScope.inProcess}">
                <div style="margin-top: 20px;">
                     <label for="retrievedText">Status: ${sessionScope.fixedText.status}</label>
                     <br>
                     <textarea rows="10" cols="50" readonly>${sessionScope.fixedText.fixedValue}</textarea>
                     </div>
            </c:when>
            <c:when test="${not empty sessionScope.fixedText}">
                <div style="margin-top: 20px;">
                    <label for="retrievedText">Fixed Text: №${sessionScope.fixedText.id} Status: ${sessionScope.fixedText.status}</label>
                    <br>
                    <textarea rows="10" cols="50" readonly>${sessionScope.fixedText.fixedValue}</textarea>
                </div>
            </c:when>
        </c:choose>

</body>
</html>