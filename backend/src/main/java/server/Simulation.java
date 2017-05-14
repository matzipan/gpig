package server;

import domain.Agent;
import domain.World;
import domain.util.Location;
import domain.vessel.SmartBoat;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Oliver Lea
 */
public class Simulation {

    private static final long PERIOD = 1000l;
    private static final long INITIAL_DELAY = 1000l;

    private Timer timer;
    private World world;
    private Session session;

    private List<Agent> agents = Arrays.asList(new Agent[] {
            new SmartBoat(new Location(-2.69165,49.91741))
    });

    public Simulation(Session session) {
        this.session = session;
        this.world = new World(agents);

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Simulation.this.tick();
                Simulation.this.sendToRemote();
            }
        };
        this.timer = new Timer();
        this.timer.schedule(tt, INITIAL_DELAY, PERIOD);
    }

    public void end() {
        this.timer.cancel();
    }

    private void tick() {
        world.tick();
    }

    private void sendToRemote() {
        try {
            session.getRemote().sendString(String.valueOf(world.toJSON()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
