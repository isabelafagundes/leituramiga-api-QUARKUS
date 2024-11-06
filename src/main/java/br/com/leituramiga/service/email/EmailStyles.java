package br.com.leituramiga.service.email;

public class EmailStyles {


    public static String MODELO_BOAS_VINDAS = """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Bem-vindo ao LeiturAmiga!</title>
                <style>
                    body {
                        margin: 0;
                        font-family: Arial, sans-serif;
                        text-align: center;
                        background-color: #333333;
                        color: #333;
                    }
                        
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: #ffffff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
                    }
                        
                    .header {
                        background-image: url('cid:logo_leituramiga.svg');
                        background-size: cover;
                        color: #fff;
                        padding: 50px 20px;
                        font-size: 24px;
                        font-weight: bold;
                        position: relative;
                    }
                        
                    .header-link {
                        text-decoration: none;
                        color: inherit;
                    }
                        
                    .header h2 {
                        font-size: 50px;
                        margin: 10px 0 0;
                    }
                        
                    .header p {
                        background-color: transparent;
                        font-size: 14px;
                        opacity: 0.25;
                    }
                        
                    .content {
                        padding: 20px;
                    }
                        
                    .content a {
                        color: #79b791;
                    }
                        
                    .content h2 {
                        font-size: 22px;
                        color: #333;
                    }
                        
                    .content p {
                        font-size: 16px;
                        color: #555;
                        margin: 10px 0;
                    }
                        
                    .footer {
                        padding: 10px;
                        background: #555555;
                        font-size: 14px;
                        color: #666;
                    }
                        
                    .footer h3 {
                        color: #79b791;
                        text-decoration: none;
                    }
                        
                    .footer p{
                        color: white;
                        opacity: 0.2;
                    }
                </style>
            </head>
            <body>
            <div class="container">
                <!--depois alterar o link-->
                <a href="https://github.com/KauaTGuedes" target="_blank" class="header-link">
                    <div class="header">
                        <h2>LeiturAmiga</h2>
                        <p>(Clique aqui para voltar ao site)</p>
                    </div>
                </a>
                <div class="content">
                    <h2>Olá, {{nomeUsuario}}!</h2>
                    <p>Estamos muito felizes em tê-lo conosco no LeiturAmiga. O nosso aplicativo foi projetado para ajudar você a
                        superar os obstáculos de acesso à leitura e descobrir novos mundos através dos livros.</p>
                    <p>Se você tiver alguma dúvida ou precisar de ajuda, não hesite em entrar em contato conosco através do nosso <a
                            href="mailto:leituramiga.oficial@gmail.com">email de suporte</a>.</p>
                </div>
                <div class="footer">
                    <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.<br>Se você não se inscreveu, pode ignorar este email.
                    </p>
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
                        <meta name="viewport" content="width=device-width, initial-scale=1">
                        <title>Código de Segurança - LeiturAmiga</title>
                        <style>
                            body {
                                margin: 0;
                                font-family: Arial, sans-serif;
                                text-align: center;
                                background-color: #333333;
                                color: #333;
                            }
                            .container {
                                max-width: 600px;
                                margin: 20px auto;
                                background-color: #ffffff;
                                border-radius: 8px;
                                overflow: hidden;
                                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
                            }
                            .header {
                                background-image: url('./resources/img/logo_leituramiga.svg);
                                background-size: cover;
                                color: #fff;
                                padding: 50px 20px;
                                font-size: 24px;
                                font-weight: bold;
                                position: relative;
                            }
                            .header h2 {
                                font-size: 50px;
                                margin: 10px 0 0;
                            }
                            .header p {
                                background-color: transparent;
                                font-size: 14px;
                                opacity: 0.25;
                            }
                            .content {
                                padding: 20px;
                            }
                            .content img {
                                padding: 5px;
                                max-width: 100%;
                                height: auto;
                                width: 50%;
                            }
                            .content a {
                                color: #79b791;
                            }
                            .content h2 {
                                font-size: 22px;
                                color: #333;
                            }
                            .content p {
                                font-size: 14px;
                                color: #555;
                                margin: 10px 0;
                            }
                            .codigo-seguranca {
                                display: inline-block;
                                padding: 10px;
                                border: 2px solid #333333;
                                border-radius: 5px;
                                background-color: #666666;
                                font-size: 24px;
                                color: #333;
                                font-weight: bold;
                            }
                            .footer {
                                padding: 10px;
                                background: #555555;
                                font-size: 14px;
                                color: #666;
                            }
                            .footer h3 {
                                color: #79b791;
                                text-decoration: none;
                            }
                            .footer p {
                                color: white;
                                opacity: 0.2;
                            }
                        </style>
                    </head>
                    <body>
                    <div class="container">
                        <div class="header">
                        </div>
                        <div class="content">
                            <img src="./resources/img/icon_seguranca.svg" alt="icon de segurança">
                            <h2>Olá, {{nomeUsuario}}!</h2>
                            <p>Seu código de segurança:</p>
                            <h3 class="codigo-seguranca">{{codigoSeguranca}}</h3>
                            <p>Não compartilhe com ninguém.
                                <br><br>
                                Se você não solicitou este código, por favor, ignore este email! Obrigado pela atenção.
                            </p>
                        </div>
                        <div class="footer">
                            <h3>LeiturAmiga</h3>
                            <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.<br>Se você não se inscreveu, pode ignorar este email.</p>
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
                        margin: 0;
                        font-family: Arial, sans-serif;
                        text-align: center;
                        background-color: #333333;
                        color: #333;
                    }
                        
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: #ffffff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
                    }
                        
                    .header {
                        background-image: url('./resources/img/logo_leituramiga.svg');
                        background-size: cover;
                        color: #fff;
                        padding: 50px 20px;
                        font-size: 24px;
                        font-weight: bold;
                        position: relative;
                    }
                        
                    .header h2 {
                        font-size: 50px;
                        margin: 10px 0 0;
                    }
                        
                    .header p {
                        background-color: transparent;
                        font-size: 14px;
                        opacity: 0.25;
                    }
                        
                    .content {
                        padding: 20px;
                    }
                        
                    .content img {
                        padding: 5px;
                        max-width: 100%;
                        height: auto;
                        width: 50%
                    }
                        
                    .content a {
                        color: #79b791;
                    }
                        
                    .content h2 {
                        font-size: 22px;
                        color: #333;
                    }
                        
                    .content p {
                        font-size: 14px;
                        color: #555;
                        margin: 10px 0;
                    }
                    .details-screen{
                        background-color: #79b791;
                        border: 1px solid #ddd;
                        border-radius: 8px;
                        padding: 15px;
                        margin: 20px 0;
                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                    }
                    ul {
                        list-style-type: none;
                        padding: 0;
                    }
                    ul li {
                        margin: 10px 0;
                        background-color: #f9f9f9;
                        padding: 10px;
                        border-radius: 5px;
                        text-align: left;
                    }
                    ul li strong {
                        color: #333;
                        display: inline-block;
                        width: 120px;
                    }
                        
                    .footer {
                        padding: 10px;
                        background: #555555;
                        font-size: 14px;
                        color: #666;
                    }
                    .footer h3 {
                        color: #79b791;
                        text-decoration: none;
                    }
                    .footer p{
                        color: white;
                        opacity: 0.2;
                    }
                </style>
            </head>
            <body>
            <<div class="container">
                <div class="header">
                </div>
                <div class="content">
                    <img src="./resources/img/solicitacao_icon.svg" alt="icone de segurança">
                    <h2>Olá, {{nomeUsuario}}!</h2>
                    <p>Você recebeu uma solicitação de {{nomeSolicitante}}. Logo abaixo estão os detalhes:</p>
                    <div class="details-screen">
                    <ul>
                        <li><strong>Email:</strong> {{emailSolicitante}}</li>
                        <li><strong>Endereço:</strong> {{endereco}}</li>
                        <li><strong>Livros Solicitados:</strong> {{livros}}</li>
                        <li><strong>Data de entrega:</strong> {{dataEntrega}}</li>
                        <li><strong>Data de devolução:</strong> {{dataDevolucao}}</li>
                        <li><strong>informações adicionais:</strong> {{informacoesAdicionais}}</li>
                    </ul>
                    </div>
                    <br>
                    <p>Acesse a plataforma LeiturAmiga para obter mais detalhes, e seja deseja recusar ou aceitar a proposta.</p>
                </div>
                <br>
                <div class="footer">
                    <h3>LeiturAmiga</h3>
                    <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.<br>Se você não se inscreveu, pode ignorar este email.</p>
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
                               margin: 0;
                               font-family: Arial, sans-serif;
                               text-align: center;
                               background-color: #333333;
                               color: #333;
                           }
                        
                           .container {
                               max-width: 600px;
                               margin: 20px auto;
                               background-color: #ffffff;
                               border-radius: 8px;
                               overflow: hidden;
                               box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
                           }
                        
                           .header {
                               background-image: url('./resources/img/logo_leituramiga.svg');
                               background-size: cover;
                               color: #fff;
                               padding: 50px 20px;
                               font-size: 24px;
                               font-weight: bold;
                               position: relative;
                           }
                        
                           .header p {
                               background-color: transparent;
                               font-size: 14px;
                               opacity: 0.25;
                           }
                        
                           .content {
                               padding: 20px;
                           }
                        
                           .content img {
                               padding: 5px;
                               max-width: 100%;
                               height: auto;
                               width: 50%;
                           }
                        
                           .content a {
                               color: #79b791;
                           }
                        
                           .content h1 {
                               font-size: 28px;
                               margin: 20px 0;
                           }
                        
                           .content p {
                               font-size: 14px;
                               color: #555;
                               margin: 10px 0;
                           }
                        
                           .content strong {
                               color: #79b791;
                           }
                        
                           .footer {
                               padding: 10px;
                               background: #555555;
                               font-size: 14px;
                               color: #666;
                               text-align: center;
                           }            
                           .footer h3 {
                               color: #79b791;
                               text-decoration: none;
                           }
                        
                           .footer p {
                               color: white;
                               opacity: 0.2;
                           }
                       </style>
                   </head>
                   <body>
                   <div class="container">
                       <div class="header">
                       </div>
                       <div class="content">
                           <img src="./resources/img/aceito_icon.svg" alt="icone de aceito">
                           <h1>Solicitação Aceita - LeiturAmiga</h1>
                           <p>Olá, <strong>{{nomeSolicitante}}!</strong></p>
                           <p>Temos o prazer de informar que sua solicitação foi <strong>aceita</strong> por <strong>{{nomeReceptor}}</strong>.</p>
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
                                            background-image: url('./resources/img/logo_leituramiga.svg');
                                            background-size: cover;
                                            background-position: center;
                                            padding: 50px;
                                            text-align: center;
                                            color: white;
                                        }
                                        .content h1 strong {
                                            margin: 0;
                                            color: #79b791;
                                            font-size: 24px;
                                        }
                                        .content {
                                            padding: 20px;
                                            background: #ffffff;
                                            text-align: center;
                                        }
                                        .content img {
                                            max-width: 200px;
                                        }
                                        .content strong {
                                            color: #79b791;
                                        }
                                        .details-screen {
                                            background-color: #79b791;
                                            border: 1px solid #ddd;
                                            border-radius: 8px;
                                            padding: 15px;
                                            margin: 20px 0;
                                            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                                            color: #fff;
                                        }
                                        .footer {
                                            padding: 10px;
                                            background: #555555;
                                            font-size: 14px;
                                            color: #666;
                                            text-align: center;
                                        }
                                        .footer h3 {
                                            color: #79b791;
                                            margin: 0;
                                        }
                                        .footer p {
                                            color: white;
                                            opacity: 0.5;
                                            margin: 5px 0 0;
                                        }
                                    </style>
                                </head>
                                <body>
                                <div class="container">
                                    <div class="header">
                                    </div>
                                    <div class="content">
                                        <h1>Solicitação <strong>{{status}}</strong> - LeiturAmiga</h1>
                                        <img src="./resources/img/recusado_icon.svg" alt="icon recusada">
                                        <p>Olá, <strong>{{nomeUsuario}}</strong>!</p>
                                        <p>A solicitação criada por {{nomeSolicitante}} foi <strong>{{status}}</strong>.</p>
                                        <div class="details-screen">
                                            <p>{{motivo}}</p>
                                        </div>
                                    </div>
                                    <div class="footer">
                                        <h3>LeiturAmiga</h3>
                                        <p>&copy; 2024 LeiturAmiga. Todos os direitos reservados.</p>
                                    </div>
            </div>
            </body>
            /html>
            """;
}
