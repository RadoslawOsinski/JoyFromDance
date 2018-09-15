Joy from dance - Google Cloud Engine experiments - project has been shut down
=========

Interesting parts:
---
* Project creation from scratch on GKE: /src/kubernetes/README.md
* Using Gitlab pipelines in /.gitlab-ci.yml
* Building JDK 10 images with /src/cloud-builders/gradlejdk10/Dockerfile
* Proxy to production database which makes easy connection for Idea to SQL server: /src/operProxyForProd.sh
* LetsEncrypt automatic certificates management on kubernetes: /src/kubernetes/letsencrypt/*
* Prometheus monitoring on kubernetes: /src/kubernetes/prometheus/*

Releasing application
---
1. Change application version for kubernates in yaml
2. `git tag -f -a 1.0-release`
3. click release button in gitlab

Remove trash tags
---
```
git tag --delete tagname
git push --delete gke tagname
```
