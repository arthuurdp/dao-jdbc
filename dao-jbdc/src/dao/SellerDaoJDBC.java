package dao;

import db.DB;
import db.DBException;
import entities.Department;
import entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller s) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO seller " +
                            "(Name, Email, " +
                            "BirthDate, BaseSalary, " +
                            "DepartmentId) " +
                            "VALUES (?, ?, ?, ?, ?)",
                            st.RETURN_GENERATED_KEYS);

            st.setString(1, s.getName());
            st.setString(2, s.getEmail());
            st.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
            st.setDouble(4, s.getBaseSalary());
            st.setInt(5, s.getDepartment().getId());

            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Error inserting seller");
            } else {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    s.setId(rs.getInt(1));
                }
                System.out.println("Seller inserted successfully");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller s) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE seller SET " +
                            "Name = ?, " +
                            "Email = ?, " +
                            "BirthDate = ?, " +
                            "BaseSalary = ?, " +
                            "DepartmentId = ? " +
                            "WHERE Id = ?"
            );
            st.setString(1, s.getName());
            st.setString(2, s.getEmail());
            st.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
            st.setDouble(4, s.getBaseSalary());
            st.setInt(5, s.getDepartment().getId());
            st.setInt(6, s.getId());

            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Seller not found");
            } else {
                System.out.println("Seller updated successfully");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void delete(Seller s) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

            st.setInt(1, s.getId());
            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Seller not found");
            } else {
                System.out.println("Seller deleted successfully");
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
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

            st.setInt(1, id);
            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Seller not found");
            } else {
                System.out.println("Seller deleted successfully");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT seller.*, department.Name DepName "
                    + "FROM seller JOIN department ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Seller s = instanciateSeller(rs);

                Department d = new Department();
                d.setId(rs.getInt("DepartmentId"));
                d.setName(rs.getString("DepName"));

                s.setDepartment(d);

                return s;
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
    public List<Seller> findAll() {
        Statement st = null;
        ResultSet rs = null;

        List<Seller> sellers = new ArrayList<>();

        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    "SELECT seller.*, department.Name DepName "
                    + "FROM seller JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "ORDER BY Id");

            while (rs.next()) {
                Seller s = instanciateSeller(rs);

                Department d = new Department();
                d.setId(rs.getInt("DepartmentId"));
                d.setName(rs.getString("DepName"));
                s.setDepartment(d);

                sellers.add(s);
            }
            return sellers;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Seller> sellers = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                        "SELECT s.*, d.Name depName " +
                            "FROM seller s JOIN department d " +
                            "ON s.DepartmentId = d.Id " +
                                "WHERE d.Id = ?");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                Seller s = instanciateSeller(rs);
                Department d = new Department();
                d.setId(rs.getInt("DepartmentId"));
                d.setName(rs.getString("depName"));
                s.setDepartment(d);

                sellers.add(s);
            }
            return sellers;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Seller instanciateSeller (ResultSet rs) throws SQLException {
        Seller s = new Seller();
        s.setId(rs.getInt("Id"));
        s.setName(rs.getString("Name"));
        s.setEmail(rs.getString("Email"));
        s.setBirthDate(rs.getDate("BirthDate"));
        s.setBaseSalary(rs.getDouble("BaseSalary"));
        return s;
    }
}
