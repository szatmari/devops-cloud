# DevOps - virtualization

## Preparation
1. Fork the https://github.com/szatmari/devops-virtualization project
2. Start a "DevOps - virtualization" in the BME cloud

## Vagrant basics

1. Setup project folder
```bash
mkdir myVagrantProject; cd myVagrantProject
```
2. Init vagrant project
```bash
vagrant init hashicorp/precise32
```
3. Limit CPUs to 1
```
  config.vm.provider "virtualbox" do |vb| 
    vb.cpus = 1
  end 
```

4. Start the project
```bash
vagrant up
vagrant ssh
ls /vagrant
```
5. Check the Vagrant cloud service: https://app.vagrantup.com

## VirtualBox

1. Check the running vms
```bash
vboxmanage list vms
```

## Deployment using Jenkins

1. Map the port 8080 to the public interface in the BME Cloud Manager
2. Visit the generated endpoint 
3. Create a new freestyle job
   - Set your GitHub link to your project
   - Configure SCM: git
   - Add your repository URL
   - Set the "Branches to build" property to "*/master"
   - Add "Invoke gradle script" to the Build section
   - Set the gradle task: "build"
   - Add a Post Build action: Publish Junit test results
   - Set the path of the "Test report XMLs" to "build/test-results/test/*.xml"
4. Check the Vagrantfile in the project
5. Add a new build task to do deployment: "Execute shell" and execute Vagrant Up!
```bash
vagrant up
```
6. Try to enter to the VM console and start your application
```bash
sudo su jenkins -s /bin/bash
cd /var/lib/jenkins/jobs/%%myDevOpsJob%%/workspace
vagrant ssh
``` 
7. Check the Packerfile and the content of the packer folder in the project
   - Check the Ubuntu AMI locator for AWS: https://cloud-images.ubuntu.com/locator/ec2/
8. Try the packer builder using an other Execute shell build step
```bash
packer build -var 'aws_access_key=XXXXXXXXXXX' -var 'aws_secret_key=XXXXXXXXXXXXX' Packerfile
```
