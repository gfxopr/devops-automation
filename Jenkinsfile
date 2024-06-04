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
                script{
                groovyScript = load '/var/jenkins_home/scriptler/scripts/grypescan.groovy'
                groovyScript(scanDest: '.', repName:'report.csv', autoInstall:true, acceptCritical:true)
                }
            }
        }
    }
}
