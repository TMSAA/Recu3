package Recu;

import java.util.Scanner;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

public class InsertarFabricantes {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		
		Session session = sesion.openSession();
		
		Transaction tx = session.beginTransaction();
		
		System.out.println("Insertar nuevo fabricante:");
		Fabricantes fab = new Fabricantes();
		System.out.println("Codigo de fabricante: ");
		fab.setCodFab(sc.next());
		System.out.println("Nombre: ");
		fab.setNombre(sc.next());
		System.out.println("Pais: ");
		fab.setPais(sc.next());

		try {
			session.persist(fab);
			tx.commit();
		} catch (PersistenceException e) {
			Throwable t = e.getCause();
			while((t != null) && !(t instanceof ConstraintViolationException)){
			t = t.getCause();
			}
			if (t instanceof ConstraintViolationException) {
				System.out.println("El fabricante ya existe");
				tx.rollback();
			}
		}
		sc.close();
		session.close();
		System.exit(0);
	}
}
