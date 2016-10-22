package sudokusolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe Main permettant de resoudre un sudoku
 * et obtenir la difficulté de celui ci.
 * @author Johann Rakotoharisoa
 */
public class SudokuSolver {

    
    public static void main(String[] args) {
        if(args.length > 0){
            try{
                String option; 
                TracesResolution res;
                Grid g;
                String line;
                CSP csp;
                File file = new File(args[0]);
                Scanner sc = new Scanner(file);
                int row = 0;
                boolean level_found;
                boolean use_naked_pair;
                if(args.length>1){
                    option = args[1];
                }
                else{
                    option = "";
                }
                while(sc.hasNextLine()){
                    line = sc.nextLine();
                    use_naked_pair = false;
                    level_found = false;
                    do{
                        g = new Grid(line);
                        res = new TracesResolution();
                        csp = new CSP(g,res,use_naked_pair,option);
                        csp.Solve();
                        if(res.getNbBacktrack()>0){
                            if(use_naked_pair){
                                level_found = true;
                            }
                            else{
                                use_naked_pair = true;
                            }
                        }
                        else{
                            level_found = true;
                        }
                    }while(!level_found);
                    
                    System.out.println("");
                    System.out.println(" * * * SUDOKU COMPLETE * * *");
                    g.Display();
                    res.Display();
                    res.levelDifficulty();
                }
                
                
            }
            catch(FileNotFoundException e){
                System.out.println("Veuillez entrer un nom de fichier valide !");
                System.exit(0);
            }
        }
        else{
            System.out.println("Veuillez entrer un nom de fichier !");
            System.exit(1);
        }
    }
}
