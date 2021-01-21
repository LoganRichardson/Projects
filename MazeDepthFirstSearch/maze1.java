
public class maze1 {
//C:\Users\Logan\Documents\NetBeansProjects\maze\src\mazefile.txt
    public static void main(String[] args) {
        
        
        TextFileClass textFile = new TextFileClass();
        textFile.getFileName("Input the name of maze file: ");
        
        if (textFile.fileName.length()>0) {
        int linecount = textFile.getFileContents();
        //    System.out.println(linecount);
        //KeyboardInputClass keyboardInput = new KeyboardInputClass();
        int rows = Integer.parseInt(textFile.text[0]);
        int columns = Integer.parseInt(textFile.text[1]);
        //   System.out.println(rows + " ygfdx" + columns);
        int rowsLocation = Integer.parseInt(textFile.text[2]);
        int columnsLocation = Integer.parseInt(textFile.text[3]);
        //int rows = textFile.text[0];
        
        String maze[][] = new String[rows-1][columns-1];
        
        //StringBuilder r = new StringBuilder(textFile.text[11]);
        //r.setCharAt(5, 'X');
        
        //Figure out how to access a certain character in a string
        
        }
        else System.out.println("YOU DID NOT INPUT ANYTHING!!");   
    }           
}       
        

        
    


