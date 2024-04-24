package Recu;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

public class InsertarFabricantes2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Session session = null;
		Transaction tx = null;

		try {
			Configuration configuration = new Configuration().configure();
			configuration.addAnnotatedClass(Fabricantes.class);
			configuration.addAnnotatedClass(Programas.class);

			SessionFactory sessionFactory = configuration.buildSessionFactory();
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			System.out.println("Insertando un nuevo fabricante");
			Fabricantes fab = new Fabricantes();
			System.out.println("Ingrese codigo de fabricante:");
			fab.setCodFab(sc.next());
			System.out.println("Ingrese el nombre: ");
			fab.setNombre(sc.next());
			System.out.println("Ingrese el pais de procedencia: ");
			fab.setPais(sc.next());

			System.out.println("Quieres insertar algun programa asociado a ese fabricante?(Y/N)");
			if ("Y".equalsIgnoreCase(sc.next())) {
				boolean continuar = false;
				do {
					Set<Programas> programas = new HashSet<>();
					Programas pro = new Programas();
					System.out.println("Ingrese codigo de programa: ");
					pro.setCodPro(sc.next());
					System.out.println("Ingrese el nombre del programa");
					pro.setNombre(sc.next());
					System.out.println("Ingrese la version del programa");
					pro.setVer(sc.next());
					pro.getFabricanteses().add(fab);
					session.persist(pro);
					programas.add(pro);
					fab.setProgramases(programas);
					System.out.println("Programa insertado");
					System.out.println("Quieres irgresar otro programa?(Y/N)");
					if ("Y".equalsIgnoreCase(sc.next())) {
						continuar = true;
					}
				} while (continuar);
			}
			session.persist(fab);
			tx.commit();
			System.out.println("Fabricante insertado correctamente");
		} catch (DataException | ConstraintViolationException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
			sc.close();
		}
	}
}
