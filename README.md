# Heroku Docker Maven Plugin

[![](https://badgen.net/github/license/gcatanese/heroku-docker-maven-plugin)](LICENSE)
[![](https://badgen.net/maven/v/maven-central/com.tweesky.cloudtools/heroku-docker-maven-plugin)](https://repo1.maven.org/maven2/com/tweesky/cloudtools/heroku-docker-maven-plugin/)
[![](https://badgen.net/circleci/github/gcatanese/heroku-docker-maven-plugin/main?cache=300)](https://circleci.com/gh/gcatanese/heroku-docker-maven-plugin/tree/main)

Maven plugin to build multiple Docker images and deploy them to Heroku.  

The plugin has several goals:

- `heroku-docker:deploy`: build, push and release the Docker image(s) to Heroku
- `heroku-docker:info`: print info about the Heroku application(s) defined in the Maven POM file(s)
- `heroku-docker:start`: start (scale up) the Heroku application(s) defined in the Maven POM file(s)
- `heroku-docker:stop`: stop (scale down) the Heroku application(s) defined in the Maven POM file(s)

Optionally it is possible to define the ConfigVars for the application

## Pre-requisites

The Heroku CLI must be available in the environment/hosting running the Maven plugin.

## How to use: deploy

Add the following to your `pom.xml`

```xml

<build>
  <plugins>
    <plugin>
      <groupId>com.tweesky.cloudtools</groupId>
      <artifactId>heroku-docker-maven-plugin</artifactId>
      <version>1.0.0</version>
      <configuration>
        <appName>myAppName</appName>
        <processType>web</processType>
        <configVars>
          <VAR_ONE>SomeValue</VAR_ONE>
          <VAR_TWO>SomeOtherValue</VAR_TWO>
        </configVars>
      </configuration>
    </plugin>
  </plugins>
</build>
```

Run 
```
  mvn heroku-docker:deploy
```

## How to use with Maven multi-module project

The plugin can be convenient for a Maven multi-project where each child is deployed in its on Dyno.

For example a microservices architecture defining each service as a sub-module:
- Parent POM
    - service1 (web dyno)
    - service2 (web dyno)
    - service3 (worker dyno)

In the parent POM add the following to your `pom.xml`

```xml

<build>
  <plugins>
    <plugin>
      <groupId>com.tweesky.cloudtools</groupId>
      <artifactId>heroku-docker-maven-plugin</artifactId>
      <version>1.0.0</version>
    </plugin>
  </plugins>
</build>
```

Configure each sub-module accordingly

```xml

<build>
  <plugins>
    <plugin>
      <groupId>com.tweesky.cloudtools</groupId>
      <artifactId>heroku-docker-maven-plugin</artifactId>
      <version>1.0.0</version>
      <configuration>
        <appName>serviceOne</appName>
        <processType>web</processType>
        <configVars>
          <VAR_ONE>SomeValue</VAR_ONE>
          <VAR_TWO>SomeOtherValue</VAR_TWO>
        </configVars>
      </configuration>
    </plugin>
  </plugins>
</build>

```

In the root of the project run the following
```
  mvn heroku-docker:deploy
```

## How to use: start and stop

The plugin is convenient to start and stop multiple Heroku dynos at once, for example scale down the Dynos to
avoid using unnecessary computation time.

In the root of the project run the following
```
  mvn heroku-docker:stop
```

## How to use on CI/CD

The Maven plugin can be used with your CI/CD tool

### Github Actions

Login into the Heroku Docker Registry then invoke the Maven plugin:
```
    - name: Heroku Container Registry login
      env:
        HEROKU_API_KEY: ${{ secrets.HEROKU_API_KEY }}
      run: heroku container:login
    - name: Build with Maven
      run: mvn -B heroku-docker:deploy
      env:
        HEROKU_API_KEY: ${{ secrets.HEROKU_API_KEY }}
```

See full [example](https://github.com/gcatanese/MavenMultiModule/blob/main/.github/workflows/maven.yml) of the workflow
