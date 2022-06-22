# data-query
data-query

docker-compose up

http://localhost:8080/store

http://localhost:8080/store?query=OR(EQUAL(views,1),EQUAL(views,100))
http://localhost:8080/store?query=AND(EQUAL(content,"Scala Tutorial"),EQUAL(views,50))
http://localhost:8080/store?query=EQUAL(id,second-post),LESS_THAN(views,50)