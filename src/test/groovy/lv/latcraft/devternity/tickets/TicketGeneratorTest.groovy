package lv.latcraft.devternity.tickets

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger
import groovy.mock.interceptor.StubFor
import org.junit.Test

class TicketGeneratorTest {

  @Test
  void testGenerator() {
    println TicketGenerator.generate([
      name     : 'Andrey Adamovich',
      ticketId : 'RVUhC4YTTonZqj1earUh',
      product  : 'ARTDT2016',
      company  : 'Aestas/IT',
      email    : 'andrey@aestasit.com',
    ], context)
    println TicketGenerator.generate([
      name     : 'Andrey Adamovich',
      ticketId : 'gQQ0bDIeqHm3fFB2Iqeo',
      product  : 'AWSDT2016',
      company  : 'Aestas/IT',
      email    : 'andrey@aestasit.com',
    ], context)
    println TicketGenerator.generate([
      name     : 'Andrey Adamovich',
      ticketId : 'K2iYj4QU3LxB6OE2HoJp',
      product  : 'DVTRN2016',
      company  : 'Aestas/IT',
      email    : 'andrey@aestasit.com',
    ], context)
    println TicketGenerator.generate([
      name     : 'Andrey Adamovich',
      ticketId : 'vOXBT8tKu9lCXy2Zo05p',
      product  : 'KVLDT2016',
      company  : 'Aestas/IT',
      email    : 'andrey@aestasit.com',
    ], context)
    println TicketGenerator.generate([
      name     : 'Andrey Adamovich',
      ticketId : 't5skDAdGAFCB9slqysm1',
      product  : 'ELKDT2016',
      company  : 'Aestas/IT',
      email    : 'andrey@aestasit.com',
    ], context)
    println TicketGenerator.generate([
      name     : 'Andrey Adamovich',
      ticketId : 'X0HLBGF6iFBSKaMEwwkb',
      product  : 'SPRDT2016',
      company  : 'Aestas/IT',
      email    : 'andrey@aestasit.com',
    ], context)
    println TicketGenerator.generate([
      name     : 'Andrey Adamovich',
      ticketId : 'wFHpiBRG98wA61T1BTIK',
      product  : 'VAULT2016',
      company  : 'Aestas/IT',
      email    : 'andrey@aestasit.com',
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
