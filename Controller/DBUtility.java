package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {

	public static Connection getConnection() {
		Connection con=null;
		try {
		// 1. Mysql database class�� �ε��Ѵ�.
		Class.forName("com.mysql.jdbc.Driver");
		// 2. �ּ�, ���̵�, ��к�ȣ�� ���ؼ� ���ӿ�û�Ѵ�.
		con =DriverManager.getConnection("jdbc:mysql://localhost/new_project","root","123456");
//		LoginController.callAlert("�����ͺ��̽� ���Ἲ�� : �����ͺ��̽������� ��������");
		}catch(Exception e){
			LoginController.callAlert("�����ͺ��̽� ������� : ����");
			e.printStackTrace();
			return null;
		}
		return con;
	}
	
}
