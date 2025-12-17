FROM jenkins/jenkins:lts

COPY my_marvin.yml /usr/share/jenkins/ref/my_marvin.yml
COPY job_dsl.groovy /usr/share/jenkins/ref/job_dsl.groovy
RUN jenkins-plugin-cli --plugins "cloudbees-folder configuration-as-code credentials github instance-identity job-dsl script-security structs role-strategy ws-cleanup"
ENV CASC_JENKINS_CONFIG /var/jenkins_home/my_marvin.yml
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
