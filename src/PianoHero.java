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
public class PianoHero {
    public static final int KEYS = 88;
    public static final double RESONANCE_FACTOR = 0.25;

    //  Create a piano, the GUI for it, and let the user interact with it
    public static void main(String[] args){
        PianoString[] piano = createpiano();
        Keyboard keyboard = new Keyboard();

        //  Keeps track of which strings have been plucked
        boolean[] plucked = new boolean[KEYS];
        String keyArrangement = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        //  Intentional infinite loop to run the program
        while (true) {
            //  Handle key presses
            if (keyboard.hasNextKeyPlayed()) {
                int stringNumber = keyArrangement.indexOf(keyboard.nextKeyPlayed());
                if (stringNumber < 0) stringNumber = -100;
                stringNumber += 35;
                if (stringNumber >= 0) {
                    plucked[stringNumber] = true;
                    piano[stringNumber].pluck(1); //- (6 * RESONANCE_FACTOR));
                    System.out.println(stringNumber);
                    System.out.println((stringNumber + 1) % 12);
                    System.out.println();
                    for (int i = 0; i < KEYS / 12; i++){
                        int resonantString = i * 12 + (stringNumber + 1) % 12 - 1;
                        System.out.println(resonantString);
                        if (resonantString >= 0 && resonantString != stringNumber) {
                            piano[resonantString].pluck(RESONANCE_FACTOR);
                        }
                    }
                    System.out.println();
                }
            }
            //  Output audio and advance timestep
            StdAudio.play(sumSamples(piano, plucked));
            tickPluckedStrings(piano, plucked);
        }
    }

    //  Creates and returns an array of pianoStrings ranging 110hz to 880hz
    public static PianoString[] createpiano(){
        PianoString[] piano = new PianoString[KEYS];
        for (int i = 0; i < piano.length; i++){
            double frequency = 440 * Math.pow(1.05956, i-47);
            piano[i] = new PianoString(frequency);
        }
        return piano;
    }

    //  Takes the sum of all samples from plucked strings
    public static double sumSamples(PianoString[] piano, boolean[] plucked){
        double sum = 0;
        for (int i = 0; i < piano.length; i++){
            if (plucked[i]) {
                sum += piano[i].sample();
            }
        }
        return sum;
    }

    //  Calls tic on all plucked strings to advance their timesteps
    public static void tickPluckedStrings(PianoString[] piano, boolean[] plucked){
        for (int i = 0; i < piano.length; i++){
            if (plucked[i]) {
                piano[i].tic();
            }
        }
    }
}
