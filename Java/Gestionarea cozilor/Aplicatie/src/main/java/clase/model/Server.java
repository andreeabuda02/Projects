package clase.model;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
public class Server implements Runnable {
    private LinkedBlockingQueue <Task> tasks;
    private AtomicInteger waitingPeriod;
    private int idServer;
    private volatile boolean running;
    public Server(int maxTasksPerServer, int idServer) {
        tasks = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
        running = true;
        this.idServer=idServer;
    }
    public int cautareTask(Task task){
        for (Task t:tasks) {
            if(t.getIdA()==task.getIdA()){
               return 1;
            }
        }
        return 0;
    }
    public int dimensiuneTasks(){
        return tasks.size();
    }

    public void afisareTask(JTextArea text){
        for (Task ts:tasks) {
            text.append(ts.toString() + " ");
        }
        text.append("\n");
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.set(waitingPeriod.get()+newTask.getProcessingTime());
    }

    public void run(){
        //process task
        while(running) {
            try {
                if(tasks.size()!=0){
                    Task currentTask = tasks.element();
                    Thread.sleep(currentTask.getProcessingTime()*1000L);
                    waitingPeriod.set(waitingPeriod.get()-currentTask.getProcessingTime());
                    tasks.remove();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }

    public int getWaitingPeriod() {

        return waitingPeriod.get();
    }

    @Override
    public String toString() {
        return tasks.toString();
    }

    public int getIdServer() {
        return idServer;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
