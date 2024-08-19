package baseDatos;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import gestorAplicacion.establecimientos.Establecimiento;
import gestorAplicacion.financiero.CuentaBancaria;
import gestorAplicacion.financiero.Factura;
import gestorAplicacion.inventario.Inventario;
import gestorAplicacion.inventario.Producto;
import gestorAplicacion.inventario.Vehiculo;
import gestorAplicacion.personas.Persona;


public class Serializador {

    private static void serializar(ArrayList<? extends Object> lista, String nombre) {
    
        File archivo = new File("");

        try{
            File path = new File(archivo.getAbsolutePath()+"/src/main/java/baseDatos/temp"+nombre+".txt"); 
          
            
            FileOutputStream f = new FileOutputStream(path);
            ObjectOutputStream o = new ObjectOutputStream(f);
            
            o.writeObject(lista);

            o.close();
            f.close();
        }
        catch(FileNotFoundException e){
            System.out.println("No se encuentra el archivo");
        }
        catch(IOException e){
            System.out.println("Error Flujo de inicializacion");
        }
        // catch(ClassNotFoundException e){
        //     e.printStackTrace();
        // }
    }

    public static void serializarListas(){
        // Aqui van las clases que vamos a serealizar: ejem:
        Serializador.serializar(Establecimiento.getEstablecimientos(), "Establecimientos");
        Serializador.serializar(Inventario.getInventarioTotal(), "Inventario");
        Serializador.serializar(Persona.getPersonas(), "Personas");
        Serializador.serializar(Vehiculo.getVehiculos(), "Vehiculos");
        Serializador.serializar(Factura.getFacturas(), "Facturas");
        Serializador.serializar(CuentaBancaria.getCuentas(), "Cuentas");
        Serializador.serializar(Producto.getProductos(), "Productos");
  

        // y asi ...
    }
	
	

}
