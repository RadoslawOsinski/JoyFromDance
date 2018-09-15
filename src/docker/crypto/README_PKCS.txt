0. (Krok dla testów) Tworzenie wlasnego CA.
---- tworzenie klucza prywatnego CA
$ openssl genrsa -out testCAKey.pem 4096

---- tworzenie certyfikatu testowego CA
[rmo@rmo localhost]$ openssl req -new -x509 -key testCAKey.pem -sha256 -subj "/C=PL/ST=Mazowsze/L=Warsaw/O=TestCA/CN=testCA.joyfrom.dance" -out testCA.crt

1. Tworzymy klucz prywatny i publiczny.
#GKE does not support 4096 :(
#$ openssl genrsa -des3 -out joyFromDancePrivateKey.pem 4096
$ openssl genrsa -des3 -out joyFromDancePrivateKey.pem 2048

2. Tworzymy zgloszenie o podpisanie certyfikatu.
$ openssl req -new -key joyFromDancePrivateKey.pem -subj "/C=PL/ST=Mazowsze/L=Warsaw/O=CWSFE/CN=joyfrom.dance" -out joyFromDanceCert.csr

Opcjonalnie sprawdzenie zawartosci:
openssl req -text -noout -verify -in joyFromDanceCert.csr

3. Tworzymy certyfikat z zgłoszenia csr.
$ openssl x509 -req -days 5000 -in joyFromDanceCert.csr -CA testCA.crt -CAkey testCAKey.pem -set_serial 01 -out joyFromDanceCert.crt

Opcjonalnie podejrzenie certyfikatu:
$ openssl x509 -in myCert.crt -text -noout

4. Tworzymy keystore PKCS12 przechowujacy certyfikat do składania sygnatur wraz z kluczem prywatnym. Alias dla klucza powinien być podany małymi literami.
$ openssl pkcs12 -export -out joyFromDanceKeystore.p12 -inkey joyFromDancePrivateKey.pem -name "joyFromDance" -in joyFromDanceCert.crt -certfile testCA.crt

Zawartość keystore można sprawdzić za pomocą:
keytool -list -keystore joyFromDanceKeystore.p12 -storepass myExportPassword -storetype PKCS12 -v

5. Tworzenie trusted store z certyfikatami:
keytool -importcert -keystore joyFromDanceTrustStore.p12 -storetype pkcs12 -storepass changeit -alias testCA -file testCA.crt
keytool -importcert -keystore joyFromDanceTrustStore.p12 -storetype pkcs12 -storepass changeit -alias testCA -file joyFromDanceDeliveryCert.crt
keytool -importcert -keystore joyFromDanceTrustStore.p12 -storetype pkcs12 -storepass changeit -alias testCA -file joyFromDanceJobsCert.crt
keytool -importcert -keystore joyFromDanceTrustStore.p12 -storetype pkcs12 -storepass changeit -alias testCA -file joyFromDanceWebsiteCert.crt
