<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Freeview</title>

    <!-- build:css -->
    <link rel="stylesheet" href="freeview/styles/main.css">
      <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="freeview/styles/bootstrap.min.css">
  
    <!-- Optional theme -->
    <link rel="stylesheet" href="freeview/styles/bootstrap-theme.min.css">
	<link rel="stylesheet" href="freeview/styles/rc-menu.css">
	<link rel="stylesheet" href="freeview/styles/react-datepicker.css">
	<link rel="stylesheet" href="freeview/styles/toggle.css">
    <link rel="stylesheet" href="freeview/styles/react-select.min.css">
    
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
     <script src="freeview/js/app.js"></script>
    <!-- endbuild -->
  </body>
</html>
