package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Connection1;

import entity.DayMoney;

public class DayMoneyDao {
	//���ݿ�����
	private Connection con=null;
	//����ps
	private PreparedStatement ps=null;
	//����rs
	private ResultSet rs=null;
	//��ȡ���ݿ⵱ǰ������
	public String getDBDate(){
		//�������ر���
		String rtn="";
		//�������ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="SELECT CURDATE() AS 'curDate'";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//ִ��sql
			rs=ps.executeQuery();
			//����
			if(rs.next()){
				rtn=rs.getString("curDate");
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
	//���ݵ�ǰ����,��ȡ��ǰ��Ӫҵ��
	public double getCurDayMoney(String curDate){
		//�������ر���
		double rtn=0;
		//�������ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" SELECT ";
		sql+="  money ";
		sql+=" FROM ";
		sql+="  daymoney ";
		sql+=" WHERE ";
		sql+="  DAY=? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setString(1, curDate);
			//ִ��
			rs=ps.executeQuery();
			//����
			if(rs.next()){
				rtn=rs.getDouble("money");
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
	//�жϸ������Ƿ���"Daymoney"���Ƿ����
	public boolean ifDayMoneyexists(String curDay){
		//�������ر���
		boolean rtn=true; //false:������;true:����
		//�������ݿ�����
		con=Connection1.getCon();
		//sql
		String sql=" ";
		sql+=" SELECT ";
		sql+="  COUNT(DAY) AS 'num' ";
		sql+=" FROM ";
		sql+="  daymoney ";
		sql+=" WHERE ";
		sql+="  DAY=? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setString(1, curDay);
			//ִ��sql
			rs=ps.executeQuery();
			//����
			if (rs.next()) {
				if (!(rs.getInt("num")>0)) {
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
	//����
	public int insrtDyMny(DayMoney dm){
		//�������ر���
		int rtn=0;
		//�������ݿ�����
		con=Connection1.getCon();
		//sql
		String sql="INSERT INTO daymoney VALUES(?, ?)";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setString(1, dm.getDay());
			ps.setDouble(2, dm.getMoney());
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
	//����
	public int updateDyMny(DayMoney dm){
		//�������ر���
		int rtn=0;
		//�������ݿ�����
		con=Connection1.getCon();
		//sql
		String sql=" ";
		sql+=" UPDATE ";
		sql+="  daymoney ";
		sql+=" SET ";
		sql+="  money = money+? ";
		sql+=" WHERE ";
		sql+="  DAY = ? ";
		try {
			//����ps����
			ps=con.prepareStatement(sql);
			//���ò���
			ps.setDouble(1, dm.getMoney());
			ps.setString(2, dm.getDay());
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
}
