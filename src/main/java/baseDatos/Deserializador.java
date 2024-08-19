package baseDatos;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import gestorAplicacion.establecimientos.Cementerio;
import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.financiero.Factura;
import gestorAplicacion.inventario.Inventario;
import gestorAplicacion.inventario.Producto;
import gestorAplicacion.inventario.Vehiculo;
import gestorAplicacion.personas.Persona;

public class Deserializador {

    private static <T> void deserializar(ArrayList<T> lista, String nombre){
        
        File archivo = new File("");
        FileInputStream fis;
        ObjectInputStream ois;

        try {

            File path = new File(archivo.getAbsolutePath()+"/src/main/java/baseDatos/temp"+nombre+".txt");
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            if (lista!=null) {
            	lista.addAll((ArrayList<T>) ois.readObject());
            }
            
            ois.close();
            fis.close();

        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

    }

    public static void deserializarListas(){
    	
    	deserializar(Establecimiento.getEstablecimientos(), "Establecimientos");
    	deserializar(Inventario.getInventarioTotal(), "Inventario");
    	deserializar(Persona.getPersonas(), "Personas");
    	deserializar(Vehiculo.getVehiculos(), "Vehiculos");
    	deserializar(Factura.getFacturas(), "Facturas");
    	deserializar(CuentaBancaria.getCuentas(), "Cuentas");
    	deserializar(Producto.getProductos(), "Productos");
    	deserializar(Cementerio.getCementerios(), "Cementerios");
    	
    	

    }
    
}

