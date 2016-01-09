#### Maven

To build:

    mvn compile

To create an uber JAR:

    mvn package
    
Command for running from Maven:

    mvn exec:java -Dexec.mainClass='com.coveo.blitz.client.Main' -Dexec.args='YOURKEY TRAINING'

    mvn exec:java -Dexec.mainClass='com.coveo.blitz.client.Main' -Dexec.args='YOURKEY COMPETITION YOURGAMEID'

You can run the uber jar directly

    java -jar target/client-1.3.0-SNAPSHOT-runnable.jar YOURKEY COMPETITION YOURGAMEID

#### CLI Usage

The CLI takes 4 arguments:

* Your key
* The game mode (TRAINING or COMPETITION)
* The game id (if playing COMPETITION)

The arguments are space-delimited.

##### Key
The key is your bot's API key.

