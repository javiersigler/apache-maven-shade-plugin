This repository is a fork of https://maven.apache.org/plugins/maven-shade-plugin/

Here we have added two features particurlarly useful for docker build.
1. Consistent dates
2. Disable jar signed verification (https://issues.apache.org/jira/browse/MSHADE-147)

### Consistent Dates

Here we add the posibility to create an uberjar with all the entries with date: 01/01/1970 01:00, with a new configuration parameter named: consistentDates.

~~~~
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.0.0</version>
    <configuration>
        <consistentDates>true</consistentDates>
    </configuration>
</plugin>
~~~~

### Consistent Disable jar signed verification

In order to get rid of the Java Exception: *java.lang.SecurityException: Invalid signature file digest for Manifest main attributes*, you should configure you plugin as follows:

~~~~
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.0.0</version>
    <configuration>
        <disableJarFileVerification>true</disableJarFileVerification>
    </configuration>
</plugin>
~~~~

### Final advice

To create a uberjar with always the same hash, it is important to also remove the entry: META-INF/maven/${package}/pom.properties because it contains a line with a timestamp.

We suggest to make use of the plugin maven-jar-plugin as follows:

~~~~
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>2.4</version>
    <configuration>
        <archive>
            <addMavenDescriptor>false</addMavenDescriptor>
        </archive>
    </configuration>
</plugin>
~~~~

With these features, you will always build a consistent uberjar totally independent of any timestamp.
