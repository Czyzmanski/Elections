package elections;

import elections.data.DataLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream = args.length == 0 ? System.in : new FileInputStream(args[0]);
        } catch (FileNotFoundException e) {
            System.err.println("The file does not exist, is a directory or cannot be opened.");
            e.printStackTrace();
            System.exit(-1);
        }

        try (DataLoader dataLoader = new DataLoader(inputStream)) {
            dataLoader.loadData();

        } catch (IOException e) {
            System.err.println("An IO error has occurred when loading a data.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
