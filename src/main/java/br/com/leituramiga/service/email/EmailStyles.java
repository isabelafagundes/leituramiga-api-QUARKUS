package br.com.leituramiga.service.email;

public class EmailStyles {


    public static String MODELO_BOAS_VINDAS = """
            <!DOCTYPE html>
                      <html lang="pt-BR">
                      <head>
                          <meta charset="UTF-8">
                          <meta name="viewport" content="width=device-width, initial-scale=1.0">
                          <title>Boas-vindas ao LeiturAmiga!</title>
                          <style>
                              body {
                                  font-family: Arial, sans-serif;
                                  background-color: #f7f7f7;
                                   color: #333333;
                                  margin: 0;
                                  padding: 20px;
                              }
                              .container {
                                  max-width: 600px;
                                  margin: auto;
                                  background: #ffffff;
                                  border-radius: 8px;
                                  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                                  overflow: hidden;
                              }
                              .header {
                                  background-color: #79b791;
                                 color: white;
                                  padding: 10px 20px;
                                  text-align: center;
                              }
                              .content {
                                  padding: 20px;
                                  background: #ffffff;
                              }
                              .footer {
                                  background-color: #f1f1f1;
                                  text-align: center;
                                  padding: 10px 20px;
                                  font-size: 0.9em;
                                  color: #777777;
                              }
                              a {
                                  color: #79b791;
                                  text-decoration: none;
                              }
                          </style>
                      </head>
                      <body>
                          <div class="container">
                              <div class="header">
                                  <h1>Bem-vindo ao LeiturAmiga!</h1>
                              </div>
                              <div class="content">
                                  <p>Olá, {{nomeUsuario}}!</p>
                                  <p>Estamos muito felizes em tê-lo conosco no LeiturAmiga. O nosso aplicativo foi projetado para ajudar você a superar os obstáculos de acesso à leitura e descobrir novos mundos através dos livros.</p>
                                  <p>Se você tiver alguma dúvida ou precisar de ajuda, não hesite em entrar em contato conosco através do nosso <a href="leituramiga.oficial@gmail.com">email de suporte</a>.</p>
                                  <p>Boas leituras!</p>
                              </div>
                              <div class="footer">
                                  <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.</p>
                                  <p>Se você não se inscreveu, pode ignorar este email.</p>
                              </div>
                          </div>
                      </body>
                      </html> 
            """;

    public static String MODELO_CODIGO_SEGURANCA = """
            <!DOCTYPE html>
                      <html lang="pt-BR">
                      <head>
                          <meta charset="UTF-8">
                          <meta name="viewport" content="width=device-width, initial-scale=1.0">
                          <title>Código de Segurança - LeiturAmiga</title>
                          <style>
                              body {
                                  font-family: Arial, sans-serif;
                                  background-color: #f7f7f7;
                                  margin: 0;
                                  padding: 20px;
                              }
                              .container {
                                  max-width: 600px;
                                  margin: auto;
                                  background: #ffffff;
                                  border-radius: 8px;
                                  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                                  overflow: hidden;
                              }
                              .header {
                                  background-color: #79b791;
                                  color: white;
                                  padding: 10px 20px;
                                  text-align: center;
                              }
                              .content {
                                  padding: 20px;
                                  background: #ffffff;
                              }
                              .footer {
                                  background-color: #f1f1f1;
                                  text-align: center;
                                  padding: 10px 20px;
                                  font-size: 0.9em;
                                  color: #777777;
                              }
                              a {
                                  color: #79b791;
                                  text-decoration: none;
                              }
                          </style>
                      </head>
                      <body>
                          <div class="container">
                              <div class="header">
                                  <h1>Código de Segurança - LeiturAmiga</h1>
                              </div>
                              <div class="content">
                                  <p>Olá, {{nomeUsuario}}!</p>
                                  <p>Seu código de segurança é: <strong>{{codigoSeguranca}}</strong></p>
                                  <p>Não compartilhe com ninguém.</p>
                                  <p>Se você não solicitou este código, por favor, ignore este email.</p>
                              </div>
                              <div class="footer">
                                  <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.</p>
                                  <p>Se você não se inscreveu, pode ignorar este email.</p>
                              </div>
                          </div>
                        </body>
                        </html>
            """;

