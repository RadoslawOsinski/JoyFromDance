Create project
---
```
gcloud auth login Radoslaw.Osinski@cwsfe.pl
gcloud projects create projectidjoyfromdance --organization=309840496278 --enable-cloud-apis
gcloud init
#billing i budżetowanie trzeba wyklikać na stronie
gcloud services enable container.googleapis.com
gcloud services enable sqladmin.googleapis.com
gcloud services enable places-backend.googleapis.com
gcloud services enable cloudtrace.googleapis.com
```

Kubernetes cluster creation
---
```
gcloud auth login Radoslaw.Osinski@cwsfe.pl
gcloud config set project projectidjoyfromdance
gcloud config set compute/zone europe-west3-a
#check valid versions
gcloud container get-server-config
gcloud container clusters create joyfromdancek8s --cluster-version=1.10.5-gke.4 --machine-type=n1-standard-1 --enable-autoupgrade --enable-autorepair --num-nodes=5
gcloud container clusters get-credentials joyfromdancek8s --zone europe-west3-a --project projectidjoyfromdance
#gcloud config set account Radoslaw.Osinski@cwsfe.pl
```

Project namespace
---
```
kubectl apply -f src/kubernetes/conf/namespace.yaml
```
Postgresql instance creation
---
```
gcloud sql instances create joy-from-dance --database-version=POSTGRES_9_6 --storage-size=10 --storage-type=HDD --storage-auto-increase --tier=db-f1-micro --region=europe-west3 --maintenance-window-day=THU --maintenance-window-hour=3 --backup-start-time=05:00
gcloud sql users set-password postgres no-host --instance joy-from-dance --password secretPassword
gcloud sql users create joyfromdancewebsite no-host --instance=joy-from-dance --password=secretPassword
gcloud sql ssl-certs create joy-from-dance-user src/kubernetes/joy-from-dance-postgres-private-key.pem --instance=joy-from-dance
gcloud sql ssl-certs describe joy-from-dance-user --instance=joy-from-dance --format='value(cert)' > src/kubernetes/joy-from-dance-postgres-client.crt
gcloud sql instances describe joy-from-dance --format='value(serverCaCert.cert)' > src/kubernetes/joy-from-dance-postgres-serverCa.crt
gcloud sql instances patch joy-from-dance --require-ssl
gcloud sql instances patch joy-from-dance --maintenance-window-day=THU --maintenance-window-hour=3 --backup-start-time=05:00
gcloud sql databases create joy-from-dance-instance-prod --instance=joy-from-dance
```

Connect application with instance
---
```
#login as project owner for access to specific roles like: roles/cloudsql.client
gcloud auth login Radoslaw.Osinski@cwsfe.pl
gcloud config set project joyfromdance
gcloud iam service-accounts create joy-from-dance-sqlproxyuser --display-name "joy-from-dance proxy user"
#listing roles at: https://console.cloud.google.com/iam-admin/roles
gcloud projects add-iam-policy-binding joyfromdance --member serviceAccount:joy-from-dance-sqlproxyuser@joyfromdance.iam.gserviceaccount.com --role roles/cloudsql.client
gcloud iam service-accounts list
gcloud iam service-accounts keys create --iam-account=joy-from-dance-sqlproxyuser@joyfromdance.iam.gserviceaccount.com src/kubernetes/proxy-user-key.json
gcloud sql users create sqlproxyuser cloudsqlproxy~% --instance=joy-from-dance --password=secretPassword
gcloud sql instances describe joy-from-dance | grep connectionName
connectionName: joyfromdance:europe-west3:joy-from-dance
kubectl create secret generic cloudsql-instance-credentials --namespace=joy-from-dance --from-file=credentials.json=src/kubernetes/proxy-user-key.json
kubectl create secret generic cloudsql-db-credentials --namespace=joy-from-dance --from-literal=username=sqlproxyuser --from-literal=password=secretPassword
#gcloud sql instances patch joy-from-dance --authorized-networks=188.146.96.198,127.0.0.1
#gcloud sql instances patch joy-from-dance --clear-authorized-networks
```

Tracing account
---
```
export PROJECT_ID=$(gcloud config list --format 'value(core.project)')
gcloud iam service-accounts create joy-from-dance-rest --display-name "joy from dance rest user"
gcloud projects add-iam-policy-binding ${PROJECT_ID} --member serviceAccount:joy-from-dance-rest@${PROJECT_ID}.iam.gserviceaccount.com --role roles/editor
gcloud iam service-accounts keys create joyFromDance_rest/src/main/resources/joy-from-dance-rest-service-account.json --iam-account joy-from-dance-rest@${PROJECT_ID}.iam.gserviceaccount.com
```

