FROM --platform=linux/amd64 openjdk:8-jdk-alpine
MAINTAINER shah syed
VOLUME /tmp
FROM --platform=linux/amd64 gradle:6.9.2-jdk8
WORKDIR /AdminSkillAPIAWS
copy . /AdminSkillAPIAWS/
RUN gradle bootJar
WORKDIR target
RUN echo "Before copying jar file"
RUN ls -l /AdminSkillAPIAWS/build/libs
RUN cp /AdminSkillAPIAWS/build/libs/AdminSkillAPIAWS.jar /AdminSkillAPIAWS/target/AdminSkillAPIAWS.jar
ENTRYPOINT ["java","-jar","/AdminSkillAPIAWS/target/AdminSkillAPIAWS.jar"]