# Airport manager


## Running the project

Run using Podman (for Docker replace `podman` with `docker` and `podman-compose` with `docker compose`):
```bash
podman-compose up airport

# or

cd airplane-module
podman build -f Dockerfile -t airplane-module
podman run -t airplane-module -e PORT=8081

cd steward-module
podman build -f Dockerfile -t steward-module
podman run -t steward-module -e PORT=8082

cd airport
podman build -f Dockerfile -t airport
podman run -t airport -e airport_url="http://localhost:8081/" -e steward_url="http://localhost:8082/" 
```


Run using Gradle:
```bash
PORT=8081 gradle :airplane-module:bootRun
PORT=8082 gradle :steward-module:bootRun
PORT=8080 airplane_url="http://localhost:8081/" steward_url="http://localhost:8082/" gradle :airport:bootRun
```

Documentation is available at
- Airport: http://localhost:8080/docs
- Airplane module: http://localhost:8081/docs
- Steward module: http://localhost:8082/docs



## Development

Example for `airplane-module`:
```bash
# compile project
gradle :airplane-module:build

# just run
gradle :airplane-module:bootRun
PORT=8081 gradle :airplane-module:bootRun

# cleanup and run
gradle clean bootRun

# compile and run
gradle :airplane-module:bootJar
java -jar ./build/libs/airplane-module-*.jar 
```


