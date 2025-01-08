package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

/**
 * A queue implementation for storing customers.
 */
public class QueueOfCustomers {
    private final Queue<Customer> queue;

    public QueueOfCustomers() {
        queue = new LinkedList<>();
    }

    /**
     * Enqueue a new customer at the end of the queue.
     */
    public void enqueue(Customer c) {
        queue.offer(c);
    }

    /**
     * Dequeue (remove) the next customer from the front of the queue.
     */
    public Customer dequeue() {
        return queue.poll();
    }

    /**
     * Check if the queue is empty.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns the total number of customers in the queue.
     */
    public int size() {
        return queue.size();
    }

    /**
     * Return a copy of all customers currently in the queue (for display or iteration).
     */
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(queue);
    }
}
