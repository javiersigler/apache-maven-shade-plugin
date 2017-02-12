This repository is a fork of https://maven.apache.org/plugins/maven-shade-plugin/

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

To create an consistent uberjar, it is important to also remove the entry: META-INF/maven/${package}/pom.properties because it contains a line with a timestamp.

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

With this two settings, you will create a consistent uberjar independent of the generation timestamp.
