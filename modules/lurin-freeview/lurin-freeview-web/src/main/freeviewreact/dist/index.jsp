<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Freeview</title>

    <link rel="stylesheet" href="styles/bootstrap.min.css">
    <link rel="stylesheet" href="styles/bootstrap-theme.min.css">
    <link rel="stylesheet" href="styles/main.css">
    <link rel="stylesheet" href="styles/rc-menu.css">
    <link rel="stylesheet" href="styles/react-select.min.css">
    <link rel="stylesheet" href="styles/react-datepicker.css">
    <link rel="stylesheet" href="styles/toggle.css">
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

    <script src="js/app.js"></script>
  </body>
</html>
