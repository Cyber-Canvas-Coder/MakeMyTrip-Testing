pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17'
        }
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out source code..."
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo "Running Maven build & tests..."
                sh 'mvn clean test'
            }
        }

        stage('Reports') {
            steps {
                echo "Publishing reports..."
                // Archive TestNG results as JUnit
                junit 'target/surefire-reports/*.xml'

                // Publish Extent Report (HTML)
                publishHTML([allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target',
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report'])
            }
        }
    }

    post {
        always {
            echo "Archiving results..."
            archiveArtifacts artifacts: 'target/**/*.html', fingerprint: true
        }
    }
}
