pipeline {
    agent any

    environment {
        IMAGE_NAME = 'todo-app'
        IMAGE_TAG = 'latest'
    }

    tools {
        git 'Default'
        maven 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Pulling master branch is started.'
                git branch: 'master', url: 'https://github.com/unvercan/todo-app.git'
                echo 'Pulling master branch was completed.'
            }
        }

        stage('Build') {
            steps {
                echo 'Download dependencies is started.'
                sh "mvn clean install -DskipTests"
                echo 'Download dependencies was completed.'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests is started.'
                sh "mvn test"
                echo 'Running tests was completed.'
            }
        }

        stage('Package') {
            steps {
                echo 'Preparing deployable package (jar) is started.'
                sh "mvn package -DskipTests"
                echo 'Preparing deployable package (jar) was completed.'
            }
        }

        stage('Docker Build') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'DOCKERHUB',
                        usernameVariable: 'DOCKERHUB_USERNAME',
                        passwordVariable: 'DOCKERHUB_PASSWORD'
                    )
                ]) {
                    echo 'Building Docker image is started.'
                    script {
                        sh '''
                            docker build -t "$DOCKERHUB_USERNAME/$IMAGE_NAME:$IMAGE_TAG" .
                        '''
                    }
                    echo 'Building Docker image was completed.'
                }
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([
                  usernamePassword(
                    credentialsId: 'DOCKERHUB',
                    usernameVariable: 'DOCKERHUB_USERNAME',
                    passwordVariable: 'DOCKERHUB_PASSWORD'
                  )]){
                   echo 'Logging in to Docker Hub is started.'
                   script {
                      sh (
                          script: '''
                              echo "$DOCKERHUB_PASSWORD" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin
                          ''', mask: true
                      )
                   }
                   echo 'Logging in to Docker Hub was completed.'

                   echo 'Pushing Docker image to Docker Hub is started.'
                   script {
                       sh '''
                          docker push "$DOCKERHUB_USERNAME/$IMAGE_NAME:$IMAGE_TAG"
                       '''
                   }
                   echo 'Pushing Docker image to Docker Hub was completed.'
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline was completed successfully."
        }

        failure {
            echo "Pipeline failed."
        }

        aborted {
            echo 'Pipeline was aborted'
        }

        always {
            echo 'Cleaning workspace...'
            cleanWs()
        }
    }
}
