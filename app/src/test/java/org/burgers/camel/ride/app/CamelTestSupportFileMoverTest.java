package org.burgers.camel.ride.app;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CamelTestSupportFileMoverTest extends CamelTestSupport {
    /*
    An example of how to test a route with a producer component using the Java DSL, Mockito, and CamelTestSupport.
     */

    @EndpointInject(uri = "mock:to")
    private MockEndpoint mockTo;

    @Produce(uri = "direct:start")
    private ProducerTemplate template;

    @Mock
    private CsvToPersonProcessor processor;

    @Captor
    private ArgumentCaptor<List<List<String>>> captor;

    @Before
    public void setup() throws Exception {
        FileMover route = new FileMover();
        route.setTo(mockTo.getEndpointUri());
        route.setFrom("direct:start");
        route.setCsvToPersonProcessor(processor);
        context().addRoutes(route);
    }

    @Test
    public void route() throws Exception {
        mockTo.setExpectedMessageCount(1);

        template.sendBody("John,Smith\nSue,Anderson");

        verify(processor, times(2)).process(captor.capture());

        verifyNoMoreInteractions(processor);

        assertMockEndpointsSatisfied();

        List<List<List<String>>> results = captor.getAllValues();

        assertEquals(results.size(), 2);

        assertCallContains(results, 0, "John", "Smith");
        assertCallContains(results, 1, "Sue", "Anderson");
    }

    private void assertCallContains(List<List<List<String>>> results, int i, String first, String last) {
        List<List<String>> call = results.get(i);
        assertEquals(call.size(), 1);

        List<String> row = call.get(0);

        assertEquals(row.get(0), first);
        assertEquals(row.get(1), last);
    }

}
