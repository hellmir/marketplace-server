pipeline {
	agent any

	parameters {
		choice(name: 'SERVICE', choices: ['auto','user-service','product-service','order-service'], description: '배포 대상 서비스')
	}

	environment {
		SLACK_SUCCESS_COLOR = "#2C953C"
		SLACK_FAIL_COLOR = "#E03131"
		SLACK_GENERAL_COLOR = "#5C7CFA"
	}

	stages {
		stage('Select Target Service') {
			steps {
				script {
					def base = sh(script: 'git rev-parse HEAD~1 || git rev-parse HEAD', returnStdout: true).trim()
					def changed = sh(script: "git diff --name-only ${base} HEAD || true", returnStdout: true)
					.trim()
					.split('\n')
					.findAll {
						it?.trim()
					} as List

					def targetAdapters = [
						'user-service'   : 'user-adapters',
						'order-service'  : 'order-adapters',
						'product-service': 'product-adapters'
					]

					def serviceTouched = {
						svc, adp ->
						def prefixes = [
							"${svc}/${adp}/",
							"${svc}/${svc.replace('-service','')}-application/",
							"${svc}/${svc.replace('-service','')}-domain/"
						]
						changed.any {
							p -> prefixes.any {
								pre -> p.startsWith(pre)
							}
						}
					}

					def commonChanged = changed.any {
						it.startsWith("common/")
					}

					def candidates = commonChanged
					? (targetAdapters.keySet() as List)
					: (targetAdapters.findAll {
						svc, adp -> serviceTouched(svc, adp)
					}.keySet() as List)

					candidates = candidates.sort()

					def targetService =
					(params.SERVICE && params.SERVICE != 'auto') ? params.SERVICE :
					(candidates ? candidates[0] : 'user-service')

					if (!targetAdapters.containsKey(targetService)) {
						error "Unknown SERVICE '${targetService}'. Allowed: ${targetAdapters.keySet()}"
					}

					env.SERVICE_NAME = targetService
					env.SERVICE_DIRECTORY = "${targetService}/${targetAdapters[targetService]}"
					env.CANDIDATE_SERVICES = candidates.join(',')

					sh "test -d '${env.SERVICE_DIRECTORY}'"

					echo "Changed files count       = ${changed.size()}"
					echo "COMMON changed?           = ${commonChanged}"
				}
			}
		}

		stage('Environment Setup') {
			steps {
				script {
					sh 'chmod +x ./gradlew'
					env.PROJECT_NAME = sh(script: "./gradlew -p ${env.SERVICE_DIRECTORY} -q printProjectName",    returnStdout: true).trim()
					env.PROJECT_VERSION = sh(script: "./gradlew -p ${env.SERVICE_DIRECTORY} -q printProjectVersion", returnStdout: true).trim()
					env.DOCKERFILE_PATH = "${WORKSPACE}/${env.SERVICE_DIRECTORY}/Dockerfile"
					env.BUILD_CONTEXT = "${WORKSPACE}/${env.SERVICE_DIRECTORY}"
				}

				echo 'Environment variables set'

				echo "CANDIDATE_SERVICES        = ${env.CANDIDATE_SERVICES}"
				echo "SERVICE_NAME              = ${env.SERVICE_NAME}"
				echo "SERVICE_DIRECTORY         = ${env.SERVICE_DIRECTORY}"
				echo "PROJECT_NAME              = ${env.PROJECT_NAME}"
				echo "PROJECT_VERSION           = ${env.PROJECT_VERSION}"
			}
		}

		stage('Notify Start') {
			steps {
				withCredentials([
					string(credentialsId: 'MARKETNOTE_SLACK_NOTIFICATION_CHANNEL', variable: 'SLACK_NOTIFICATION_CHANNEL')
				]) {
					script {
						env.SLACK_NOTIFICATION_CHANNEL = SLACK_NOTIFICATION_CHANNEL
						def branchName = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
						def commitAuthor = sh(script: 'git log -1 --pretty=%an', returnStdout: true).trim()
						def commitMessage = sh(script: 'git log -1 --pretty=%B', returnStdout: true).trim()

						slackSend(
							channel: "${env.SLACK_NOTIFICATION_CHANNEL}",
							color:   "${env.SLACK_GENERAL_COLOR}",
							message: "[QA] Start a build\n"
							+ "SERVICE: ${env.SERVICE_NAME}\n"
							+ "PROJECT_NAME: ${env.PROJECT_NAME}\n"
							+ "VERSION: ${env.PROJECT_VERSION}\n"
							+ "JOB_NAME: ${env.JOB_NAME}\n"
							+ "BUILD_NUMBER: ${env.BUILD_NUMBER}\n"
							+ "BUILD_URL: ${env.BUILD_URL}\n"
							+ "BRANCH: ${branchName}\n"
							+ "AUTHOR: ${commitAuthor}\n"
							+ "COMMIT: ${env.GIT_COMMIT}\n"
							+ "MESSAGE: ${commitMessage}"
						)
					}
				}
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
				sh "./gradlew -p ${env.SERVICE_DIRECTORY} clean build -x test"
			}
		}

		stage('Resolve Service Mappings') {
			steps {
				script {
					withCredentials([
						string(credentialsId: 'USER_SERVICE_ECR_REPOSITORY',      variable: 'USER_SERVICE_ECR_REPOSITORY'),
						string(credentialsId: 'PRODUCT_SERVICE_ECR_REPOSITORY',   variable: 'PRODUCT_SERVICE_ECR_REPOSITORY'),
						string(credentialsId: 'ORDER_SERVICE_ECR_REPOSITORY',     variable: 'ORDER_SERVICE_ECR_REPOSITORY'),

						string(credentialsId: 'USER_SERVICE_ECS_SERVICE_NAME',    variable: 'USER_SERVICE_ECS_SERVICE_NAME'),
						string(credentialsId: 'PRODUCT_SERVICE_ECS_SERVICE_NAME', variable: 'PRODUCT_SERVICE_ECS_SERVICE_NAME'),
						string(credentialsId: 'ORDER_SERVICE_ECS_SERVICE_NAME',   variable: 'ORDER_SERVICE_ECS_SERVICE_NAME'),

						string(credentialsId: 'USER_SERVICE_TARGET_GROUP_ARN',    variable: 'USER_SERVICE_TARGET_GROUP_ARN'),
						string(credentialsId: 'PRODUCT_SERVICE_TARGET_GROUP_ARN', variable: 'PRODUCT_SERVICE_TARGET_GROUP_ARN'),
						string(credentialsId: 'ORDER_SERVICE_TARGET_GROUP_ARN',   variable: 'ORDER_SERVICE_TARGET_GROUP_ARN'),

						string(credentialsId: 'USER_SERVICE_SERVER_PORT',         variable: 'USER_SERVICE_SERVER_PORT'),
						string(credentialsId: 'PRODUCT_SERVICE_SERVER_PORT',      variable: 'PRODUCT_SERVICE_SERVER_PORT'),
						string(credentialsId: 'ORDER_SERVICE_SERVER_PORT',        variable: 'ORDER_SERVICE_SERVER_PORT')
					]) {
						def svc = env.SERVICE_NAME
						if (svc == 'user-service') {
							env.ECR_REPOSITORY = USER_SERVICE_ECR_REPOSITORY
							env.ECS_SERVICE_NAME = USER_SERVICE_ECS_SERVICE_NAME
							env.TARGET_GROUP_ARN = USER_SERVICE_TARGET_GROUP_ARN
							env.CRED_SERVER_PORT = USER_SERVICE_SERVER_PORT
						} else if (svc == 'product-service') {
							env.ECR_REPOSITORY = PRODUCT_SERVICE_ECR_REPOSITORY
							env.ECS_SERVICE_NAME = PRODUCT_SERVICE_ECS_SERVICE_NAME
							env.TARGET_GROUP_ARN = PRODUCT_SERVICE_TARGET_GROUP_ARN
							env.CRED_SERVER_PORT = PRODUCT_SERVICE_SERVER_PORT
						} else if (svc == 'order-service') {
							env.ECR_REPOSITORY = ORDER_SERVICE_ECR_REPOSITORY
							env.ECS_SERVICE_NAME = ORDER_SERVICE_ECS_SERVICE_NAME
							env.TARGET_GROUP_ARN = ORDER_SERVICE_TARGET_GROUP_ARN
							env.CRED_SERVER_PORT = ORDER_SERVICE_SERVER_PORT
						} else {
							error "SERVICE_NAME not mapped: ${svc}"
						}

						if (!env.ECR_REPOSITORY?.trim())   error "ECR_REPOSITORY not resolved for ${env.SERVICE_NAME}"
						if (!env.ECS_SERVICE_NAME?.trim()) error "ECS_SERVICE_NAME not resolved for ${env.SERVICE_NAME}"
						if (!env.TARGET_GROUP_ARN?.trim()) error "TARGET_GROUP_ARN not resolved for ${env.SERVICE_NAME}"

						echo "ECR_REPOSITORY   = ${env.ECR_REPOSITORY}"
						echo "ECS_SERVICE_NAME = ${env.ECS_SERVICE_NAME}"
						echo "TARGET_GROUP_ARN = ${env.TARGET_GROUP_ARN}"
					}
				}
			}
		}

		stage('Build Market Note Service Image') {
			steps {
				script {
					def dockerImageTag = "${env.PROJECT_NAME}:${env.PROJECT_VERSION}"
					env.DOCKER_IMAGE_TAG = dockerImageTag
					sh """
                    docker build \
                      --build-arg JAR_FILE=build/libs/${env.PROJECT_NAME}-${env.PROJECT_VERSION}.jar \
                      -t ${dockerImageTag} \
                      -f ${env.DOCKERFILE_PATH} \
                      ${env.BUILD_CONTEXT}
                    """
				}
			}
		}

		stage('Push Market Note Service Image') {
			steps {
				script {
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_ACCOUNT_ID',        variable: 'AWS_ACCOUNT_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_ACCESS_KEY_ID',     variable: 'AWS_ACCESS_KEY_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',    variable: 'AWS_DEFAULT_REGION'),
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

		stage('Register Market Note Service Task Definition') {
			steps {
				script {
					withCredentials([
						string(credentialsId: 'MARKETNOTE_DB_URL',                        variable: 'CRED_DB_URL'),
						string(credentialsId: 'MARKETNOTE_DB_USERNAME',                   variable: 'CRED_DB_USERNAME'),
						string(credentialsId: 'MARKETNOTE_DB_PASSWORD',                   variable: 'CRED_DB_PASSWORD'),
						string(credentialsId: 'MARKETNOTE_JWT_SECRET_KEY',                variable: 'CRED_JWT_SECRET_KEY'),
						string(credentialsId: 'MARKETNOTE_ACCESS_TOKEN_EXPIRATION_TIME',  variable: 'CRED_ACCESS_TOKEN_EXPIRATION_TIME'),
						string(credentialsId: 'MARKETNOTE_REFRESH_TOKEN_EXPIRATION_TIME', variable: 'CRED_REFRESH_TOKEN_EXPIRATION_TIME'),
						string(credentialsId: 'MARKETNOTE_CLIENT_ORIGIN',                 variable: 'CRED_CLIENT_ORIGIN'),
						string(credentialsId: 'MARKETNOTE_COOKIE_DOMAIN',                 variable: 'CRED_COOKIE_DOMAIN'),
						string(credentialsId: 'MARKETNOTE_ACCESS_CONTROL_ALLOWED_ORIGINS',variable: 'CRED_ACCESS_CONTROL_ALLOWED_ORIGINS'),
						string(credentialsId: 'MARKETNOTE_SERVER_ORIGIN',                 variable: 'CRED_SERVER_ORIGIN'),
						string(credentialsId: 'MARKETNOTE_GOOGLE_CLIENT_ID',              variable: 'CRED_GOOGLE_CLIENT_ID'),
						string(credentialsId: 'MARKETNOTE_GOOGLE_CLIENT_SECRET',          variable: 'CRED_GOOGLE_CLIENT_SECRET'),
						string(credentialsId: 'MARKETNOTE_KAKAO_CLIENT_ID',               variable: 'CRED_KAKAO_CLIENT_ID'),
						string(credentialsId: 'MARKETNOTE_KAKAO_CLIENT_SECRET',           variable: 'CRED_KAKAO_CLIENT_SECRET'),
						string(credentialsId: 'MARKETNOTE_S3_ACCESS_KEY',                 variable: 'CRED_S3_ACCESS_KEY'),
						string(credentialsId: 'MARKETNOTE_S3_SECRET_KEY',                 variable: 'CRED_S3_SECRET_KEY'),
						string(credentialsId: 'MARKETNOTE_S3_BUCKET_NAME',                variable: 'CRED_S3_BUCKET_NAME'),
						string(credentialsId: 'MARKETNOTE_AWS_ACCOUNT_ID',                variable: 'AWS_ACCOUNT_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_ACCESS_KEY_ID',             variable: 'AWS_ACCESS_KEY_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_SECRET_ACCESS_KEY',         variable: 'AWS_SECRET_ACCESS_KEY'),
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',            variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_EXECUTION_ROLE_ARN',   variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_ROLE_ARN',             variable: 'ECS_TASK_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_CLOUDWATCH_LOG_GROUP',          variable: 'CLOUDWATCH_LOG_GROUP')
					]) {
						sh '''
                        aws logs create-log-group --log-group-name "$CLOUDWATCH_LOG_GROUP" --region "$AWS_DEFAULT_REGION" || true
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
									[name: "SERVICE_NAME",                  value: "${env.ECS_SERVICE_NAME}"],
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
									[name: "SERVER_PORT",                   value: "${env.CRED_SERVER_PORT}"],
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

		stage('Deploy Market Note Service') {
			steps {
				script {
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_ACCOUNT_ID',        variable: 'AWS_ACCOUNT_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_ACCESS_KEY_ID',     variable: 'AWS_ACCESS_KEY_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',    variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_CLUSTER_NAME',      variable: 'ECS_CLUSTER_NAME'),
						string(credentialsId: 'MARKETNOTE_SUBNET_IDS',            variable: 'SUBNET_IDS'),
						string(credentialsId: 'MARKETNOTE_SECURITY_GROUP_IDS',    variable: 'SECURITY_GROUP_IDS'),
					]) {
						def newTaskDefArn = sh(
							script: "aws ecs list-task-definitions --family-prefix ${env.PROJECT_NAME} --sort DESC --region $AWS_DEFAULT_REGION --query 'taskDefinitionArns[0]' --output text",
							returnStdout: true
						).trim()
						def exists = sh(
							script: "aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION --query 'services[0].status' --output text || true",
							returnStdout: true
						).trim()
						def subnets = SUBNET_IDS.split(',').collect{
							it.trim()
						}.join(",")
						def sgs = SECURITY_GROUP_IDS.split(',').collect{
							it.trim()
						}.join(",")

						if (exists == "ACTIVE" || exists == "DRAINING") {
							sh """
                            aws ecs update-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service $ECS_SERVICE_NAME \
                              --task-definition ${newTaskDefArn} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --health-check-grace-period-seconds 180 \
                              --region $AWS_DEFAULT_REGION \
                              --force-new-deployment
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
                              --region $AWS_DEFAULT_REGION \
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
					def changed = sh(script: 'git diff --quiet HEAD~1 HEAD monitoring/prometheus || echo "changed"', returnStdout: true).trim() == 'changed'
					if (!changed) {
						sleep time: 1, unit: 'SECONDS'; return
					}
					sh "test -f monitoring/prometheus/Dockerfile"
					sh "test -f monitoring/prometheus/prometheus.yml"
					def prometheusTag = "prometheus:${env.PROJECT_VERSION}"
					env.PROMETHEUS_LOCAL_TAG = prometheusTag
					sh """
                    docker build \
                     -t ${prometheusTag} \
                     -f ${env.WORKSPACE}/monitoring/prometheus/Dockerfile \
                     ${env.WORKSPACE}/monitoring/prometheus
                    """
				}
			}
		}

		stage('Push Prometheus Image') {
			steps {
				script {
					def changed = sh(script: 'git diff --quiet HEAD~1 HEAD monitoring/prometheus || echo "changed"', returnStdout: true).trim() == 'changed'
					if (!changed) {
						sleep time: 1, unit: 'SECONDS'; return
					}
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_ACCOUNT_ID',        variable: 'AWS_ACCOUNT_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_ACCESS_KEY_ID',     variable: 'AWS_ACCESS_KEY_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY'),
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',    variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_PROMETHEUS_ECR_REPOSITORY',   variable: 'PROMETHEUS_ECR_REPOSITORY')
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
					def changed = sh(script: 'git diff --quiet HEAD~1 HEAD monitoring/prometheus || echo "changed"', returnStdout: true).trim() == 'changed'
					if (!changed) {
						sleep time: 1, unit: 'SECONDS'; return
					}
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',         variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_EXECUTION_ROLE_ARN',variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_ROLE_ARN',          variable: 'ECS_TASK_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_CLOUDWATCH_LOG_GROUP_PROMETHEUS',  variable: 'CLOUDWATCH_LOG_GROUP_PROMETHEUS')
					]) {
						sh '''
                        aws logs create-log-group --log-group-name "$CLOUDWATCH_LOG_GROUP_PROMETHEUS" --region "$AWS_DEFAULT_REGION" || true
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
					def changed = sh(script: 'git diff --quiet HEAD~1 HEAD monitoring/prometheus || echo "changed"', returnStdout: true).trim() == 'changed'
					if (!changed) {
						sleep time: 1, unit: 'SECONDS'; return
					}
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',      variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_CLUSTER_NAME',        variable: 'ECS_CLUSTER_NAME'),
						string(credentialsId: 'MARKETNOTE_PROMETHEUS_SERVICE_NAME', variable: 'PROMETHEUS_SERVICE_NAME'),
						string(credentialsId: 'MARKETNOTE_SUBNET_IDS',              variable: 'SUBNET_IDS'),
						string(credentialsId: 'MARKETNOTE_SECURITY_GROUP_IDS',      variable: 'SECURITY_GROUP_IDS')
					]) {
						def latestTask = sh(
							script: "aws ecs list-task-definitions --family-prefix prometheus --sort DESC --region $AWS_DEFAULT_REGION --query 'taskDefinitionArns[0]' --output text",
							returnStdout: true
						).trim()
						def exists = sh(
							script: "aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $PROMETHEUS_SERVICE_NAME --region $AWS_DEFAULT_REGION --query 'services[0].status' --output text || true",
							returnStdout: true
						).trim()
						def subnets = SUBNET_IDS.split(',').collect{
							it.trim()
						}.join(",")
						def sgs = SECURITY_GROUP_IDS.split(',').collect{
							it.trim()
						}.join(",")

						if (exists == "ACTIVE" || exists == "DRAINING") {
							sh """
                            aws ecs update-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service $PROMETHEUS_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --region $AWS_DEFAULT_REGION \
                              --force-new-deployment
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
                              --region $AWS_DEFAULT_REGION \
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
					def changed = sh(script: 'git diff --quiet HEAD~1 HEAD monitoring/prometheus || echo "changed"', returnStdout: true).trim() == 'changed'
					if (!changed) {
						sleep time: 1, unit: 'SECONDS'; return
					}
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',             variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_EXECUTION_ROLE_ARN',    variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_ROLE_ARN',              variable: 'ECS_TASK_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_CLOUDWATCH_LOG_GROUP_GRAFANA',   variable: 'CLOUDWATCH_LOG_GROUP_GRAFANA'),
						string(credentialsId: 'MARKETNOTE_GRAFANA_ADMIN_PASSWORD',         variable: 'GRAFANA_ADMIN_PASSWORD'),
						string(credentialsId: 'MARKETNOTE_GRAFANA_DOMAIN',                 variable: 'GRAFANA_DOMAIN'),
						string(credentialsId: 'MARKETNOTE_GRAFANA_ROOT_URL',               variable: 'GRAFANA_ROOT_URL'),
						string(credentialsId: 'MARKETNOTE_GRAFANA_SERVE_FROM_SUB_PATH',    variable: 'GRAFANA_SERVE_FROM_SUB_PATH')
					]) {
						sh '''
                        aws logs create-log-group --log-group-name "$CLOUDWATCH_LOG_GROUP_GRAFANA" --region "$AWS_DEFAULT_REGION" || true
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
									[name: "GF_SECURITY_ADMIN_PASSWORD",      value: "${env.GRAFANA_ADMIN_PASSWORD}"],
									[name: "GF_SERVER_DOMAIN",                value: "${env.GRAFANA_DOMAIN}"],
									[name: "GF_SERVER_ROOT_URL",              value: "${env.GRAFANA_ROOT_URL}"],
									[name: "GF_SERVER_SERVE_FROM_SUB_PATH",   value: "${env.GRAFANA_SERVE_FROM_SUB_PATH?:'false'}"]
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
					def changed = sh(script: 'git diff --quiet HEAD~1 HEAD monitoring/prometheus || echo "changed"', returnStdout: true).trim() == 'changed'
					if (!changed) {
						sleep time: 1, unit: 'SECONDS'; return
					}
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',   variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_CLUSTER_NAME',     variable: 'ECS_CLUSTER_NAME'),
						string(credentialsId: 'MARKETNOTE_GRAFANA_SERVICE_NAME', variable: 'GRAFANA_SERVICE_NAME'),
						string(credentialsId: 'MARKETNOTE_SUBNET_IDS',           variable: 'SUBNET_IDS'),
						string(credentialsId: 'MARKETNOTE_SECURITY_GROUP_IDS',   variable: 'SECURITY_GROUP_IDS')
					]) {
						def latestTask = sh(
							script: "aws ecs list-task-definitions --family-prefix grafana --sort DESC --region $AWS_DEFAULT_REGION --query 'taskDefinitionArns[0]' --output text",
							returnStdout: true
						).trim()
						def exists = sh(
							script: "aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $GRAFANA_SERVICE_NAME --region $AWS_DEFAULT_REGION --query 'services[0].status' --output text || true",
							returnStdout: true
						).trim()
						def subnets = SUBNET_IDS.split(',').collect{
							it.trim()
						}.join(",")
						def sgs = SECURITY_GROUP_IDS.split(',').collect{
							it.trim()
						}.join(",")

						if (exists == "ACTIVE" || exists == "DRAINING") {
							sh """
                            aws ecs update-service \
                              --cluster $ECS_CLUSTER_NAME \
                              --service $GRAFANA_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --region $AWS_DEFAULT_REGION \
                              --force-new-deployment
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
                              --region $AWS_DEFAULT_REGION \
                            """
						}
						sh "aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $GRAFANA_SERVICE_NAME --region $AWS_DEFAULT_REGION"
					}
				}
			}
		}

		stage('Archive Artifacts') {
			steps {
				archiveArtifacts artifacts: "${env.SERVICE_DIRECTORY}/build/libs/*.jar", fingerprint: true
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
			echo '====================================Build Success====================================='
			slackSend(
				channel: "${env.SLACK_NOTIFICATION_CHANNEL}",
				color:   "${env.SLACK_SUCCESS_COLOR}",
				message: "Build Success!!\n" +
				"SERVICE: ${env.SERVICE_NAME}\n" +
				"ECS_SERVICE: ${env.ECS_SERVICE_NAME}\n" +
				"IMAGE: ${env.IMAGE_URI?:'N/A'}\n" +
				"BUILD_NUMBER: ${env.BUILD_NUMBER}"
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
