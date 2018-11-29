<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>React Starterify</title>

    <!-- build:css -->
    <link rel="stylesheet" href="dist/styles/main.css">
    <!-- endbuild -->
  </head>
  <body>
    <div>TOKEN: <c:out value="${sessionScope.TOKEN}"/></div>
    <div id="content"></div>

    <!-- build:js -->
    <script src="dist/js/app.js"></script>
    <!-- endbuild -->
  </body>
</html>
