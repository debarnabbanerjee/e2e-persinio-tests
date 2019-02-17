#DOCKER CONFIGURATION FOR SELENIUM SERVER STOP
#!/usr/bin/env bash
echo Stop Selenium Chrome Node container
docker stop selenium-chrome
echo Remove Selenium Chrome Node container
docker rm selenium-chrome

echo Stop Selenium Firefox Node container
docker stop selenium-firefox
echo Remove Selenium Firefox Node container
docker rm selenium-firefox

echo Stop Selenium Hub container
docker stop selenium-hub
echo Remove Selenium Hub container
docker rm selenium-hub

echo Done