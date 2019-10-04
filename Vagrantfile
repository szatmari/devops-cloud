Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise32"
  config.vm.network "forwarded_port", guest: 80, host: 8088

  config.vm.provider "virtualbox" do |vb|
        vb.cpus = 1
  end

  config.vm.provision "shell", inline: <<-SHELL
        sudo apt-get update
        sudo apt-get install -y apt-transport-https software-properties-common python-software-properties
        sudo add-apt-repository ppa:openjdk-r/ppa
        sudo apt-get update
        sudo apt-get install -y openjdk-8-jre
   SHELL
end

