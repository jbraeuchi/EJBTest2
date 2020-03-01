package ch.brj.ejb.concurrent;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Stateless
public class Executor {

    @Resource
    ManagedExecutorService mes;

    public List<Integer> doConcurrrently() {
        try {
            List<Callable<Integer>> retrieverTasks = new ArrayList<Callable<Integer>>();

            retrieverTasks.add(new Retriever("Fast", 10));
            retrieverTasks.add(new Retriever("Slow 1", 4000));
            retrieverTasks.add(new Retriever("Slow 2", 2000));

            long t1 = System.currentTimeMillis();
            // Submit the tasks to the thread pool and wait for them
            // to complete (successfully or otherwise).
            List<Future<Integer>> taskResults = mes.invokeAll(retrieverTasks);

            // Retrieve the results from the resulting Future list.
            List<Integer> results = new ArrayList<Integer>();
            for (Future<Integer> taskResult : taskResults) {
                try {
                    results.add(taskResult.get());
                } catch (ExecutionException e) {
                    System.out.println(e);
                }
            }
            long t2 = System.currentTimeMillis();
            System.out.println("Duration=" + (t2 - t1));

            return results;

        } catch (Exception e) {
            System.out.println(e);

        }
        return null;
    }

    public static class Retriever implements Callable<Integer> {
        private String name;
        private int sleep;

        public Retriever(String name, int sleep) {
            this.name = name;
            this.sleep = sleep;
        }

        public Integer call() {
            Integer result = 42 * sleep;
            System.out.println(name + " start");
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " end");
            return result;
        }
    }
}
