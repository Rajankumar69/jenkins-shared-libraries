def call(Map config = [:]) {

    def imageName = config.imageName ?: error("Image name is required")
    def imageTag = config.imageTag ?: "latest"
    def dockerHubUser = config.dockerHubUser ?: error("Docker Hub username is required")

    withCredentials([usernamePassword(
        credentialsId: 'dockerHubCred',
        usernameVariable: 'dockerHubUserName',
        passwordVariable: 'dockerHubPass'
    )]) {

        sh """
            echo "\$dockerHubPass" | docker login -u "\$dockerHubUserName" --password-stdin

            docker tag ${imageName}:${imageTag} ${dockerHubUser}/${imageName}:${imageTag}

            docker push ${dockerHubUser}/${imageName}:${imageTag}
        """
    }
}
