package cn.monica.missionimpossible.util;

import java.util.concurrent.Semaphore;

public class SemaphoreUtil {
    private static SemaphoreUtil semaphoreUtil = new SemaphoreUtil();
    final Semaphore semaphore = new Semaphore(1);
    public static SemaphoreUtil getInstance() {
        return semaphoreUtil;
    }
    public void Lock() throws InterruptedException {
        semaphore.acquire();
    }
    public void UnLock()
    {
        semaphore.release();
    }

}
