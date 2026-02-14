FROM ubuntu

RUN apt-get update \
 && apt-get install -y openjdk-25-jdk-headless maven git

USER ubuntu

COPY <<-'EOF' /home/ubuntu/.m2/settings.xml
<settings>
    <localRepository>.m2/repository</localRepository>
</settings>
EOF
