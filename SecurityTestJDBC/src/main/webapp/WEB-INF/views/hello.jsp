<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page session="true"%>
<%@ page isELIgnored="false"%>
<html>
<head>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <link href="<c:url value='/resources/bootstrap/bootstrap.min.css' />" type="text/css" rel="stylesheet">
  <script type="text/javascript" src="/resources/bootstrap/bootstrap.min.js"></script>
  <style>
  .carousel-inner > .item > img,
  .carousel-inner > .item > a > img {
      width: 70%;
      margin: auto;
  }
  </style>
</head>
<body>

<div class="container">
  <br>
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
      <li data-target="#myCarousel" data-slide-to="3"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">

      <div class="item active">
        <img src="<c:url value='/resources/images/summer2.jpg'/>" alt="summer" width="460" height="345">
        <div class="carousel-caption">
          <h3>Romania</h3>
          <p>The land of beauty.</p>
        </div>
      </div>

      <div class="item">
        <img src="<c:url value='/resources/images/summer1.jpg'/>" alt="summer" width="460" height="345">
      </div>

      <div class="item">
        <img src="<c:url value='/resources/images/summer4.jpg'/>" alt="summer" width="460" height="345">
      </div>
  	  
  	  <div class="item">
        <img src="<c:url value='/resources/images/summer3.jpg'/>" alt="summer" width="460" height="345">
      </div>
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</div>

<br/><br/>
<a href="showLogin" style="background:#044B85" class="btn btn-primary btn-block" role="button">Come to our World</a>

	
</body>
</html>
