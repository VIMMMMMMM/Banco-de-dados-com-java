package model.dao.impl;

import infra.ConnectionFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        try (Connection connection=ConnectionFactory.getConnection()){
            PreparedStatement st = null;
            ResultSet rs= null;

            st=   connection.prepareStatement(
                    "select *\n" +
                            "from seller inner join department\n" +
                            "on seller.DepartmentId = department.Id\n" +
                            "where seller.Id=?;\n"
            );
            st.setInt(1,id);
            rs = st.executeQuery();
            if (rs.next()){
                Department department = new Department();
                department.setId(rs.getInt("DepartmentId"));
                department.setName(rs.getString("department.Name"));
                Seller seller = new Seller();
                seller.setId(rs.getInt("Id"));
                seller.setName(rs.getString("Name"));
                seller.setGmail(rs.getString("Email"));
                seller.setBaseSalary(rs.getDouble("BaseSalary"));
                seller.setBirthDate(rs.getDate("BirthDate"));
                seller.setDepartment(department);
                return seller;
            }
            return null;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }



    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
