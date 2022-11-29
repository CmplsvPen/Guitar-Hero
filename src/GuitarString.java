public class GuitarString {
    private static final double ENERGY_DECAY_FACTOR = 0.994;
    private final int DEFAULT_SAMPLING_RATE = 44100;
    private boolean plucked;
    private int impulseSamples;
    private RingBuffer rb;
    private int time;

    // create a guitar string of the given frequency,
    // using a sampling rate of 44,100
    public GuitarString(double frequency) {
        rb = new RingBuffer(DEFAULT_SAMPLING_RATE);
        impulseSamples = (int) (DEFAULT_SAMPLING_RATE / frequency);
    }

    // create a guitar string whose size and initial
    // values are given by the array
    public GuitarString(double[] init){
        rb = new RingBuffer((init.length));
        for (double i: init){
            rb.enqueue(i);
        }
    }

    // set the buffer to white noise
    public void pluck() {
        while (!rb.isEmpty()){
            rb.dequeue();
        }
        for (int i = 0; i < impulseSamples; i++) {
            double point = Math.random() - 0.5;
            rb.enqueue(point);
        }
    }

    // advance the simulation one time step
    public void tic() {
        rb.enqueue((rb.dequeue() + rb.peek()) / 2 * ENERGY_DECAY_FACTOR);
        time++;
    }

    // return the current sample
    public double sample() {
        return rb.peek();
    }

    // return the number of tics
    public int time() {
        return time;
    }
}
