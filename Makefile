help: ## Show this help
	@egrep -h '\s##\s' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

serveDocker: ## Build docker container and run
	./gradlew jibDockerBuild
	docker run -it --rm -p 8080:8080 strings-diff-visualiser

run: ## Run the service via gradle
	./gradlew clean bootRun

test: ## Execute the tests
	./gradlew clean check

testAPI: ## Run one example to check API is working (please deploy using serveDocker or run first)
	 curl --location 'localhost:8080/v1/stringsdiff' --header 'Content-Type: application/json' --data '{"input_one" : "my&friend&Paul has heavy hats! &","input_two": "my friend John has many many friends &"}'


