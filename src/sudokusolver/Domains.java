package sudokusolver;

import java.util.*;

/**
 *
 * Classe représentant l'ensemble des domaines
 * des variables du problème.
 * 
 * @author Juwans
 * 
 */
public class Domains {
    
    private Map _domains;
    
    /**
     * 
     * Constructeur
     * 
     */
    public Domains(Grid g){
        
        _domains = new HashMap<Coordonnees, List<Integer>>();
        
        /* Initialisation du domaine fini initiale */
        List<Integer> default_domain = new ArrayList<Integer>();
        for (int n = 1; n<=9; n++){
            default_domain.add(n);
        }
        
        for( int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Coordonnees  coordonnees = new Coordonnees(i,j);
                int value = g.getValue(i, j);
                if(value == 0){
                    _domains.put(coordonnees, new ArrayList<Integer>(default_domain));
                }
                              
            }
        }
        
        
         
    }
    
    /**
     * Méthode permettant d'obtenir le domaine d'une variable représentée
     * par les coordonnées de sa case dans la grille.
     * 
     * @param c Coordonnées de la case
     * @return List contenant les valeurs du domaine de la case.
     */
    public List<Integer> get(Coordonnees c){
        return (List<Integer>) _domains.get(c);
        
    }
    
    
    /**
     * Méthode permettant de rajouter un domaine associer à sa variable
     * dans la Map de l'ensemble des domaines.
     * 
     * @param c Coordonnées de la case représentant la variable.
     * @param l Liste représentant le domaine de la variable.
     */
    public void put(Coordonnees c, List<Integer> l){
        _domains.put(c, l);
    }
    
    /**
     * Méthode permettant de supprimer une variable et son domaine de 
     * l'ensemble des domaines lorsque la variable est assignée.
     * 
     * @param c Coordonnée de la case représentant la variable. 
     */
    public void remove(Coordonnees c){
        _domains.remove(c);        
    };
    
    
    /**
     * Méthode permettant d'obtenir la variable ayant le domaine 
     * le plus restreint (MRV : Minimum Remaining Values ).
     * 
     * @return Les coordonnées de la case représentant la variable la plus restreinte.
     */
    public Coordonnees getMRV(){
        Coordonnees res = null;
        int bestSize = 10;
        
        for (Iterator it = _domains.keySet().iterator(); it.hasNext();) {
            Coordonnees cle = (Coordonnees) it.next();
            List<Integer> valeur = (List<Integer>) _domains.get(cle);
            if (valeur.size()<bestSize){
                res = cle;
                bestSize = valeur.size();
            }
        }
        return res;
    }
    
    
    
    /**
     * Méthode permettant d'afficher les domaines.
     */
    public void displaydomains(){
        for(Object c :  _domains.keySet()){
            Coordonnees coor = (Coordonnees) c;
            System.out.println(coor.toString() + _domains.get(coor));
        }
    }
}
