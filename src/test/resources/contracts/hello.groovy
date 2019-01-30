import org.springframework.cloud.contract.spec.Contract

import static java.util.regex.Pattern.DOTALL
import static java.util.regex.Pattern.compile

Contract.make {
    request {
        def anyJson = regex(compile(/\{.*\:.*\}/, DOTALL))
        method 'POST'
        url '/api/persons'
        body(
                person: $(c(anyJson), p(file('person.json'))),
        )
        headers {
            header(contentType(), applicationJsonUtf8())
        }
    }
    response {
        status 200
    }
}