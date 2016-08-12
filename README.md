#FluentQuery
A fluent interface for building query in Android.

##Background
Android uses **SQLiteDatabase** to access SQLite database. However, the query methods are very complicated and not clear what is needed.
```java
query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
String having, String orderBy, String limit)
```
**FluentQuery** provides an easier way to create query with less room for error.
