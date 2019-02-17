#DOCKER CONFIGURATION FOR SELENIUM SERVER START
#!/usr/bin/env bash
echo 'Starting Selenium Hub Container...'
HUB=$(docker run --privileged -d -p 4444:4444 --name selenium-hub selenium/hub:latest)
HUB_NAME=$(docker inspect -f '{{ .Name  }}' $HUB | sed s:/::)
echo 'Waiting for Hub to come online...'
#docker logs -f $HUB &
sleep 2

echo 'Starting Selenium Chrome node...'
NODE_CHROME=$(docker run --privileged -d -v /dev/shm:/dev/shm -e NODE_MAX_SESSION=10  -e SE_OPTS="-browser browserName=chrome,maxInstances=10,platform=LINUX" -P -p 5900:5900 --name selenium-chrome --link $HUB_NAME:hub selenium/node-chrome-debug:latest)
echo 'Starting Selenium Firefox node...'
NODE_FIREFOX=$(docker run --privileged -d -e NODE_MAX_SESSION=10 -e SE_OPTS="-browser browserName=firefox,version=54.0,maxInstances=10,platform=LINUX,acceptSslCerts=true,acceptInsecureCerts=true" -P -p 5901:5900 --name selenium-firefox --link $HUB_NAME:hub selenium/node-firefox-debug:latest)
docker logs -f $NODE_CHROME &
#docker logs -f $NODE_FIREFOX &
echo 'Waiting for nodes to register and come online...'
sleep 2

echo 'List of containers:'
docker ps

echo Done