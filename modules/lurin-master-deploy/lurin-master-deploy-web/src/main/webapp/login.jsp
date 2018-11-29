<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">

<head>
  <!-- The first thing in any HTML file should be the charset -->
  <meta charset="utf-8">
  <!-- Make the page mobile compatible -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="mobile-web-app-capable" content="yes">
  <title>Login</title>
  <!-- Latest compiled and minified CSS -->
  <link href="css/bootstrap/bootstrap.min.css" type="text/css" rel="stylesheet" />
  <!-- Optional theme -->
  <link href="css/bootstrap/bootstrap-theme.min.css" type="text/css" rel="stylesheet" />
  <link href="css/styles.css" type="text/css" rel="stylesheet" />
  <script type="text/javascript" src="js/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="js/util.js"></script>
  <script>
    var labelsLanguage = {
        'es': {
          'formHeader': 'Iniciar Sesión',
          'formUserPlaceholder': 'Usuario',
          'formPasswordPlaceholder': 'Contraseña',
          'formOptionSpanish': 'Español',
          'formSelectOptionPortuguese': 'Portugués',
          'formOptionEnglish': 'Ingles',
          'formLabelForgetPassword': 'Olvido su contraseña',
          'formButtonText': 'Ingresar'
        },
        'en': {
          'formHeader': 'Login',
          'formUserPlaceholder': 'User Name',
          'formPasswordPlaceholder': 'Password',
          'formOptionSpanish': 'Spanish',
          'formSelectOptionPortuguese': 'Portuguese',
          'formOptionEnglish': 'English',
          'formLabelForgetPassword': 'Forgot Your Password',
          'formButtonText': 'Sign In'
        },
        'pr': {
          'formHeader': 'Iniciar sessão',
          'formUserPlaceholder': 'Usuário',
          'formPasswordPlaceholder': 'Senha',
          'formOptionSpanish': 'Espanhol',
          'formSelectOptionPortuguese': 'Português',
          'formOptionEnglish': 'Inglês',
          'formLabelForgetPassword': 'Esqueceu sua senha',
          'formButtonText': 'Entrar'
        },
      };
  </script>

</head>

<body>
  <div class="content">
    <div class="row">
      <div class="col-sm-8 col-sm-offset-2 col-xs-7 col-xs-offset-2">
        <img src="img/TelefonicaLogo.png" class="img-fluid mx-auto logo" alt="logo">
      </div>
    </div>
    <br />
    <div class="panel panel-primary">
      <div id="formHeader" class="panel-heading panel-header-bg">Iniciar Sesión</div>
      <div class="panel-body">
        <c:if test="${requestScope['javax.servlet.forward.servlet_path'] == '/j_security_check'}">
         <div class="alert alert-warning" role="alert">
           <strong>Autenticaci&oacute;n Fallida!</strong> Usuario o Contrase&ntilde;a incorrectos.
         </div>
        </c:if>

        <form action="j_security_check" method="post">
          <div class="row">
            <div class="col-sm"><br/>
              <br/></div>
          </div>
          <div class="row">
            <div class="col-sm-10 col-sm-offset-1 col-xs-12 input-group">
              <div class="input-group-addon div-icon-user"></div>
              <input name="j_username" type="text" class="form-control" id="j_username" aria-describedby="j_username" placeholder="Usuario"
              />
            </div>

          </div>
          <div class="row">
            <div class="col-sm"><br/>
              <br/></div>
          </div>
          <div class="row">
            <div class="col-sm-10 col-sm-offset-1 col-xs-12 input-group">
              <div class="input-group-addon div-icon-pwd"></div>
              <input type="password" name="j_password" class="form-control" id="j_password" placeholder="contraseña" />
            </div>
          </div>
          <div class="row">
            <div class="col-sm"><br/>
              <br/></div>
          </div>
          <div class="row">
            <div class="col-sm-10 col-sm-offset-1 rm-ph">
              <select class="form-control" name="j_lang" name="j_lang" onchange="saveLanguageSelected();">
                <option id="esopt" value="es">Español</option>
                <option id="propt" value="pr">Portugués</option>
                <option id="enopt" value="en">Ingles</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-10 col-sm-offset-9 col-xs-offset-8">
              <br/>
              <button id="btnLogin" type="submit" class="btn btn-login btn-primary">Ingresar</button>
            </div>
          </div>

          <div class="form-group">
            <div class="col-sm-11 col-sm-offset-1">
              <br/>
              <a id="lblForgetPass" href="/retrievepassword.jsp">Olvido su contraseña</a>
            </div>
          </div>
          <input type="hidden" name="j_hash" id="j_hash" />
        </form>
      </div>
    </div>
  </div>
  <script>

    $(document).ready(function(){
      var timeData = new Date().getTime();
      $("#j_hash").val(timeData);
      setLangugeComponent();
    });

    function saveLanguageSelected() {
      localStorage.language = $('[name="j_lang"]').val();
      setLangugeComponent();
    }

    function setLangugeComponent() {
      if(typeof localStorage.language !== "undefined" && localStorage.language.length > 0) {
        $('#formHeader').text(labelsLanguage[localStorage.language].formHeader);
        $('[name="j_username"]').attr("placeholder", labelsLanguage[localStorage.language].formUserPlaceholder);
        $('[name="j_password"]').attr("placeholder", labelsLanguage[localStorage.language].formPasswordPlaceholder);
        $('#esopt').text(labelsLanguage[localStorage.language].formOptionSpanish);
        $('#enopt').text(labelsLanguage[localStorage.language].formOptionEnglish);
        $('#propt').text(labelsLanguage[localStorage.language].formSelectOptionPortuguese);
        $('#lblForgetPass').text(labelsLanguage[localStorage.language].formLabelForgetPassword);
        $('#btnLogin').text(labelsLanguage[localStorage.language].formButtonText);
        $('[name="j_lang"]').val(localStorage.language);
      }
    }
  </script>

</body>

</html>