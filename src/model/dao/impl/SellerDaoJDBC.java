package model.dao.impl;

import infra.ConnectionFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    @Override
    public void insert(Seller obj) {
PreparedStatement st;
try (Connection connection= ConnectionFactory.getConnection()){
    st=connection.prepareStatement(
                    "INSERT INTO  seller\n"+
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)\n"+
                    "VALUES\n"+
                    "(?, ?, ?, ?, ?)\n"
                    ,Statement.RETURN_GENERATED_KEYS
            );
    st.setString(1,obj.getName());
    st.setString(2, obj.getGmail());
    st.setDate(3,new Date(obj.getBirthDate().getTime()));
    st.setDouble(4,obj.getBaseSalary());
    st.setInt(5,obj.getDepartment().getId());
    int linhaAffected = st.executeUpdate();
    if (linhaAffected>0){
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()){
            int id=rs.getInt(1);
            obj.setId(id);
        }
    }else {
        throw new RuntimeException ("erro inesperado,nenhuma linha foi afetada");
    }

} catch (SQLException e) {
    throw new RuntimeException(e);
}
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st;
        try (Connection connection= ConnectionFactory.getConnection()){
            st=connection.prepareStatement(
                    "UPDATE seller\n"+
                            "set Name=?,Email=?,BirthDate=?,BaseSalary=?,DepartmentId=?\n" +
                            "where Id=?\n"

            );
            st.setString(1,obj.getName());
            st.setString(2, obj.getGmail());
            st.setDate(3,new Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());
            st.setInt(6,obj.getId());
           st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        try (Connection connection=ConnectionFactory.getConnection()){
            PreparedStatement st;
            ResultSet rs;
            st=   connection.prepareStatement(

                            "select*\n "+
                            "from seller inner join department\n "+
                            "on seller.DepartmentId = department.Id\n"+
                            "where seller.Id=?;\n"

            );
            st.setInt(1,id);
            rs = st.executeQuery();
            if (rs.next()){
                Department department = instanciaDepartment(rs);
                Seller seller;
                seller = instanciaSeller(rs,department);
                return seller;
            }
            return null;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }



    }

    private Seller instanciaSeller(ResultSet rs, Department department) throws SQLException {
      Seller seller=  new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setGmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instanciaDepartment (ResultSet rs) throws SQLException {
       Department department= new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("department.Name"));
        return department;
    }

    @Override
    public List<Seller> findAll() {

        try (Connection connection=ConnectionFactory.getConnection()){
            PreparedStatement st;
            ResultSet rs;
            st=   connection.prepareStatement(

                            "select*\n"+
                            "from seller inner join department\n"+
                            "on seller.DepartmentId = department.Id\n"
                            );
            rs = st.executeQuery();
            List<Seller>list= new ArrayList<>();
            Map<Integer, Department>map=new HashMap<>();

            while (rs.next()){
                Department dep =map.get(rs.getInt("DepartmentId"));
                if (dep==null){
                    dep=instanciaDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                Seller seller= instanciaSeller(rs,dep);
                list.add(seller);

            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        try (Connection connection=ConnectionFactory.getConnection()){
            PreparedStatement st;
            ResultSet rs;
            st=   connection.prepareStatement(
                    "select*\n "+
                            "from seller inner join department\n "+
                            "on seller.DepartmentId = department.Id\n"+
                            "where department.Id=?;\n"
                            );

            st.setInt(1,department.getId());
            rs = st.executeQuery();

            List<Seller>list= new ArrayList<>();
            Map<Integer, Department>map=new HashMap<>();

            while (rs.next()){
                Department dep =map.get(rs.getInt("DepartmentId"));
                if (dep==null){
                    dep=instanciaDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }
                Seller seller= instanciaSeller(rs,dep);
                list.add(seller);

            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
