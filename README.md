# Desafio Samba Tech

Este app foi criado para converter videos para o formato da web.

## Requisitos

A aplicação foi desenvolvida sob o Laravel Framework e os requisitos estão disponíveis no link abaixo:

https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-system-requirements.html

## Executar aplicação

Para executar essa aplicação em seu computador, siga os passos abaixo:

1) Clone este repositório, importe-o atráves de IDE e importe as dependências caso sua IDE não faça esse processo automaticamente.

```shell
$ git clone https://github.com/weullerk/DesafioSambaTech
```

2) Crie um banco de dados e configure os dados de conexão no arquivo `.env`

```
DB_CONNECTION=
DB_HOST=
DB_PORT=
DB_DATABASE=
DB_USERNAME=
DB_PASSWORD=
```

```

#### Admin

As rotas desse módulo encontra-se no arquivo `routes/web.php`. A interface administrativa é acessada utilizando o usuário/senha do Active Directory do Sebrae Minas. Para funcionar, será necessário informar no arquivo `.env` uma credencial de consulta no AD:

```
LDAP_USERNAME=
LDAP_PASSWORD=
```

As telas foram desenvolvidas utilizando o [MaterializeCSS](http://materializecss.com/).

Está sendo utilizado o [Laravel Mix](https://laravel.com/docs/5.4/mix) para compilar os assets.
