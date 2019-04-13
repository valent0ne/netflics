## NETFLICS - ceNtralized, sErvice orienTed, selF adaptive appLICation for movie Streaming

NETFLICS is a cross-course video streaming application project based on web services, with particular focus on self-adaptivity.

###Brief description

The system should be able to stream video contents to several connected clients in parallel (the clients can access the system by means of a web-based gui) and it should be able to react to unexpected load peaks, faults and components unavailability exploiting dynamic policies, load distribution techniques and self-adaptive mechanisms.

###Key features

#### Service Oriented Software Enginnering

- service oriented architecure
- consumer/prosumer/producer-based organization
- dynamic policies for parallel requests
- web-based gui

#### Software Enginnering for Autonomous Systems

- MAPE-K based self-adaptive behaviours

  - the prosumer(s) polls the various producer to understands the current loads/endpoint availabilties

  - the prosumer(s) redirects the consumers' requests to the producers that are currently considered to be able to deliver the best performances

  - the producer(s) autonomously updates movies metadata by interrogating the knowledge base

  - the system adapt itself to new instances of producer(s)
