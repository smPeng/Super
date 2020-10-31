package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Connection1;

import entity.DayMoney;

public class DayMoneyDao {
	//数据库连接
	private Connection con=null;
	//声明ps
	private PreparedStatement ps=null;
	//声明rs
	private ResultSet rs=null;
	//获取数据库当前的日期
	public String getDBDate(){
		//声明返回变量
		String rtn="";
		//返回数据库连接
		con=Connection1.getCon();
		//sql
		String sql="SELECT CURDATE() AS 'curDate'";
		try {
			//创建ps对象
			ps=con.prepareStatement(sql);
			//执行sql
			rs=ps.executeQuery();
			//遍历
			if(rs.next()){
				rtn=rs.getString("curDate");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭rs
			Connection1.closeRS(rs);
			//关闭ps
			Connection1.closeSt(ps);
			//关闭数据库连接
			Connection1.closeCon(con);
		}
		//返回
		return rtn;
	}
	//根据当前日期,获取当前的营业额
	public double getCurDayMoney(String curDate){
		//声明返回变量
		double rtn=0;
		//返回数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setString(1, curDate);
			//执行
			rs=ps.executeQuery();
			//遍历
			if(rs.next()){
				rtn=rs.getDouble("money");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭rs
			Connection1.closeRS(rs);
			//关闭ps
			Connection1.closeSt(ps);
			//关闭数据库连接
			Connection1.closeCon(con);
		}
		//返回
		return rtn;
	}
	//判断该日期是否在"Daymoney"表是否存在
	public boolean ifDayMoneyexists(String curDay){
		//声明返回变量
		boolean rtn=true; //false:不存在;true:存在
		//返回数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setString(1, curDay);
			//执行sql
			rs=ps.executeQuery();
			//遍历
			if (rs.next()) {
				if (!(rs.getInt("num")>0)) {
					rtn=false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭rs
			Connection1.closeRS(rs);
			//关闭ps
			Connection1.closeSt(ps);
			//关闭数据库连接
			Connection1.closeCon(con);
		}
		//返回
		return rtn;
	}
	//插入
	public int insrtDyMny(DayMoney dm){
		//声明返回遍历
		int rtn=0;
		//返回数据库连接
		con=Connection1.getCon();
		//sql
		String sql="INSERT INTO daymoney VALUES(?, ?)";
		try {
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setString(1, dm.getDay());
			ps.setDouble(2, dm.getMoney());
			//执行
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭ps
			Connection1.closeSt(ps);
			//关闭数据库连接
			Connection1.closeCon(con);
		}
		//返回
		return rtn;
	}
	//更新
	public int updateDyMny(DayMoney dm){
		//声明返回遍历
		int rtn=0;
		//返回数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setDouble(1, dm.getMoney());
			ps.setString(2, dm.getDay());
			//执行sql
			rtn=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭ps
			Connection1.closeSt(ps);
			//关闭数据库连接
			Connection1.closeCon(con);
		}
		//返回
		return rtn;
	}
}
