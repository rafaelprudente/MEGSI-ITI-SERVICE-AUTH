pipeline {
    agent any

    tools { maven 'MVN' }

    def image

    stages {
        stage('Create settings.xml') {
            steps {
                sh '''
                cat << 'EOF' > settings.xml
                <settings>
                    <servers>
                        <server>
                            <id>onedev</id>
                            <username>admin</username>
                            <password>H4EnOm154rWviHDSGpVOIm7aQYgvD1Ol2kJyiCmT</password>
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
        stage('Build Package') {
            steps {
                sh 'mvn -s settings.xml -B -DskipTests clean package'
            }
        }
        stage('Build Image') {
            steps {
                script {
                    image = docker.build("UMINHO/iti-service-auth:${env.BUILD_ID}", ".")
                }
            }
        }
        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('http://artifactory:6610', '9402b541-33c9-453b-a7eb-90d7cb999f5e') {
                        image.push()
                        image.push("latest")
                    }
                }
            }
        }
    }
}