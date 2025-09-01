package PaqueteDeEntidades;

/**
 *
 * @author geroj
 */
public class Insumo {
    private int Stock;
    private int MinReposicion;
    private String NombreCatInsumo;

    public Insumo(int Stock, int MinReposicion, String NombreCatInsumo) {
        this.Stock = Stock;
        this.MinReposicion = MinReposicion;
        this.NombreCatInsumo = NombreCatInsumo;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public int getMinReposicion() {
        return MinReposicion;
    }

    public void setMinReposicion(int MinReposicion) {
        this.MinReposicion = MinReposicion;
    }

    public String getNombreCatInsumo() {
        return NombreCatInsumo;
    }

    public void setNombreCatInsumo(String NombreCatInsumo) {
        this.NombreCatInsumo = NombreCatInsumo;
    }
    
}
