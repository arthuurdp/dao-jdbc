package dao;

import entities.Department;

import java.util.List;

public interface DepartmentDao {
    void insert(Department p);
    void update(Department p);
    void delete(Department p);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();
}
