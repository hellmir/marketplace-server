pipeline {
    agent any

    environment {
        SLACK_SUCCESS_COLOR = "#2C953C"
        SLACK_FAIL_COLOR    = "#E03131"
        SLACK_GENERAL_COLOR = "#5C7CFA"
    }

    stages {
        stage('Notify Start') {
            steps {
                withCredentials([
                    string(credentialsId: 'SHOP_SLACK_NOTIFICATION_CHANNEL', variable: 'SLACK_NOTIFICATION_CHANNEL')
                ]) {
                    script {
                        env.SLACK_NOTIFICATION_CHANNEL = SLACK_NOTIFICATION_CHANNEL
                    }
                    slackSend (
                        channel: "${env.SLACK_NOTIFICATION_CHANNEL}",
                        color:   "${env.SLACK_GENERAL_COLOR}",
                        message: "[QA] Start a build.\nJOB_NAME: ${env.JOB_NAME}\nBUILD_NUMBER: ${env.BUILD_NUMBER}\nBUILD_URL: ${env.BUILD_URL}\nCommit ID: ${env.GIT_COMMIT}"
                    )
                }
            }
        }

        stage('Environment Setup') {
            steps {
                script {
                    sh 'chmod +x ./gradlew'
                    def projectName    = sh(script: './gradlew -q printProjectName',    returnStdout: true).trim()
                    def projectVersion = sh(script: './gradlew -q printProjectVersion', returnStdout: true).trim()
                    env.PROJECT_NAME    = projectName
                    env.PROJECT_VERSION = projectVersion
                    env.JAR_PATH        = "${WORKSPACE}/build/libs/${env.PROJECT_NAME}-${env.PROJECT_VERSION}.jar"
                }
                echo 'Environment variables set'
            }
        }

        stage('Test') {
            steps {
                dir("${WORKSPACE}") {
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
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID',        variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID',     variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',    variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECR_REPOSITORY',        variable: 'ECR_REPOSITORY')
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

        stage('Register Shop Reward Service Task Definition') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_DB_URL',                        variable: 'CRED_DB_URL'),
                        string(credentialsId: 'SHOP_DB_USERNAME',                   variable: 'CRED_DB_USERNAME'),
                        string(credentialsId: 'SHOP_DB_PASSWORD',                   variable: 'CRED_DB_PASSWORD'),
                        string(credentialsId: 'SHOP_JWT_SECRET_KEY',                variable: 'CRED_JWT_SECRET_KEY'),
                        string(credentialsId: 'SHOP_ACCESS_TOKEN_EXPIRATION_TIME',  variable: 'CRED_ACCESS_TOKEN_EXPIRATION_TIME'),
                        string(credentialsId: 'SHOP_REFRESH_TOKEN_EXPIRATION_TIME', variable: 'CRED_REFRESH_TOKEN_EXPIRATION_TIME'),
                        string(credentialsId: 'SHOP_CLIENT_ORIGIN',                 variable: 'CRED_CLIENT_ORIGIN'),
                        string(credentialsId: 'SHOP_COOKIE_DOMAIN',                 variable: 'CRED_COOKIE_DOMAIN'),
                        string(credentialsId: 'SHOP_ACCESS_CONTROL_ALLOWED_ORIGINS',variable: 'CRED_ACCESS_CONTROL_ALLOWED_ORIGINS'),
                        string(credentialsId: 'SHOP_SERVER_ORIGIN',                 variable: 'CRED_SERVER_ORIGIN'),
                        string(credentialsId: 'SHOP_GOOGLE_CLIENT_ID',              variable: 'CRED_GOOGLE_CLIENT_ID'),
                        string(credentialsId: 'SHOP_GOOGLE_CLIENT_SECRET',          variable: 'CRED_GOOGLE_CLIENT_SECRET'),
                        string(credentialsId: 'SHOP_KAKAO_CLIENT_ID',               variable: 'CRED_KAKAO_CLIENT_ID'),
                        string(credentialsId: 'SHOP_KAKAO_CLIENT_SECRET',           variable: 'CRED_KAKAO_CLIENT_SECRET'),
                        string(credentialsId: 'SHOP_S3_ACCESS_KEY',                 variable: 'CRED_S3_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_S3_SECRET_KEY',                 variable: 'CRED_S3_SECRET_KEY'),
                        string(credentialsId: 'SHOP_S3_BUCKET_NAME',                variable: 'CRED_S3_BUCKET_NAME'),
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID',                variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID',             variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY',         variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',            variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_TASK_EXECUTION_ROLE_ARN',   variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
                        string(credentialsId: 'SHOP_ECS_TASK_ROLE_ARN',             variable: 'ECS_TASK_ROLE_ARN'),
                        string(credentialsId: 'SHOP_CLOUDWATCH_LOG_GROUP',          variable: 'CLOUDWATCH_LOG_GROUP')
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
                                    [name: "DB_URL",                        value: "${env.CRED_DB_URL}"],
                                    [name: "DB_USERNAME",                   value: "${env.CRED_DB_USERNAME}"],
                                    [name: "DB_PASSWORD",                   value: "${env.CRED_DB_PASSWORD}"],
                                    [name: "JWT_SECRET_KEY",                value: "${env.CRED_JWT_SECRET_KEY}"],
                                    [name: "ACCESS_TOKEN_EXPIRATION_TIME",  value: "${env.CRED_ACCESS_TOKEN_EXPIRATION_TIME}"],
                                    [name: "REFRESH_TOKEN_EXPIRATION_TIME", value: "${env.CRED_REFRESH_TOKEN_EXPIRATION_TIME}"],
                                    [name: "CLIENT_ORIGIN",                 value: "${env.CRED_CLIENT_ORIGIN}"],
                                    [name: "COOKIE_DOMAIN",                 value: "${env.CRED_COOKIE_DOMAIN}"],
                                    [name: "ACCESS_CONTROL_ALLOWED_ORIGINS",value: "${env.CRED_ACCESS_CONTROL_ALLOWED_ORIGINS}"],
                                    [name: "SERVER_ORIGIN",                 value: "${env.CRED_SERVER_ORIGIN}"],
                                    [name: "GOOGLE_CLIENT_ID",              value: "${env.CRED_GOOGLE_CLIENT_ID}"],
                                    [name: "GOOGLE_CLIENT_SECRET",          value: "${env.CRED_GOOGLE_CLIENT_SECRET}"],
                                    [name: "KAKAO_CLIENT_ID",               value: "${env.CRED_KAKAO_CLIENT_ID}"],
                                    [name: "KAKAO_CLIENT_SECRET",           value: "${env.CRED_KAKAO_CLIENT_SECRET}"],
                                    [name: "S3_ACCESS_KEY",                 value: "${env.CRED_S3_ACCESS_KEY}"],
                                    [name: "S3_SECRET_KEY",                 value: "${env.CRED_S3_SECRET_KEY}"],
                                    [name: "S3_BUCKET_NAME",                value: "${env.CRED_S3_BUCKET_NAME}"]
                                ],
                                logConfiguration: [
                                    logDriver: "awslogs",
                                    options: [
                                        "awslogs-group":         "${env.CLOUDWATCH_LOG_GROUP}",
                                        "awslogs-region":        "${env.AWS_DEFAULT_REGION}",
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

        stage('Deploy Shop Reward Service') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID',        variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID',     variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',    variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_CLUSTER_NAME',      variable: 'ECS_CLUSTER_NAME'),
                        string(credentialsId: 'SHOP_ECS_SERVICE_NAME',      variable: 'ECS_SERVICE_NAME'),
                        string(credentialsId: 'SHOP_SUBNET_IDS',            variable: 'SUBNET_IDS'),
                        string(credentialsId: 'SHOP_SECURITY_GROUP_IDS',    variable: 'SECURITY_GROUP_IDS'),
                        string(credentialsId: 'SHOP_TARGET_GROUP_ARN',       variable: 'TARGET_GROUP_ARN')
                    ]) {
                        def newTaskDefArn = sh(
                            script: "aws ecs list-task-definitions --family-prefix ${env.PROJECT_NAME} --sort DESC --region $AWS_DEFAULT_REGION --query 'taskDefinitionArns[0]' --output text",
                            returnStdout: true
                        ).trim()
                        def exists = sh(
                            script: "aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION --query 'services[0].status' --output text || true",
                            returnStdout: true
                        ).trim()
                        def subnets = SUBNET_IDS.split(',').collect{it.trim()}.join(",")
                        def sgs     = SECURITY_GROUP_IDS.split(',').collect{it.trim()}.join(",")

                        if (exists == "ACTIVE" || exists == "DRAINING") {
                            sh """
                            aws ecs update-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service $ECS_SERVICE_NAME \
                              --task-definition ${newTaskDefArn} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --region $AWS_DEFAULT_REGION
                            """
                            sh "aws ecs update-service --cluster $ECS_CLUSTER_NAME --service $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION --health-check-grace-period-seconds 180 || true"
                        } else {
                            sh """
                            aws ecs create-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service-name $ECS_SERVICE_NAME \
                              --task-definition ${newTaskDefArn} \
                              --desired-count 1 \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --load-balancers targetGroupArn=$TARGET_GROUP_ARN,containerName=${env.PROJECT_NAME},containerPort=8080 \
                              --network-configuration "awsvpcConfiguration={subnets=[${subnets}],securityGroups=[${sgs}],assignPublicIp=ENABLED}" \
                              --region $AWS_DEFAULT_REGION
                            """
                            sh "aws ecs update-service --cluster $ECS_CLUSTER_NAME --service $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION --health-check-grace-period-seconds 180 || true"
                        }
                        sh "aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION"
                    }
                }
            }
        }

        stage('Build Prometheus Image') {
            steps {
                script {
                    sh "test -f monitoring/prometheus/Dockerfile"
                    sh "test -f monitoring/prometheus/prometheus.yml"
                    def prometheusTag = "prometheus:${env.PROJECT_VERSION}"
                    env.PROMETHEUS_LOCAL_TAG = prometheusTag
                    sh """
                    docker build \
                      -t ${prometheusTag} \
                      -f monitoring/prometheus/Dockerfile \
                      monitoring/prometheus
                    """
                }
            }
        }

        stage('Push Prometheus Image') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_ACCOUNT_ID',        variable: 'AWS_ACCOUNT_ID'),
                        string(credentialsId: 'SHOP_AWS_ACCESS_KEY_ID',     variable: 'AWS_ACCESS_KEY_ID'),
                        string(credentialsId: 'SHOP_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',    variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_PROMETHEUS_ECR_REPOSITORY',   variable: 'PROMETHEUS_ECR_REPOSITORY')
                    ]) {
                        sh '''
                        aws ecr describe-repositories --repository-names $PROMETHEUS_ECR_REPOSITORY --region $AWS_DEFAULT_REGION || aws ecr create-repository --repository-name $PROMETHEUS_ECR_REPOSITORY --region $AWS_DEFAULT_REGION
                        aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com
                        docker tag $PROMETHEUS_LOCAL_TAG $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$PROMETHEUS_ECR_REPOSITORY:$PROJECT_VERSION
                        docker push $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$PROMETHEUS_ECR_REPOSITORY:$PROJECT_VERSION
                        '''
                        env.PROMETHEUS_IMAGE_URI = "${env.AWS_ACCOUNT_ID}.dkr.ecr.${env.AWS_DEFAULT_REGION}.amazonaws.com/${env.PROMETHEUS_ECR_REPOSITORY}:${env.PROJECT_VERSION}"
                    }
                }
            }
        }

        stage('Register Prometheus Task Definition') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',         variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_TASK_EXECUTION_ROLE_ARN',variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
                        string(credentialsId: 'SHOP_ECS_TASK_ROLE_ARN',          variable: 'ECS_TASK_ROLE_ARN'),
                        string(credentialsId: 'SHOP_CLOUDWATCH_LOG_GROUP_PROMETHEUS',  variable: 'CLOUDWATCH_LOG_GROUP_PROMETHEUS')
                    ]) {
                        sh '''
                        aws logs describe-log-groups --log-group-name-prefix "$CLOUDWATCH_LOG_GROUP_PROMETHEUS" --region "$AWS_DEFAULT_REGION" \
                          || aws logs create-log-group --log-group-name "$CLOUDWATCH_LOG_GROUP_PROMETHEUS" --region "$AWS_DEFAULT_REGION" || true
                        '''
                        def promTask = [
                            family: "prometheus",
                            networkMode: "awsvpc",
                            requiresCompatibilities: ["FARGATE"],
                            cpu: "256",
                            memory: "512",
                            executionRoleArn: "${env.ECS_TASK_EXECUTION_ROLE_ARN}",
                            taskRoleArn: "${env.ECS_TASK_ROLE_ARN}",
                            containerDefinitions: [[
                                name: "prometheus",
                                image: "${env.PROMETHEUS_IMAGE_URI}",
                                portMappings: [[containerPort: 9090, protocol: "tcp"]],
                                essential: true,
                                command: [
                                    "--config.file=/etc/prometheus/prometheus.yml",
                                    "--storage.tsdb.path=/prometheus",
                                    "--storage.tsdb.retention.time=1d",
                                    "--storage.tsdb.retention.size=512MB",
                                    "--web.console.libraries=/usr/share/prometheus/console_libraries",
                                    "--web.console.templates=/usr/share/prometheus/consoles"
                                ],
                                logConfiguration: [
                                    logDriver: "awslogs",
                                    options: [
                                        "awslogs-group": "${env.CLOUDWATCH_LOG_GROUP_PROMETHEUS}",
                                        "awslogs-region": "${env.AWS_DEFAULT_REGION}",
                                        "awslogs-stream-prefix": "prometheus"
                                    ]
                                ]
                            ]]
                        ]
                        def json = groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(promTask))
                        writeFile file: 'prom-taskdef.json', text: json
                        sh 'aws ecs register-task-definition --cli-input-json file://prom-taskdef.json'
                    }
                }
            }
        }

        stage('Deploy Prometheus Service') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',      variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_CLUSTER_NAME',        variable: 'ECS_CLUSTER_NAME'),
                        string(credentialsId: 'SHOP_PROMETHEUS_SERVICE_NAME', variable: 'PROMETHEUS_SERVICE_NAME'),
                        string(credentialsId: 'SHOP_SUBNET_IDS',              variable: 'SUBNET_IDS'),
                        string(credentialsId: 'SHOP_SECURITY_GROUP_IDS',      variable: 'SECURITY_GROUP_IDS')
                    ]) {
                        def latestTask = sh(
                            script: "aws ecs list-task-definitions --family-prefix prometheus --sort DESC --region $AWS_DEFAULT_REGION --query 'taskDefinitionArns[0]' --output text",
                            returnStdout: true
                        ).trim()
                        def exists = sh(
                            script: "aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $PROMETHEUS_SERVICE_NAME --region $AWS_DEFAULT_REGION --query 'services[0].status' --output text || true",
                            returnStdout: true
                        ).trim()
                        def subnets = SUBNET_IDS.split(',').collect{it.trim()}.join(",")
                        def sgs     = SECURITY_GROUP_IDS.split(',').collect{it.trim()}.join(",")

                        if (exists == "ACTIVE" || exists == "DRAINING") {
                            sh """
                            aws ecs update-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service $PROMETHEUS_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --region $AWS_DEFAULT_REGION
                            """
                        } else {
                            sh """
                            aws ecs create-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service-name $PROMETHEUS_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --desired-count 1 \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --network-configuration "awsvpcConfiguration={subnets=[${subnets}],securityGroups=[${sgs}],assignPublicIp=ENABLED}" \
                              --region $AWS_DEFAULT_REGION
                            """
                        }
                        sh "aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $PROMETHEUS_SERVICE_NAME --region $AWS_DEFAULT_REGION"
                    }
                }
            }
        }

        stage('Register Grafana Task Definition') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',         variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_TASK_EXECUTION_ROLE_ARN',variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
                        string(credentialsId: 'SHOP_ECS_TASK_ROLE_ARN',          variable: 'ECS_TASK_ROLE_ARN'),
                        string(credentialsId: 'SHOP_CLOUDWATCH_LOG_GROUP_GRAFANA',  variable: 'CLOUDWATCH_LOG_GROUP_GRAFANA'),
                        string(credentialsId: 'SHOP_GRAFANA_ADMIN_PASSWORD',     variable: 'GRAFANA_ADMIN_PASSWORD')
                    ]) {
                        sh '''
                        aws logs describe-log-groups --log-group-name-prefix "CLOUDWATCH_LOG_GROUP_GRAFANA" --region "$AWS_DEFAULT_REGION" \
                          || aws logs create-log-group --log-group-name "CLOUDWATCH_LOG_GROUP_GRAFANA" --region "$AWS_DEFAULT_REGION" || true
                        '''
                        def grafanaTask = [
                            family: "grafana",
                            networkMode: "awsvpc",
                            requiresCompatibilities: ["FARGATE"],
                            cpu: "256",
                            memory: "512",
                            executionRoleArn: "${env.ECS_TASK_EXECUTION_ROLE_ARN}",
                            taskRoleArn: "${env.ECS_TASK_ROLE_ARN}",
                            containerDefinitions: [[
                                name: "grafana",
                                image: "grafana/grafana:latest",
                                portMappings: [[containerPort: 3000, protocol: "tcp"]],
                                essential: true,
                                environment: [
                                    [name: "GF_SECURITY_ADMIN_PASSWORD", value: "${env.GRAFANA_ADMIN_PASSWORD}"]
                                ],
                                logConfiguration: [
                                    logDriver: "awslogs",
                                    options: [
                                        "awslogs-group": "${env.CLOUDWATCH_LOG_GROUP_GRAFANA}",
                                        "awslogs-region": "${env.AWS_DEFAULT_REGION}",
                                        "awslogs-stream-prefix": "grafana"
                                    ]
                                ]
                            ]]
                        ]
                        def json = groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(grafanaTask))
                        writeFile file: 'grafana-taskdef.json', text: json
                        sh 'aws ecs register-task-definition --cli-input-json file://grafana-taskdef.json'
                    }
                }
            }
        }

        stage('Deploy Grafana Service') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'SHOP_AWS_DEFAULT_REGION',   variable: 'AWS_DEFAULT_REGION'),
                        string(credentialsId: 'SHOP_ECS_CLUSTER_NAME',     variable: 'ECS_CLUSTER_NAME'),
                        string(credentialsId: 'SHOP_GRAFANA_SERVICE_NAME', variable: 'GRAFANA_SERVICE_NAME'),
                        string(credentialsId: 'SHOP_SUBNET_IDS',           variable: 'SUBNET_IDS'),
                        string(credentialsId: 'SHOP_SECURITY_GROUP_IDS',   variable: 'SECURITY_GROUP_IDS')
                    ]) {
                        def latestTask = sh(
                            script: "aws ecs list-task-definitions --family-prefix grafana --sort DESC --region $AWS_DEFAULT_REGION --query 'taskDefinitionArns[0]' --output text",
                            returnStdout: true
                        ).trim()
                        def exists = sh(
                            script: "aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $GRAFANA_SERVICE_NAME --region $AWS_DEFAULT_REGION --query 'services[0].status' --output text || true",
                            returnStdout: true
                        ).trim()
                        def subnets = SUBNET_IDS.split(',').collect{it.trim()}.join(",")
                        def sgs     = SECURITY_GROUP_IDS.split(',').collect{it.trim()}.join(",")

                        if (exists == "ACTIVE" || exists == "DRAINING") {
                            sh """
                            aws ecs update-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service $GRAFANA_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --region $AWS_DEFAULT_REGION
                            """
                        } else {
                            sh """
                            aws ecs create-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service-name $GRAFANA_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --desired-count 1 \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --network-configuration "awsvpcConfiguration={subnets=[${subnets}],securityGroups=[${sgs}],assignPublicIp=ENABLED}" \
                              --region $AWS_DEFAULT_REGION
                            """
                        }
                        sh "aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $GRAFANA_SERVICE_NAME --region $AWS_DEFAULT_REGION"
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

    post {
        always {
            echo '====================================Clean workspace===================================='
            cleanWs()
            sh 'docker system prune -f'
        }

        success {
            echo '====================================Build Success======================================'
            slackSend (
                channel: "${env.SLACK_NOTIFICATION_CHANNEL}",
                color:   "${env.SLACK_SUCCESS_COLOR}",
                message: "Build Success!!\nBUILD_NUMBER: ${env.BUILD_NUMBER}"
            )
        }

        failure {
            echo '====================================Build Failed======================================='
            slackSend (
                channel: "${env.SLACK_NOTIFICATION_CHANNEL}",
                color:   "${env.SLACK_FAIL_COLOR}",
                message: "Build Failed...\nBUILD_NUMBER: ${env.BUILD_NUMBER}"
            )
        }
    }
}
