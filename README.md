# DevOps - Docker, Kubernetes

## Preparation
1. Fork the https://github.com/szatmari/devops-docker project
2. Start a "DevOps - Docker, Kubernetes" in the BME cloud
3. Expose ports 80, 8088
4. If needed: tune the /etc/resolv.conf and add 8.8.8.8 as nameserver
5. Clone the repository to the cloud VM

## Docker

1. Test simple docker command
```bash
docker run -it ubuntu
docker run -d ubuntu sleep 100
docker ps
```

1. Test a simple PHP application
```bash
mkdir php-project
echo '<?php echo "Hello world!" ?>' >> php-project/index.php
docker run -d -v /home/cloud/php-project:/var/www/html --name myphpcontainer php:7.2-apache
docker inspect myphpcontainer
IP=$(docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' myphpcontainer)
curl $IP
```

2. Test your gradle project
```bash
cd devops-docker
docker run --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project -p 8088:8088 gradle gradle build
docker run --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project -p 8088:8088 gradle gradle bootRun
docker run -v "${PWD}/build/libs":/app java java -jar /app/gs-rest-service-0.1.0.jar
```
Chech the http://cloudip:port/greeting in your browser

3. Test multi-container setup
```bash
docker run -d -e MYSQL_ROOT_PASSWORD=devops -e MYSQL_DATABASE=golf -v $(pwd)/database:/docker-entrypoint-initdb.d --name=mymysql mysql
IP=$(docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mymysql)
docker run --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project -p 8088:8088 -e DBHOST=$IP gradle gradle bootRun
```
Chech the http://cloudip:port/sql your browser

4. Test docker-compose deployment
```bash
docker-compose up
```

## Kubernetes

1. Test basic commands
```bash
microk8s.kubectl get nodes -o wide
microk8s.kubectl get all
microk8s.kubectl apply -f kubernetes.yml
```
