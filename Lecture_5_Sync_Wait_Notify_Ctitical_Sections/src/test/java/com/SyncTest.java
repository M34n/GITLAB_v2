package com;

import com.mycompany.prepare.utils.Utils;
import org.junit.Test;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static junit.framework.TestCase.assertEquals;

// Mutex, ctitical section in the static method, acquire lock in the same thread (Mutex knows who locked it)
// Intrinsic lock is associated with the Class instance (static context)
// Extrinsic lock is associated with a particular dynamic object (not the Class instance)
public class SyncTest {

    private static int counter = 0;

    Lock lock = new ReentrantLock();

    public void change() {

        lock.lock();
        try {
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
            counter++;
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testSync() {
        new Thread(() -> {
            change();
        }).start();
        new Thread(() -> {
            change();
        }).start();

        // TODO: fix it with use of 'if(tryLock())' for heavy calculations (~sleep(1000))
        if(lock.tryLock())
            assertEquals(1, counter);
    }
}
