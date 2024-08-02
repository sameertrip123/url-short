Open the folder in IntelliJ and Load it as a Maven Project.

I have used MySQL as the DB, so make sure to create a DB as per the infomation mentioned in application.properties

To test the APIs, paste the CURL in postman
curl --location 'http://localhost:8080/api/shorten' \
--header 'Content-Type: application/json' \
--data '{
    "url": "https://www.facebook.com"
}'


curl --location 'http://localhost:8080/-19'


curl --location 'http://localhost:8080/api/stats/-19'

Also added rate limiting, max 10 request in a minute for a particular IP. 
Keep Calling the curl --location 'http://localhost:8080/api/stats/-19'. We will get TOO_MANY_REQUEST error.
