log4-min
========

Fork of Log4j 1.2 project, without additional appenders and classes causing security issues.

CVE:
 * **CVE-2019-17571** - high severity - SocketServer
 * **CVE-2020-9488** - moderate severity - SMTPAppender
 * **CVE-2021-4104** - high severity - JMSAppender
 * **CVE-2022-23302** - high severity - JMSSink
 * **CVE-2022-23305** - high serverity - JDBCAppender
 * **CVE-2022-23307** - critical severity -Apache Chainsaw

### Removed Classes

Packages:
 * org.apache.log4j.chainsaw
 * org.apache.log4j.jdbc
 * org.apache.log4j.lf5
 * org.apache.log4j.lf5.*
 * org.apache.log4j.net
 * org.apache.log4j.nt
 * org.apache.log4j.or.jms

Classes:
* org.apache.log4j.varia.ExternallyRolledFileAppender

### Gradle

The project uses the Gradle build system.

 * **Eclipse Setup**: Run the command 'gradlew eclipse' or 'gradlew cleanEclipse eclipse'.

 * **Building artifacts**: Call 'gradlew assemble'.

### Replace default log4j library 

build.gradle:
```groovy
allprojects {
  configurations {
    all {
      resolutionStrategy {
        dependencySubstitution {
          // log4j 1.2 replacement
          substitute module('log4j:log4j') with module( 'com.github.schnitker.log4j12:log4j-min:1.3.0' )                  
        }
      }
    }
  }
}
```