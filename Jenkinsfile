pipeline {
    agent {
        docker {
            image 'maven:3.9.0'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Run') {
            steps {
                sh 'java -jar target/my-app-1.0.0-SNAPSHOT.jar'
            }
        }
    }
}
