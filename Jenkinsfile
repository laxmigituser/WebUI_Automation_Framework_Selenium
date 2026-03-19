pipeline {
    agent any

    tools {
        maven 'Maven'   // Configure this in Jenkins Global Tool Config
        jdk 'JDK'      // Adjust based on your setup
    }

    environment {
        HEADLESS = 'true'
    }

    stages {

        stage('Checkout') {
            steps {
                // If using Jenkins Git config
                checkout scm

                // OR manually:
                // git url: 'https://github.com/your-repo.git', branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                bat "mvn clean test -PRegression -Dheadless=${HEADLESS}"
            }
        }

        stage('Publish Report') {
            steps {
                publishHTML([
                    reportName: 'Execution Report',
                    reportDir: 'Reports/ExecutionReport',
                    reportFiles: 'index.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
            }
        }
    }

    post {
        always {
            echo "Build completed"
        }

        success {
            echo "Tests Passed ✅"
        }

        failure {
            echo "Tests Failed ❌"
        }
    }
}
