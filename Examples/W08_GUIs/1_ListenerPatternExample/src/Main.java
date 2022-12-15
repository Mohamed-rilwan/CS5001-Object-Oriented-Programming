public class Main {

    /** Creates a frog and a couple of objects that listen to it. */
    public static void main(String[] args) {
        // Create a frog (the model)
        Frog frog = new Frog("green", 8);

        // Create two different views (both of them implement Frog.Listener)
        FrogPrinter printer = new FrogPrinter(frog);
        FrogGUI gui = new FrogGUI(frog);

        // Change the model 3 times
        frog.setColour("red");
        frog.setLength(11);
        frog.setColour("green");

        // Both views should display the results
    }
    
}
