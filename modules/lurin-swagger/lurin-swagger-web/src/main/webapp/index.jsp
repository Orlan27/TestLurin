<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
  <title>Swagger UI</title>
  <link href='https://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet' type='text/css'/>
  <link href='css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='css/reset.css' media='print' rel='stylesheet' type='text/css'/>
  <link href='css/screen.css' media='print' rel='stylesheet' type='text/css'/>
  <script type="text/javascript" src="lib/shred.bundle.js"></script>
  <script src='lib/jquery-1.8.0.min.js' type='text/javascript'></script>
  <script src='lib/jquery.slideto.min.js' type='text/javascript'></script>
  <script src='lib/jquery.wiggle.min.js' type='text/javascript'></script>
  <script src='lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
  <script src='lib/handlebars-1.0.0.js' type='text/javascript'></script>
  <script src='lib/underscore-min.js' type='text/javascript'></script>
  <script src='lib/backbone-min.js' type='text/javascript'></script>
  <script src='lib/swagger.js' type='text/javascript'></script>
  <script src='swagger-ui.js' type='text/javascript'></script>
  <script src='lib/highlight.7.3.pack.js' type='text/javascript'></script>

  <!-- enabling this will enable oauth2 implicit scope support -->
  <script src='lib/swagger-oauth.js' type='text/javascript'></script>

</head>

<body class="swagger-section">
<div id='header'>
  <div class="swagger-ui-wrap">
    <a id="logo" href="swagger/">swagger</a>
  </div>
</div>


<!--<div id="message-bar" class="swagger-ui-wrap">&nbsp;</div>-->
<div id="swagger-ui-container" class="swagger-ui-wrap">
  <h1>Documentation of REST APIs of the LURIN Project</h1>
          <%
              String file = application.getRealPath("swagger-ui");

              File f = new File(file);
              String [] fileNames = f.list();
              File [] fileObjects= f.listFiles();
          %>
          <ul>
          <%
              for (int i = 0; i < fileObjects.length; i++) {
                  if(fileObjects[i].isDirectory()){
          %>
          <li>
            Documentation of <%= fileNames[i] %>
            <a href="/swagger/swagger-dist/index.html?url=/swagger/swagger-ui/<%= fileNames[i] %>/swagger.json">
                <img src="images/logo_small.png" />
            </a>
            &nbsp;&nbsp;&nbsp;
            <a href="/swagger/html/<%= fileNames[i] %>/document.html">
                <img src="images/html.png" />
            </a>
          </li>
          <%
                  }
              }
          %>
          </ul>

</div>
</body>
</html>
