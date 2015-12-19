# props4j
Comfortable Properties for Java

Simple and useful properties tools with no dependencies.

## Cases
### Props with auto convert values
```java
import base.props4j.impl.MapProps;

MapProps props = new MapProps();
props.putVal("key1", 1);
props.putVal("key2", "val2");
props.putVal("key3", true);

String strVal_1 = props.strVal("key1"); //"1"
long longVal_1 = props.longVal("key1"); //1L
Boolean boolVal_1 = props.boolVal("key1"); //false
```

### Default values for empty keys or bad values
```java
int defVal =  props.intVal("unknown-key", 42); //42
```

### File props with updates check
```java
import base.props4j.impl.FileProps;
import java.io.File;

File file = new File("test.properties");
writeToFile(file, "key1 = 1");

FileProps props = new FileProps(file);
int val = props.intVal("key1"); //1

//update file
writeToFile(file, "key1 = 2");

Thread.sleep(...);

int newVal = props.intVal("key1"); //2
```

### MultiProps for many sources
```java
import base.props4j.impl.FileProps;
import base.props4j.impl.MultiProps;

MultiProps props = new MultiProps();
props.addSource(new FileProps(file1));
props.addSource(new FileProps(file2));
```

### Enum for keys!
```java
import base.props4j.DefValKey;

public enum Keys implements DefValKey {
	
	planets_in_solar_system(8),
	is_frog_green(true),
	myName("Evgeny"),
	star_wars_episodes_count(999.999d),
	...
}


MapProps props = new MapProps();
int defVal = Keys.planets_in_solar_system.intFrom(props); //8
		
props.putVal(planets_in_solar_system, 9);
int updatedVal = Keys.planets_in_solar_system.intFrom(props); //9
```

### Expressions ${...} in values
```java
props.putVal("url", "127.0.0.1");
props.putVal("link1", "${url}/some1");
props.putVal("link2", "${url}/some2");

String link1 = props.strVal("link1"); // 127.0.0.1/some1
String link2 = props.strVal("link2"); // 127.0.0.1/some2
```

## Code examples
- [Example 1: Simple MapProps and basic get-put methods](https://github.com/edolganov/props4j/blob/master/props-common/src/test/java/examples/Ex1_Simple_map_props.java)
- [Example 2: FileProps for file properties](https://github.com/edolganov/props4j/blob/master/props-common/src/test/java/examples/Ex2_File_props.java)
- [Example 3: FileProps and file updated check](https://github.com/edolganov/props4j/blob/master/props-common/src/test/java/examples/Ex3_File_props_update.java)
- [Example 4: MultiProps for many props sources](https://github.com/edolganov/props4j/blob/master/props-common/src/test/java/examples/Ex4_Multi_props.java)
- [Example 5: Java Enums are best keys for props!](https://github.com/edolganov/props4j/blob/master/props-common/src/test/java/examples/Ex5_Enum_by_key.java)
- [Example 6: Expressions ${...} in values](https://github.com/edolganov/props4j/blob/master/props-common/src/test/java/examples/Ex6_Expressions_in_vals.java)


## Production ready
Props4j has been successfully used on [Cheapchat.me](http://cheapchat.me) and in open source [live-chat-engine](https://github.com/edolganov/live-chat-engine) project.


## Installation to your project
**Java 8 is required** 
(sources can easily be ported to Java 6 or 7 -- but I'm on Java 8 now :))

### by Maven
[via stackoverflow](http://stackoverflow.com/questions/20161602/loading-maven-dependencies-from-github)
```xml
<dependencies>
	<dependency>
		<groupId>com.github.edolganov</groupId>
		<artifactId>props4j</artifactId>
		<version>1.0</version>
	</dependency>
</dependencies>

<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```

### by Release build
Download and add **props4j-1.0.jar** to your project's classpath
