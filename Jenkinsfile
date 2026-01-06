import com.cloudbees.groovy.cps.NonCPS

@NonCPS
def buildMarketNoteTaskDefinition(env) {
    [
        family: env.PROJECT_NAME,
        networkMode: "awsvpc",
        requiresCompatibilities: ["FARGATE"],
        cpu: "512",
        memory: "1024",
        executionRoleArn: env.ECS_TASK_EXECUTION_ROLE_ARN,
        taskRoleArn:      env.ECS_TASK_ROLE_ARN,
        containerDefinitions: [[
            name:  env.PROJECT_NAME,
            image: env.IMAGE_URI,
            portMappings: [[containerPort: 8080, protocol: "tcp"]],
            essential: true,
            environment: [
                [name: "SERVICE_NAME",                   value: env.ECS_SERVICE_NAME],
                [name: "DB_URL",                         value: env.DB_URL],
                [name: "DB_USERNAME",                    value: env.DB_USERNAME],
                [name: "DB_PASSWORD",                    value: env.DB_PASSWORD],
                [name: "JWT_SECRET_KEY",                 value: env.JWT_SECRET_KEY],
                [name: "ACCESS_TOKEN_EXPIRATION_TIME",   value: env.ACCESS_TOKEN_EXPIRATION_TIME],
                [name: "REFRESH_TOKEN_EXPIRATION_TIME",  value: env.REFRESH_TOKEN_EXPIRATION_TIME],
                [name: "CLIENT_ORIGIN",                  value: env.CLIENT_ORIGIN],
                [name: "COOKIE_DOMAIN",                  value: env.COOKIE_DOMAIN],
                [name: "ACCESS_CONTROL_ALLOWED_ORIGINS", value: env.ACCESS_CONTROL_ALLOWED_ORIGINS],
                [name: "SERVER_ORIGIN",                  value: env.SERVER_ORIGIN],
                [name: "SPRING_PROFILES_ACTIVE",         value: env.SPRING_PROFILE],
                [name: "GOOGLE_CLIENT_ID",               value: env.GOOGLE_CLIENT_ID],
                [name: "GOOGLE_CLIENT_SECRET",           value: env.GOOGLE_CLIENT_SECRET],
                [name: "KAKAO_CLIENT_ID",                value: env.KAKAO_CLIENT_ID],
                [name: "KAKAO_CLIENT_SECRET",            value: env.KAKAO_CLIENT_SECRET],
                [name: "KAKAO_ADMIN_KEY",                value: env.KAKAO_ADMIN_KEY],
                [name: "S3_ACCESS_KEY",                  value: env.S3_ACCESS_KEY],
                [name: "S3_SECRET_KEY",                  value: env.S3_SECRET_KEY],
                [name: "S3_BUCKET_NAME",                 value: env.S3_BUCKET_NAME],
                [name: "SES_SMTP_USERNAME",              value: env.SES_SMTP_USERNAME],
                [name: "SES_SMTP_PASSWORD",              value: env.SES_SMTP_PASSWORD],
                [name: "MAIL_FROM",                      value: env.MAIL_FROM],
                [name: "MAIL_SENDER_NAME",               value: env.MAIL_SENDER_NAME],
                [name: "MAIL_VERIFICATION_TTL_MINUTES",  value: env.MAIL_VERIFICATION_TTL_MINUTES],
                [name: "REDIS_HOST_NAME",                value: env.REDIS_HOST_NAME],
                [name: "REDIS_PASSWORD",                 value: env.REDIS_PASSWORD],
                [name: "REDIS_EMAIL_VERIFICATION_PREFIX",value: env.REDIS_EMAIL_VERIFICATION_PREFIX],
                [name: "FILE_SERVICE_SERVER_ORIGIN",     value: env.FILE_SERVICE_SERVER_ORIGIN],
                [name: "PRODUCT_SERVICE_SERVER_ORIGIN",  value: env.PRODUCT_SERVICE_SERVER_ORIGIN],
                [name: "JWT_ADMIN_ACCESS_TOKEN",         value: env.JWT_ADMIN_ACCESS_TOKEN],
            ],
            logConfiguration: [
                logDriver: "awslogs",
                options: [
                    "awslogs-group":  env.CLOUDWATCH_LOG_GROUP,
                    "awslogs-region": env.AWS_DEFAULT_REGION,
                    "awslogs-stream-prefix": env.PROJECT_NAME
                ]
            ],
            healthCheck: [
                command: ["CMD-SHELL",
                    "(curl -fsS http://127.0.0.1:8080/actuator/health || wget -qO- http://127.0.0.1:8080/actuator/health) | grep '\"status\":\"UP\"'"],
                interval: 15,
                timeout: 5,
                retries: 3,
                startPeriod: 45
            ]
        ]]
    ]
}

