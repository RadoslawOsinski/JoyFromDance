#!/bin/bash
#This script is useful for remote workers working on linux. It is based on:
#https://docs.docker.com/engine/security/https/#create-a-ca-server-and-client-keys-with-openssl
#This script recreates DOCKER TLS configuration for changing IP address in certificate SAN

DOCKER_SERVER_IP=${1:-192.168.8.102}
CLIENT_USER=${2:-radek}
CLIENT_USER_HOME_DIRECTORY=/home/$2
DOCKER_SERVER_HOST_NAME=`hostname`

echo "Creating TLS certs for docker on IP $DOCKER_SERVER_IP, hostName: $DOCKER_SERVER_HOST_NAME, client user: $CLIENT_USER"

openssl genrsa -aes256 -passout pass:ca-key -out /etc/docker/ca-key.pem 4096
openssl req -passin pass:ca-key -new -x509 -subj "/CN=$DOCKER_SERVER_IP" -key /etc/docker/ca-key.pem -sha256 -out /etc/docker/ca.pem

openssl genrsa -passout pass:server-key -out /etc/docker/server-key.pem 4096
openssl req -passin pass:server-key -subj "/CN=$DOCKER_SERVER_HOST_NAME" -sha256 -new -key /etc/docker/server-key.pem -out /etc/docker/server.csr

echo subjectAltName = DNS:$DOCKER_SERVER_HOST_NAME,IP:$DOCKER_SERVER_IP,IP:127.0.0.1 > /etc/docker/extfile.cnf
echo extendedKeyUsage = serverAuth >> /etc/docker/extfile.cnf

openssl x509 -passin pass:ca-key -req -sha256 -in /etc/docker/server.csr -CA /etc/docker/ca.pem -CAkey /etc/docker/ca-key.pem -CAcreateserial -out /etc/docker/server-cert.pem -extfile /etc/docker/extfile.cnf
rm /etc/docker/server.csr

openssl genrsa -passout pass: -out $CLIENT_USER_HOME_DIRECTORY/.docker/key.pem 4096
chown $CLIENT_USER:$CLIENT_USER $CLIENT_USER_HOME_DIRECTORY/.docker/key.pem

openssl req -passin pass: -subj '/CN=client' -new -key $CLIENT_USER_HOME_DIRECTORY/.docker/key.pem -out $CLIENT_USER_HOME_DIRECTORY/.docker/client.csr
cat /etc/docker/extfile.cnf > $CLIENT_USER_HOME_DIRECTORY/.docker/extfile.cnf
echo extendedKeyUsage = clientAuth >> $CLIENT_USER_HOME_DIRECTORY/.docker/extfile.cnf

openssl x509 -passin pass:ca-key -req -sha256 -in $CLIENT_USER_HOME_DIRECTORY/.docker/client.csr -CA /etc/docker/ca.pem -CAkey /etc/docker/ca-key.pem -CAcreateserial -out $CLIENT_USER_HOME_DIRECTORY/.docker/cert.pem -extfile $CLIENT_USER_HOME_DIRECTORY/.docker/extfile.cnf
rm $CLIENT_USER_HOME_DIRECTORY/.docker/client.csr

cp /etc/docker/ca.pem $CLIENT_USER_HOME_DIRECTORY/.docker/
chown $CLIENT_USER:$CLIENT_USER $CLIENT_USER_HOME_DIRECTORY/.docker/ca.pem
chown $CLIENT_USER:$CLIENT_USER $CLIENT_USER_HOME_DIRECTORY/.docker/cert.pem
chown $CLIENT_USER:$CLIENT_USER $CLIENT_USER_HOME_DIRECTORY/.docker/key.pem
chmod 0444 $CLIENT_USER_HOME_DIRECTORY/.docker/ca.pem $CLIENT_USER_HOME_DIRECTORY/.docker/key.pem $CLIENT_USER_HOME_DIRECTORY/.docker/cert.pem

#chmod 0444 /etc/docker/ca-key.pem /etc/docker/key.pem /etc/docker/server-key.pem
#chmod 0444 /etc/docker/ca-key.pem /etc/docker/key.pem /etc/docker/server-key.pem
#chmod 0444 /etc/docker/ca.pem /etc/docker/server-cert.pem $CLIENT_USER_HOME_DIRECTORY/.docker/cert.pem
