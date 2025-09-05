pipeline {
    agent any   // <-- instead of docker agent
    stages {
        stage('Checkout') {
            steps {
                echo "Code started"
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                bat 'mvn clean test'  // use bat since you’re on Windows
            }
        }
        stage('Report') {
            steps {
                junit 'target/surefire-reports/*.xml'
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'
                ])
            }
        }
    }
}
