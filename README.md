Sample HTTPS server for fun and profit
===

1. Generate x509 certificate (**-nodes** means password is not required)

> openssl req -x509 -sha256 -newkey rsa:2048 -keyout certificate.key -out certificate.crt -days 1024 -nodes

2. Export generated certificate as PCKS12

> openssl pkcs12 -export -in certificate.crt -inkey certificate.key -out server.p12 -name sample-https-server -password pass:change-me-please

3. Create a Java keystore (JKS)

> keytool -importkeystore -srcstorepass change-me-please -destkeystore sample-https-server.jks -deststorepass change-me-please -srckeystore server.p12 -srcstoretype PKCS12 -alias sample-https-server

4. Run the server

> sbt run 

5. Run a few curl commands (or open browser windows at https://localhost:10999)

> curl -vi -k https://localhost:10999
>
> curl -vi --cacert src/main/resources/certificate.crt  https://localhost:10999 