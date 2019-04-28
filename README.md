# Permission Mapper
This is a sandbox for playing around with parsing and permission handling for a fictional four element permission mapping system.

Currently three mapping types are implemented:
* simple 'loop' map type taking a brute force approach
* caching map type which takes the loop approach and wraps it with a cache
* tree map type, which tokenizes the permission array into a tree then uses that for lookups
!(https://github.com/Lexiyntax/permissionmap/sample-timings.png "Permission Mapper Timings")

## Building
```
mvn clean build
```

## Running
```
java -jar target/permissionmap-<version>.jar
```

