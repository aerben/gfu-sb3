---
marp: true
style: |
    section {
         font-family: 'Helvetica' , sans-serif !important;
    }
    h1 {
        font-size: 2em
    }
    h2 {
        font-size: 1.4em
    }
    section {
        font-size: 2.5em
    }
    img[alt~="center"] {
        display: block;
        margin: 0 auto;
    }
---
<!-- paginate: false -->
# Spring Boot Testing

---
<!-- paginate: true -->
## Was brauchen wir?
* Zugriff auf den `ApplicationContext`
* Spring Dependency Injection
* Auslesen von Properties
* Mocks für Web, Data, REST APIs...
