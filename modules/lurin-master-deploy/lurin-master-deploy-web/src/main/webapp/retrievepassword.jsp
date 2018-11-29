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
  <title>Retrieve Password</title>
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
          'formHeader': 'Recuperar Contraseña',
          'formEmailPlaceholder': 'correo@dominio.com',
          'formlabel': 'Ingresa tu correo de recuperación',
          'messageInvalid': '¡Correo inválido! no se tiene ningún registro para el correo proporcionado',
          'messageValid': '¡Correo enviado! La contraseña se ha enviado a su correo',
          'messageLdap': '¡Advertencia! No se puede recuperar contraseña, usuario esta en LDAP',
          'formLabelBackLogin': 'Regresar al inicio de sesión',
          'formButtonText': 'Enviar'
        },
        'en': {
          'formHeader': 'Recover Password',
          'formEmailPlaceholder': 'email@domain.com',
          'formlabel': 'Enter your recovery email',
          'messageInvalid': 'Invalid email! there is not record for provided email',
          'messageValid': 'Email Sent! The password has been sent to your email',
          'messageLdap': 'Warning! Unable to recover password, user is in LDAP',
          'formLabelBackLogin': 'Back to login',
          'formButtonText': 'Send'
        },
        'pr': {
          'formHeader': 'Recuperar Senha',
          'formEmailPlaceholder': 'email@dominio.com',
          'formlabel': 'Digite seu e-mail de recuperação',
          'messageInvalid': 'Email inválido! não há registro para o e-mail fornecido',
          'messageValid': 'E-mail enviado! A senha foi enviada para o seu email',
          'messageLdap': 'Aviso! Não é possível recuperar a senha, o usuário está no LDAP',
          'formLabelBackLogin': 'Retornar ao login',
          'formButtonText': 'Enviar'
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
      <div id="formHeader" class="panel-heading panel-header-bg">Recuperar Contraseña</div>
      <div class="panel-body">
         <div id="alert" class="alert alert-warning" role="alert"></div>

        <form action="recoverpass" method="post">
            <input type="hidden" value="${code}" id="inpCode"/>
            <div class="row">
                <div class="col-sm"><br/>
                  <br/></div>
              </div>
              <div class="row">
                <div class="col-sm-10 col-xs-12">
                  <strong id="labelPwd">Ingresa tu correo de recuperación</strong>
              </div>
          <div class="row">
            <div class="col-sm"><br/>
              <br/></div>
          </div>
          <div class="row">
            <div class="col-sm-10 col-sm-offset-1 col-xs-12 input-group">
              <input name="email" type="text" class="form-control" id="email" aria-describedby="email" placeholder="correo@dominio.com"/>
          </div>
          <div class="row">
            <div class="col-sm"><br/>
              <br/></div>
          </div>
          <div class="form-group">
            <div class="col-sm-10 col-sm-offset-9 col-xs-offset-8">
              <br/>
              <button id="btnSend" type="submit" class="btn btn-login btn-primary">Enviar</button>
            </div>
          </div>

          <div class="form-group">
            <div class="col-sm-11 col-sm-offset-1">
              <br/>
              <a id="lblBackLogin" href="/login.jsp">Regresar al inicio de sesión</a>
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
      $('#alert').hide();
      $("#j_hash").val(timeData);
      setLangugeComponent();
    });

    function setLangugeComponent() {
      if(typeof localStorage.language !== "undefined" && localStorage.language.length > 0) {
        var code = $('#inpCode').val();
        $('#formHeader').text(labelsLanguage[localStorage.language].formHeader);
        $('#labelPwd').text(labelsLanguage[localStorage.language].formlabel);
        $('[name="email"]').attr("placeholder", labelsLanguage[localStorage.language].formEmailPlaceholder);
        $('#lblBackLogin').text(labelsLanguage[localStorage.language].formLabelBackLogin);
        $('#btnSend').text(labelsLanguage[localStorage.language].formButtonText);
        
        if(code && code == "001") {
          $('#alert').show();
          $('#alert').text(labelsLanguage[localStorage.language].messageValid);
        }

        if(code && code == "002") {
          $('#alert').show();
          $('#alert').text(labelsLanguage[localStorage.language].messageInvalid);
        }

        if(code && code == "003") {
          $('#alert').show();
          $('#alert').text(labelsLanguage[localStorage.language].messageLdap);
        }
      }
    }
  </script>

</body>

</html>