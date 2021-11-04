# Deduplication

We loaded json file into the memory.
Then we use disjoint union set to help us find the duplicated user info, and reconcile them.

When we find the union set to which a new user belonged, we get two roots of the union set by looking up their email and id in the union set respectively.
1. If the roots are the same, we just reconcile the new user info with the user info associated to the root of union set.
2. If the root are different, we need to union them and reconcile the user info associated to both roots. 
   And then we reconcile the new user info with the one reconciled in previous step.

## Prerequisites

* java 16 (OpenJDK 16)
* maven 3.8.3

## Compile

```bash
mvn clean compile assembly:single
```

## test files

```text
src/main/resources/leads.json (from original test case)

src/main/resources/leads2.json (added a new test case where a user info can find different union set with its id and email)

```

## Run

```bash
java -jar target/deduplication-1.0-SNAPSHOT-jar-with-dependencies.jar  src/main/resources/leads.json
java -jar target/deduplication-1.0-SNAPSHOT-jar-with-dependencies.jar  src/main/resources/leads2.json
```