pipeline {
	agent any

	parameters {
		choice(name: 'SERVICE', choices: ['auto','user-service','product-service','order-service','file-service'], description: '배포 대상 서비스')
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
						'product-service': 'product-adapters',
						'order-service': 'order-adapters',
						'file-service': 'file-adapters',
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

					echo "Changed fileDomain count       = ${changed.size()}"
					echo "COMMON changed?           = ${commonChanged}"
				}
			}
		}

		stage('Environment Setup') {
			steps {
				script {
					sh 'chmod +x ./gradlew'
					env.PROJECT_NAME = sh(script: "./gradlew -p ${env.SERVICE_DIRECTORY} -q printProjectName", returnStdout: true).trim()
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

                        sh '''
                          git config remote.origin.fetch "+refs/heads/*:refs/remotes/origin/*"
                          git fetch --all --prune --tags || true
                          git fetch --unshallow         || true
                        '''

                        def branchLabel = sh(
                            script: '''
                              if [ -n "$BRANCH_NAME" ]; then
                                echo "$BRANCH_NAME"
                                exit 0
                              fi
                              if [ -n "$CHANGE_ID" ] && [ -n "$CHANGE_BRANCH" ] && [ -n "$CHANGE_TARGET" ]; then
                                echo "PR #$CHANGE_ID ($CHANGE_BRANCH -> $CHANGE_TARGET)"
                                exit 0
                              fi
                              if [ -n "$GIT_BRANCH" ]; then
                                echo "${GIT_BRANCH#origin/}"
                                exit 0
                              fi
                              bn=$(git rev-parse --abbrev-ref HEAD 2>/dev/null)
                              if [ "$bn" != "HEAD" ] && [ -n "$bn" ]; then
                                echo "$bn"
                                exit 0
                              fi
                              guess=$(git for-each-ref --format="%(refname:short)" --contains HEAD refs/remotes/origin | sed "s#^origin/##" | head -n1)
                              if [ -n "$guess" ]; then
                                echo "$guess"
                                exit 0
                              fi
                              tag=$(git describe --tags --exact-match 2>/dev/null || true)
                              if [ -n "$tag" ]; then
                                echo "tag: $tag"
                                exit 0
                              fi
                              name=$(git name-rev --name-only HEAD 2>/dev/null | sed "s#remotes/origin/##;s#~[0-9]*##")
                              if [ -n "$name" ] && [ "$name" != "undefined" ]; then
                                echo "$name"
                                exit 0
                              fi
                              git rev-parse --short HEAD
                            ''',
                            returnStdout: true
                        ).trim()

                        def commitAuthor  = sh(script: 'git log -1 --pretty=%an', returnStdout: true).trim()
                        def commitMessage = sh(script: 'git log -1 --pretty=%B',  returnStdout: true).trim()

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
                                + "BRANCH: ${branchLabel}\n"
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
						string(credentialsId: 'MARKETNOTE_QA_USER_SERVICE_ECR_REPOSITORY',      variable: 'USER_SERVICE_ECR_REPOSITORY'),
						string(credentialsId: 'MARKETNOTE_QA_PRODUCT_SERVICE_ECR_REPOSITORY',   variable: 'PRODUCT_SERVICE_ECR_REPOSITORY'),
						string(credentialsId: 'MARKETNOTE_QA_ORDER_SERVICE_ECR_REPOSITORY',     variable: 'ORDER_SERVICE_ECR_REPOSITORY'),
						string(credentialsId: 'MARKETNOTE_QA_FILE_SERVICE_ECR_REPOSITORY',      variable: 'FILE_SERVICE_ECR_REPOSITORY'),

						string(credentialsId: 'MARKETNOTE_QA_USER_SERVICE_ECS_SERVICE_NAME',    variable: 'USER_SERVICE_ECS_SERVICE_NAME'),
						string(credentialsId: 'MARKETNOTE_QA_PRODUCT_SERVICE_ECS_SERVICE_NAME', variable: 'PRODUCT_SERVICE_ECS_SERVICE_NAME'),
						string(credentialsId: 'MARKETNOTE_QA_ORDER_SERVICE_ECS_SERVICE_NAME',   variable: 'ORDER_SERVICE_ECS_SERVICE_NAME'),
						string(credentialsId: 'MARKETNOTE_QA_FILE_SERVICE_ECS_SERVICE_NAME',    variable: 'FILE_SERVICE_ECS_SERVICE_NAME'),

						string(credentialsId: 'MARKETNOTE_QA_USER_SERVICE_TARGET_GROUP_ARN',    variable: 'USER_SERVICE_TARGET_GROUP_ARN'),
						string(credentialsId: 'MARKETNOTE_QA_PRODUCT_SERVICE_TARGET_GROUP_ARN', variable: 'PRODUCT_SERVICE_TARGET_GROUP_ARN'),
						string(credentialsId: 'MARKETNOTE_QA_ORDER_SERVICE_TARGET_GROUP_ARN',   variable: 'ORDER_SERVICE_TARGET_GROUP_ARN'),
						string(credentialsId: 'MARKETNOTE_QA_FILE_SERVICE_TARGET_GROUP_ARN',    variable: 'FILE_SERVICE_TARGET_GROUP_ARN'),

						string(credentialsId: 'MARKETNOTE_QA_USER_SERVICE_SERVER_ORIGIN',       variable: 'USER_SERVICE_SERVER_ORIGIN'),
						string(credentialsId: 'MARKETNOTE_QA_PRODUCT_SERVICE_SERVER_ORIGIN',    variable: 'PRODUCT_SERVICE_SERVER_ORIGIN'),
						string(credentialsId: 'MARKETNOTE_QA_ORDER_SERVICE_SERVER_ORIGIN',      variable: 'ORDER_SERVICE_SERVER_ORIGIN'),
						string(credentialsId: 'MARKETNOTE_QA_FILE_SERVICE_SERVER_ORIGIN',       variable: 'FILE_SERVICE_SERVER_ORIGIN'),

						string(credentialsId: 'MARKETNOTE_QA_USER_SERVICE_DB_URL',              variable: 'USER_SERVICE_DB_URL'),
                        string(credentialsId: 'MARKETNOTE_QA_PRODUCT_SERVICE_DB_URL',           variable: 'PRODUCT_SERVICE_DB_URL'),
                        string(credentialsId: 'MARKETNOTE_QA_ORDER_SERVICE_DB_URL',             variable: 'ORDER_SERVICE_DB_URL'),
                        string(credentialsId: 'MARKETNOTE_QA_FILE_SERVICE_DB_URL',              variable: 'FILE_SERVICE_DB_URL'),

                        string(credentialsId: 'MARKETNOTE_QA_USER_SERVICE_DB_PASSWORD',         variable: 'USER_SERVICE_DB_PASSWORD'),
                        string(credentialsId: 'MARKETNOTE_QA_PRODUCT_SERVICE_DB_PASSWORD',      variable: 'PRODUCT_SERVICE_DB_PASSWORD'),
                        string(credentialsId: 'MARKETNOTE_QA_ORDER_SERVICE_DB_PASSWORD',        variable: 'ORDER_SERVICE_DB_PASSWORD'),
                        string(credentialsId: 'MARKETNOTE_QA_FILE_SERVICE_DB_PASSWORD',         variable: 'FILE_SERVICE_DB_PASSWORD'),
					]) {
						def svc = env.SERVICE_NAME
						if (svc == 'user-service') {
							env.ECR_REPOSITORY = USER_SERVICE_ECR_REPOSITORY
							env.ECS_SERVICE_NAME = USER_SERVICE_ECS_SERVICE_NAME
							env.TARGET_GROUP_ARN = USER_SERVICE_TARGET_GROUP_ARN
							env.SERVER_ORIGIN = USER_SERVICE_SERVER_ORIGIN
							env.DB_URL = USER_SERVICE_DB_URL
							env.DB_PASSWORD = USER_SERVICE_DB_PASSWORD
						} else if (svc == 'product-service') {
							env.ECR_REPOSITORY = PRODUCT_SERVICE_ECR_REPOSITORY
							env.ECS_SERVICE_NAME = PRODUCT_SERVICE_ECS_SERVICE_NAME
							env.TARGET_GROUP_ARN = PRODUCT_SERVICE_TARGET_GROUP_ARN
							env.SERVER_ORIGIN = PRODUCT_SERVICE_SERVER_ORIGIN
							env.DB_URL = PRODUCT_SERVICE_DB_URL
                        	env.DB_PASSWORD = PRODUCT_SERVICE_DB_PASSWORD
                        } else if (svc == 'order-service') {
                           	env.ECR_REPOSITORY = ORDER_SERVICE_ECR_REPOSITORY
                           	env.ECS_SERVICE_NAME = ORDER_SERVICE_ECS_SERVICE_NAME
                           	env.TARGET_GROUP_ARN = ORDER_SERVICE_TARGET_GROUP_ARN
                           	env.SERVER_ORIGIN = ORDER_SERVICE_SERVER_ORIGIN
                           	env.DB_URL = ORDER_SERVICE_DB_URL
                            env.DB_PASSWORD = ORDER_SERVICE_DB_PASSWORD
                        } else if (svc == 'file-service') {
                           	env.ECR_REPOSITORY = FILE_SERVICE_ECR_REPOSITORY
                           	env.ECS_SERVICE_NAME = FILE_SERVICE_ECS_SERVICE_NAME
                           	env.TARGET_GROUP_ARN = FILE_SERVICE_TARGET_GROUP_ARN
                           	env.SERVER_ORIGIN = FILE_SERVICE_SERVER_ORIGIN
                           	env.DB_URL = FILE_SERVICE_DB_URL
                            env.DB_PASSWORD = FILE_SERVICE_DB_PASSWORD
						}  else {
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

		stage('Detect Redis Changes') {
			steps {
				script {
					def base = sh(script: 'git rev-parse HEAD~1 || git rev-parse HEAD', returnStdout: true).trim()
					def changed = sh(script: "git diff --name-only ${base} HEAD || true", returnStdout: true)
					.trim()
					.split('\n')
					.findAll {
						it?.trim()
					} as List
					def redisChanged = changed.any {
						p ->
						p ==~ /.*user-service\/.*\/src\/.*\/com\/personal\/marketnote\/user\/configuration\/CacheConfig\.java/ ||
						p ==~ /.*user-service\/.*\/src\/.*\/com\/personal\/marketnote\/user\/configuration\/SessionConfig\.java/
					}
					env.REDIS_CHANGED = redisChanged ? "true" : "false"
					echo "REDIS_CHANGED              = ${env.REDIS_CHANGED}"
				}
			}
		}

		stage('Register Redis Task Definition') {
			steps {
				script {
					if (env.REDIS_CHANGED != "true") {
						sleep time: 1, unit: 'SECONDS'; return
					}
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',          variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_EXECUTION_ROLE_ARN', variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_ROLE_ARN',           variable: 'ECS_TASK_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_CLOUDWATCH_LOG_GROUP_REDIS',  variable: 'CLOUDWATCH_LOG_GROUP_REDIS'),
						string(credentialsId: 'MARKETNOTE_REDIS_SERVICE_NAME',          variable: 'REDIS_SERVICE_NAME'),
						string(credentialsId: 'MARKETNOTE_REDIS_PASSWORD',              variable: 'REDIS_PASSWORD')
					]) {
						sh '''
				          LG="$CLOUDWATCH_LOG_GROUP_REDIS"
				          EXISTS=$(aws logs describe-log-groups --log-group-name-prefix "$LG" --region "$AWS_DEFAULT_REGION" --query "length(logGroups[?logGroupName=='$LG'])" --output text || echo 0)
				          if [ "$EXISTS" = "0" ]; then
				            aws logs create-log-group --log-group-name "$LG" --region "$AWS_DEFAULT_REGION"
				          fi
				        '''

						def redisTask = [
							family: "redis",
							networkMode: "awsvpc",
							requiresCompatibilities: ["FARGATE"],
							cpu: "256",
							memory: "512",
							executionRoleArn: "${env.ECS_TASK_EXECUTION_ROLE_ARN}",
							taskRoleArn: "${env.ECS_TASK_ROLE_ARN}",
							containerDefinitions: [[
								name: "redis",
								image: "redis:7.2",
								portMappings: [[containerPort: 6379, protocol: "tcp"]],
								essential: true,
								command: [
									"sh",
									"-c",
									"exec redis-server --appendonly yes --requirepass \\\"\$REDIS_PASSWORD\\\""
								],
								environment: [
									[name: "REDIS_SERVICE_NAME", value: "${env.REDIS_SERVICE_NAME}"],
									[name: "REDIS_PASSWORD",     value: "${env.REDIS_PASSWORD}"]
								],
								logConfiguration: [
									logDriver: "awslogs",
									options: [
										"awslogs-group": "${env.CLOUDWATCH_LOG_GROUP_REDIS}",
										"awslogs-region": "${env.AWS_DEFAULT_REGION}",
										"awslogs-stream-prefix": "redis"
									]
								]
							]]
						]

						def json = groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(redisTask))
						writeFile file: 'redis-taskdef.json', text: json

						def out = sh(
							script: 'aws ecs register-task-definition --cli-input-json file://redis-taskdef.json --region $AWS_DEFAULT_REGION --query \'taskDefinition.taskDefinitionArn\' --output text',
							returnStdout: true
						).trim()

						if (!out || out == 'None') {
							error 'Failed to register Redis task definition'
						}
						env.REDIS_TASK_DEF_ARN = out
						echo "Registered Redis TaskDef ARN = ${env.REDIS_TASK_DEF_ARN}"
					}
				}
			}
		}

		stage('Deploy Redis Service') {
			steps {
				script {
					if (env.REDIS_CHANGED != "true") {
						sleep time: 1, unit: 'SECONDS'; return
					}
					withCredentials([
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',      variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_CLUSTER_NAME',        variable: 'ECS_CLUSTER_NAME'),
						string(credentialsId: 'MARKETNOTE_REDIS_SERVICE_NAME',      variable: 'REDIS_SERVICE_NAME'),
						string(credentialsId: 'MARKETNOTE_SUBNET_IDS',              variable: 'SUBNET_IDS'),
						string(credentialsId: 'MARKETNOTE_SECURITY_GROUP_IDS',      variable: 'SECURITY_GROUP_IDS')
					]) {
						def latestTask = env.REDIS_TASK_DEF_ARN?.trim()
						if (!latestTask) {
							latestTask = sh(script: 'aws ecs list-task-definitions --family-prefix redis --sort DESC --region $AWS_DEFAULT_REGION --query \'taskDefinitionArns[0]\' --output text', returnStdout: true).trim()
						}
						if (!latestTask || latestTask == 'None') {
							error 'No Redis TaskDefinition found to deploy'
						}

						def exists = sh(script: 'aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $REDIS_SERVICE_NAME --region $AWS_DEFAULT_REGION --query \'services[0].status\' --output text || true', returnStdout: true).trim()
						def subnets = sh(script: 'printf "%s" "$SUBNET_IDS" | awk -F, \'{for(i=1;i<=NF;i++){gsub(/^ +| +$/,"",$i);printf "%s%s",$i,(i<NF?",":"")}}\'', returnStdout: true).trim()
						def sgs = sh(script: 'printf "%s" "$SECURITY_GROUP_IDS" | awk -F, \'{for(i=1;i<=NF;i++){gsub(/^ +| +$/,"",$i);printf "%s%s",$i,(i<NF?",":"")}}\'', returnStdout: true).trim()

						if (exists == "ACTIVE" || exists == "DRAINING") {
							sh """
          					  aws ecs update-service \
          					    --cluster \$ECS_CLUSTER_NAME \
          					    --service \$REDIS_SERVICE_NAME \
          					    --task-definition ${latestTask} \
          					    --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
          					    --desired-count 1 \
          					    --region \$AWS_DEFAULT_REGION \
          					    --force-new-deployment
          					"""
						} else {
							sh """
          					  aws ecs create-service \
          					    --cluster \$ECS_CLUSTER_NAME \
          					    --service-name \$REDIS_SERVICE_NAME \
          					    --task-definition ${latestTask} \
          					    --desired-count 1 \
          					    --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
          					    --network-configuration "awsvpcConfiguration={subnets=[${subnets}],securityGroups=[${sgs}],assignPublicIp=ENABLED}" \
          					    --region \$AWS_DEFAULT_REGION
          					"""
						}
						sh 'aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $REDIS_SERVICE_NAME --region $AWS_DEFAULT_REGION'
					}
				}
			}
		}

		stage('Build Market Note Service Image') {
			steps {
				script {
					def dockerImageTag = "${env.PROJECT_NAME}:${env.PROJECT_VERSION}"
					env.DOCKER_IMAGE_TAG = dockerImageTag
					sh '''
			          docker build \
			            --build-arg JAR_FILE=build/libs/$PROJECT_NAME-$PROJECT_VERSION.jar \
			            -t $DOCKER_IMAGE_TAG \
			            -f "$DOCKERFILE_PATH" \
			            "$BUILD_CONTEXT"
			        '''
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
						string(credentialsId: 'MARKETNOTE_DB_USERNAME',                       variable: 'DB_USERNAME'),
						string(credentialsId: 'MARKETNOTE_JWT_SECRET_KEY',                    variable: 'JWT_SECRET_KEY'),
						string(credentialsId: 'MARKETNOTE_ACCESS_TOKEN_EXPIRATION_TIME',      variable: 'ACCESS_TOKEN_EXPIRATION_TIME'),
						string(credentialsId: 'MARKETNOTE_REFRESH_TOKEN_EXPIRATION_TIME',     variable: 'REFRESH_TOKEN_EXPIRATION_TIME'),
						string(credentialsId: 'MARKETNOTE_CLIENT_ORIGIN',                     variable: 'CLIENT_ORIGIN'),
						string(credentialsId: 'MARKETNOTE_COOKIE_DOMAIN',                     variable: 'COOKIE_DOMAIN'),
						string(credentialsId: 'MARKETNOTE_ACCESS_CONTROL_ALLOWED_ORIGINS',    variable: 'ACCESS_CONTROL_ALLOWED_ORIGINS'),
						string(credentialsId: 'MARKETNOTE_QA_SPRING_PROFILE',                 variable: 'SPRING_PROFILE'),
						string(credentialsId: 'MARKETNOTE_GOOGLE_CLIENT_ID',                  variable: 'GOOGLE_CLIENT_ID'),
						string(credentialsId: 'MARKETNOTE_GOOGLE_CLIENT_SECRET',              variable: 'GOOGLE_CLIENT_SECRET'),
						string(credentialsId: 'MARKETNOTE_KAKAO_CLIENT_ID',                   variable: 'KAKAO_CLIENT_ID'),
						string(credentialsId: 'MARKETNOTE_KAKAO_CLIENT_SECRET',               variable: 'KAKAO_CLIENT_SECRET'),
						string(credentialsId: 'MARKETNOTE_KAKAO_ADMIN_KEY',                   variable: 'KAKAO_ADMIN_KEY'),
						string(credentialsId: 'MARKETNOTE_S3_ACCESS_KEY',                     variable: 'S3_ACCESS_KEY'),
						string(credentialsId: 'MARKETNOTE_S3_SECRET_KEY',                     variable: 'S3_SECRET_KEY'),
						string(credentialsId: 'MARKETNOTE_S3_BUCKET_NAME',                    variable: 'S3_BUCKET_NAME'),
						string(credentialsId: 'MARKETNOTE_AWS_ACCOUNT_ID',                    variable: 'AWS_ACCOUNT_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_ACCESS_KEY_ID',                 variable: 'AWS_ACCESS_KEY_ID'),
						string(credentialsId: 'MARKETNOTE_AWS_SECRET_ACCESS_KEY',             variable: 'AWS_SECRET_ACCESS_KEY'),
						string(credentialsId: 'MARKETNOTE_AWS_DEFAULT_REGION',                variable: 'AWS_DEFAULT_REGION'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_EXECUTION_ROLE_ARN',       variable: 'ECS_TASK_EXECUTION_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_ECS_TASK_ROLE_ARN',                 variable: 'ECS_TASK_ROLE_ARN'),
						string(credentialsId: 'MARKETNOTE_CLOUDWATCH_LOG_GROUP',              variable: 'CLOUDWATCH_LOG_GROUP'),
						string(credentialsId: 'MARKETNOTE_SES_SMTP_USERNAME',                 variable: 'SES_SMTP_USERNAME'),
						string(credentialsId: 'MARKETNOTE_SES_SMTP_PASSWORD',                 variable: 'SES_SMTP_PASSWORD'),
						string(credentialsId: 'MARKETNOTE_MAIL_FROM',                         variable: 'MAIL_FROM'),
						string(credentialsId: 'MARKETNOTE_MAIL_SENDER_NAME',                  variable: 'MAIL_SENDER_NAME'),
						string(credentialsId: 'MARKETNOTE_MAIL_VERIFICATION_TTL_MINUTES',     variable: 'MAIL_VERIFICATION_TTL_MINUTES'),
						string(credentialsId: 'MARKETNOTE_REDIS_PASSWORD',                    variable: 'REDIS_PASSWORD'),
						string(credentialsId: 'MARKETNOTE_REDIS_HOST_NAME',                   variable: 'REDIS_HOST_NAME'),
						string(credentialsId: 'MARKETNOTE_REDIS_EMAIL_VERIFICATION_PREFIX',   variable: 'REDIS_EMAIL_VERIFICATION_PREFIX'),
						string(credentialsId: 'MARKETNOTE_QA_FILE_SERVICE_SERVER_ORIGIN',     variable: 'FILE_SERVICE_SERVER_ORIGIN'),
						string(credentialsId: 'MARKETNOTE_QA_PRODUCT_SERVICE_SERVER_ORIGIN',  variable: 'PRODUCT_SERVICE_SERVER_ORIGIN'),
						string(credentialsId: 'MARKETNOTE_QA_JWT_ADMIN_ACCESS_TOKEN',         variable: 'JWT_ADMIN_ACCESS_TOKEN'),
					]) {
						sh '''
                  LG="$CLOUDWATCH_LOG_GROUP"
                  EXISTS=$(aws logs describe-log-groups --log-group-name-prefix "$LG" --region "$AWS_DEFAULT_REGION" --query "length(logGroups[?logGroupName=='$LG'])" --output text || echo 0)
                  if [ "$EXISTS" = "0" ]; then
                    aws logs create-log-group --log-group-name "$LG" --region "$AWS_DEFAULT_REGION"
                  fi
                '''

						def td = buildMarketNoteTaskDefinition(env)

						def json = groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(td))
						writeFile file: 'taskdef.json', text: json

						def out = sh(
							script: 'set -eu; aws ecs register-task-definition --cli-input-json file://taskdef.json --region $AWS_DEFAULT_REGION --query "taskDefinition.taskDefinitionArn" --output text',
							returnStdout: true
						).trim()
						echo "Registered TaskDef ARN = ${out}"
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
						def newTaskDefArn = sh(script: 'aws ecs list-task-definitions --family-prefix $PROJECT_NAME --sort DESC --region $AWS_DEFAULT_REGION --query \'taskDefinitionArns[0]\' --output text', returnStdout: true).trim()
						if (!newTaskDefArn || newTaskDefArn == 'None') {
							error 'No application TaskDefinition found to deploy'
						}
						def exists = sh(script: 'aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION --query \'services[0].status\' --output text || true', returnStdout: true).trim()
						def subnets = sh(script: 'printf "%s" "$SUBNET_IDS" | awk -F, \'{for(i=1;i<=NF;i++){gsub(/^ +| +$/,"",$i);printf "%s%s",$i,(i<NF?",":"")}}\'', returnStdout: true).trim()
						def sgs = sh(script: 'printf "%s" "$SECURITY_GROUP_IDS" | awk -F, \'{for(i=1;i<=NF;i++){gsub(/^ +| +$/,"",$i);printf "%s%s",$i,(i<NF?",":"")}}\'', returnStdout: true).trim()

						if (exists == "ACTIVE" || exists == "DRAINING") {
							sh """
                            aws ecs update-service \
                              --cluster \$ECS_CLUSTER_NAME \
                              --service \$ECS_SERVICE_NAME \
                              --task-definition ${newTaskDefArn} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --health-check-grace-period-seconds 180 \
                              --region \$AWS_DEFAULT_REGION \
                              --force-new-deployment
                            """
							sh 'aws ecs update-service --cluster $ECS_CLUSTER_NAME --service $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION --health-check-grace-period-seconds 180 || true'
						} else {
							sh """
                            aws ecs create-service \
                              --cluster \$ECS_CLUSTER_NAME \
                              --service-name \$ECS_SERVICE_NAME \
                              --task-definition ${newTaskDefArn} \
                              --desired-count 1 \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --load-balancers targetGroupArn=\$TARGET_GROUP_ARN,containerName=${env.PROJECT_NAME},containerPort=8080 \
                              --network-configuration "awsvpcConfiguration={subnets=[${subnets}],securityGroups=[${sgs}],assignPublicIp=ENABLED}" \
                              --region \$AWS_DEFAULT_REGION
                            """
							sh 'aws ecs update-service --cluster $ECS_CLUSTER_NAME --service $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION --health-check-grace-period-seconds 180 || true'
						}
						int maxWaitRetries = 20
						for (int i = 1; i <= maxWaitRetries; i++) {
							def rc = sh(
								script: 'aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $ECS_SERVICE_NAME --region $AWS_DEFAULT_REGION',
								returnStatus: true
							)
							if (rc == 0) {
								echo "ECS services-stable 성공 (attempt ${i})"
								break
							}
							echo "ECS services-stable 타임아웃 (attempt ${i}) → 재시도"
							sleep time: 20, unit: 'SECONDS'
							if (i == maxWaitRetries) {
								error "ECS 서비스가 안정화되지 않음 (총 ${maxWaitRetries}회 대기 실패)"
							}
						}
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
					sh 'test -f monitoring/prometheus/Dockerfile'
					sh 'test -f monitoring/prometheus/prometheus.yml'
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
						def latestTask = sh(script: 'aws ecs list-task-definitions --family-prefix prometheus --sort DESC --region $AWS_DEFAULT_REGION --query \'taskDefinitionArns[0]\' --output text', returnStdout: true).trim()
						if (!latestTask || latestTask == 'None') {
							error 'No Prometheus TaskDefinition found to deploy'
						}
						def exists = sh(script: 'aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $PROMETHEUS_SERVICE_NAME --region $AWS_DEFAULT_REGION --query \'services[0].status\' --output text || true', returnStdout: true).trim()
						def subnets = sh(script: 'printf "%s" "$SUBNET_IDS" | awk -F, \'{for(i=1;i<=NF;i++){gsub(/^ +| +$/,"",$i);printf "%s%s",$i,(i<NF?",":"")}}\'', returnStdout: true).trim()
						def sgs = sh(script: 'printf "%s" "$SECURITY_GROUP_IDS" | awk -F, \'{for(i=1;i<=NF;i++){gsub(/^ +| +$/,"",$i);printf "%s%s",$i,(i<NF?",":"")}}\'', returnStdout: true).trim()

						if (exists == "ACTIVE" || exists == "DRAINING") {
							sh """
                            aws ecs update-service \
                              --cluster \$ECS_CLUSTER_NAME \
                              --service \$PROMETHEUS_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --region \$AWS_DEFAULT_REGION \
                              --force-new-deployment
                            """
						} else {
							sh """
                            aws ecs create-service \
                              --cluster \$ECS_CLUSTER_NAME \
                              --service-name \$PROMETHEUS_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --desired-count 1 \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --network-configuration "awsvpcConfiguration={subnets=[${subnets}],securityGroups=[${sgs}],assignPublicIp=ENABLED}" \
                              --region \$AWS_DEFAULT_REGION
                            """
						}
						sh 'aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $PROMETHEUS_SERVICE_NAME --region $AWS_DEFAULT_REGION'
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
						def latestTask = sh(script: 'aws ecs list-task-definitions --family-prefix grafana --sort DESC --region $AWS_DEFAULT_REGION --query \'taskDefinitionArns[0]\' --output text', returnStdout: true).trim()
						if (!latestTask || latestTask == 'None') {
							error 'No Grafana TaskDefinition found to deploy'
						}
						def exists = sh(script: 'aws ecs describe-services --cluster $ECS_CLUSTER_NAME --services $GRAFANA_SERVICE_NAME --region $AWS_DEFAULT_REGION --query \'services[0].status\' --output text || true', returnStdout: true).trim()
						def subnets = sh(script: 'printf "%s" "$SUBNET_IDS" | awk -F, \'{for(i=1;i<=NF;i++){gsub(/^ +| +$/,"",$i);printf "%s%s",$i,(i<NF?",":"")}}\'', returnStdout: true).trim()
						def sgs = sh(script: 'printf "%s" "$SECURITY_GROUP_IDS" | awk -F, \'{for(i=1;i<=NF;i++){gsub(/^ +| +$/,"",$i);printf "%s%s",$i,(i<NF?",":"")}}\'', returnStdout: true).trim()

						if (exists == "ACTIVE" || exists == "DRAINING") {
							sh """
                            aws ecs update-service \
                              --cluster \$ECS_CLUSTER_NAME \
                              --service \$GRAFANA_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --desired-count 1 \
                              --region \$AWS_DEFAULT_REGION \
                              --force-new-deployment
                            """
						} else {
							sh """
                            aws ecs create-service \
                              --cluster \$ECS_CLUSTER_NAME \
                              --service-name \$GRAFANA_SERVICE_NAME \
                              --task-definition ${latestTask} \
                              --desired-count 1 \
                              --capacity-provider-strategy capacityProvider=FARGATE,weight=0 capacityProvider=FARGATE_SPOT,weight=1 \
                              --network-configuration "awsvpcConfiguration={subnets=[${subnets}],securityGroups=[${sgs}],assignPublicIp=ENABLED}" \
                              --region \$AWS_DEFAULT_REGION
                            """
						}
						sh 'aws ecs wait services-stable --cluster $ECS_CLUSTER_NAME --services $GRAFANA_SERVICE_NAME --region $AWS_DEFAULT_REGION'
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
				message: "Build Success!!\n"
				+ "SERVICE: ${env.SERVICE_NAME}\n"
				+ "ECS_SERVICE: ${env.ECS_SERVICE_NAME}\n"
				+ "IMAGE: ${env.IMAGE_URI?:'N/A'}\n"
				+ "BUILD_NUMBER: ${env.BUILD_NUMBER}"
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
