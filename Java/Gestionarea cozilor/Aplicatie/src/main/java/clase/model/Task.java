package clase.model;

public class Task implements Comparable<Task>{
    private int arrivalTime;
    private int processingTime;
    private int idA;
    public Task(int arrivalTime, int processingTime,int id) {
        this.arrivalTime=arrivalTime;
        this.processingTime=processingTime;
        this.idA=id;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public int compareTo(Task otherTask){
        if(this.arrivalTime<otherTask.arrivalTime){
            return -1;
        }else if(this.arrivalTime==otherTask.arrivalTime){
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "(" + idA + "," + arrivalTime + "," + processingTime + ")";
    }

    public int getIdA() {
        return idA;
    }


}
