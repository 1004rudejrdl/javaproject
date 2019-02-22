package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Modeling;

public class projectDAO {
	public static ArrayList<Modeling> dbArrayList = new ArrayList<>();
	public static ArrayList<Modeling> dbChartArrayList = new ArrayList<Modeling>();
	public static ArrayList<Modeling> dbInventoryArrayList = new ArrayList<>();
	public static ArrayList<Modeling> dbInventory2ArrayList = new ArrayList<>();
	

	

	// �ֹ�â
	public static int insertData(Modeling modeling) {
		StringBuffer insertProduct = new StringBuffer();
		insertProduct.append("insert into ordertb ");
		insertProduct.append("(thatday,thattime,receiptno,orderno,instore,food,price) ");
		insertProduct.append("values ");
		insertProduct.append("(?,?,?,?,?,?,?) ");

		Connection con = null;
		int count = 0;
		// 1.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
		PreparedStatement psmt = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(insertProduct.toString());
			// 1.4 �������� ���������͸� �����Ѵ�.
			psmt.setString(1, modeling.getThatDay());
			psmt.setString(2, modeling.getThatTime());
			psmt.setInt(3, modeling.getReceiptNo());
			psmt.setInt(4, modeling.getOrderNo());
			psmt.setInt(5, modeling.getInStore());
			psmt.setString(6, modeling.getFood());
			psmt.setInt(7, modeling.getPrice());

			// 1.5 ���� �����͸� ������ �������� �����϶�
			count = psmt.executeUpdate();
			if (count == 0) {
				LoginController.callAlert("���� ���� ���� : ���������� ����");
				return count;
			}

		} catch (SQLException e) {
			LoginController.callAlert("���� ���� : �����ͺ��̽��� ���Խ���");
			e.printStackTrace();
		} finally {
			try {// 1.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("�ݱ� ���� : psmt, con �ݱ����");
			}
		}
		return count;
	}

	// �ֹ�Ȯ��
	public static ArrayList<Modeling> getOrderDBData() {
		// 2.1 �����ͺ��̽����� ordertb�� �ִ� ���ڵ带 ��� �������� ������
		String selectModeling = "select * from ordertb";
		// 2.2 ����Ÿ���̽� connetion�� �����;� �Ѵ�.
		Connection con = null;
		// 2.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
		PreparedStatement psmt = null;
		// 2.4 �������� �����ϰ� ���� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectModeling);

			// 1.5 ���� �����͸� ������ �������� �����϶�(������� ������)
			// executeQuery(); �������� �����ؼ� ����� �����ö� ����ϴ� ������������
			// executeUpdate(); �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������������
			rs = psmt.executeQuery();
			if (rs == null) {
				LoginController.callAlert("select ���� : select ������ ����");
				return null;
			}
			while (rs.next()) {
				Modeling modeling = new Modeling(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getInt(5), rs.getString(6), rs.getInt(7));
				dbArrayList.add(modeling);
			}

		} catch (SQLException e) {
			LoginController.callAlert("���� ���� : �����ͺ��̽��� ���Խ���");
			e.printStackTrace();
		} finally {
			try {// 2.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("�ݱ� ���� : psmt, con �ݱ����");
			}
		}
		return dbArrayList;
	}
	//�ֹ��ϸ� �������ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
	public static ArrayList<Modeling> orderNInventory(String str) {
		// 
		String selectModeling = "select product, productNumber from inventorytbl where product = ? ";
		// 2.2 ����Ÿ���̽� connetion�� �����;� �Ѵ�.
		Connection con = null;
		// 2.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
		PreparedStatement psmt = null;
		// 2.4 �������� �����ϰ� ���� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectModeling);
			psmt.setString(1, str);
			System.out.println(str+"DAO");
			// 1.5 ���� �����͸� ������ �������� �����϶�(������� ������)
			// executeQuery(); �������� �����ؼ� ����� �����ö� ����ϴ� ������������
			// executeUpdate(); �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������������
			rs = psmt.executeQuery();
			if (rs == null) {
				LoginController.callAlert("select ���� : select ������ ����");
				return null;
			}
			while (rs.next()) {
				Modeling modeling = new Modeling(rs.getString(1), rs.getInt(2),0,0);
				System.out.println(modeling+"modeling");
				dbInventory2ArrayList.add(modeling);
			}
		} catch (SQLException e) {
			LoginController.callAlert("���� ���� : �����ͺ��̽��� ���Խ���");
			e.printStackTrace();
		} finally {
			try {// 2.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("�ݱ� ���� : psmt, con �ݱ����");
			}
		}
		return dbInventory2ArrayList;
	}
	//��� �� ���� ������Ʈ�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
	public static int updateInventoryset(Modeling update) {

				StringBuffer updateStudent=new StringBuffer();
				updateStudent.append("update inventorytbl set ");
				updateStudent.append("productNumber=? where product=? ");
				
				// 1.2 ����Ÿ���̽� connetion�� �����;� �Ѵ�.
				Connection con = null;
				int count=0;
				// 1.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
				PreparedStatement psmt = null;
				try {
					con = DBUtility.getConnection();
					psmt = con.prepareStatement(updateStudent.toString());
					// 1.4 �������� ���������͸� �����Ѵ�.
					
					psmt.setInt(1, update.getProductNumber());
					psmt.setString(2, update.getProduct());						
					// 1.5 ���� �����͸� ������ �������� �����϶�
					count = psmt.executeUpdate();
					if (count == 0) {
						LoginController.callAlert("���� ���� ���� : ���������� ����");
						return count;
					}

				} catch (SQLException e) {
					LoginController.callAlert("���� ���� : �����ͺ��̽��� ��������");
				} finally {
					try {// 1.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
						if (psmt != null) {
							psmt.close();
						}
						if (con != null) {
							con.close();
						}
					} catch (SQLException e) {
						LoginController.callAlert("�ݱ� ���� : psmt, con �ݱ����");
					}
				}
		return count;
	}

	// ��� Ȯ��
	public static ArrayList<Modeling> getInventoryDBData() {
		// 2.1 �����ͺ��̽����� inventorytbl�� �ִ� ���ڵ带 ��� �������� ������
		String selectInventoryModeling = "select * from inventorytbl order by productNo asc ";
		// 2.2 ����Ÿ���̽� connetion�� �����;� �Ѵ�.
		Connection conInventory = null;
		// 2.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
		PreparedStatement psmtInventory = null;
		// 2.4 �������� �����ϰ� ���� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rsInventory = null;

		try {
			conInventory = DBUtility.getConnection();
			psmtInventory = conInventory.prepareStatement(selectInventoryModeling);

			// 1.5 ���� �����͸� ������ �������� �����϶�(������� ������)
			// executeQuery(); �������� �����ؼ� ����� �����ö� ����ϴ� ������������
			// executeUpdate(); �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������������
			rsInventory = psmtInventory.executeQuery();
			if (rsInventory == null) {
				LoginController.callAlert("select ���� : select ������ ����");
				return null;
			}
			while (rsInventory.next()) {
				Modeling inventorymodeling = new Modeling(rsInventory.getInt(1), rsInventory.getString(2),
						rsInventory.getInt(3), rsInventory.getString(4));
				dbInventoryArrayList.add(inventorymodeling);
			}

		} catch (SQLException e) {
			LoginController.callAlert("���� ���� : �����ͺ��̽��� ���Խ���");
			e.printStackTrace();
		} finally {
			try {// 2.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
				if (psmtInventory != null) {
					psmtInventory.close();
				}
				if (conInventory != null) {
					conInventory.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("�ݱ� ���� : psmtInventory, conInventory �ݱ����");
			}
		}
		return dbInventoryArrayList;
	}

	// ��� ����
	public static int insertInventoryData(Modeling inventoryData) {
		StringBuffer insertProduct = new StringBuffer();
		insertProduct.append("insert into inventorytbl ");
		insertProduct.append("(productNo,product,productNumber,expirationDate) ");
		insertProduct.append("values ");
		insertProduct.append("(?,?,?,?) ");

		Connection conInventoryInsert = null;
		int count = 0;
		// 1.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
		PreparedStatement psmtInventoryInsert = null;

		try {
			conInventoryInsert = DBUtility.getConnection();
			psmtInventoryInsert = conInventoryInsert.prepareStatement(insertProduct.toString());
			// 1.4 �������� ���������͸� �����Ѵ�.
			psmtInventoryInsert.setInt(1, inventoryData.getProductNo());
			psmtInventoryInsert.setString(2, inventoryData.getProduct());
			psmtInventoryInsert.setInt(3, inventoryData.getProductNumber());
			psmtInventoryInsert.setString(4, inventoryData.getExpirationDate());

			// 1.5 ���� �����͸� ������ �������� �����϶�
			count = psmtInventoryInsert.executeUpdate();
			if (count == 0) {
				LoginController.callAlert("���� ���� ���� : ���������� ����");
				return count;
			}

		} catch (SQLException e) {
			LoginController.callAlert("���� ���� : �����ͺ��̽��� ���Խ���");
			e.printStackTrace();
		} finally {
			try {// 1.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
				if (psmtInventoryInsert != null) {
					psmtInventoryInsert.close();
				}
				if (conInventoryInsert != null) {
					conInventoryInsert.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("�ݱ� ���� : psmtInventoryInsert, conInventoryInsert �ݱ����");
			}
		}
		return count;
	}

	// ��� ����
	public static int inventoryModify(Modeling inventoryModifyData) {
		StringBuffer inventoryModify = new StringBuffer();
		inventoryModify.append("update inventorytbl set ");
		inventoryModify.append("productNo=?, product=?, productNumber=?, expirationDate=? where productNo=? ");

		// 1.2 ����Ÿ���̽� connetion�� �����;� �Ѵ�.
		Connection conInventoryModifyData = null;
		int count = 0;
		// 1.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
		PreparedStatement psmtInventoryModifyData = null;
		try {
			conInventoryModifyData = DBUtility.getConnection();
			psmtInventoryModifyData = conInventoryModifyData.prepareStatement(inventoryModify.toString());
			// 1.4 �������� ���������͸� �����Ѵ�.

			psmtInventoryModifyData.setInt(1, inventoryModifyData.getProductNo());
			psmtInventoryModifyData.setString(2, inventoryModifyData.getProduct());
			psmtInventoryModifyData.setInt(3, inventoryModifyData.getProductNumber());
			psmtInventoryModifyData.setString(4, inventoryModifyData.getExpirationDate());
			psmtInventoryModifyData.setInt(5, inventoryModifyData.getProductNo());
			// 1.5 ���� �����͸� ������ �������� �����϶�
			count = psmtInventoryModifyData.executeUpdate();
			if (count == 0) {
				LoginController.callAlert("���� ���� ���� : ���������� ����");
				return count;
			}

		} catch (SQLException e) {
			LoginController.callAlert("���� ���� : �����ͺ��̽��� ��������");
			e.printStackTrace();
		} finally {
			try {// 1.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
				if (psmtInventoryModifyData != null) {
					psmtInventoryModifyData.close();
				}
				if (conInventoryModifyData != null) {
					conInventoryModifyData.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("�ݱ� ���� : psmt, con �ݱ����");
			}
		}
		return count;
	}

	//����
	public static int deleteInventoryData(int productNo) {
		
			String deleteInventory = "delete from inventorytbl where productNo = ?";
			// 3.2 ����Ÿ���̽� connetion�� �����;� �Ѵ�.
			Connection con = null;
			// 3.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
			PreparedStatement psmt = null;
			// 3.4 �������� �����ϰ� ���� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
			int count=0;
			
			try {
				con = DBUtility.getConnection();
				psmt = con.prepareStatement(deleteInventory);
				psmt.setInt(1, productNo);
				// 1.5 ���� �����͸� ������ �������� �����϶�(������� ������)
				//executeQuery(); �������� �����ؼ� ����� �����ö� ����ϴ� ������������
				//executeUpdate(); �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������������
				count = psmt.executeUpdate();
				if (count == 0) {
					LoginController.callAlert("delete ���� : delete ������ ����");
					return count;
				}
				
			} catch (SQLException e) {
				LoginController.callAlert("delete ���� : �����ͺ��̽� delete�� ��������");
			} finally {
				try {// 2.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
					if (psmt != null) {
						psmt.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					LoginController.callAlert("�ݱ� ���� : psmt, con �ݱ����");
				}
			}
		return count;
	}	
	//���հ�
	public static ArrayList<Modeling> getTotalPrice() {
		// 2.1 �����ͺ��̽����� ordertb�� �ִ� ���ڵ带 ��� �������� ������
		String selectModeling = "select sum(price) from ordertb ";
		// 2.2 ����Ÿ���̽� connetion�� �����;� �Ѵ�.
		Connection con = null;
		// 2.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
		PreparedStatement psmt = null;
		// 2.4 �������� �����ϰ� ���� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectModeling);

			// 1.5 ���� �����͸� ������ �������� �����϶�(������� ������)
			// executeQuery(); �������� �����ؼ� ����� �����ö� ����ϴ� ������������
			// executeUpdate(); �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������������
			rs = psmt.executeQuery();
			if (rs == null) {
				LoginController.callAlert("select ���� : select ������ ����");
				return null;
			}
			while (rs.next()) {
				Modeling modeling = new Modeling(rs.getInt(1));
				dbArrayList.add(modeling);
			}

		} catch (SQLException e) {
			LoginController.callAlert("���� ���� : �����ͺ��̽��� ���Խ���");
			e.printStackTrace();
		} finally {
			try {// 2.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("�ݱ� ���� : psmt, con �ݱ����");
			}
		}
		return dbArrayList;
	}
	//��Ʈ�����
	public static ArrayList<Modeling> getChartData() {
		// 2.1 �����ͺ��̽����� ordertb�� �ִ� ���ڵ带 ��� �������� ������
		String selectModeling = "select thatday, sum(price) as '����' from ordertb group by thatday ";
		// 2.2 ����Ÿ���̽� connetion�� �����;� �Ѵ�.
		Connection con = null;
		// 2.3 �������� �����ؾ��� Statement�� ������ �Ѵ�.
		PreparedStatement psmt = null;
		// 2.4 �������� �����ϰ� ���� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
		ResultSet rs = null;

		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectModeling);
			// 1.5 ���� �����͸� ������ �������� �����϶�(������� ������)
			// executeQuery(); �������� �����ؼ� ����� �����ö� ����ϴ� ������������
			// executeUpdate(); �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������������
			rs = psmt.executeQuery();
		
			if (rs == null) {
				LoginController.callAlert("select ���� : select ������ ����");
				return null;
			}
			while (rs.next()) {
				Modeling modeling = new Modeling(rs.getString(1), rs.getInt(2),0);
//				Modeling modeling=new Modeling(0);
//				Modeling.ThatDay that=modeling.new ThatDay(rs.getString(1), rs.getInt(2));
				dbChartArrayList.add(modeling);
//				System.out.println(dbChartArrayList);
			}
		} catch (SQLException e) {
			LoginController.callAlert("���� ���� : �����ͺ��̽��� ���Խ���");
			e.printStackTrace();
		} finally {
			try {// 2.6 �ڿ���ü�� �ݾƾ� �Ѵ�.
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				LoginController.callAlert("�ݱ� ���� : psmt, con �ݱ����");
			}
		}
		return dbChartArrayList;
	}
}
