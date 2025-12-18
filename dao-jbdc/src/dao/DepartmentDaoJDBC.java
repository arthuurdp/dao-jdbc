package dao;

import db.DB;
import db.DBException;
import entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department p) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)",
                                        st.RETURN_GENERATED_KEYS);

            st.setString(1, p.getName());

            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Error inserting department");
            } else {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
                System.out.println("Department inserted successfully");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department p) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE department SET Name = ? WHERE Id = ?");

            st.setString(1, p.getName());
            st.setInt(2, p.getId());

            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Department not found");
            } else {
                System.out.println("Department updated successfully");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void delete(Department p) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

            st.setInt(1, p.getId());

            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Department not found");
            } else {
                System.out.println("Department deleted successfully");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

            st.setInt(1, id);
            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Department not found");
            } else {
                System.out.println("Department deleted successfully");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
        st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            while (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("Id"));
                dep.setName(rs.getString("Name"));
                return dep;
            }
            return null;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Department> departments = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");

            rs = st.executeQuery();

            while (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("Id"));
                dep.setName(rs.getString("Name"));
                departments.add(dep);
            }
            return departments;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
}
