FROM openjdk:17.0.1-jdk-slim
RUN apt-get -y update
RUN apt -y install wget
RUN apt -y install unzip
RUN apt -y install curl
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt -y install ./google-chrome-stable_current_amd64.deb
RUN wget -O /tmp/chromedriver.zip https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/117.0.5938.92/linux64/chromedriver-linux64.zip
RUN unzip /tmp/chromedriver.zip
RUN mv -f chromedriver-linux64/chromedriver /usr/bin/chromedriver
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]