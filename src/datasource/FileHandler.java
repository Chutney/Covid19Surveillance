package datasource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;


public class FileHandler {

    //Scanner attribut for at kunne l�se fra CSV filen.
    private Scanner fileReader;
    //Arraylist med Covid19Data. Hver instans af Covid19Data er 1 r�kke i vores CSV fil.
    private ArrayList<Covid19Data> dataList;

    public FileHandler() {
        //Vi instansiere datalist og FileReader. FileReader s�ttes lig med null da Java kr�ver den instansieres
        //et scope som altid kan blive k�rt, som vores fil ikke n�dvendigvis altid vil g�re grundet vores try catch.
        dataList = new ArrayList<>();
        fileReader = null;
        try {
            //Vi inds�tter vores fil i vores scanner. Filen ligger i resources/coviddata.csv. File.seperator'
            //Er blot / der viser filstien. Denne bruges fordi File.separator �ndre sig ud fra computerens OS.
            //Dette er n�dvendigt fordi Windows bruger '/' og MacOS bruger '\'.
            //StandardCharset s�ttes, da det CharSet Java benytter pr. default er UTF-8, som ikke underst�tter �,�,�.
            //Dog underst�tter ISO_8859_1 �,�,�, og derfor s�ttes denne i scanneren.
            fileReader = new Scanner(new File("resources"+File.separator+"coviddata.csv"), StandardCharsets.ISO_8859_1);
        } catch(IOException ioE) {
            //Throw RuntimeException s� vi stadig f�r vores Exception vist ved fejl, men den fortsat gider at compile.
            //Vi konvertere blot en checked exception til en uncheckedexception.
            throw new RuntimeException(ioE);
        }
    }

    public ArrayList<Covid19Data> readFile() {
        //Whileloop. K�rer s� l�nge der er en linje i vores CSV der kan l�ses.
        //Dette betyder whileloop stopper, n�r der ikke er flere linjer at l�se i CSV filen.
        while(fileReader.hasNextLine()) {
            //Vi henter vores linje.
            String line = fileReader.nextLine();
            //Vi separere hver v�rdi i linjen ved hvert ';'.
            String[] attributes = line.split(";");
            //Vi tilf�jer attributterne fra CSV filen til hver attribut i vores Covid19Data klasse.
            //Derefter tilf�jes instans til en liste, s� vi har alle data i en liste, med hver linje
            //som dens egen instans af Covid19Data.
            //Integer.parseInt bruges, fordi data fra CSV fil kommer ind som String, ligegyldigt om det er et tal eller ej.
            //Vi konvertere derfor fra String til int, som vores Covid19Data instans benytter.
            dataList.add(new Covid19Data(attributes[0],attributes[1],
                    Integer.parseInt(attributes[2]),Integer.parseInt(attributes[3]),
                    Integer.parseInt(attributes[4]), Integer.parseInt(attributes[5]),attributes[6]));
        }

        //Vi returnere listen.
        return dataList;
    }


}
