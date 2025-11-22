pipeline {
    agent any

    tools { maven 'MVN' }

    environment {
        IMAGE_NAME = "iti-service-auth"
    }

    stages {
        stage('Create settings.xml') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'OneDev',
                    usernameVariable: 'MVN_USER',
                    passwordVariable: 'MVN_PASS'
                )]) {
                    sh '''
                    cat << 'EOF' > settings.xml
                    <settings>
                        <servers>
                            <server>
                                <id>onedev</id>
                                <username>${MVN_USER}</username>
                                <password>${MVN_PASS}</password>
                            </server>
                        </servers>

                        <mirrors>
                            <mirror>
                                <id>maven-default-http-blocker</id>
                                <mirrorOf>dummy</mirrorOf>
                                <name>Dummy mirror to override default blocking mirror that blocks http</name>
                                <url>http://0.0.0.0/</url>
                            </mirror>
                        </mirrors>
                    </settings>'''
                }
            }
        }
        stage('Info') {
            steps {
                sh 'mvn help:evaluate -Dexpression=settings.localRepository'
            }
        }
        stage('Build Package') {
            steps {
                sh 'mvn -s settings.xml -B -DskipTests clean package'
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