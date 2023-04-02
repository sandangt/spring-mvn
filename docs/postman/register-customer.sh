curl --location 'localhost:10132/api/v1/customers' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "San",
    "lastName": "Dang",
    "email": "sandang@email.com"
}'
