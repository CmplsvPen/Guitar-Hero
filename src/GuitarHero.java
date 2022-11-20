import java.util.NoSuchElementException;

public class GuitarHero {
    public static final int KEYS = 37;

    public static void main(String[] args){
        double[] freqs = initFreqs();
        printFreqs(freqs);
        GuitarString[] guitar = createGuitar(freqs);
        String keyArrangement = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        Keyboard keyboard = new Keyboard();
        while (true) {
//            System.out.print("going");
            if (keyboard.hasNextKeyPlayed()) {
                System.out.println("KEY PRESSED");

                int stringNumber = keyArrangement.indexOf(keyboard.nextKeyPlayed());
                if (stringNumber < 0) continue;

                guitar[stringNumber].pluck();
                System.out.println(stringNumber + " plucked!");
            }

//            try {
                double sample = sumSamples(guitar);
                StdAudio.play(sample);
                ticAllStrings(guitar);
//            }
//            catch (NoSuchElementException){
//                System.out.println("NOSUCHELEMENT");
//            }
        }
    }

    private static void printFreqs(double[] freqs) {
        for (double i: freqs) {
            System.out.println(i);
        }
    }

    //  Linear from 110 to 880 in 37 steps
    public static double[] initFreqs(){
        double[] freqs = new double[KEYS];
        for (int i = 0; i < KEYS; i++){
            freqs[i] = 440 * Math.pow(1.05956, i+24);
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

    public static double sumSamples(GuitarString[] guitar){
        double sum = 0;
        for (int i = 0; i < guitar.length; i++){
            sum += guitar[i].sample();
        }
        return sum;
    }

    public static void ticAllStrings(GuitarString[] guitar){
        for (int i = 0; i < guitar.length; i++){
            guitar[i].tic();
        }
    }
}
