package Recu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.query.Query;

public class Pregunta3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Fabricantes.class);
        configuration.addAnnotatedClass(Programas.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
		System.out.println("Introduce la ruta al archivo:");
		String ruta = sc.next();
		FileReader fr;
		String caracteres = "";
		String version = "";
		String operador = "";
		try {
			fr = new FileReader(ruta);
			BufferedReader br = new BufferedReader(fr);
			
			String linea;
			if ((linea = br.readLine()) != null) {
                String[] palabras = linea.split("\\s+");
                if (palabras.length >= 3) {
                    caracteres = palabras[0];
                    version = palabras[1];
                    operador = palabras[2];
                } else {
                    System.out.println("El archivo no contiene al menos tres palabras");
                }
            }
			 String queryStr = "FROM Programas p WHERE p.nombre LIKE :cadena AND p.ver LIKE :version";
	            if ("OR".equalsIgnoreCase(operador)) {
	                queryStr = "FROM Programas p WHERE p.nombre LIKE :cadena OR p.ver LIKE :version";
	            }

	            Query<Programas> query = session.createQuery(queryStr, Programas.class);
	            query.setParameter("cadena", "%" + caracteres + "%");
	            query.setParameter("version", "%" + version + "%");

	            List<Programas> programas = query.list();

	            if (programas.isEmpty()) {
	                System.out.println("No se encontraron programas que coincidan con los criterios de búsqueda.");
	            } else {
	                System.out.println("Los programas que coinciden con los criterios de búsqueda son:");
	                for (Programas programa : programas) {
	                	System.out.println("-------------------------------------------");
	                    System.out.println(programa.getNombre() + " (Versión: " + programa.getVer() + ")");
	                }
	            }
			
            br.close();
            fr.close();
            sc.close();
            session.close();
			
		} catch (IOException | DataException | ConstraintViolationException e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
}
