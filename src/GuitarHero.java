public class GuitarHero {
    public static final int KEYS = 37;

    public static void main(String[] args){
        boolean[] plucked = new boolean[KEYS];
        double[] freqs = initFreqs();
        GuitarString[] guitar = createGuitar(freqs);
        String keyArrangement = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        Keyboard keyboard = new Keyboard();
        while (true) {
            if (keyboard.hasNextKeyPlayed()) {
//                System.out.println("KEY PRESSED");

                int stringNumber = keyArrangement.indexOf(keyboard.nextKeyPlayed());
                if (stringNumber > 0) {
                    plucked[stringNumber] = true;
                    guitar[stringNumber].pluck();
                }
            }
            double sample = sumSamples(guitar, plucked);
            StdAudio.play(sample);
            tickPluckedStrings(guitar, plucked);
        }
    }

    //  Creates and return an array of equal-tempered frequencies at A=440hz
    //  Range is from 110hz to 880hz
    public static double[] initFreqs(){
        double[] freqs = new double[KEYS];
        for (int i = 0; i < KEYS; i++){
            freqs[i] = 440 * Math.pow(1.05956, i-24);
        }
        return freqs;
    }

    public static GuitarString[] createGuitar(double[] freqs){
        GuitarString[] guitar = new GuitarString[37];
        for (int i = 0; i < guitar.length; i ++){
            guitar[i] = new GuitarString(freqs[i]);
        }
        return guitar;
    }

    public static double sumSamples(GuitarString[] guitar, boolean[] plucked){
        double sum = 0;
        for (int i = 0; i < guitar.length; i++){
            if (plucked[i]) {
                sum += guitar[i].sample();
            }
        }
        return sum;
    }

    public static void tickPluckedStrings(GuitarString[] guitar, boolean[] plucked){
        for (int i = 0; i < guitar.length; i++){
            if (plucked[i]) {
                guitar[i].tic();
            }
        }
    }
}
