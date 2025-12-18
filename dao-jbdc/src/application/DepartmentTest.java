package application;

import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;

import java.util.List;
import java.util.Scanner;

public class DepartmentTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== test: findById() ===");
        Department dep = departmentDao.findById(1);
        System.out.println(dep);

        System.out.println();

        System.out.println("=== test: findAll() ===");
        List<Department> deps = departmentDao.findAll();
        deps.forEach(System.out::println);

        System.out.println();

        System.out.println("=== test: insert() ===");
        Department department = new Department(null, "Sales");
        departmentDao.insert(department);
        System.out.println("Department inserted! new department id: " + department.getId());

        System.out.println();

        System.out.println("=== test: update() ===");
        Department depToUpdate = departmentDao.findById(2);
        depToUpdate.setName("Food");
        departmentDao.update(depToUpdate);
        System.out.println("Department updated!");

        System.out.println();

        System.out.println("=== test: delete() ===");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        Department depToDelete = departmentDao.findById(id);

        if (SellerTest.confirmation(sc)) {
            departmentDao.delete(depToDelete);
            System.out.println("Department deleted!");
        } else {
            System.out.println("Operation canceled");
        }

        System.out.println();

        System.out.println("=== test: deleteById() ===");
        System.out.println("Enter id for deleteById test: ");
        int idToDelete = sc.nextInt();

        if (SellerTest.confirmation(sc)) {
            departmentDao.deleteById(idToDelete);
            System.out.println("Department deleted!");
        } else {
            System.out.println("Operation canceled");
        }

        sc.close();
    }
}