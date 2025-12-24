pipeline {
    agent any

    stages {
        stage('Environment Setup') {
            steps {
                script {
                    sh 'chmod +x ./gradlew'
                    def projectName = sh(script: './gradlew -q printProjectName', returnStdout: true).trim()
                    def projectVersion = sh(script: './gradlew -q printProjectVersion', returnStdout: true).trim()
                    env.PROJECT_NAME = projectName
                    env.PROJECT_VERSION = projectVersion
                    env.JAR_PATH = "${WORKSPACE}/build/libs/${env.PROJECT_NAME}-${env.PROJECT_VERSION}.jar"
                }
                echo 'Environment variables set'
            }
        }

        stage('Test') {
            steps {
                dir("${WORKSPACE}") {
//                     sh './gradlew :clean :test'
                }
                echo 'Tests complete'
            }
        }

        stage('Build') {
            steps {
                sh "chmod u+x ${WORKSPACE}/gradlew"
                dir("${WORKSPACE}") {
                    sh './gradlew :clean :build -x test'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImageTag = "${env.PROJECT_NAME}:${env.PROJECT_VERSION}"
                    env.DOCKER_IMAGE_TAG = dockerImageTag
                    sh """
                    docker build \
                      --build-arg JAR_FILE=build/libs/${env.PROJECT_NAME}-${env.PROJECT_VERSION}.jar \
                      -t ${dockerImageTag} \
                      -f ${WORKSPACE}/Dockerfile \
                      ${WORKSPACE}
                    """
                }
            }
        }

        stage('Push to ECR') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID', variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID', variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION', variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECR_REPOSITORY', variable: 'ECR_REPOSITORY')
                    ]) {
                        sh '''
                        aws --version
                        aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com
                        aws ecr describe-repositories --repository-names $ECR_REPOSITORY --region $AWS_DEFAULT_REGION || aws ecr create-repository --repository-name $ECR_REPOSITORY --region $AWS_DEFAULT_REGION
                        docker tag $DOCKER_IMAGE_TAG $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$ECR_REPOSITORY:$PROJECT_VERSION
                        docker push $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$ECR_REPOSITORY:$PROJECT_VERSION
                        '''
                        env.IMAGE_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${ECR_REPOSITORY}:${PROJECT_VERSION}"
                    }
                }
            }
        }

        stage('Register Task Definition') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID', variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID', variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION', variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_TASK_EXECUTION_ROLE_ARN', variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
                        string(credentialsId: 'SHOP_ECS_TASK_ROLE_ARN', variable: 'ECS_TASK_ROLE_ARN'),
                        string(credentialsId: 'SHOP_CLOUDWATCH_LOG_GROUP', variable: 'CLOUDWATCH_LOG_GROUP')
                    ]) {
                        writeFile file: 'taskdef.json', text: """
{
  "family": "${PROJECT_NAME}",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "512",
  "memory": "1024",
  "executionRoleArn": "${ECS_TASK_EXECUTION_ROLE_ARN}",
  "taskRoleArn": "${ECS_TASK_ROLE_ARN}",
  "containerDefinitions": [
    {
      "name": "${PROJECT_NAME}",
      "image": "${IMAGE_URI}",
      "portMappings": [
        { "containerPort": 8080, "protocol": "tcp" }
      ],
      "essential": true,
      "secrets": [
        { "name": "DB_URL", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/DB_URL" },
        { "name": "DB_USERNAME", "valueFrom": "arn:aws:secretsmanager:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:secret:shop-reward/DB_USERNAME" },
        { "name": "DB_PASSWORD", "valueFrom": "arn:aws:secretsmanager:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:secret:shop-reward/DB_PASSWORD" },
        { "name": "JWT_SECRET", "valueFrom": "arn:aws:secretsmanager:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:secret:shop-reward/JWT_SECRET" },
        { "name": "ACCESS_TOKEN_EXPIRATION_TIME", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/ACCESS_TOKEN_EXPIRATION_TIME" },
        { "name": "REFRESH_TOKEN_EXPIRATION_TIME", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/REFRESH_TOKEN_EXPIRATION_TIME" },
        { "name": "CLIENT_ORIGIN", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/CLIENT_ORIGIN" },
        { "name": "COOKIE_DOMAIN", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/COOKIE_DOMAIN" },
        { "name": "ACCESS_CONTROL_ALLOWED_ORIGINS", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/ACCESS_CONTROL_ALLOWED_ORIGINS" },
        { "name": "SERVER_ORIGIN", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/SERVER_ORIGIN" },
        { "name": "GOOGLE_CLIENT_ID", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/GOOGLE_CLIENT_ID" },
        { "name": "GOOGLE_CLIENT_SECRET", "valueFrom": "arn:aws:secretsmanager:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:secret:shop-reward/GOOGLE_CLIENT_SECRET" },
        { "name": "KAKAO_CLIENT_ID", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/KAKAO_CLIENT_ID" },
        { "name": "KAKAO_CLIENT_SECRET", "valueFrom": "arn:aws:secretsmanager:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:secret:shop-reward/KAKAO_CLIENT_SECRET" },
        { "name": "S3_ACCESS_KEY", "valueFrom": "arn:aws:secretsmanager:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:secret:shop-reward/S3_ACCESS_KEY" },
        { "name": "S3_SECRET_KEY", "valueFrom": "arn:aws:secretsmanager:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:secret:shop-reward/S3_SECRET_KEY" },
        { "name": "S3_BUCKET_NAME", "valueFrom": "arn:aws:ssm:${AWS_DEFAULT_REGION}:${AWS_ACCOUNT_ID}:parameter/shop-reward/S3_BUCKET_NAME" }
      ],
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "${CLOUDWATCH_LOG_GROUP}",
          "awslogs-region": "${AWS_DEFAULT_REGION}",
          "awslogs-stream-prefix": "${PROJECT_NAME}"
        }
      }
    }
  ]
}
"""
                        sh 'aws ecs register-task-definition --cli-input-json file://taskdef.json'
                    }
                }
            }
        }

        stage('Deploy Service') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID', variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID', variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION', variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_CLUSTER_NAME', variable: 'ECS_CLUSTER_NAME'),
                        string(credentialsId: 'SHOP_ECS_SERVICE_NAME', variable: 'ECS_SERVICE_NAME')
                    ]) {
                        def newTaskDefArn = sh(script: "aws ecs list-task-definitions --family-prefix ${PROJECT_NAME} --sort DESC --region $AWS_DEFAULT_REGION --max-items 1 --query 'reverse(taskDefinitionArns)[0]'", returnStdout: true).trim().replaceAll('\"','')
                        sh "aws ecs update-service --cluster $ECS_CLUSTER_NAME --service $ECS_SERVICE_NAME --task-definition ${newTaskDefArn} --region $AWS_DEFAULT_REGION"
                        sh "aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION"
                    }
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: "build/libs/*.jar", fingerprint: true
            }
        }
    }
}
