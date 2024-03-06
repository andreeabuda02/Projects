package clase.logic;

import clase.model.Server;
import clase.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;

    public Scheduler(int maxNoServers, int maxTasksPerServer){

        this.maxNoServers=maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;
        this.servers=new ArrayList<>();
        for(int i=0;i<maxNoServers;i++){
            Server server=new Server(maxTasksPerServer,i);
            Thread thread =new Thread(server);
            thread.start();
            servers.add(server);
        }
    }

    public void dispatchTask(Task t){
        int min=1000000;
        int pozMin=0;
        for(int i=0;i<maxNoServers;i++){
            if (servers.get(i).getWaitingPeriod()<min) {
                min = servers.get(i).getWaitingPeriod();
                pozMin = i;
            }
        }
        servers.get(pozMin).addTask(t);
    }

    public List<Server> getServers() {
        return servers;
    }

    public void stopServers(){
        for(Server server:servers){
            server.stop();
        }
    }
}
