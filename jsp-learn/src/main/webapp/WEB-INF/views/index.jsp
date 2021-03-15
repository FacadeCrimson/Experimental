<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Welcome</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style>
        <%@include file="/WEB-INF/views/Index.css"%>
    </style>
</head>
<body>
    <div class="out">
    <div class="block">
        <div class="title">List</div>
        <div class="name"><a href="/artistlist">Artist</a></div>
        <div class="name"><a href="/venuelist">Venue</a></div>  
        <div class="name"><a href="/showlist">Show</a></div>  
    </div>
    <div class="block">
        <div class="title">Registration</div>
        <div class="name"><a href="/artistregister">Artist</a></div>
        <div class="name"><a href="/venueregister">Venue</a></div>  
        <div class="name"><a href="/showregister">Show</a></div> 
    </div>
</div>
</body>
</html>
