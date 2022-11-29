/**
 * CS312 Assignment 11.
 *
 * On my honor, Khang Tran, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 *  email address: khang.h.t@utexas.edu
 *  UTEID: kht446
 *  TA name: Yundi Li
 *  Number of slip days used on this assignment: 0
 *
 */
public class GuitarHero {
    public static final int KEYS = 37;

    //  Create a guitar, the GUI for it, and let the user interact with it
    public static void main(String[] args){
        GuitarString[] guitar = createGuitar();
        Keyboard keyboard = new Keyboard();

        //  Keeps track of which strings have been plucked
        boolean[] plucked = new boolean[KEYS];
        String keyArrangement = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        //  Intentional infinite loop to run the program
        while (true) {
            //  Handle key presses
            if (keyboard.hasNextKeyPlayed()) {
                int stringNumber = keyArrangement.indexOf(keyboard.nextKeyPlayed());
                if (stringNumber >= 0) {
                    plucked[stringNumber] = true;
                    guitar[stringNumber].pluck();
                }
            }
            //  Output audio and advance timestep
            StdAudio.play(sumSamples(guitar, plucked));
            tickPluckedStrings(guitar, plucked);
        }
    }

    //  Creates and returns an array of guitarStrings ranging 110hz to 880hz
    public static GuitarString[] createGuitar(){
        GuitarString[] guitar = new GuitarString[37];
        for (int i = 0; i < guitar.length; i++){
            double frequency = 440 * Math.pow(1.05956, i-24);
            guitar[i] = new GuitarString(frequency);
        }
        return guitar;
    }

    //  Takes the sum of all samples from plucked strings
    public static double sumSamples(GuitarString[] guitar, boolean[] plucked){
        double sum = 0;
        for (int i = 0; i < guitar.length; i++){
            if (plucked[i]) {
                sum += guitar[i].sample();
            }
        }
        return sum;
    }

    //  Calls tic on all plucked strings to advance their timesteps
    public static void tickPluckedStrings(GuitarString[] guitar, boolean[] plucked){
        for (int i = 0; i < guitar.length; i++){
            if (plucked[i]) {
                guitar[i].tic();
            }
        }
    }
}
