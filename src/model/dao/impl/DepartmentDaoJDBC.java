package model.dao.impl;

import infra.ConnectionFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {
    @Override
    public void insert(Department obj) {
 PreparedStatement st;
 try (Connection connection= ConnectionFactory.getConnection()){
     st= connection.prepareStatement(
             "INSERT INTO department\n" +
                     "( Name) \n" +
                     "VALUES \n" +
                     "(?)\n", Statement.RETURN_GENERATED_KEYS
     );
     st.setString(1,obj.getName());

     int linhasAfetadas = st.executeUpdate();
     if (linhasAfetadas >0){
         ResultSet rs = st.getGeneratedKeys();
         if (rs.next()){
             int id =rs.getInt(1);
             obj.setId(id);
         }
     }else {
         throw new RuntimeException("erro, nenhuma linha foi afetada");

     }
 } catch (SQLException e) {
     throw new RuntimeException(e);
 }
    }

    @Override
    public void update(Department obj) {
    PreparedStatement st;

    try (Connection connection = ConnectionFactory.getConnection()){
        st= connection.prepareStatement("UPDATE department\n"+
                "set Id=?,Name=?\n" +
                "where Id=?\n");
        st.setInt(1,obj.getId());
        st.setString(2,obj.getName());
        st.setInt(3,obj.getId());
        st.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public void deleteById(Integer id) {
    PreparedStatement st;
    try (Connection connection = ConnectionFactory.getConnection()){
        st= connection.prepareStatement("DELETE from department where Id=?"
        );
        st.setInt(1,id);
        st.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st;
        ResultSet rs;
        try (Connection connection= ConnectionFactory.getConnection()){
            st = connection.prepareStatement("select * from department\n" +
                    "where department.Id=?\n");
            st.setInt(1,id);
            rs= st.executeQuery();
            if (rs.next()){
                Department department= instanciaDepartment(rs);
                return department;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st;
        ResultSet rs;
        try (Connection connection= ConnectionFactory.getConnection()){
            st=connection.prepareStatement("select* " +
                    "from department\n"
                    );
            rs=st.executeQuery();
            List<Department>list=new ArrayList<>();
            Map<Integer, Department>map=new HashMap<>();
            while (rs.next()){
                Department department=map.get(rs.getInt("Id"));
                if (department==null){
                   department  = instanciaDepartment(rs);
                    map.put(rs.getInt("Id"),department);
                }
                list.add(department);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private Department instanciaDepartment (ResultSet rs) throws SQLException {
        Department department= new Department();
        department.setId(rs.getInt("Id"));
        department.setName(rs.getString("Name"));
        return department;
    }
}
