package PaqueteDeEntidades;

/**
 *
 * @author geroj
 */
public class CategoriaBien {
    private int MinReposicion;
    private int Stock;

    public CategoriaBien(int MinReposicion, int Stock) {
        this.MinReposicion = MinReposicion;
        this.Stock = Stock;
    }

    public int getMinReposicion() {
        return MinReposicion;
    }

    public void setMinReposicion(int MinReposicion) {
        this.MinReposicion = MinReposicion;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }
    
            
}
