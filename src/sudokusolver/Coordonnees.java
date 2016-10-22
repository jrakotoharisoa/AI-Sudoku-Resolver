package sudokusolver;

/**
 * Classe représentant une pair de coordonnées
 * @author Juwans
 */
public class Coordonnees {

    /* Attributs de classe */
    private final int row;
    private final int col;
    
    /**
     * 
     * Constructeur
     * 
     */
    public Coordonnees(int r,int c)
    {
        row = r;
        col = c;
    }

    /**
     * Accesseur sur l'attribut row. 
     */
    public int getRow(){
        return row;
    }
    
    /**
     * Accesseur sur l'attribut col. 
     */
    public int getCol(){
        return col;
    }
    
    
    
    /**
     * Redéfinition de la méthode equals. 
     */
    @Override
    public boolean equals(Object obj)
    {
         if (obj instanceof Coordonnees)
         {
              Coordonnees c = (Coordonnees) obj;
              return c.row==row && c.col==col;
         }
         else return false;
    }

    /**
     * Redéfinition du HashCode. 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.row;
        hash = 79 * hash + this.col;
        return hash;
    }

    /**
     * Redéfintion de la méthode toString. 
     */
    @Override
    public String toString(){
        return "row: "+this.row + " col: "+ this.col;
    }
}
