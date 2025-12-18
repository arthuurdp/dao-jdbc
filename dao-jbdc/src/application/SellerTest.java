package application;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class SellerTest {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST: seller findById() ===");
        Seller seller = sellerDao.findById(19);
        System.out.println(seller);

        System.out.println();

        System.out.println("=== TEST: seller findByDepartment() ===");
        Department department = new Department(2, null);

        List<Seller> sellers = sellerDao.findByDepartment(department);
        sellers.forEach(System.out::println);

        System.out.println();

        System.out.println("=== TEST: seller findAll() ===");
        sellers = sellerDao.findAll();
        sellers.forEach(System.out::println);

        System.out.println();

        System.out.println("=== TEST: seller insert() ===");
        Seller s = new Seller(null, "Arthur", "arthur@teste", new Date(), 4000.00, department);
        sellerDao.insert(s);
        System.out.println("Seller inserted! new seller id: " + s.getId());

        System.out.println();

        System.out.println("=== TEST: seller update() ===");
        seller = sellerDao.findById(29);
        seller.setName("updated name");
        sellerDao.update(seller);

        System.out.println(seller);
        System.out.println("Done!");

        System.out.println();

        System.out.println("=== TEST: seller delete() ===");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        Seller sellerToDelete = sellerDao.findById(id);

        System.out.println(sellerToDelete);

        if (confirmation(sc)) {
            sellerDao.delete(sellerToDelete);
            System.out.println("Seller deleted!");
        } else {
            System.out.println("Operation canceled");
        }

        System.out.println();

        System.out.println("=== TEST: seller deleteById() ===");
        System.out.print("Enter id for deleteById test: ");
        int idToDelete = sc.nextInt();

        System.out.println(sellerDao.findById(idToDelete));

        if (confirmation(sc)) {
            sellerDao.deleteById(idToDelete);
            System.out.println("Seller deleted!");
        } else {
            System.out.println("Operation canceled");
        }

        sc.close();
    }

    public static boolean confirmation(Scanner sc) {
        System.out.print("Are you sure? (y/n): ");
        char answer = sc.next().charAt(0);
        return answer == 'y' || answer == 'Y';
    }

}
