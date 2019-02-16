# neo4j
docker run -p 7474:7474 -p 7687:7687 neo4j

localhost:7474

默认用户名密码均为neo4j，首次登陆修改密码。我将其改为1234qwer

```
CREATE (Inception:Movie {title: 'Inception', director: 'Nolan'})
CREATE (DarkKnight:Movie {title: 'The Dark Knight', director: 'Nolan'})
CREATE (Peter:User {name: 'Peter', age: 30})
CREATE (Sam:User {name: 'Sam', age: 28})
CREATE (Ryan:User {name: 'Ryan', age: 29})

CREATE (Inception)-[:RATED{rating: 9}]->(Peter),
(Inception)-[:RATED{rating: 8}]->(Sam),
(DarkKnight)-[:RATED{rating: 9}]->(Sam),
(DarkKnight)-[:RATED{rating: 8}]->(Peter);
```

http://localhost:8080/rest/neo4j/users


### 修改数据
MATCH (DarkKnight:Movie {title: 'The Dark Knight'}), (Ryan:User)
CREATE
(DarkKnight)-[:RATED {rating: 8}]->(Ryan);


