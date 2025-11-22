pipeline {
    agent any

    tools { maven 'MVN' }

    environment {
        IMAGE_NAME = "iti-service-auth"
    }

    stages {
        stage('Build Package') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Build Image') {
            steps {
                script {
                    image = docker.build("uminho/iti-service-auth:latest", ".")
                }
            }
        }
        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('http://artifactory:6610', 'OneDev') {
                        image.push()
                    }
                }
            }
        }
    }
}