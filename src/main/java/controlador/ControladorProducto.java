
package controlador;

import java.util.ArrayList;
import java.util.Optional;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import vista.Marco;


public class ControladorProducto {
    ArrayList<Producto> listaProductos;
    @Autowired
    RepositorioProducto rp;
    Marco m;

    public ControladorProducto(RepositorioProducto rp, Marco m) {
        this.rp = rp;
        this.m = m;
    }
    
    
    public void setlistaProductos(ArrayList<Producto> listaProductos){
        this.listaProductos =  listaProductos;
    };
            
    public Producto agregar(Producto p){
        rp.save(p);
        return p;
    };
    
    public Producto actualizar(Producto p){
        rp.save(p);
        return p;
    };
    
    public boolean eliminar(Integer id){
        try{
            rp.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
        
    };
    
    public ArrayList<Producto> obtenerProductos(){
        return (ArrayList<Producto>) rp.findAll();
    };
    
    public Optional<Producto> obtenerProductos(Integer id){
        return rp.findById(id);
    };
    
    public double inventarioTotal(){
        double suma = 0.0;
        for (Producto p: listaProductos){
            suma += p.getInventario()*p.getPrecio();
            
        }
        return suma;
    };
    
    public String precioMenor(){
        String nombre = listaProductos.get(0).getNombre();
        double precio = listaProductos.get(0).getPrecio();
        for (Producto p: listaProductos){
            if (p.getPrecio() < precio){
                nombre = p.getNombre();
                precio = p.getPrecio();
            }
        }
        return nombre;
    };
    
    public String precioMayor(){
        String nombre = listaProductos.get(0).getNombre();
        double precio = listaProductos.get(0).getPrecio();
        for (Producto p: listaProductos){
            if (p.getPrecio() > precio){
                nombre = p.getNombre();
                precio = p.getPrecio();
            }
        }
        return nombre;
    };
    
    public double promedio(){
        double suma = 0.0;
        for (Producto p: listaProductos){
            suma += p.getPrecio();
        }
        return suma/listaProductos.size();
    };
    
    public void eventoAgregar(){
        if (!m.getTextNombre().equals("") && !m.getTextPrecio().equals("") && !m.getTextInventario().equals("")){
            Producto new_p = new Producto(m.getTextNombre(), Double.parseDouble(m.getTextPrecio()), Integer.parseInt(m.getTextInventario()));
            listaProductos.add(new_p);
            DefaultTableModel modelo = (DefaultTableModel) m.getTablainventario().getModel();
            modelo.insertRow(listaProductos.size() - 1, new Object[] {new_p.getNombre(), new_p.getPrecio(), new_p.getInventario()});
            m.getCp().agregar(new_p);
        }
    };
    
    public void inicializarTabla(){
        DefaultTableModel modelo = (DefaultTableModel) m.getTablainventario().getModel();
        Integer cont = 0;
        for (Producto p: listaProductos){
            modelo.insertRow(cont, new Object[] {p.getNombre(), p.getPrecio(), p.getInventario()});
            cont += 1;
        }
    };
}
