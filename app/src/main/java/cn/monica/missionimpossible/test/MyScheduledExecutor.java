package cn.monica.missionimpossible.test;

public class MyScheduledExecutor implements Runnable {

    private String jobName;

    MyScheduledExecutor() {

    }

    public MyScheduledExecutor(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void run() {

        System.out.println(jobName + " is running");
    }
}
