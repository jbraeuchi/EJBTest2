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

            retrieverTasks.add(new FastRetriever());
            retrieverTasks.add(new SlowRetriever());

            // Submit the tasks to the thread pool and wait for them
            // to complete (successfully or otherwise).
            List<Future<Integer>> taskResults = mes.invokeAll(retrieverTasks);

            // Retrieve the results from the resulting Future list.
            List<Integer> results = new ArrayList<Integer>();
            for (Future<Integer> taskResult : taskResults) {
                try {
                    results.add(taskResult.get());
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    // Handle the AccountRetrieverError.
                }
            }

            return results;

        } catch (Exception e) {
            System.out.println(e);

        }
        return null;
    }

    public static class FastRetriever implements Callable<Integer> {
        public Integer call() {
            Integer result = 42;
            System.out.println("FastRetriever");
            return result;
        }
    }


    public static class SlowRetriever implements Callable<Integer> {
        public Integer call() {
            Integer result = 42 * 42 * 42;
            System.out.println("SlowRetriever");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
