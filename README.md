# heroku-docker-maven-plugin

[![](https://badgen.net/github/license/gcatanese/heroku-docker-maven-plugin)](LICENSE)
[![](https://badgen.net/maven/v/maven-central/com.tweesky.cloudtools/heroku-docker-maven-plugin)](https://search.maven.org/artifact/com.tweesky.cloudtools/heroku-docker-maven-plugin)
[![](https://badgen.net/circleci/github/gcatanese/heroku-docker-maven-plugin/main)](https://circleci.com/gh/gcatanese/heroku-docker-maven-plugin/tree/main)

Maven plugin to build a Docker image and deploy it to Heroku

The plugin has one goal:

- `heroku-docker:deploy`: build, push and release a Docker image to Heroku

Optionally it is possible to define the ConfigVars for the application

## How to use

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
      <groupId>com.tweeskycom.tweesky</groupId>
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
      <groupId>com.tweeskycom.tweesky</groupId>
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


