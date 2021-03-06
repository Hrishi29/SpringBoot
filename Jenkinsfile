node{}
node {
    def DOCKERHUB_REPO = "hrishigadkari94/myspring-app"
    def DOCKER_SERVICE_ID = "myspring-service"
    def DOCKER_IMAGE_VERSION = ""

    stage("clean woekspace") {
        deleteDir()
    }

    stage("git checkout") {
        checkout scm

        def GIT_COMMIT = sh(returnStdout: true, script: "git rev-parse HEAD").trim().take(11)
        DOCKER_IMAGE_VERSION = "${BUILD_NUMBER}-${GIT_COMMIT}"
    }

    stage("mvn build") {
        sh "mvn clean install -DskipTests"
    }

    stage("docker build") {
        sh "docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION} ."
    }

    stage("docker push") {
        withCredentials([usernamePassword(credentialsId: 'dockertestspring', passwordVariable: 'pass', usernameVariable: 'user')]) {
            sh "docker login -u $user -p $pass"
            sh "docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}"
        }
    }

    stage("docker service") {
        try {
            sh """
                if [ \$(docker service ls --filter name=${DOCKER_SERVICE_ID} --quiet | wc -l) -eq 0 ]; then
                  docker service create \
                    --replicas 1 \
                    --name ${DOCKER_SERVICE_ID} \
                    --publish 8082:8080 \
                    --secret spring.datasource.url \
                    --secret spring.datasource.username \
                    --secret spring.datasource.password \
                    ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION}
                else
                  docker service update \
                    --image ${DOCKERHUB_REPO}:${DOCKER_IMAGE_VERSION} \
                    ${DOCKER_SERVICE_ID}
                fi
            """
        }
        catch (e) {
            sh "docker service update --rollback ${DOCKER_SERVICE_ID}"
            error "Service update failed. RollING back ${DOCKER_SERVICE_ID}"
        }
        finally {
            sh "docker container prune -f"
            sh "docker image prune -af"
        }
    }
}