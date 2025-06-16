## Understanding reactive programming

- [https://github.com/ReactiveX/RxJava](https://github.com/ReactiveX/RxJava)

| Framework                 | UI Style            | Reactive Support | Language for UI    | Good for                            |
| ------------------------- | ------------------- | ---------------- | ------------------ | ----------------------------------- |
| **JavaFX**                | Desktop GUI         | Yes (via RxJava) | Java               | Desktop apps                        |
| **Vaadin**                | Web (Java backend)  | Yes              | Java               | JavaFX-style UIs on web             |
| **Spring Boot + WebFlux** | Web + Reactive APIs | Yes              | Java + JS frontend | Streaming dashboards, APIs          |
| **Micronaut/Quarkus**     | Web + Microservices | Yes              | Java + JS frontend | Cloud-native, low-latency apps      |
| **Jetty + Atmosphere**    | Web server w/ push  | Yes              | JS frontend        | Real-time data push over WebSockets |

- Core Principles

| Term                      | Definition                                                                |
| ------------------------- | ------------------------------------------------------------------------- |
| **Observable**            | A stream that emits data or events over time.                             |
| **Observer / Subscriber** | Listens and reacts to items emitted by Observable.                        |
| **Schedulers**            | Determine what thread the work is done on (e.g., computation, I/O).       |
| **Operators**             | Functions that modify or filter data (`map`, `filter`, `debounce`, etc.). |

- Note

    - Event Driven Programming
        - Functional Programming
        - Reactive Programming
    - Values change over time
    - consumer reacts to data as and when they come 

- Note
    - An observer subscribes to an observable sequence
        - sequence: sending data to the observer one at a 
    - Types of Observables
        - Blocking and Non Blocking
    - Operators:
        - takes an observable
            - every item that the observable emits, it will apply a function to that item
        - returns an observable (emits the result on the destination observable)
    - you can look into the case of `back-pressure` : obeservable emitting more items than the operator or the obeserver can consume

- Info
    - [https://www.baeldung.com/rx-java](https://www.baeldung.com/rx-java)
    - [https://www.reactivemanifesto.org/](https://www.reactivemanifesto.org/)

## Solution
[./prjstockprice/README.md](./prjstockprice/README.md)