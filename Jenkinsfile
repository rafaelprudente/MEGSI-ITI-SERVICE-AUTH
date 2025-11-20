pipeline {
    agent any
        tools {
            maven 'MVN'
    }
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
        stage('Build') {
            steps {
                sh 'mvn -s settings.xml -B -DskipTests clean package'
            }
        }
        stage('Build image') {
            steps {
                script {
                    app = docker.build("iti-service-auth:latest")
                }
            }
        }
    }
}