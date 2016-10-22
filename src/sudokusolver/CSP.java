package sudokusolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Juwans
 */
public class CSP {

    //private Map variables = new HashMap();
    private Domains _domains;
    private List<Coordonnees> _variables = new ArrayList<Coordonnees>();
    private Grid _grille;
    private TracesResolution _resolution;
    private boolean _useNakedPair;
    private String _option;
     
    
    /**
     * 
     * Constructeur
     * 
     */
    public CSP (Grid grid, TracesResolution r, boolean useNakedPair, String option){
        _grille = grid;
        _resolution = r;
        _domains = new Domains(_grille);
        _useNakedPair = useNakedPair;
        _option = option;
        for( int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Coordonnees  coordonnees = new Coordonnees(i,j);
                int value = grid.getValue(i, j);
                if(value == 0){
                    _variables.add(coordonnees);
                    _resolution.incrementeNbCaseVide();
                }
                              
            }
        }
        
        if(_option.equals("-t")){
            System.out.println(" * * * Grille de initiale * * *");
            _grille.Display();
        }
    }
    
    /**
     * Méthode permattant d'appliquer les 3 contraintes de bases d'un Sudoku 
     */
    private boolean Constraints(){
        for(Coordonnees c : _variables){
                if(!rowConstraints(c.getRow(),c.getCol()))
                    return false;
                if(!colConstraints(c.getRow(),c.getCol()))
                    return false;
                if(!regionConstraints(c.getRow(),c.getCol()))
                    return false;
        }
        
        // utilisation des contraintes de paires cachées
        if(_useNakedPair){
            _resolution.useNakedPair();
            nakedPair();
        }
        return true;
    }
    
    /**
     * Méthode permettant d'appliquer la contrainte des regions à une case données. 
     */
    private boolean regionConstraints(int row, int col){
        List<Integer> valueOfRegion = _grille.getValueOfRegion(row, col);
        Coordonnees coordonnees = new Coordonnees (row,col);
        List<Integer> list = (ArrayList<Integer>) _domains.get(coordonnees);
        for (Integer v : valueOfRegion){
            if(list.contains(v))
                    list.remove(v);
        } 
        _domains.put(coordonnees, new ArrayList<Integer>(list));
        if(list.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    
    /**
     * Méthode permettant d'appliquer la contrainte des lignes à une case données. 
     */
    private boolean rowConstraints(int row, int col){
        
        List<Integer> valueOfRow = _grille.getValueOfRow(row);
        Coordonnees coordonnees = new Coordonnees (row,col);
        List<Integer> list = (ArrayList<Integer>) _domains.get(coordonnees);
        for (Integer v : valueOfRow){
            if(list.contains(v))
                    list.remove(v);
        } 
        _domains.put(coordonnees, new ArrayList<Integer>(list));
        if(list.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    
    /**
     * Méthode permettant d'appliquer la contrainte des colonnes à une case données. 
     */
    private boolean colConstraints(int row, int col){
        
        List<Integer> valueOfCol = _grille.getValueOfCol(col);
        Coordonnees coordonnees = new Coordonnees (row,col);
        List<Integer> list = (List<Integer>) _domains.get(coordonnees);
        for (Integer v : valueOfCol){
            if(list.contains(v))
                    list.remove(v);
        }      
         _domains.put(coordonnees, new ArrayList<Integer>(list));
        if(list.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    
    
    
    
    
    /*
     * Méthode qui vérifie si un valeur est présente dans rangée.
     */
    private boolean checkRow(int row, Integer value ){
        List<Integer> valueOfRow = _grille.getValueOfRow(row);    
        return !valueOfRow.contains(value);
    }
    
    /*
     * Méthode qui vérifie si un valeur est présente dans colonne.
     */
    private boolean checkCol(int col, Integer value){
        List<Integer> valueOfCol = _grille.getValueOfCol(col);    
        return !valueOfCol.contains(value);
    }
    
    /*
     * Méthode qui vérifie si un valeur est présente dans un sous espace 3x3.
     */
    private boolean checkRegion(int row, int col, Integer value){
        List<Integer> valueOfRegion = _grille.getValueOfRegion(row, col);  
        return !valueOfRegion.contains(value);
    }
    
    /*
     * Méthode qui vérifie qu'aucune des contraintes n'a été violées
     */
    private boolean NoConstraintsViolated(Coordonnees c, Integer i){
        return checkRow(c.getRow(), i) && checkCol(c.getCol(), i) && checkRegion(c.getRow(),c.getCol(), i);
    }
   
    
    /*
     * Méthode permettant de réaliser un assignement.
     */
    private void doAssignement(Coordonnees mrv, int i){
        _grille.AddElement(i, mrv.getRow(), mrv.getCol());
        _domains.remove(mrv);
        _variables.remove(mrv);
        if(_option.equals("-t")){
            System.out.println("");
            System.out.println(" * * * ASSIGNATION case ("+mrv.getRow()+","+mrv.getCol()+") * * *");
            _grille.Display();
        }
    }
    
    /*
     * Méthode permettant d'annuler une mauvaise assignation.
     */
    private void goBack(Coordonnees mrv){
        _grille.removeElement(mrv.getRow(), mrv.getCol());
        _domains = new Domains(_grille);
        _variables.add(mrv);
        Constraints();
        if(_option.equals("-t")){
            System.out.println("");
            System.out.println(" * * * BACKTRACK case ("+mrv.getRow()+","+mrv.getCol()+") * * *");
            _grille.Display();
        }
    }
    
    
    /*
     * Méthode permettant de choisir la valeur qui restreindra le plus de domaine.
     */
    private Integer getBestValueInDomain(List<Integer> d, Coordonnees mrv){
        int max = 0;
        int result = 0;
        
        for(Integer value : d){
            int count = 0;

            for(Coordonnees c:_variables){
                if(c.getCol() == mrv.getCol()){
                    if(_domains.get(c).contains(value))
                        count++;
                }
                else{
                    if(c.getRow() == mrv.getRow()){
                        if(_domains.get(c).contains(value))
                            count++;
                    }
                    else{
                        List<Coordonnees> l = _grille.getCoordonneesRegionOf(mrv);
                        if(l.contains(c)){
                                if(_domains.get(c).contains(value)){
                                    count++;
                                }
                        }
                        
                    }
                }
            }
            if(count>max){
                max = count;
                result = value;
            }
            
        }
        
        return result;    
    }
    
    /**
     * Méthode permettant d'appliquer la contraintes de paires cachées
     * sur une ligne.
     */
    private boolean nakedPairRow(){
        boolean result = false;
        Map map;
        List<Coordonnees> listPair;
        for(int row=0; row<9; row++){
            
            listPair = new ArrayList<Coordonnees>();
            map = new HashMap<Coordonnees, List<Integer>>();
            
            // récuperation des domaines de la ligne
            for(int col=0; col<9; col++){
                Coordonnees c = new Coordonnees(row,col);
                if(_variables.contains(c)){
                    map.put(c, _domains.get(c));
                    if(_domains.get(c).size() == 2){
                        listPair.add(c);
                    }
                }
                
            }
            
            if(listPair.size()>1){
                result = checkAndRemovePair(listPair,map);
            }
            
        }
    
        return result;
    
    }
    
    /**
     * Méthode permettant d'appliquer la contraintes de paires cachées
     * sur une colonne.
     */
    private boolean nakedPairCol(){
        boolean result = false;
        Map map;
        List<Coordonnees> listPair;
        for(int col=0; col<9; col++){
            
            listPair = new ArrayList<Coordonnees>();
            map = new HashMap<Coordonnees, List<Integer>>();
            
            // récuperation des domaines de la colonne
            for(int row=0; row<9; row++){
                Coordonnees c = new Coordonnees(row,col);
                if(_variables.contains(c)){
                    map.put(c, _domains.get(c));
                    if(_domains.get(c).size() == 2){
                        listPair.add(c);
                    }
                }
                
            }
            
            if(listPair.size()>1){
                result = checkAndRemovePair(listPair,map);
            }
            
        }
    
        return result;
    }
    
   /**
     * Méthode permettant d'appliquer la contraintes de paires cachées
     * sur une région.
     */ 
   private boolean nakedPairRegion(){
       boolean result = false;
        Map map;
        List<Coordonnees> listPair;
        for(int col=0; col<9; col = col+3){
            for(int row=0; row<9; row = row+3){
                
                listPair = new ArrayList<Coordonnees>();
                map = new HashMap<Coordonnees, List<Integer>>();
                Coordonnees c = new Coordonnees(row,col);
                
                //recuperation des domaines de la region
                List<Coordonnees> list_coor = _grille.getCoordonneesRegionOf(c);
                for(Coordonnees co:list_coor){
                    if(_variables.contains(co)){
                        map.put(co, _domains.get(co));
                        if(_domains.get(co).size() == 2){
                            listPair.add(co);
                        }
                    }
                }
                
                if(listPair.size()>1){
                    result = checkAndRemovePair(listPair,map);
                }
            }
            
            
            
        }
        
        return result;
   
   } 
   
   /**
    * Méthode permettant de trouver les paires et des les supprimer des autres 
    * domaines.
    */
   private boolean checkAndRemovePair(List<Coordonnees> listPair, Map map){
        List<Coordonnees> temp = new ArrayList<Coordonnees>(listPair);
        boolean result = false;
        int i = 0;
        Coordonnees c_compare = temp.get(0);
        temp.remove(c_compare);
        List<Integer> domainPair = new ArrayList<Integer>();
        boolean found = false;
        while(!found && temp.size()>0){
            if(_domains.get(c_compare).containsAll(_domains.get(temp.get(i)))){
                found = true;
                map.remove(c_compare);
                map.remove(temp.get(i));
                domainPair = new ArrayList<Integer>(_domains.get(c_compare));
            }
            else{
                i++;
                if(i == temp.size()){
                    c_compare = temp.get(0);
                    temp.remove(temp.get(0));
                    i = 0;
                }
            }
        }
                
                
        if(found){
            for(Object coor : map.keySet()){
                result = _domains.get((Coordonnees)coor).removeAll(domainPair);
            }
        }
        return result;
   }
   
   /**
    * Méthode permettant d'appliquer les contraintes de paires cachées.  
    */
   private boolean nakedPair(){ 
       return nakedPairRow() || nakedPairCol() || nakedPairRegion(); 
   }
    
   /**
    * Méthode permettant de résoudre le Sudoku.
    */
    public boolean Solve(){
        // Vérifie si la vie est complète.
        if(_grille.isComplete()){
            return true;
        }
        
        // AC-3 Applique contrainte de base.
        if(Constraints()){
            
            //MRV
            Coordonnees mrv = _domains.getMRV();
            List<Integer> domain = new ArrayList<Integer>(_domains.get(mrv));
            Integer val;
            
            while(!domain.isEmpty()){
                if(domain.size()>1){
                    _resolution.incrementChoixMultiple();
                    val = getBestValueInDomain(domain, mrv); 
                }
                else{
                    _resolution.incrementChoixUnique();
                    val = domain.get(0);
                }
                
                //Foward Checking
                if(NoConstraintsViolated(mrv,val)){
                    doAssignement(mrv,val);
                    boolean result = Solve();
                    if(result)
                        return true;
                    else{
                        _resolution.incrementeNbBacktrack();
                        goBack(mrv);
                    }
                }
                
                domain.remove(val);
            }
        }
        
        return false;
    }
    
    
}
