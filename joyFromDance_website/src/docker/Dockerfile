FROM openjdk:10-jdk
MAINTAINER  Radosław Osiński <Radoslaw.Osinski@gmail.com>

COPY build/libs/*.war /opt/joyFromDance/JOY_FROM_DANCE_WEBSITE.war
WORKDIR /opt/joyFromDance
EXPOSE 8080

CMD ["java", "--add-modules=java.xml.bind", "-jar", "JOY_FROM_DANCE_WEBSITE.war"]
