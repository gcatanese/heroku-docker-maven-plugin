# heroku-docker-maven-plugin

[![](https://badgen.net/github/license/gcatanese/heroku-docker-maven-plugin)](LICENSE)
[![](https://badgen.net/maven/v/maven-central/com.perosa/heroku-docker-maven-plugin)](https://search.maven.org/artifact/com.perosa/heroku-docker-maven-plugin)

Maven plugin to deploy Docker apps on Heroku

This plugin builds and pushes Docker images to Heroku.

The plugin has one goal:

- `heroku:deploy-docker`: build, push and release a Docker image to Heroku

Optionally define the ConfigVars for the application

## How to use it

Add the following to your `pom.xml`

```xml
<build>
  <plugins>
    <plugin>
      <groupId>com.perosa</groupId>
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
