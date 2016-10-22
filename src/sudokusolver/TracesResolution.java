package sudokusolver;

/**
 * Classe représentant les traces de résolution.
 * @author Juwans
 */
public class TracesResolution {
    
    /* Attributs de classe*/
    private int _nbBacktrack;
    private int _nbCaseVide;
    private int _nb_choix_unique;
    private int _nb_choix_multiple;
    private boolean _use_naked_pair;
    
    /**
     * 
     * Constructeur
     * 
     */
    public TracesResolution(){
        _nbBacktrack = 0;
        _nbCaseVide = 0;
        _nb_choix_unique = 0;
        _nb_choix_multiple = 0;
        _use_naked_pair = false;
    }
    
    /**
     * Méthode permettant d'incrémenter le nombre backtrack effectué
     */
    public void incrementeNbBacktrack(){
        _nbBacktrack++;
        
    }
    
    /**
     * Méthode permettant de signaler qu'on a utilisé la contrainte de pair unique 
     */
    public void useNakedPair(){
        _use_naked_pair = true;
    };
    
    
    /**
     * Méthode permettant d'incrémenter le nombre de case vide.
     */
    public void incrementeNbCaseVide(){
        _nbCaseVide++;
    }
    
    
    /**
     * Méthode permettant d'incrémenter le nombre de choix unique effectué
     */
    public void incrementChoixUnique(){
        _nb_choix_unique++;
    }
    
    /**
     * Méthode permettant d'incrémenter le nombre de choix multiple effectué.
     */
    public void incrementChoixMultiple(){
        _nb_choix_multiple++;
    }
    
    /**
     * Méthode permettant d'obtenir le nombre de backtrack.
     */
    public int getNbBacktrack(){
        return _nbBacktrack;
    }
    
    /**
     * Méthode permettant d'afficher le résultat des traces de résolution.
     */
    public void Display(){
        
        System.out.println("------------------------------------------");
        System.out.println( "Backtrack: "+ _nbBacktrack);
        System.out.println( "case connues: "+ (81-_nbCaseVide) );
        System.out.println( "Choix uniques: " + _nb_choix_unique);
        System.out.println( "Choix multiples: "+ _nb_choix_multiple);
        if(_use_naked_pair){
            System.out.println("Utilisation Méthode Paires cachées : Oui" );
        }else{
            
            System.out.println("Utilisation Méthode Paires cachées : Non" );
        }
        
                          
    }
    
    /**
     * Méthode permettant de décider du niveau de difficulté du sudoku.
     */
    public void levelDifficulty(){
    
        if(_nbBacktrack == 0){
            if(_use_naked_pair || _nb_choix_multiple >0){
                System.out.println("===> Difficulté : Moyen");
            }
            else{
                System.out.println("===> Difficulté : Facile");
            }
        }
        else{
                System.out.println("===> Difficulté : Difficile");
            }
    }
}