    public static String EMAIL_SOLICITACAO_RECEBIDA = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Solicitação Recebida - LeiturAmiga</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f7f7f7;
                            margin: 0;
                            padding: 20px;
                        }
                        .container {
                            max-width: 600px;
                            margin: auto;
                            background: #ffffff;
                            border-radius: 8px;
                            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                        }
                        .header {
                            background-color: #79b791;
                            color: white;
                            padding: 10px 20px;
                            text-align: center;
                        }
                        .content {
                            padding: 20px;
                            background: #ffffff;
                        }
                        .footer {
                            background-color: #f1f1f1;
                            text-align: center;
                            padding: 10px 20px;
                            font-size: 0.9em;
                            color: #777777;
                        }
                        a {
                            color: #79b791;
                            text-decoration: none;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>Solicitação Recebida - LeiturAmiga</h1>
                        </div>
                        <div class="content">
                            <p>Olá, {{nomeReceptor}}!</p>
                            <p>Você recebeu uma solicitação de {{nomeSolicitante}}. Abaixo estão os detalhes:</p>
                            <ul>
                                <li><strong>Email:</strong> {{emailSolicitante}}</li>
                                <li><strong>Endereço:</strong> {{endereco}}</li>
                                <li><strong>Livros Solicitados: {{livros}}</strong></li>
                                {{dataEntrega}}
                                {{dataDevolucao}}
                            </ul>
                            <p>Acesse o LeiturAmiga para visualizar mais detalhes e escolher entre aceitar e recusar a proposta!</p>
                        </div>
                        <div class="footer">
                            <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.</p>
                        </div>
                    </div>
                </body>
                </html>
""";


    public static String EMAIL_SOLICITACAO_ACEITA = """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Solicitação Aceita - LeiturAmiga</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f7f7f7;
                        margin: 0;
                        padding: 20px;
                    }
                    .container {
                        max-width: 600px;
                        margin: auto;
                        background: #ffffff;
                        border-radius: 8px;
                        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                        overflow: hidden;
                    }
                    .header {
                        background-color: #79b791;
                        color: white;
                        padding: 10px 20px;
                        text-align: center;
                    }
                    .content {
                        padding: 20px;
                        background: #ffffff;
                    }
                    .footer {
                        background-color: #f1f1f1;
                        text-align: center;
                        padding: 10px 20px;
                        font-size: 0.9em;
                        color: #777777;
                    }
                    a {
                        color: #79b791;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Solicitação Aceita - LeiturAmiga</h1>
                    </div>
                    <div class="content">
                        <p>Olá, {{nomeSolicitante}}!</p>
                        <p>Temos o prazer de informar que sua solicitação foi <strong>aceita</strong> por {{nomeReceptor}}.</p>
                        <p>Acesse o LeiturAmiga para mais detalhes!</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.</p>
                    </div>
                </div>
            </body>
            </html>
            """;

    public static String EMAIL_SOLICITACAO_RECUSADA = """
                    <!DOCTYPE html>
                    <html lang="pt-BR">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Solicitação {{status}} - LeiturAmiga</title>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f7f7f7;
                                margin: 0;
                                padding: 20px;
                            }
                            .container {
                                max-width: 600px;
                                margin: auto;
                                background: #ffffff;
                                border-radius: 8px;
                                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                                overflow: hidden;
                            }
                            .header {
                                background-color: #79b791;
                                color: white;
                                padding: 10px 20px;
                                text-align: center;
                            }
                            .content {
                                padding: 20px;
                                background: #ffffff;
                            }
                            .footer {
                                background-color: #f1f1f1;
                                text-align: center;
                                padding: 10px 20px;
                                font-size: 0.9em;
                                color: #777777;
                            }
                            a {
                                color: #79b791;
                                text-decoration: none;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <div class="header">
                                <h1>Solicitação {{status}} - LeiturAmiga</h1>
                            </div>
                            <div class="content">
                                <p>Olá, {{nomeUsuario}}!</p>
                                <p>A solicitação criada por {{nomeSolicitante}} foi <strong>{{status}}</strong>.</p>
                                <p>{{motivo}}</p>
                            </div>
                            <div class="footer">
                                <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.</p>
                            </div>
                        </div>
                    </body>
                    </html>
        """;
}
