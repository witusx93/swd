package pwr.swd;

import org.junit.Before;
import org.junit.Test;
import pwr.swd.algorithm.DPAlgorithm;
import pwr.swd.algorithm.SystemState;
import pwr.swd.algorithm.graph.GraphNode;
import pwr.swd.algorithm.graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mi on 2016-05-15.
 */
public class DPAlgorithmTest {

    private Vertex[] v;
    private List<Vertex> vertexList = new ArrayList<Vertex>();

    @Before
    public void setUp() {
        GraphNode n0 = new GraphNode("Loc0", 50L);
        GraphNode n1 = new GraphNode("Loc1", 15L);
        GraphNode n2 = new GraphNode("Loc2", 20L);
        GraphNode n3 = new GraphNode("Loc3", 10L);

        Vertex v0 = new Vertex(n0);
        Vertex v1 = new Vertex(n1);
        Vertex v2 = new Vertex(n2);
        Vertex v3 = new Vertex(n3);

        v = new Vertex[]{v0, v1, v2, v3};
        Collections.addAll(vertexList, v[1], v[2], v[3]);

        v[0].addDestination(v[1], 1L);
        v[0].addDestination(v[2], 2L);
        v[0].addDestination(v[3], 3L);

        v[1].addDestination(v[0], 2L);
        v[2].addDestination(v[0], 4L);
        v[3].addDestination(v[0], 6L);
    }

    @Test
    public void newDestinataionsShouldAdd() {
        DPAlgorithm dpa = new DPAlgorithm(v[0], vertexList);

        SystemState s1 = new SystemState(v[0], vertexList);
        SystemState s2 = s1.generateNewState(v[1]);
        SystemState s3 = s1.generateNewState(v[2]);
        SystemState s4 = s1.generateNewState(v[3]);

        List<SystemState> stateList = new ArrayList<SystemState>();
        Collections.addAll(stateList,s2,s3);

        assertEquals(2, stateList.size());

        dpa.filterBestOptions(stateList, s4);

        assertEquals(3, stateList.size());
    }

    @Test
    public void meetOneCriteriaShouldAdd() {
        DPAlgorithm dpa = new DPAlgorithm(v[0], vertexList);

        SystemState s1 = new SystemState(v[0], vertexList);
        SystemState s2 = s1.generateNewState(v[1]);
        SystemState s3 = s1.generateNewState(v[2]);

        s2=s2.generateNewState(v[0]);
        s3=s3.generateNewState(v[0]);

        List<SystemState> stateList = new ArrayList<SystemState>();
        stateList.add(s2);

        assertEquals(1, stateList.size());

        dpa.filterBestOptions(stateList, s3);

        assertEquals(2, stateList.size());
    }

    @Test
    public void meetNoCriteriaShouldNotAdd() {
        DPAlgorithm dpa = new DPAlgorithm(v[0], vertexList);

        SystemState s1 = new SystemState(v[0], vertexList);
        SystemState s2 = s1.generateNewState(v[1]);
        SystemState s3 = s1.generateNewState(v[3]);

        s2=s2.generateNewState(v[0]);
        s3=s3.generateNewState(v[0]);

        List<SystemState> stateList = new ArrayList<SystemState>();
        stateList.add(s2);

        assertEquals(1, stateList.size());

        dpa.filterBestOptions(stateList, s3);

        assertEquals(1, stateList.size());
    }

    @Test
    public void e2ePositiveCaseTest() {
        DPAlgorithm dpa = new DPAlgorithm(v[0], vertexList);

        v[0].addDestination(v[1], 10L);
        v[0].addDestination(v[2], 5L);
        v[0].addDestination(v[3], 10L);

        v[1].addDestination(v[0], 10L);
        v[1].addDestination(v[2], 1L);
        v[1].addDestination(v[3], 5L);

        v[3].addDestination(v[1], 1L);
        v[3].addDestination(v[0], 6L);
        v[3].addDestination(v[2], 3L);

        v[2].addDestination(v[1], 40L);
        v[2].addDestination(v[3], 3L);
        v[2].addDestination(v[0], 11L);

        SystemState result = dpa.getOptimalPath();

        assertEquals(v[2], result.getVisitedLocations().get(1));
        assertEquals(v[3], result.getVisitedLocations().get(2));
        assertEquals(v[1], result.getVisitedLocations().get(3));
    }

    @Test
    public void e2eNoPathCaseTest() {
        DPAlgorithm dpa = new DPAlgorithm(v[0], vertexList);

        v[0].addDestination(v[1], 1000L);
        v[0].addDestination(v[2], 500L);
        v[0].addDestination(v[3], 1000L);

        v[1].addDestination(v[0], 1000L);
        v[1].addDestination(v[2], 1000L);
        v[1].addDestination(v[3], 5000L);

        v[3].addDestination(v[1], 1000L);
        v[3].addDestination(v[0], 6000L);
        v[3].addDestination(v[2], 3000L);

        v[2].addDestination(v[1], 4000L);
        v[2].addDestination(v[3], 3000L);
        v[2].addDestination(v[0], 1100L);

        SystemState result = dpa.getOptimalPath();

        assertEquals(null, result);
    }
}
