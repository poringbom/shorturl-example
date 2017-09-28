# shorturl-example
angular2 (client) + spring boot (backend)

Folder BackendServer is a backend application<br/>

Spring boot Server Run by maven: <br/>
 1. cd {directory}/BackendServer
 2. mvn clean install
 3. mvn spring-boot:run

  - H2 database (inmemory database) http://localhost:8080/h2 for view db
      <br/>url=jdbc:h2:file:~/database
      <br/>username=sa
      <br/>password=

  - spring web API
  
     1. signup user http://localhost:8080/api/v1/user
     Request: 
      curl -X POST \
      http://localhost:8080/api/v1/user \
      -H 'content-type: application/json' \
      -d '{"username": "admin", "password": "1234"}'
     Response: 
       {
        "code": "000",
        "message": "create suceess",
        "username": "admin"
       }
       
      2. login http://localhost:8080/api/v1/login (secretKey = base64(username+password))
      Request: 
        curl -X POST \
        http://localhost:8080/api/v1/login \
        -H 'content-type: application/json' \
        -d '{"username": "admin", "password": "1234"}'
      Response: 
       {
          "code": "000",
          "message": "login success",
          "username": admin,
          "secretKey": "YWRtaW4xMjM0"
       }
       
       3. get static uri http://localhost:8080/api/v1/uri?page=0&size=10
        Request: 
          curl -X GET \
          http://localhost:8080/api/v1/uri?page=0&size=10 \
          -H 'username: admin' \
          -H 'secretKey: YWRtaW4xMjM0' \
        Response: 
         {
            "content": [],
            "totalElements": 0,
            "totalPages": 0,
            "last": true,
            "size": 10,
            "number": 1,
            "sort": null,
            "first": false,
            "numberOfElements": 0
          }
          
         4. create short url  http://localhost:8080/api/v1/uri
         Request: 
          curl -X POST \
          http://localhost:8080/api/v1/uri \
          -H 'content-type: application/json' \
          -d '{"uri": "www.yahoo.com"}' 
         Response: 
         {
              "code": "000",
              "message": "create uri success",
              "uri": "www.yahoo.com",
              "shortUri": "http://localhost:8080/yvWtunCw9"
          }
          
          5. redirect http://localhost:8080/{shortUri} 
          Response: redirect to real url
       
      
    
Folder client-web is a front end application (http://localhost:4200/)<br/>
angular Cli Run by node + cli :
  1. cd {directory}/client-web
  2. npm install -g @angular/cli@latest 
  3. npm install
  4. ng serve
