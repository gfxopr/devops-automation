pipeline {
    agent any
    tools{
        maven 'maven_3_5_0'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Java-Techie-jt/devops-automation']]])
                sh 'mvn clean install'
            }
        }
        stage('Groovy plugins check'){
        steps{
            script{
            @NonCPS
            def checkForVulnerabilities(String imageName) {
                def grypeScanner = Jenkins.instance.getExtensionList('io.jenkins.plugins.grypescanner.GrypeScanner')[0]
                def scanResult = grypeScanner.scan(imageName)
                
                if (scanResult.hasVulnerabilities()) {
                    println "Vulnerabilities found in image: ${imageName}"
                    scanResult.getVulnerabilities().each { vulnerability ->
                        println "ID: ${vulnerability.getId()}, Severity: ${vulnerability.getSeverity()}"
                    }
                } else {
                    println "No vulnerabilities found in image: ${imageName}"
                }

            }
            checkForVulnerabilities(".")
        }
        }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'systemctl start docker'
                    sh 'docker build -t javatechie/devops-integration .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                   sh 'docker login -u javatechie -p ${dockerhubpwd}'

}
                   sh 'docker push javatechie/devops-integration'
                }
            }
        }
        stage('Deploy to k8s'){
            steps{
                script{
                    kubernetesDeploy (configs: 'deploymentservice.yaml',kubeconfigId: 'k8sconfigpwd')
                }
            }
        }
    }
}
