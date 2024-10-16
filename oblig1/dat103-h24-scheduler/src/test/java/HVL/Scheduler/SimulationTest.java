package HVL.Scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationTest {

    Map<Integer, List<Task>> arrivals;
    Simulation simulation;

    @BeforeEach
    public void setUp() {
        simulation = new Simulation();
        arrivals = Map.ofEntries(
                Map.entry(0, List.of(
                        simulation.makeTask(1),  
                        simulation.makeTask(5),
                        simulation.makeTask(3))),
		Map.entry(4, List.of(
                        simulation.makeTask(2),
                        simulation.makeTask(4),
                        simulation.makeTask(6))),
		Map.entry(14, List.of(
                        simulation.makeTask(5),
                        simulation.makeTask(2))),
		Map.entry(16, List.of(
                        simulation.makeTask(1),
                        simulation.makeTask(4))));

        simulation.setArrivals(arrivals);
    }


    @Test
    public void testRR() {
        var rrScheduler = new RRScheduler(simulation.getClock(), 4);
        simulation.setScheduler(rrScheduler);

        // Use the existing view() method to generate the task scheduling output
        var steps = Stream.generate(() -> {
            simulation.step();
            // Use the view() method provided by the RRScheduler class
            var state = "T=%d %s".formatted(simulation.time(), rrScheduler.view());
            simulation.clocktick();
            return state;
        }).limit(34).collect(Collectors.toList());  // Collect the steps as a list of strings

        // Print the actual outputs for debugging
        for (String step : steps) {
            System.out.println(step);
        }

        // Expected output after 33 time quanta
        assertThat(steps, contains(
                "T=0 Scheduled: T1 Ready: T2, T3",
                "T=1 Scheduled: T2 Ready: T3", //t1 finished
                "T=2 Scheduled: T2 Ready: T3",
                "T=3 Scheduled: T2 Ready: T3",
                "T=4 Scheduled: T2 Ready: T3, T4, T5, T6",
                "T=5 Scheduled: T3 Ready: T4, T5, T6, T2", //t2 back in queue (1)
                "T=6 Scheduled: T3 Ready: T4, T5, T6, T2",
                "T=7 Scheduled: T3 Ready: T4, T5, T6, T2",
                "T=8 Scheduled: T4 Ready: T5, T6, T2", //t3 finished
                "T=9 Scheduled: T4 Ready: T5, T6, T2",
                "T=10 Scheduled: T5 Ready: T6, T2", //t4 finished
                "T=11 Scheduled: T5 Ready: T6, T2",
                "T=12 Scheduled: T5 Ready: T6, T2",
                "T=13 Scheduled: T5 Ready: T6, T2",
                "T=14 Scheduled: T6 Ready: T2, T7, T8", //t5 finished
                "T=15 Scheduled: T6 Ready: T2, T7, T8",
                "T=16 Scheduled: T6 Ready: T2, T7, T8, T9, T10",
                "T=17 Scheduled: T6 Ready: T2, T7, T8, T9, T10",
                "T=18 Scheduled: T2 Ready: T7, T8, T9, T10, T6", //t6 back in queue (2)
                "T=19 Scheduled: T7 Ready: T8, T9, T10, T6", //t2 finished
                "T=20 Scheduled: T7 Ready: T8, T9, T10, T6",
                "T=21 Scheduled: T7 Ready: T8, T9, T10, T6",
                "T=22 Scheduled: T7 Ready: T8, T9, T10, T6",
                "T=23 Scheduled: T8 Ready: T9, T10, T6, T7", //t7 back in queue (1)
                "T=24 Scheduled: T8 Ready: T9, T10, T6, T7",
                "T=25 Scheduled: T9 Ready: T10, T6, T7", //t8 finished
                "T=26 Scheduled: T10 Ready: T6, T7", //t9 finished
                "T=27 Scheduled: T10 Ready: T6, T7",
                "T=28 Scheduled: T10 Ready: T6, T7",
                "T=29 Scheduled: T10 Ready: T6, T7",
                "T=30 Scheduled: T6 Ready: T7", //t10 finished
                "T=31 Scheduled: T6 Ready: T7",
                "T=32 Scheduled: T7 Ready: ", //t6 finished
                "T=33 Scheduled: Ready: " //t7 finished
        ));
    }






    @Test
    public void testNSJF() {
        var nsjfScheduler = new NSJFScheduler();
        simulation.setScheduler(nsjfScheduler);

        var steps = Stream.generate(() -> {
            simulation.step();
            var state = "T=%d %s".formatted(simulation.time(), nsjfScheduler.view());
            simulation.clocktick();
            return state;
	    }).limit(34).collect(Collectors.toList());  

        assertThat(steps,contains(
                "T=0 Scheduled: T1 Ready: T3, T2",
                "T=1 Scheduled: T3 Ready: T2",
                "T=2 Scheduled: T3 Ready: T2",
                "T=3 Scheduled: T3 Ready: T2",
                "T=4 Scheduled: T4 Ready: T5, T2, T6",
                "T=5 Scheduled: T4 Ready: T5, T2, T6",
                "T=6 Scheduled: T5 Ready: T2, T6",
                "T=7 Scheduled: T5 Ready: T2, T6",
		"T=8 Scheduled: T5 Ready: T2, T6",
                "T=9 Scheduled: T5 Ready: T2, T6",
                "T=10 Scheduled: T2 Ready: T6",
                "T=11 Scheduled: T2 Ready: T6",
                "T=12 Scheduled: T2 Ready: T6",
                "T=13 Scheduled: T2 Ready: T6",
                "T=14 Scheduled: T2 Ready: T8, T7, T6",
                "T=15 Scheduled: T8 Ready: T7, T6",
                "T=16 Scheduled: T8 Ready: T9, T10, T7, T6",
                "T=17 Scheduled: T9 Ready: T10, T7, T6",
                "T=18 Scheduled: T10 Ready: T7, T6",
                "T=19 Scheduled: T10 Ready: T7, T6",
                "T=20 Scheduled: T10 Ready: T7, T6",
                "T=21 Scheduled: T10 Ready: T7, T6",
                "T=22 Scheduled: T7 Ready: T6",
                "T=23 Scheduled: T7 Ready: T6",
                "T=24 Scheduled: T7 Ready: T6",
                "T=25 Scheduled: T7 Ready: T6",
                "T=26 Scheduled: T7 Ready: T6",
                "T=27 Scheduled: T6 Ready: ",
                "T=28 Scheduled: T6 Ready: ",
                "T=29 Scheduled: T6 Ready: ",
                "T=30 Scheduled: T6 Ready: ",
                "T=31 Scheduled: T6 Ready: ",
                "T=32 Scheduled: T6 Ready: ",				
                "T=33 Scheduled: Ready: "	
        ));
    }
    
}
