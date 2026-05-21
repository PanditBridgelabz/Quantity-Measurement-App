pipeline {
    agent any

    environment {

        BACKEND_IMAGE = "skye06024/quantity-backend:v1"
        FRONTEND_IMAGE = "skye06024/quantity-frontend:v1"

        BACKEND_CONTAINER = "quantity-backend-container"
        FRONTEND_CONTAINER = "quantity-frontend-container"
    }

    stages {

        stage('Clone Code') {
            steps {

                git branch: 'main',
                url: 'https://github.com/PanditBridgelabz/Quantity-Measurement-App.git'
            }
        }

        stage('Build Backend Jar') {
            steps {

                sh '''
                chmod +x mvnw
                ./mvnw clean package -DskipTests
                '''
            }
        }

        stage('Build Backend Docker Image') {
            steps {

                sh '''
                docker build -t $BACKEND_IMAGE .
                '''
            }
        }

        stage('Build Frontend Docker Image') {
            steps {

                sh '''
                cd frontend
                docker build -t $FRONTEND_IMAGE .
                '''
            }
        }

        stage('DockerHub Login') {
            steps {

                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-cred',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {

                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    '''
                }
            }
        }

        stage('Push Backend Image') {
            steps {

                sh '''
                docker push $BACKEND_IMAGE
                '''
            }
        }

        stage('Push Frontend Image') {
            steps {

                sh '''
                docker push $FRONTEND_IMAGE
                '''
            }
        }

        stage('Deploy Backend Container') {
            steps {

                sh '''
                docker stop $BACKEND_CONTAINER || true
                docker rm $BACKEND_CONTAINER || true

                docker pull $BACKEND_IMAGE

                docker run -d \
                  --name $BACKEND_CONTAINER \
                  -p 8080:8080 \
                  $BACKEND_IMAGE
                '''
            }
        }

        stage('Deploy Frontend Container') {
            steps {

                sh '''
                docker stop $FRONTEND_CONTAINER || true
                docker rm $FRONTEND_CONTAINER || true

                docker pull $FRONTEND_IMAGE

                docker run -d \
                  --name $FRONTEND_CONTAINER \
                  -p 3000:3000 \
                  $FRONTEND_IMAGE
                '''
            }
        }
    }
}
