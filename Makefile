.PHONY: all package run clean dist-clean

all: package

package:
	./mvnw package

run: package
	java -jar target/visualnovel-0.1.0-SNAPSHOT-jar-with-dependencies.jar

clean:
	./mvnw clean
	rm -f sqlite.db

dist-clean: clean
	rm -f .mvn/wrapper/maven-wrapper.jar
