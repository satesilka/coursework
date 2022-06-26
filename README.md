# coursework
## Spotify REST API
This is API, which allows you to receive and convey information about songs, artists, albums, lyrics and
music labels. Written using java and spring framework.

## REST API Overview
`GET`, `POST`, `PUT`, `DELETE` are implemented for each entity (`album`, `author`, `lyrics`, `musical_label`, `song`).  
For `GET` there are 2 options, either `/entity/{id}` or `/entity`, former returns entity by id, while latter returns all entities.  
`PUT` and `DELETE` both take `{id}` in url as a parameter.

## How to run
To run this app you will need `java` and `maven`.  
Run `mvn package` and then `java -jar target/Spptify-1.0.jar`.

## How to run site (checkstyle and spotbugs)
Run `mvn clean compile site`

## How to run tests
Run `mvn -DskipTests=false -Dtest=com.satesilka.EntityTest#testName test`, replace `EntityTest` with test class name, can be one of:
- `AlbumTest` - Tests Album REST
- `AuthorTest` - Tests Author REST
- `LyricsTest` - Tests Lyrics REST
- `MusicalLabelTest`- Tests MusicalLabel REST
- `SongTest` - Tests Song REST

While `testName` can be one of:
- `testCreate` - Tests creation of entity, sends `POST`
- `testCSV` - Tests if entity gets saved into csv file
- `testGet` - Tests retrieving of entity, sends `GET`
- `testUpdate` - Tests updating of entity, sends `PUT`
- `testDelete` - Tests deleting of entity, sends `DELETE`

### Test Cases
1. Create. Name: `testCreate`
2. Get. Run after Create. Name: `testGet`
3. Delete. Run after Create or Get. Cannot be run after Update. Name: `testDelete`
4. Update. Run after Create or Get. Cannot be run after Delete. Name: `testUpdate`
