# Java Socket Programming

This project demonstrates a simple client-server application using Java sockets. The server can handle multiple clients simultaneously, and clients can send messages to each other through the server.

## Files

- [Client.java](#file:Client.java-context)
- [Server.java](#file:Server.java-context)

## How to Run

### Server

1. Compile the `Server.java` file:
    ```sh
    javac Server.java
    ```

2. Run the server:
    ```sh
    java Server
    ```

### Client

1. Compile the `Client.java` file:
    ```sh
    javac Client.java
    ```

2. Run the client:
    ```sh
    java Client
    ```

## Features

### Server

- Accepts multiple client connections.
- Broadcasts messages from one client to all other connected clients.
- Handles client disconnections gracefully.
- Allows clients to change their nicknames using the `/nick` command.
- Allows clients to quit the chat using the `/quit` command.

### Client

- Connects to the server.
- Sends messages to the server, which are then broadcasted to all other clients.
- Allows changing the nickname using the `/nick` command.
- Allows quitting the chat using the `/quit` command.

## Example Usage

1. Start the server:
    ```sh
    java Server
    ```

2. Start multiple clients:
    ```sh
    java Client
    ```

3. In each client, enter a nickname when prompted.
4. Send messages from one client, and observe them being broadcasted to all other clients.
5. Change the nickname using the `/nick new_nickname` command.
6. Quit the chat using the `/quit` command.