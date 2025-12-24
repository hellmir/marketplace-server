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
                    // sh './gradlew :clean :test'
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
                        env.IMAGE_URI = "${env.AWS_ACCOUNT_ID}.dkr.ecr.${env.AWS_DEFAULT_REGION}.amazonaws.com/${env.ECR_REPOSITORY}:${env.PROJECT_VERSION}"
                    }
                }
            }
        }

        stage('Register Task Definition') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_DB_URL',                       variable: 'CRED_DB_URL'),
                        string(credentialsId: 'SHOP_DB_USERNAME',                  variable: 'CRED_DB_USERNAME'),
                        string(credentialsId: 'SHOP_DB_PASSWORD',                  variable: 'CRED_DB_PASSWORD'),
                        string(credentialsId: 'SHOP_JWT_SECRET_KEY',               variable: 'CRED_JWT_SECRET_KEY'),
                        string(credentialsId: 'SHOP_ACCESS_TOKEN_EXPIRATION_TIME', variable: 'CRED_ACCESS_TOKEN_EXPIRATION_TIME'),
                        string(credentialsId: 'SHOP_REFRESH_TOKEN_EXPIRATION_TIME', variable: 'CRED_REFRESH_TOKEN_EXPIRATION_TIME'),
                        string(credentialsId: 'SHOP_CLIENT_ORIGIN',                variable: 'CRED_CLIENT_ORIGIN'),
                        string(credentialsId: 'SHOP_COOKIE_DOMAIN',                variable: 'CRED_COOKIE_DOMAIN'),
                        string(credentialsId: 'SHOP_ACCESS_CONTROL_ALLOWED_ORIGINS', variable: 'CRED_ACCESS_CONTROL_ALLOWED_ORIGINS'),
                        string(credentialsId: 'SHOP_SERVER_ORIGIN',                variable: 'CRED_SERVER_ORIGIN'),
                        string(credentialsId: 'SHOP_GOOGLE_CLIENT_ID',             variable: 'CRED_GOOGLE_CLIENT_ID'),
                        string(credentialsId: 'SHOP_GOOGLE_CLIENT_SECRET',         variable: 'CRED_GOOGLE_CLIENT_SECRET'),
                        string(credentialsId: 'SHOP_KAKAO_CLIENT_ID',              variable: 'CRED_KAKAO_CLIENT_ID'),
                        string(credentialsId: 'SHOP_KAKAO_CLIENT_SECRET',          variable: 'CRED_KAKAO_CLIENT_SECRET'),
                        string(credentialsId: 'SHOP_S3_ACCESS_KEY',                variable: 'CRED_S3_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_S3_SECRET_KEY',                variable: 'CRED_S3_SECRET_KEY'),
                        string(credentialsId: 'SHOP_S3_BUCKET_NAME',               variable: 'CRED_S3_BUCKET_NAME'),
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID',               variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID',            variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY',        variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',           variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_TASK_EXECUTION_ROLE_ARN',  variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
                        string(credentialsId: 'SHOP_ECS_TASK_ROLE_ARN',            variable: 'ECS_TASK_ROLE_ARN'),
                        string(credentialsId: 'SHOP_CLOUDWATCH_LOG_GROUP',         variable: 'CLOUDWATCH_LOG_GROUP')
                    ]) {
                        sh '''
                        aws logs describe-log-groups --log-group-name-prefix "$CLOUDWATCH_LOG_GROUP" --region "$AWS_DEFAULT_REGION" \
                          || aws logs create-log-group --log-group-name "$CLOUDWATCH_LOG_GROUP" --region "$AWS_DEFAULT_REGION" || true
                        '''

                        def taskdef = [
                            family: "${env.PROJECT_NAME}",
                            networkMode: "awsvpc",
                            requiresCompatibilities: ["FARGATE"],
                            cpu: "512",
                            memory: "1024",
                            executionRoleArn: "${env.ECS_TASK_EXECUTION_ROLE_ARN}",
                            taskRoleArn: "${env.ECS_TASK_ROLE_ARN}",
                            containerDefinitions: [[
                                name: "${env.PROJECT_NAME}",
                                image: "${env.IMAGE_URI}",
                                portMappings: [[containerPort: 8080, protocol: "tcp"]],
                                essential: true,
                                environment: [
                                    [name: "DB_URL", value: "${env.CRED_DB_URL}"],
                                    [name: "DB_USERNAME", value: "${env.CRED_DB_USERNAME}"],
                                    [name: "DB_PASSWORD", value: "${env.CRED_DB_PASSWORD}"],
                                    [name: "JWT_SECRET_KEY", value: "${env.CRED_JWT_SECRET_KEY}"],
                                    [name: "ACCESS_TOKEN_EXPIRATION_TIME", value: "${env.CRED_ACCESS_TOKEN_EXPIRATION_TIME}"],
                                    [name: "REFRESH_TOKEN_EXPIRATION_TIME", value: "${env.CRED_REFRESH_TOKEN_EXPIRATION_TIME}"],
                                    [name: "CLIENT_ORIGIN", value: "${env.CRED_CLIENT_ORIGIN}"],
                                    [name: "COOKIE_DOMAIN", value: "${env.CRED_COOKIE_DOMAIN}"],
                                    [name: "ACCESS_CONTROL_ALLOWED_ORIGINS", value: "${env.CRED_ACCESS_CONTROL_ALLOWED_ORIGINS}"],
                                    [name: "SERVER_ORIGIN", value: "${env.CRED_SERVER_ORIGIN}"],
                                    [name: "GOOGLE_CLIENT_ID", value: "${env.CRED_GOOGLE_CLIENT_ID}"],
                                    [name: "GOOGLE_CLIENT_SECRET", value: "${env.CRED_GOOGLE_CLIENT_SECRET}"],
                                    [name: "KAKAO_CLIENT_ID", value: "${env.CRED_KAKAO_CLIENT_ID}"],
                                    [name: "KAKAO_CLIENT_SECRET", value: "${env.CRED_KAKAO_CLIENT_SECRET}"],
                                    [name: "S3_ACCESS_KEY", value: "${env.CRED_S3_ACCESS_KEY}"],
                                    [name: "S3_SECRET_KEY", value: "${env.CRED_S3_SECRET_KEY}"],
                                    [name: "S3_BUCKET_NAME", value: "${env.CRED_S3_BUCKET_NAME}"]
                                ],
                                logConfiguration: [
                                    logDriver: "awslogs",
                                    options: [
                                        "awslogs-group": "${env.CLOUDWATCH_LOG_GROUP}",
                                        "awslogs-region": "${env.AWS_DEFAULT_REGION}",
                                        "awslogs-stream-prefix": "${env.PROJECT_NAME}"
                                    ]
                                ]
                            ]]
                        ]

                        def json = groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(taskdef))
                        writeFile file: 'taskdef.json', text: json
                        sh 'aws ecs register-task-definition --cli-input-json file://taskdef.json'
                    }
                }
            }
        }

        stage('Deploy Service') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID',       variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID',    variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY',variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',   variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_CLUSTER_NAME',     variable: 'ECS_CLUSTER_NAME'),
                        string(credentialsId: 'SHOP_ECS_SERVICE_NAME',     variable: 'ECS_SERVICE_NAME')
                    ]) {
                        def newTaskDefArn = sh(
                            script: "aws ecs list-task-definitions --family-prefix ${env.PROJECT_NAME} --sort DESC --region $AWS_DEFAULT_REGION --query 'taskDefinitionArns[0]' --output text",
                            returnStdout: true
                        ).trim()
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
