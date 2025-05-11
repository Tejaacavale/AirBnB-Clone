mvn clean package
sudo docker compose down -v --remove-orphans
sudo docker volume rm postgres-data

sudo docker compose build
sudo docker compose up