LetsEncrypt certificates auto rolling
---
```
Instruction based on: https://github.com/ahmetb/gke-letsencrypt
#login as project owner for access to specific roles
gcloud auth login Radoslaw.Osinski@cwsfe.pl
gcloud container clusters describe joyfromdancek8s
gcloud container clusters describe joyfromdancek8s | grep password
  password: secretPassword
kubectl --username=admin --password=secretPassword create clusterrolebinding cluster-admin-binding --clusterrole=cluster-admin --user=Radoslaw.Osinski@cwsfe.pl
clusterrolebinding "cluster-admin-binding" created
kubectl apply -f src/kubernetes/letsencrypt/with-rbac.yaml
kubectl apply -f src/kubernetes/letsencrypt/cluster-issuer.yaml 
```

Create service account for Kubernetes management
---
```
gcloud auth login Radoslaw.Osinski@cwsfe.pl
gcloud config set project joyfromdance
gcloud iam service-accounts create joy-from-dance-service-account --display-name "joy from dance service account"
gcloud projects add-iam-policy-binding projectidjoyfromdance --member serviceAccount:joy-from-dance-service-account@projectidjoyfromdance.iam.gserviceaccount.com --role roles/container.admin
gcloud projects add-iam-policy-binding projectidjoyfromdance --member serviceAccount:joy-from-dance-service-account@projectidjoyfromdance.iam.gserviceaccount.com --role roles/container.clusterAdmin
gcloud iam service-accounts keys create src/kubernetes/joy-from-dance-service-account-key.json --iam-account joy-from-dance-service-account@projectidjoyfromdance.iam.gserviceaccount.com --project projectidjoyfromdance
```

Create service account for for CI deployments
---
```
gcloud auth login Radoslaw.Osinski@cwsfe.pl
gcloud config set project projectidjoyfromdance
gcloud iam service-accounts create joy-from-dance-ci-account --display-name "joy from dance ci account"
#before adding role for new account check that you are invoking this command by correct user:
#gcloud config list
gcloud projects add-iam-policy-binding projectidjoyfromdance --member serviceAccount:joy-from-dance-ci-account@projectidjoyfromdance.iam.gserviceaccount.com --role roles/container.admin
gcloud projects add-iam-policy-binding projectidjoyfromdance --member serviceAccount:joy-from-dance-ci-account@projectidjoyfromdance.iam.gserviceaccount.com --role roles/container.clusterAdmin
gcloud projects add-iam-policy-binding projectidjoyfromdance --member serviceAccount:joy-from-dance-ci-account@projectidjoyfromdance.iam.gserviceaccount.com --role roles/cloudbuild.builds.builder
#only key creation! At the moment only manualy you can enable api: "iam.googleapis.com"
gcloud iam service-accounts keys create src/kubernetes/joy-from-dance-ci-account-key.json --iam-account joy-from-dance-ci-account@projectidjoyfromdance.iam.gserviceaccount.com --project projectidjoyfromdance
```

HTTPS for frontend and rest application
---
```
gcloud compute addresses create joy-from-dance-website-ip --global
gcloud compute addresses describe joy-from-dance-website-ip --global
gcloud compute addresses create joy-from-dance-rest-ip --global
gcloud compute addresses describe joy-from-dance-rest-ip --global
#certificate created by LetsEncrypt! :D
#password website
#openssl rsa -in joyFromDance_website/src/main/resources/joyFromDanceWebsitePrivateKey.pem -out joyFromDance_website/src/main/resources/joyFromDanceWebsitePrivateKey.key
#kubectl create secret generic joy-from-dance-website-letsencrypt-certs --from-file=tls.crt=./joyFromDance_website/src/main/resources/joyFromDanceWebsiteCert.crt,tls.key=./joyFromDance_website/src/main/resources/joyFromDanceWebsitePrivateKey.key --namespace=joy-from-dance
```

Prometheus configuration:
---
```
kubectl apply -f src/kubernetes/prometheus/rbac-setup.yaml --as=admin --as-group=system:masters
kubectl apply -f src/kubernetes/prometheus/prometheus-service.yaml
```

Sonar configuration (useless for JDK 9/10/11)
---
```
gcloud sql databases create sonar-prod --instance=joy-from-dance
gcloud sql users create sonar no-host --instance=joy-from-dance --password=secretPassword
kubectl create secret generic sonar-db-credentials --namespace=joy-from-dance --from-literal=username=sqlproxyuser --from-literal=password=secretPassword
kubectl create secret generic sonar-cloudsql-instance-credentials --namespace=joy-from-dance --from-file=credentials.json=src/kubernetes/proxy-user-key.json
kubectl apply -f src/kubernetes/conf/sonarServices.yaml

#The token is used to identify you when an analysis is performed. If it has been compromised, you can revoke it at any point of time in your user account.
#admin: 84d972cca96ea094ea7f64a87389aeb4d586393d
```

Application release:
---
```
gcloud config set project projectidjoyfromdance
gcloud auth activate-service-account --key-file=src/kubernetes/joy-from-dance-ci-account-key.json
gcloud container clusters get-credentials joyfromdancek8s --zone europe-west3-a --project projectidjoyfromdance
kubectl apply -f src/kubernetes/conf/joyFromDanceServices.yaml
```

Connecting to production database over proxy:
---
```
#download proxy
./sqlproxy/configureProxy.sh
#open proxy
./src/operProxyForProd.sh
#connect via jdbc client
```
