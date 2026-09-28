pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Clones the repository
                checkout scm
            }
        }

        stage('Build Backend Image') {
            steps {
                echo 'Building Spring Boot Docker Image...'
                // The multi-stage Dockerfile automatically runs Maven
                sh 'docker build -t ticketing-backend:latest ./backend'
            }
        }

        stage('Build Frontend Image') {
            steps {
                echo 'Building Angular Docker Image...'
                // The multi-stage Dockerfile automatically runs npm build
                sh 'docker build -t ticketing-frontend:latest ./frontend'
            }
        }

        stage('Security Scan (Trivy)') {
            steps {
                echo 'Scanning images for CVE vulnerabilities...'
                // In a real pipeline, this would fail the build if CRITICAL CVEs are found
                sh 'docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image ticketing-backend:latest'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully! Images are ready for deployment.'
        }
        failure {
            echo 'Pipeline failed. Check the logs.'

        }
    }
}