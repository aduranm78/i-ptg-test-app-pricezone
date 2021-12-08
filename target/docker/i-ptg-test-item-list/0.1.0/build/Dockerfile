FROM registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift:latest
ENV ARTIFACT_COPY_ARGS=*.war JAVA_APP_DIR=/deployments
EXPOSE 8080 8081
COPY maven /deployments/
