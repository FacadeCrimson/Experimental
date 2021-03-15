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
    <h1>Venue Registration</h1>
    <div class="form">
    <form action="venue" method="post">
    <div class="item">
        <label>Name</label>
        <input type="text" name="name"/>
    </div>
    <div class="item">
        <label>Address</label>
        <input type="text" name="address"/>
    </div>
    <div class="item">
        <label>City</label>
        <input type="text" name="city"/>
    </div>
    <div class="item">
        <label>State</label>
        <input type="text" name="state"/>
    </div>
    <div class="item">
        <label>Phone</label>
        <input type="text" name="phone"/>
    </div>
    <div class="item">
        <label>Website</label>
        <input type="text" name="website"/>
    </div>
    <div class="item">
        <label>Genres</label>
        <input type="text" name="genres"/>
    </div>
    
    <input id="submit" type="submit" value="Submit"/>
    </form>
    </div>
</body>
</html>
