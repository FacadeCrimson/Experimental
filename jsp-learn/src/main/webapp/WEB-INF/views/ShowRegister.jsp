<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Register an Artist</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style>
        <%@include file="/WEB-INF/views/Form.css"%>
    </style>
</head>
<body>
    <h1>Show Registration</h1>
    <div class="form">
    <form action="show" method="post">
    <div class="item">
        <label>Artist</label>
        <input type="text" name="artist"/>
    </div>
    <div class="item">
        <label>Venue</label>
        <input type="text" name="venue"/>
    </div>
    <div class="item">
        <label>Time</label>
        <input type="text" name="time"/>
    </div>
    
    <input id="submit" type="submit" value="Submit"/>
    </form>
    </div>
</body>
</html>
