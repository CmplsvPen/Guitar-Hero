import java.util.NoSuchElementException;

public class RingBuffer {
    private int size;
    private int cap;
    private int readPointer;
    private int writePointer;
    private double[] data;

    // create an empty ring buffer, with given max capacity
    public RingBuffer(int capacity) {
        cap = capacity;
        data = new double[capacity];
        size = 0;
        readPointer = 0;
        writePointer = 0;
    }

    // return number of items currently in the buffer
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public boolean isFull() {
        return (size == cap);
    }

    //  Adds a double to the array where writePointer is
    //  Then advances writePointer and increments size
    public void enqueue(double input) {
        if (isFull()) {
            throw new IllegalStateException("Cannot call enqueue on a full RingBuffer.");
        }

        data[writePointer] += input;
        this.size++;

        //  Advance the writePointer
        writePointer = (writePointer + 1) % cap;

    }

    //  Extracts a value from the array where readPointer is
    //  Then advances readPointer and decrements size
    public double dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot call dequeue on an empty RingBuffer.");
        }

        double result = data[readPointer];
        data[readPointer] = 0;
        size--;

        //  Advance the readPointer
        readPointer = (readPointer + 1 ) % cap;

        return result;
    }

    //  Returns data[readPointer] without advancing the pointer or deleting the data
    public double peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot call peek on an empty RingBuffer.");
        }
        return data[readPointer];
    }

    //  Format: [readPointer, next, next, next...]
    //  Size of toString depends on amount of stored data, not max capacity
    public String toString() {
        String result = "[";

        for (int i = 0; i < size; i++) {
            int toStringPointer = (readPointer + i) % cap;
            //  Fixing the fencepost
            if (i == size - 1) {
                result += data[toStringPointer];
            } else {
                result += data[toStringPointer] + ", ";
            }
        }
        return result + "]";
    }
}