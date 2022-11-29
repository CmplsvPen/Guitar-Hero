public class GuitarString {
    private static final double ENERGY_DECAY_FACTOR = 0.994;
    private final int DEFAULT_SAMPLING_RATE = 44100;
    private boolean plucked;
    private int impulseSamples;
    private RingBuffer rb;
    private int time;

    public GuitarString(double frequency) {
        rb = new RingBuffer(DEFAULT_SAMPLING_RATE);
        impulseSamples = (int) (DEFAULT_SAMPLING_RATE / frequency);
    }

    public GuitarString(double[] init){
        rb = new RingBuffer((init.length));
        for (double i: init){
            rb.enqueue(i);
        }
    }

    public void pluck() { /*
        if (plucked) {
            for (int i = 0; i < impulseSamples; i++) {
                double point = Math.random() - 0.5;
                rb.add(point);
            }
        } else {
            plucked = true;
            for (int i = 0; i < impulseSamples; i++) {
                double point = Math.random() - 0.5;
                rb.enqueue(point);
            }
        } */

        while (!rb.isEmpty()){
            rb.dequeue();
        }
        for (int i = 0; i < impulseSamples; i++) {
            double point = Math.random() - 0.5;
            rb.enqueue(point);
        }
    }

    public void tic() {
        rb.enqueue((rb.dequeue() + rb.peek()) / 2 * ENERGY_DECAY_FACTOR);
        time++;
    }

    public double sample() {
        return rb.peek();
    }

    public int time() {
        return time;
    }
}
