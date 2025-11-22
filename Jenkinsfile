pipeline {
    agent any

    tools { maven 'MVN' }

    stages {
        stage('Create settings.xml') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: '9402b541-33c9-453b-a7eb-90d7cb999f5e',
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
        stage('Build Package') {
            steps {
                sh 'mvn -s settings.xml -B -DskipTests clean package'
            }
        }
        stage('Build Image') {
            steps {
                script {
                    def image = docker.build("UMINHO/iti-service-auth:${env.BUILD_ID}", ".")
                }
            }
        }
        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('http://artifactory:6610', 'OneDev') {
                        image.push()
                        image.push("latest")
                    }
                }
            }
        }
    }
}