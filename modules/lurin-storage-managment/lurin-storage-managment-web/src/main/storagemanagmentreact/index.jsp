<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Global Management</title>

    <!-- build:css -->
    <link rel="stylesheet" href="storagemanagment/styles/main.css">
      <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="storagemanagment/styles/bootstrap.min.css">
  
    <!-- Optional theme -->
    <link rel="stylesheet" href="storagemanagment/styles/bootstrap-theme.min.css">
	<link rel="stylesheet" href="storagemanagment/styles/react-datepicker.css">
	<link rel="stylesheet" href="storagemanagment/styles/react-checkbox-tree.css">
	<link rel="stylesheet" href="storagemanagment/styles/rc-menu.css">
    <link rel="stylesheet" href="storagemanagment/styles/react-select.min.css">
    <link rel="stylesheet" href="storagemanagment/styles/rc-color-picker.css">
    <!-- endbuild -->
  </head>
  <script type="text/javascript">
    var currentUserSession = '${pageContext.request.userPrincipal.name}';
    var currentMenuSession = '${sessionScope.MENU}';
    var currentToken = 'Bearer ${sessionScope.TOKEN}';
    var currentCarriers =  JSON.parse('${sessionScope.CARRIES_BY_USER}');
    var currentLanguage = '${sessionScope.I18N_APP}';
    var currentTheme = JSON.parse('${sessionScope.THEME}');
    var currentApiUrl = '${sessionScope.URL_REST_SERVICES}';
    var currentCompanyId = '${sessionScope.COMPANY_ID}';
  </script>
  <body>
    <div id="content"></div>

    <!-- build:js -->
     <script src="storagemanagment/js/app.js"></script>
    <!-- endbuild -->
  </body>
</html>
