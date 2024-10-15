# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

[Phase 3 Sequence Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYtQAr5y49FMr73H0XwfksKyTAC5zAgUCYoKUCAHjysL7oeqLorE2LwQUrosqU5JUjSwEmkS4YFJa3K8gagrCjAwGOqIAA8SZ4aSNHaIxKi4WGboerqmSxv604hmxqgURy0YwEJDr-lALG5PBpRoTy2a5u28lJnJpSAQM5ajpBNZBrOzYTFBmDZOYCk-qU-aDr0QH6aMhlTsZjamaBMBLpwq7eH4gReCg6B7gevjMMe6SZBZ57MLK1DXtIACiu6JfUiXNC0D6qE+3TTiZaAaX+MGXNO0BIAAXvEiTlExeXuWg+SYHJcFyghSH2GFqGhb6GEYthrU8WRfGEUJAZuXOpFMm6ElcjyMZBrRYR1XOXEoApYlWgtnE4fkG0cCg3CCUGsIydok1muJ+SWpEwwQDQ0lbfITXFZpimtcp3VgGpCBYD+CnaT0ByWRg1lXmUdlDt5K6eH5G6Qrau7QjAADio6shFp7RTksWXvFaYVMjqUZfYo65eN6CFfk2mlVAFVVWgNXLegjXNfkSnILEJOjKoqHQqj3O9Vhq2DVN+EjcdTNoOd5FXRyVHzXG2hCkt5MFfB628fhp1PTtG0c2A-NqLC0vTbLpTIxSMDAMqKOjoy3SoDAXMoAAktIswnpkBp3NM3Q6AgoANt7o6zM7AByo6rRrQ34frMAQAAZk7aPPUClCg2qCGI7EhuqN9v1g-9L06VMzuqOMlT9M7bsVwAjL2ADMAAsTye-qTkgV8-uB8HzlmU84ejmZewwI0QMxRnJS2QOQ6l2jFcVFXo41+U9fN63kXtyOfegU83cgEHHcuX0g+jMPo9Q7566BNgPhQNg3DwAJhiGykm9Y+YbI2ZUtQNMTpPBFVkOU+KBvxgyKmnMoNM6YEGqjAWqqt8izxPqOCOO9zKsyUp6PUztYRwGfobQWWJha7U1qScWfoxp1nqibFkM15YPUVvIZWVtVZR1YmQ902tgAkI2tgzIhtYQgNoeoGaUlDb2zQI7au7sYBt17igX2aB96H23oo5Oow0FiHVhwmO5Dl7SFTqmSe4J8FegEaOfOGki6QJLig0YK8YBrybl5D+IMkw2Qhg5exrtpB10bi4y+MNr4BEsAdJCyQYAACkIA8ltqMQIKi3E4yKHjco1RKR3haM7Mm1C5xDgfsAMJUA4AQCQlAUOBiwF4wgamUo0DKqwIZvAyWSDvGFOKaU8pKwADqLAXZpRaAAIV3AoOAABpL4Mj-HN1cZg96AArWJDhRywhiTyQhKA0R9V4ZwgiFJRqSxEZdSic1GHBiVnRSW7DcgbW4bsvR7pCKCJkcc+hZyJGXLCDIm5G1Pk6wGqQx5pQ-BaAsaMWEHTKBdOgJUhx0g3lm0pNgMFL87acTkiY+U6y0CbJzD9axWli6A2SZPS4XiSXLivv5AIXgildi9LAYA2AH6EHpm-TGwMUnfwqElFKaUMrGEptpIxsE2bvRANwPAeCpVZi2ZhYhus9mSsZQaHmiLrobDuoYa2CA7QCm2oCvhsrc7G1DI8maN1tVWxtv8nhSrgUqrwEJdV5rRaiLNla+6urzmyRelihCiBGVWL+kS2xJKuVkvBjPXoUMgA)
