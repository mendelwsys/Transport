Libraries to simplify service calls from clients.
Originally intended to build interaction between a J2ME MIDlet and a server (2005)
over TCP socket or HTTP protocol

The description of a service interface is specified using a subset of the IDL specification.
The builder of the Skeleton and Proxy service in the java language is implemented in the Coder module.
There is also a builder for C# (originally the Coder_net project was intended for PocketPC) it
compatible with java builder i.e. you can use a service, for example, in java, and make client calls in C#

The service can be implemented both on the server side and called by the client,
and on the client side and be called by the server. In the latter case, the interaction scenario
looks like that

1. The client hosts a service to call by registering the implementation (implementation) of the Skeleton built according to idl
in the transport layer
2. The client initiates a connection to the server.
3. After the connection is established, the server connects the channel (transport layer) of interaction with the client with the Proxy built by idl
4. After that, on the server side, you can make calls to the client's business logic through the interface described on idl


