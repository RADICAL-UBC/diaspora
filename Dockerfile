FROM maven:3.6.3-jdk-8

# WORKDIR /diaspora
# ADD diaspora/pom.xml /diaspora
# RUN mvn verify clean --fail-never

# COPY /diaspora /diaspora
# RUN mvn -v
# RUN mvn clean install -DskipTests
# run mvn package -Dmaven.test.skip=true




# workdir /
# copy /generators /generators
# run python2 generators/generate_policy_stubs.py


# WORKDIR /todo
# ADD /example_apps/ToDoList/pom.xml /todo
# RUN mvn verify clean --fail-never

# COPY /example_apps/ToDoList/ /todo
# RUN mvn -v
# RUN mvn clean install -DskipTests
# run mvn package -Dmaven.test.skip=true
