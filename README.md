# Spring Contract Stack overflow reproducer

**Issue occurs when one has some request fields populated from a file (json in this case)  Like this:**
```groovy
body(
    person: $(c(anyJson), p(file('person.json'))),
)
```

## To reproduce
```console
./gradlew generateContractTests
```

Produces
```console
Execution failed for task ':generateContractTests'.
> java.lang.StackOverflowError (no error message)
```

## Details
Issue occurs using Spring Cloud Contract >= 2.1.0-RC3
2.1.0-RC2 works as expected

Branch master contains 2.1.0-RC3 version of Spring Cloud Contract which fails.
Branch ok contains version 2.1.0-RC2 which works as expected.