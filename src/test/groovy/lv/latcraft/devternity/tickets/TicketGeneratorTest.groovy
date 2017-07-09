package lv.latcraft.devternity.tickets

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import groovy.mock.interceptor.StubFor
import org.junit.Test

class TicketGeneratorTest {

    @Test
    void testGenerator() {
        println TicketGenerator.generate([
                name    : 'Andrey Adamovich',
                ticketId: 'RVUhC4YTTonZqj1earUh',
                product : 'DT_RIX_17',
                what    : 'Conference day',
                when    : '1nd of December, 2017',
                company : 'Aestas/IT',
                email   : 'andrey@aestasit.com'
        ], context)
    }

    private static Context getContext() {
        def context = new StubFor(Context)
        def logger = new StubFor(LambdaLogger)
        logger.demand.log(0..10) { String message -> System.out.println message }
        context.demand.getLogger(0..10) { logger.proxyInstance() }
        (Context) context.proxyInstance()
    }

}
