import java.util.NoSuchElementException;

public class RingBuffer {
    private int size;
    private int cap;
    private int readPointer;
    private int writePointer;
    private double[] data;

    public RingBuffer(int capacity) {
        cap = capacity;
        data = new double[capacity];
        size = 0;
        readPointer = 0;
        writePointer = 0;
    }

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
        data[writePointer] = input;
        this.size++;

        //  Advance the writePointer
        if (writePointer == cap - 1) writePointer = 0;
        else writePointer++;
    }

    //  Extracts a value from the array where readPointer is
    //  Then advances readPointer and decrements size
    public double dequeue() {
//        if (isEmpty()) {
//            throw new NoSuchElementException("Cannot call dequeue on an empty RingBuffer.");
//        }

        double temp = data[readPointer];
        data[readPointer] = 0;
        size--;

        //  Advance the readPointer
        if (readPointer == cap - 1) readPointer = 0;
        else readPointer++;

        return temp;
    }

    //  Returns data[readPointer] without advancing the pointer or deleting the data
    public double peek() {
//        if (isEmpty()) {
//            throw new NoSuchElementException("Cannot call peek on an empty RingBuffer.");
//        }
        return data[readPointer];
    }

    //  Format: [readPointer, next, next, next...]
    //  Size of toString depends on size, not cap
    public String toString() {
        String result = "[";
        for (int i = 0; i < size; i++) {
            int toStringPointer = (readPointer + i) % cap;
            if (i == size - 1) {
                result += data[toStringPointer];
            } else {
                result += data[toStringPointer] + ", ";
            }
        }
        return result + "]";
    }
}