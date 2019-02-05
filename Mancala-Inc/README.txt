http://localhost:8080/MancalaAPI/webapi/mancalaservice/gamestate/4
https://www.tutorialspoint.com/restful/restful_caching.htm
http://www.vogella.com/tutorials/REST/article.html
https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/


<protocol>://<service-name>/<ResourceType>/<ResourceID>

GET/ game/{id(of game)}
GET/ http://localhost:8080/MancalaAPI/webapi/mancalaservice/game/{id}
retrieve gamestate with id
return 200, 404, 500

POST/ game - non idempotent
POST/ http://localhost:8080/MancalaAPI/webapi/mancalaservice/game
creates a new mancala game
return 200, 404, 500

PUT/ game/{id(of game)}/cup/{id(of cup)}
PUT/ http://localhost:8080/MancalaAPI/webapi/mancalaservice/game/{id}
take turn on mancala game with specified id, for cup with specified id
return 201 or 200 + JSON

todo:
- session/cookies? (in browser using it..?)
- persistence
- add index.html for options
- logging
- implement interface for DAO
- authentication
- hateos (hypermedia drive)