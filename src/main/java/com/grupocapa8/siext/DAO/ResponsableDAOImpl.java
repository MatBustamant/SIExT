
package com.grupocapa8.siext.DAO;

import com.grupocapa8.siext.ConexionBD.BasedeDatos;
import static com.grupocapa8.siext.ConexionBD.BasedeDatos.getConnection;
import com.grupocapa8.siext.DTO.ResponsableDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oveja
 */
public class ResponsableDAOImpl implements DAOGenerica<ResponsableDTO, Integer>{

    @Override
    public ResponsableDTO buscar(Integer id) {
        ResponsableDTO resposable = null;
        String sql = "SELECT * FROM Responsable WHERE Legajo = ?";
        
        try (Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    resposable = new ResponsableDTO();
                    resposable.setLegajo(rs.getInt("Legajo"));
                    resposable.setNombre_apellido("Nombre_Apellido");
                    resposable.setEliminado(rs.getInt("Eliminado") != 0);
                }
            } 
        } catch (SQLException ex) {
            System.getLogger(ResponsableDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
            return resposable;
    
    }

    @Override
    public List<ResponsableDTO> buscarTodos() {
        List<ResponsableDTO> lista_responsables = new ArrayList<>();

        String sql = "SELECT * FROM Categoria";
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
        
            try(ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ResponsableDTO responsable = new ResponsableDTO();
                responsable.setLegajo(rs.getInt("Legajo"));
                responsable.setNombre_apellido("Nombre_Apellido");
                responsable.setEliminado(rs.getInt("Eliminado") != 0);

                lista_responsables.add(responsable);
            }
            }
        } catch (SQLException ex) { 
            System.getLogger(ResponsableDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return lista_responsables;
    }

    @Override
    public int insertar(ResponsableDTO responsable) {
        String sql = "INSERT INTO Responsable(Legajo, Nombre_Apellido) VALUES (?, ?)";
        int resultado = 0;
        
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, responsable.getLegajo());
            ps.setString(2, responsable.getNombre_apellido());

            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(ResponsableDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return resultado;
    }

    @Override
    public int actualizar(ResponsableDTO responsable) {
        String sql = "UPDATE Responsable SET Nombre_Apellido = ? WHERE Legajo = ? AND Eliminado = ?";
        int resultado = 0;

        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, responsable.getNombre_apellido());
            ps.setInt(2, responsable.getLegajo());
            ps.setInt(3, 0);

            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(ResponsableDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return resultado;
    }

    @Override
    public int eliminar(Integer legajo) {
        String sql = "UPDATE Responsable SET Eliminado = ? WHERE Legajo = ? AND Eliminado = ?";
        int resultado = 0;
        try (Connection con = BasedeDatos.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, 1);
            ps.setInt(2, legajo);
            ps.setInt(3, 0);
            
            resultado = ps.executeUpdate();
        } catch (SQLException ex) { 
            System.getLogger(ResponsableDAOImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return resultado;
    }
    
}
