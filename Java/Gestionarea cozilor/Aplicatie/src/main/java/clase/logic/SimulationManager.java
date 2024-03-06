package clase.logic;

import clase.interfata.SimulationFrame;
import clase.model.Server;
import clase.model.Task;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationManager implements Runnable {
    //data read from UI
    private int timeLimit;//maximum processing time-read from UI
    private int maxProcessingTime;
    private int minProcessingTime;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int numberOfServers;
    private int numberOfClients;
    //entity responsible with queue management and client distribution
    private Scheduler scheduler;
    //pool of tasks (client shopping in the store)
    private List<Task> generatedTasks;
    private SimulationFrame frame;

    private boolean oprireFortata=false;
    private int maxPeak=0;
    private int peakHour;
    private float averageTime;
    private float timpAsteptare=0;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int maxArrivalTime, int minArrivalTime, int numberOfServers, int numberOfClients) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;

        scheduler=new Scheduler(numberOfServers,100);
        generatedTasks=new ArrayList<>();
        generatedNRandomTasks();
    }

    public void setFrame(SimulationFrame frame) {
        this.frame = frame;
    }

    private void generatedNRandomTasks(){

        for(int i=0;i<numberOfClients;i++){
            int arrivalTime=(int)(Math.random()*(maxArrivalTime-minArrivalTime)+minArrivalTime);;
            int processingTime=(int)(Math.random()*(maxProcessingTime-minProcessingTime)+minProcessingTime);

            Task task=new Task(arrivalTime,processingTime,i);
            generatedTasks.add(task);
        }
        Collections.sort(generatedTasks);
    }


    private void oraVarf(int currentTime) {
        int suma = 0;
        for (Server s : scheduler.getServers()) {
            suma = suma + s.dimensiuneTasks();
        }
        if (suma > maxPeak) {
            maxPeak = suma;
            peakHour = currentTime;
        }
    }

    private void medieTimpServire(){
        for (Task t:generatedTasks) {
            averageTime=averageTime+t.getProcessingTime();
        }
        averageTime=averageTime/numberOfClients;
    }

    private void medieTimpAsteptare(Task t){
        for (Server s:scheduler.getServers()) {
            if(s.cautareTask(t)==1){
                timpAsteptare=timpAsteptare+s.getWaitingPeriod();
                timpAsteptare=timpAsteptare-t.getProcessingTime();
            }
        }
    }

    private void adaugareTask(int timpCurent){
        for(int i=0;i<generatedTasks.size();i++){
            Task task = generatedTasks.get(i);
            if(task.getArrivalTime()==timpCurent) {
                scheduler.dispatchTask(task);
                medieTimpAsteptare(task);
                generatedTasks.remove(i);
                i--;
            }
        }
    }

    @Override
    public void run() {
        int currentTime = 0;
        medieTimpServire();
        while(currentTime <= timeLimit && oprireFortata==false){
            adaugareTask(currentTime);
            oraVarf(currentTime);
            frame.afisareText(currentTime,generatedTasks,scheduler.getServers(),numberOfServers);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            currentTime++;
            oprireFortata = frame.oprireFortata();
        }
        for (Server s:scheduler.getServers()) {
            s.setRunning(false);
        }
        timpAsteptare=timpAsteptare/numberOfClients;
        frame.scriereFisier(peakHour, averageTime, timpAsteptare);
    }
}
