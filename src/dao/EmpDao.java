package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.Connection1;
import com.LoginInfo;

import entity.Emp;

public class EmpDao {
	//���ݿ�����
	private Connection con=null;
	//����ps
	PreparedStatement ps=null;
	//����rs
	ResultSet rs=null;
	//����һ��Ա����Ϣ
	public int empInsert(Emp emp){
		//�������ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="insert into emp(empID,empName,empSex,empAge,empPhone,empAddress,empPower,empPWD)"
				+"values(?,?,?,?,?,?,?,?)";
		//����ps����
		try {
			ps=con.prepareStatement(sql);
			//���ò���
			//id
			ps.setString(1, emp.getEmpID());
			//name
			ps.setString(2, emp.getEmpName());
			//sex
			ps.setInt(3, emp.getEmpSex());
			//age
			ps.setInt(4, emp.getEmpAge());
			//phone
			ps.setString(5, emp.getEmpPhone());
			//Address
			ps.setString(6, emp.getEmpAddress());
			//power
			ps.setInt(7, emp.getEmpPower());
			//pwd
			ps.setString(8, emp.getEmpPWD());
			//ִ��
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//�ж�����Ĺ����Ƿ��ظ�
	public boolean ifEmpIDRepeat(String empID){
		//�������ر���
		boolean b=false; //false:û���ظ�;true:���ظ�
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="SELECT COUNT(*) AS 'idNum' FROM emp WHERE empID=?";
		//����ps
		try {
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setString(1, empID);
			//ִ��
			rs=ps.executeQuery();
			//����
			while(rs.next()){
				if (rs.getInt("idNum")>0) {
					b=true;
				} else {
					b=false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�rs
			Connection1.closeRS(rs);
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		
		//����
		return b;
	}
	//�������е�Ա����Ϣ
	public Vector<Vector<Object>> getEmpList(String where){
		//����һ�����ر���
		Vector<Vector<Object>> vV=new Vector<Vector<Object>>();
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" SELECT ";
		sql+="  empID, ";
		sql+="  empName, ";
		sql+="  CASE  WHEN empSex=1 THEN '��' WHEN empSex=0 THEN 'Ů' END AS empSex, ";
		sql+="  empAge, ";
		sql+="  empPhone, ";
		sql+="  empAddress, ";
		sql+="  CASE  WHEN empPower=1 THEN '����Ա' WHEN empPower=2 THEN '����Ա' END AS empPower, ";
		sql+="  empSex, ";
		sql+="  empPower, ";
		sql+="  CASE  WHEN empStatus=1 THEN '����' WHEN empStatus=2 THEN '����' END AS empStatus ";
		sql+=" FROM ";
		sql+="  emp ";
		sql+= where;
		//����ps����
		try {
			ps=con.prepareStatement(sql);
			//ִ��
			rs=ps.executeQuery();
			//����rs
			while(rs.next()){
				Vector<Object> v= new Vector<Object>();
				//���
				v.add(rs.getString(1));
				//����
				v.add(rs.getString(2));
				//�Ա�
				v.add(rs.getString(3));
				//����
				v.add(rs.getInt(4));
				//�ֻ���
				v.add(rs.getString(5));
				//��ַ
				v.add(rs.getString(6));
				//Ȩ��
				v.add(rs.getString(7));
				//�Ա�
				v.add(rs.getInt(8));
				//Ȩ��
				v.add(rs.getInt(9));
				//״̬
				v.add(rs.getString(10));
				//����
				vV.add(v);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�rs
			Connection1.closeRS(rs);
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return vV;
	}
	//�޸�Ա����Ϣ
	public int empUpdate(Emp emp){
		//����һ�����ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql=" ";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET ";
		sql+=" 	empID = ?, ";
		sql+="  empName = ?, ";
		sql+="  empSex = ?, ";
		sql+="  empAge = ?, ";
		sql+="  empPhone = ?, ";
		sql+="  empAddress = ?,";
		sql+="  empPower = ? ";
		sql+=" WHERE ";
		sql+="  empID = ?  ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setString(1, emp.getEmpID());
			ps.setString(2, emp.getEmpName());
			ps.setInt(3, emp.getEmpSex());
			ps.setInt(4, emp.getEmpAge());
			ps.setString(5, emp.getEmpPhone());
			ps.setString(6, emp.getEmpAddress());
			ps.setInt(7, emp.getEmpPower());
			ps.setString(8, emp.getEmpID());
			//ִ��sql
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//���ݹ���,ɾ��Ա����Ϣ
	public int empDel(String empID){
		//����һ�����ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" DELETE ";
		sql+=" FROM ";
		sql+="  emp ";
		sql+=" WHERE ";
		sql+="  empID = ? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setString(1, empID);
			//ִ��sql
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//���븴λ
	public int empPWDReset(String empID){
		//�������ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET ";
		sql+="  empPWD=? ";
		sql+=" WHERE ";
		sql+="  empID=?  ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setString(1, "000000");
			ps.setString(2, empID);
			//ִ��sql
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//���ݵ�ǰ�û�ID������,��֤������Ա�Ƿ����
	public boolean ifRushExistLoginOn(String empID,String empPWD){
		//�������ر���
		boolean rtn=true;//Ĭ�ϲ�����;true:������;false:����
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" SELECT ";
		sql+="  COUNT(*) AS nums";
		sql+=" FROM ";
		sql+="  emp ";
		sql+=" WHERE ";
		sql+="  empPower=? ";
		sql+=" AND ";
		sql+="  empLoginOn=? ";
		sql+=" AND ";
		sql+="  empID=? ";
		sql+=" AND ";
		sql+="  empPWD=? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setInt(1, 2);
			ps.setInt(2, 1);
			ps.setString(3, empID);
			ps.setString(4, empPWD);
			//ִ��
			rs=ps.executeQuery();
			//����
			if (rs.next()) {
				if(rs.getInt("nums")>0){
					rtn=false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�rs
			Connection1.closeRS(rs);
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//���ݵ�ǰ�û�ID������,��֤����Ա�Ƿ����
	public boolean ifManagerExistLoginOn(String empID,String empPWD){
		//�������ر���
		boolean rtn=true;//Ĭ�ϲ�����;true:������;false:����
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" SELECT ";
		sql+="  COUNT(*) AS nums";
		sql+=" FROM ";
		sql+="  emp ";
		sql+=" WHERE ";
		sql+="  empPower=? ";
		sql+=" AND ";
		sql+="  empID=? ";
		sql+=" AND ";
		sql+="  empPWD=? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setInt(1, 1);
			ps.setString(2, empID);
			ps.setString(3, empPWD);
			//ִ��
			rs=ps.executeQuery();
			//����
			if (rs.next()) {
				if(rs.getInt("nums")>0){
					rtn=false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�rs
			Connection1.closeRS(rs);
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//����->����
	public int setLoginOn(String empID){
		//�������ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET";
		sql+="  empStatus = ? ";
		sql+=" WHERE ";
		sql+="  empID = ?";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setInt(1, 1);
			ps.setString(2, empID);
			//ִ��sql
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//����->����
	public int setLoginOff(String empID){
		//�������ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET";
		sql+="  empStatus = ? ";
		sql+=" WHERE ";
		sql+="  empID = ?";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setInt(1, 2);
			ps.setString(2, empID);
			//ִ��sql
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//�����е�����Ա��ͨ����
	public int setRushLoginOn(){
		//�������ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET";
		sql+="  empLoginOn = ? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setInt(1, 1);
			//ִ��sql
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//�����е�����Ա��ͨ����
	public int setRushLoginOff(){
		//�������ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET";
		sql+="  empLoginOn = ? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setInt(1, 0);
			//ִ��sql
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//��ȡ�������������Ա�ĸ���
	public int getRshLgnOnNm(){
		//����һ�����ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" SELECT ";
		sql+="  COUNT(*) AS rushNum";
		sql+=" FROM ";
		sql+="  emp ";
		sql+=" WHERE ";
		sql+="  empPower=?  ";
		sql+=" AND ";
		sql+="  empLoginOn=?  ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setInt(1, 2);
			ps.setInt(2, 1);
			//ִ��sql
			rs=ps.executeQuery();
			//����
			if(rs.next()){
				rtn=rs.getInt("rushNum");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�rs
			Connection1.closeRS(rs);
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//���ݹ����޸�����
	public int updatePWD(String empPWD){
		//�������ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET ";
		sql+="  empPWD = ?  ";
		sql+=" WHERE ";
		sql+="  empID  =?";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setString(1, empPWD);
			ps.setString(2, LoginInfo.empID);
			//ִ��
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
	//ͳ����������Ա������
	public int getRushNum(){
		//�������ر���
		int rtn=0;
		//��ȡ���ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" SELECT ";
		sql+="  COUNT(*) AS rushNum ";
		sql+=" FROM ";
		sql+="  emp ";
		sql+=" WHERE ";
		sql+="  empPower=? ";
		sql+=" AND ";
		sql+="  empStatus=? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setInt(1, 2);
			ps.setInt(2, 1);
			//ִ��
			rs=ps.executeQuery();
			//����
			if (rs.next()) {
				rtn=rs.getInt("rushNum");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//�ر�rs
			Connection1.closeRS(rs);
			//�ر�ps
			Connection1.closeSt(ps);
			//�ر����ݿ�����
			Connection1.closeCon(con);
		}
		//����
		return rtn;
	}
}
