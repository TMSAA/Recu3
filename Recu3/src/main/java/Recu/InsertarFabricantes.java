package Recu;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

public class InsertarFabricantes {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		
		Session session = sesion.openSession();
		
		Transaction tx = session.beginTransaction();
		
		System.out.println("Insertar nuevo fabricante:");
		Fabricantes fab = new Fabricantes();
		System.out.println("Codigo de fabricante: ");
		fab.setCodFab(sc.nextLine());
		System.out.println("Nombre: ");
		fab.setNombre(sc.nextLine());
		System.out.println("Pais: ");
		fab.setPais(sc.nextLine());

		try {
			session.persist(fab);
			tx.commit();
			System.out.println("Fabricante insertado correctamente");
		} 
		catch (DataException e) {} 
		catch (ConstraintViolationException e) {}
		sc.close();
		session.close();
		System.exit(0);
	}
}
