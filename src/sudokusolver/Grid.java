package sudokusolver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la grille du Sudoku
 * @author Juwans
 */
public class Grid {
    
    private int _grid[][] = new int[9][9];
    private int _regions[][] = new int[9][9];
    
    /**
     * 
     * Constructeur
     * 
     */
    public Grid(String line) throws FileNotFoundException{
        
        int index = 1;
        String[] str = line.split("");
        for(int row = 0; row<9; row++ ){
            for(int col = 0; col<9; col++){
                _grid[row][col] = Integer.parseInt(str[index]);
                index++;
            }
        }
            
        
        
        setRegionsMap();
        
    }
    
    /**
     * Méthode permettant de savoir si la grille est complète. 
     */
    public boolean isComplete(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(_grid[i][j]==0)
                    return false;
            }
        }
        return true;
         
    }
    
    /**
     * Méthode permettant de définir la map des régions.
     */
    private void setRegionsMap(){
        int num = 0; 
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                _regions[3*i][3*j] = num;   
                _regions[3*i][1+3*j] = num;   
                _regions[3*i][2+3*j] = num;
                _regions[1+3*i][3*j] = num; 
                _regions[1+3*i][1+3*j] = num; 
                _regions[1+3*i][2+3*j] = num;
                _regions[2+3*i][3*j] = num; 
                _regions[2+3*i][1+3*j] = num; 
                _regions[2+3*i][2+3*j] = num;              
                num++;
            }
        }
    }
    
    /* 
     * Méthode qui permet d'obtenir les chiffres présents 
     * dans une même région que la case. 
     */
    public List<Integer> getValueOfRegion(int row, int col){
        int num = _regions[row][col];
        List list= new ArrayList<Integer>();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if( _regions[i][j] == num){
                    list.add(_grid[i][j]);
                }
            }
        }
        return list;
    }
    
    /*
     * Méthode qui permet d'obtenir les chiffres présents
     * dans une ligne.
     */
    public List<Integer> getValueOfRow(int row){
    
        List list= new ArrayList<Integer>();
        for(int i=0; i<9; i++){
            list.add(_grid[row][i]);
        }
        return list;
    }
    
    /*
     * Méthode qui permet d'obtenir les chiffres présents
     * dans une colonne.
     */
    public List<Integer> getValueOfCol(int col){
    
        List list= new ArrayList<Integer>();
        for(int i=0; i<9; i++){
            list.add(_grid[i][col]);
        }
        return list;
    }
    
    /*
     * Méthode permettant d'obtenir la région dans laquelle 
     * se trouve une case donnée.
     */
    public List<Coordonnees> getCoordonneesRegionOf(Coordonnees c){
        int num = _regions[c.getRow()][c.getCol()];
        List list = new ArrayList<Coordonnees>();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(_regions[i][j] == num){
                    list.add(new Coordonnees(i,j));
                }
            }
        }
        return list;
    }
    
    /*
     * Méthode qui permet d'obtenir la valeur d'une case
     */
    public int getValue(int row, int col){
        return _grid[row][col];
    }
    
    /**
     * Méthode qui permet d'ajouter un élément à la grille.
     */
    public void AddElement(int element, int row, int col){
        _grid[row][col] = element;
    }
    
    /**
     * Méthode qui permet de supprimer un élément de la grille. 
     */
    public void removeElement(int row, int col){
        _grid[row][col] = 0;
    }
    
    /**
     * Méthode permettant d'afficher la grille.
     */
    public void Display(){
        draw_line();
        draw_linespace();
        for(int row = 0; row<9; row++){
            System.out.print("|");
            for(int col = 0; col<9; col++){
                if(_grid[row][col] == 0)
                    System.out.print("\t");
                else
                    System.out.print("\t"+_grid[row][col]);
                if(col == 2 || col == 5){
                     System.out.print("\t|");
                }
            }
            System.out.println("\t|");
            
            draw_linespace();
            if(row == 2 || row == 5){
                draw_line();
                draw_linespace();
            }
        }
        draw_line();
    }
    
    /**
     * Méthode qui permet de dessiner une ligne
     */
    public void draw_line(){
        System.out.println(" \t_ \t_ \t_ \t \t_ \t_ \t_ \t \t_ \t_ \t_ \t ");
    }
    
    /**
     * Méthode qui permet de dessiner l'espace sous une ligne.
     */
    public void draw_linespace(){
        System.out.println("|\t \t \t \t|\t \t \t \t|\t \t \t \t|");
    } 
    
}
