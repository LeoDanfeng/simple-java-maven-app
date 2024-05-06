pipeline {
    agent any;
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('docker-image') {
            steps {
                sh 'docker build -t my-app:v1 .'
            }
        }
        stage('docker-run') {
            steps {
                sh 'docker run -it -p 9000:9000 -d my-app:v1'
            }
        }
    }
}
