#!/bin/bash
openssl genrsa -out testCAKey.pem 2048
openssl req -new -x509 -key testCAKey.pem -sha256 -subj "/C=PL/ST=Mazowsze/L=Warsaw/O=TestCA/CN=testCA.joyfrom.dance" -out testCA.crt

openssl genrsa -des3 -out joyFromDanceDeliveryPrivateKey.pem 2048
openssl req -new -key joyFromDanceDeliveryPrivateKey.pem -subj "/C=PL/ST=Mazowsze/L=Warsaw/O=CWSFE/CN=joyFromDanceDelivery.joyfrom.dance" -out joyFromDanceDeliveryCert.csr
openssl x509 -req -days 5000 -in joyFromDanceDeliveryCert.csr -CA testCA.crt -CAkey testCAKey.pem -set_serial 01 -out joyFromDanceDeliveryCert.crt
openssl pkcs12 -export -out joyFromDanceDeliveryKeystore.p12 -inkey joyFromDanceDeliveryPrivateKey.pem -name "joyFromDance" -in joyFromDanceDeliveryCert.crt -certfile testCA.crt

openssl genrsa -des3 -out joyFromDanceJobsPrivateKey.pem 2048
openssl req -new -key joyFromDanceJobsPrivateKey.pem -subj "/C=PL/ST=Mazowsze/L=Warsaw/O=CWSFE/CN=joyFromDanceJobs.joyfrom.dance" -out joyFromDanceJobsCert.csr
openssl x509 -req -days 5000 -in joyFromDanceJobsCert.csr -CA testCA.crt -CAkey testCAKey.pem -set_serial 01 -out joyFromDanceJobsCert.crt
openssl pkcs12 -export -out joyFromDanceJobsKeystore.p12 -inkey joyFromDanceJobsPrivateKey.pem -name "joyFromDance" -in joyFromDanceJobsCert.crt -certfile testCA.crt

openssl genrsa -des3 -out joyFromDanceWebsitePrivateKey.pem 2048
openssl req -new -key joyFromDanceWebsitePrivateKey.pem -subj "/C=PL/ST=Mazowsze/L=Warsaw/O=CWSFE/CN=joyFromDanceWebsite.joyfrom.dance" -out joyFromDanceWebsiteCert.csr
openssl x509 -req -days 5000 -in joyFromDanceWebsiteCert.csr -CA testCA.crt -CAkey testCAKey.pem -set_serial 01 -out joyFromDanceWebsiteCert.crt
openssl pkcs12 -export -out joyFromDanceWebsiteKeystore.p12 -inkey joyFromDanceWebsitePrivateKey.pem -name "joyFromDance" -in joyFromDanceWebsiteCert.crt -certfile testCA.crt

keytool -importcert -keystore joyFromDanceTrustStore.p12 -storetype pkcs12 -storepass changeit -alias testCA -file testCA.crt
keytool -importcert -keystore joyFromDanceTrustStore.p12 -storetype pkcs12 -storepass changeit -alias joyFromDanceDelivery -file joyFromDanceDeliveryCert.crt
keytool -importcert -keystore joyFromDanceTrustStore.p12 -storetype pkcs12 -storepass changeit -alias joyFromDanceJobs -file joyFromDanceJobsCert.crt
keytool -importcert -keystore joyFromDanceTrustStore.p12 -storetype pkcs12 -storepass changeit -alias joyFromDanceWebsite -file joyFromDanceWebsiteCert.crt
