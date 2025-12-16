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
    public void insert(Seller p) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO seller " +
                            "(Name, Email, " +
                            "BirthDate, BaseSalary, " +
                            "DepartmentId) " +
                            "VALUES (?, ?, ?, ?, ?)");

            st.setString(1, p.getName());
            st.setString(2, p.getEmail());
            st.setDate(3, new java.sql.Date(p.getBirthDate().getTime()));
            st.setDouble(4, p.getBaseSalary());
            st.setInt(5, p.getDepartment().getId());

            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DBException("Error inserting seller");
            } else {
                System.out.println("Seller inserted successfully");
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Seller p) {
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


            st.setString(1, p.getName());
            st.setString(2, p.getEmail());
            st.setDate(3, new java.sql.Date(p.getBirthDate().getTime()));
            st.setDouble(4, p.getBaseSalary());
            st.setInt(5, p.getDepartment().getId());
            st.setInt(6, p.getId());

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
    public void delete(Seller p) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

            st.setInt(1, p.getId());
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
                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));
                Seller seller = new Seller();
                seller.setId(rs.getInt("Id"));
                seller.setName(rs.getString("Name"));
                seller.setEmail(rs.getString("Email"));
                seller.setBaseSalary(rs.getDouble("BaseSalary"));
                seller.setBirthDate(rs.getDate("BirthDate"));
                seller.setDepartment(dep);

                return seller;
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
                    + "ORDER BY Name");

            while (rs.next()) {
                Seller s = new Seller();
                s.setName(rs.getString("Name"));
                s.setEmail(rs.getString("Email"));
                s.setBirthDate(rs.getDate("BirthDate"));
                s.setBaseSalary(rs.getDouble("BaseSalary"));

                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));
                s.setDepartment(dep);

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
}
