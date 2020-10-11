# DevOps - Kubernetes, Cloud, AWS

## Preparation
1. Fork the repo
2. Start a VM based on the "DevOps - Cloud" template

### Docker HUB
1. Register/login to the Docker Hub. Generate your access token
2. Login to docker hub from the CLI
```bash
docker login --username szatmarizoltanmit
```
3. Build your project as a docker image and publish it to the Docker Hub
```bash
docker build -t szatmarizoltanmit/helloworld:latest .
docker push szatmarizoltanmit/helloworld:latest
```

### AWS
1. Register/login to AWS. This lab fits the Free Tier usage.
2. Get your AWS security credentials (Access key and Secret key)
3. Generate an SSH key in EC2, if you don't have one.

## Kubernetes
1. Clone the repository to the cloud VM
2. Enable the MicroK8S Ingress module and create the /home/cloud/db-init directory
```bash
microk8s.enable ingress dns
mkdir /home/cloud/db-init
```
3. Create kubernetes volume
```bash
microk8s.kubectl apply -f volume.yml
microk8s.kubectl get pv,pvc
```
4. Start the database POD and service
```bash
microk8s.kubectl apply -f db.yml
microk8s.kubectl logs -f pod/dbapp
```
5. Test the database connection then run the database/golf.sql
```bash
microk8s.kubectl get all
mysql -u root -p -h dbservice.ip.address golf
```
6. Fine-tune the ```DBHOST``` environment variable with the IP of the dbservvice and deploy your web application
```bash
microk8s.kubectl apply -f web-app.yml
curl httpservice.ip.address:8088/greeting
curl httpservice.ip.address:8088/sql
```
7. Add the Ingress to the deployment
```bash
microk8s.kubectl apply -f ingress.yml
curl --insecure https://localhost/sql
```
8. Map the post 443 on the cloud VM to its public interface and check the endpoint using the browser (HTTPS!)

## AWS EB deployments

### EB Java application manual deployment
1. Create a new EB application and an environment
   - Select Java platform, and the default application to deploy.
   - Go for the more detailed customization and don't deploy using the default settings
   - Set your SSH key to the environment
4. Add the environment variable ```SERVER_PORT=5000``` to your environment
5. Try to upload your jar file manually
6. Check the HTTP endpoint URL ```/greeting```

### EB Java application automatic deployment
1. Create your Job in your Jenkins to build the application
   - Add the repository
   - Add the gradle build step and define the ```build``` task for it
2. Add a new ```AWS Elastic beanstalk``` build step to your job
3. Define the AWS credentials using the Jenkins secure credential store
4. Set your application and environment name on the configuration panel
5. Define the ```root object``` as ```build/libs/gs-rest-service-0.1.0.jar```
6. Define the ```version label``` format as ```${GIT_COMMIT}```
7. Save and test your job
8. Check the AWS EB console, the Events and also the Application versions.

### EB Docker application deployment
1. Create a new EB application and an environment
2. Select Docker platform, and the default application to deploy
6. Fine-tune and upload the ```Dockerrun.aws.json``` file manually as a new deployment
7. Check the HTTP endpoint URL ```/greeting```
8. Clone your Jenkins job (Create a new one based on the previos one as a template)
9. Tune the application and environment name and Replace the ```root object``` to ```Dockerrun.aws.json```
10. Save and test your job
11. Check the AWS EB console, the Events and also the Application versions.

### Database connection
1. Create an RDS database.
   - Take care on the network settings: VPC, subnets. 
   - Grant access from your EB application security groups.
   - Set the root password to ```devops```, because it is hardwired in the Java code.
2. Define the ```DBHOST``` environment variable in your Java-based EB environment
3. SSH to the EC2 instance of your Java-based EB environment using the ```ec2-user``` ssh username and you SSH key.
4. Clone the repository and load the dataset to your database. If needed you may install the ```git``` and ```mysql-client``` CLI tools.
```bash
sudo yum install -y mysql-client git
```
5. Test your Java-deployment using the ```/sql``` endpoint.

