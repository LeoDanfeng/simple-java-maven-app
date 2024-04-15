pipeline {
    agent any;
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
