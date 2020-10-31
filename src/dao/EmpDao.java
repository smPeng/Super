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
	//数据库连接
	private Connection con=null;
	//声明ps
	PreparedStatement ps=null;
	//声明rs
	ResultSet rs=null;
	//插入一条员工信息
	public int empInsert(Emp emp){
		//声明返回变量
		int rtn=0;
		//获取数据库连接
		con=Connection1.getCon();
		//sql
		String sql="insert into emp(empID,empName,empSex,empAge,empPhone,empAddress,empPower,empPWD)"
				+"values(?,?,?,?,?,?,?,?)";
		//创建ps对象
		try {
			ps=con.prepareStatement(sql);
			//设置参数
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
	//判断输入的工号是否重复
	public boolean ifEmpIDRepeat(String empID){
		//声明返回变量
		boolean b=false; //false:没有重复;true:有重复
		//获取数据库连接
		con=Connection1.getCon();
		//sql
		String sql="SELECT COUNT(*) AS 'idNum' FROM emp WHERE empID=?";
		//创建ps
		try {
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setString(1, empID);
			//执行
			rs=ps.executeQuery();
			//遍历
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
			//关闭rs
			Connection1.closeRS(rs);
			//关闭ps
			Connection1.closeSt(ps);
			//关闭数据库连接
			Connection1.closeCon(con);
		}
		
		//返回
		return b;
	}
	//检索所有的员工信息
	public Vector<Vector<Object>> getEmpList(String where){
		//声明一个返回变量
		Vector<Vector<Object>> vV=new Vector<Vector<Object>>();
		//获取数据库连接
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" SELECT ";
		sql+="  empID, ";
		sql+="  empName, ";
		sql+="  CASE  WHEN empSex=1 THEN '男' WHEN empSex=0 THEN '女' END AS empSex, ";
		sql+="  empAge, ";
		sql+="  empPhone, ";
		sql+="  empAddress, ";
		sql+="  CASE  WHEN empPower=1 THEN '管理员' WHEN empPower=2 THEN '收银员' END AS empPower, ";
		sql+="  empSex, ";
		sql+="  empPower, ";
		sql+="  CASE  WHEN empStatus=1 THEN '在线' WHEN empStatus=2 THEN '离线' END AS empStatus ";
		sql+=" FROM ";
		sql+="  emp ";
		sql+= where;
		//创建ps对象
		try {
			ps=con.prepareStatement(sql);
			//执行
			rs=ps.executeQuery();
			//遍历rs
			while(rs.next()){
				Vector<Object> v= new Vector<Object>();
				//编号
				v.add(rs.getString(1));
				//姓名
				v.add(rs.getString(2));
				//性别
				v.add(rs.getString(3));
				//年龄
				v.add(rs.getInt(4));
				//手机号
				v.add(rs.getString(5));
				//地址
				v.add(rs.getString(6));
				//权限
				v.add(rs.getString(7));
				//性别
				v.add(rs.getInt(8));
				//权限
				v.add(rs.getInt(9));
				//状态
				v.add(rs.getString(10));
				//搁置
				vV.add(v);
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
		return vV;
	}
	//修改员工信息
	public int empUpdate(Emp emp){
		//声明一个返回变量
		int rtn=0;
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setString(1, emp.getEmpID());
			ps.setString(2, emp.getEmpName());
			ps.setInt(3, emp.getEmpSex());
			ps.setInt(4, emp.getEmpAge());
			ps.setString(5, emp.getEmpPhone());
			ps.setString(6, emp.getEmpAddress());
			ps.setInt(7, emp.getEmpPower());
			ps.setString(8, emp.getEmpID());
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
	//根据工号,删除员工信息
	public int empDel(String empID){
		//声明一个返回变量
		int rtn=0;
		//获取数据库连接
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" DELETE ";
		sql+=" FROM ";
		sql+="  emp ";
		sql+=" WHERE ";
		sql+="  empID = ? ";
		try {
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setString(1, empID);
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
	//密码复位
	public int empPWDReset(String empID){
		//声明返回变量
		int rtn=0;
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setString(1, "000000");
			ps.setString(2, empID);
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
	//根据当前用户ID和密码,验证此收银员是否存在
	public boolean ifRushExistLoginOn(String empID,String empPWD){
		//声明返回变量
		boolean rtn=true;//默认不存在;true:不存在;false:存在
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setInt(1, 2);
			ps.setInt(2, 1);
			ps.setString(3, empID);
			ps.setString(4, empPWD);
			//执行
			rs=ps.executeQuery();
			//遍历
			if (rs.next()) {
				if(rs.getInt("nums")>0){
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
	//根据当前用户ID和密码,验证管理员是否存在
	public boolean ifManagerExistLoginOn(String empID,String empPWD){
		//声明返回变量
		boolean rtn=true;//默认不存在;true:不存在;false:存在
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setInt(1, 1);
			ps.setString(2, empID);
			ps.setString(3, empPWD);
			//执行
			rs=ps.executeQuery();
			//遍历
			if (rs.next()) {
				if(rs.getInt("nums")>0){
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
	//离线->在线
	public int setLoginOn(String empID){
		//声明返回变量
		int rtn=0;
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setInt(1, 1);
			ps.setString(2, empID);
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
	//在线->离线
	public int setLoginOff(String empID){
		//声明返回变量
		int rtn=0;
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setInt(1, 2);
			ps.setString(2, empID);
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
	//对所有的收银员开通服务
	public int setRushLoginOn(){
		//声明返回变量
		int rtn=0;
		//获取数据库连接
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET";
		sql+="  empLoginOn = ? ";
		try {
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setInt(1, 1);
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
	//对所有的收银员开通服务
	public int setRushLoginOff(){
		//声明返回变量
		int rtn=0;
		//获取数据库连接
		con=Connection1.getCon();
		//sql
		String sql="";
		sql+=" UPDATE ";
		sql+="  emp ";
		sql+=" SET";
		sql+="  empLoginOn = ? ";
		try {
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setInt(1, 0);
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
	//获取开启服务的收银员的个数
	public int getRshLgnOnNm(){
		//声明一个返回变量
		int rtn=0;
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setInt(1, 2);
			ps.setInt(2, 1);
			//执行sql
			rs=ps.executeQuery();
			//遍历
			if(rs.next()){
				rtn=rs.getInt("rushNum");
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
	//根据工号修改密码
	public int updatePWD(String empPWD){
		//声明返回变量
		int rtn=0;
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setString(1, empPWD);
			ps.setString(2, LoginInfo.empID);
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
	//统计在线收银员的人数
	public int getRushNum(){
		//声明返回变量
		int rtn=0;
		//获取数据库连接
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
			//创建ps对象
			ps=con.prepareStatement(sql);
			//设置参数
			ps.setInt(1, 2);
			ps.setInt(2, 1);
			//执行
			rs=ps.executeQuery();
			//遍历
			if (rs.next()) {
				rtn=rs.getInt("rushNum");
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
}
