#Fluent Query
[ ![Download](https://api.bintray.com/packages/joshuaavalon/maven/fluent-query/images/download.svg) ](https://bintray.com/joshuaavalon/maven/fluent-query/_latestVersion)

A fluent interface for building query in Android.

##Background
Android uses **SQLiteDatabase** to access SQLite database. However, the query methods are very complicated and not clear what is needed.
```java
query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
String having, String orderBy, String limit)
```
**Fluent Query** provides an easier way to create query with less room for error.

##Install
```Gradle
compile 'com.joshuaavalon:fluent-query:1.1.0'
```

##Usage
###Basic SELECT
```java
// SELECT id, name FROM user;
final Cursor result = Query.select("id", "name").from("user").commit(yourSQLiteOpenHelper);
```
###SELECT with condition(s)
Note that the `AND` and `OR` is enclosed by brackets which means they will affect the result depends on how you put it.
```java
// SELECT id, name FROM user WHERE (sex = "M" AND age > 20);
final Cursor result = Query.select("id", "name").from("user")
				.where(Condition.property("sex").equal("M").and(Condition.property("age").greaterThan("20")))
				.commit(yourSQLiteOpenHelper);
```

##Limitation
There are also `distinct`, `orderBy`, `limit` and etc are not shown in the above usage. However, this library **cannot** execute non-query including creating tables and dropping tables.
