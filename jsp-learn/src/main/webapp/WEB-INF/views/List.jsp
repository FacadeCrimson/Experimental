
<!DOCTYPE HTML>
<html>
<head>
    <title>${title} List</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style>
        <%@include file="/WEB-INF/views/List.css"%>
    </style>
</head>
<body>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <h1>${title} List</h1>
    <c:forEach items="${list}" var="item">
    <div class="item">${item}</div>
    </c:forEach>
</body>
</html>
