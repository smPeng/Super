package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.LoginInfo;

import dao.DayMoneyDao;
import dao.EmpDao;
import dao.ShopDao;
import entity.DayMoney;
import entity.Shop;

public class Rush extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private EmpDao empDao= new EmpDao();
	private JLabel lblSum;
	private JLabel lblInfo;
	//该收银员收的钱
	private double curRushMoney;
	private JButton btnAdd;
	private JButton btnAccount;
	private JButton btnCancel;
	private JButton btnExit;
	private JButton btnDel;
	private JButton btnPlus;
	private JButton btnReduce;
	private JButton btnUpdateNum;
	private ShopDao shpDao= new ShopDao();
	private DayMoneyDao dmDao= new DayMoneyDao();
	private static Rush r= new Rush();
	public static Rush geInstance(){
		return r;
	}
	//创建
	DecimalFormat df= new DecimalFormat("#.00");
	/**
	 * Create the frame.
	 */
	private Rush() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				//添加新商品
				case KeyEvent.VK_F1:
					btnAdd.doClick();
					break;
			    //结账
				case KeyEvent.VK_F2:
					btnAccount.doClick();
					break;
				//复位
				case KeyEvent.VK_F3:
					btnCancel.doClick();
					break;
				//退出系统	
				case KeyEvent.VK_F4:
					btnExit.doClick();
					break;
				//清除	
				case KeyEvent.VK_F5:
					btnDel.doClick();
					break;
				//加一	
				case KeyEvent.VK_F6:
					btnPlus.doClick();
					break;
				//减一	
				case KeyEvent.VK_F7:
					btnReduce.doClick();
					break;
				//修改数量	
				case KeyEvent.VK_F8:
					btnUpdateNum.doClick();
					break;
				}
			}
		});

		setTitle("\u7528\u6237\u767B\u5F55");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 654, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAdd = new JButton("\u6DFB\u52A0\u65B0\u5546\u54C1[F1]");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//弹出输入对话框
				String strShpID=JOptionPane.showInputDialog(Rush.this, "请输入条码:", "输入",JOptionPane.QUESTION_MESSAGE);
				if(strShpID!=null&&strShpID.length()>0){
					//判断条码是否存在
					if (shpDao.ifShpIDExists(strShpID)) {
						//是否曾经被添加过
						if(ifAddInGrid(strShpID)){
							JOptionPane.showMessageDialog(null, "该商品已经被添加过", "提示信息", JOptionPane.ERROR_MESSAGE);
							return ;							
						}
						gridAddRow(strShpID);
						//选中刚添加的商品
						table.setRowSelectionInterval(0, 0);
					} else {
						JOptionPane.showMessageDialog(null, "该商品不存在", "提示信息", JOptionPane.ERROR_MESSAGE);
						return ;
					}
				}
			}
		});
		btnAdd.setFocusable(false);
		
		btnAdd.setIcon(new ImageIcon(Rush.class.getResource("/image/add.png")));
		btnAdd.setBounds(10, 6, 151, 30);
		contentPane.add(btnAdd);
		
		btnAccount = new JButton("\u672C\u5355\u7ED3\u8D26[F2]");
		/*
		 *  //1.这笔业务的金额
			//2.获取当前数据库日期
			//3.看当前日期是否存在
			//3.1 如果没有的话，就Insert
			//3.2 如果有的话，就update
			//4.减少库存
			//5.打印"金额总计"
			//6.获取当前已收金额
			//7.打印时间戳
			//7.1打印时间
			//7.2打印操作员
			//7.3打印已收金额
		 */
		btnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//存储本次业务的金额
				double curMoney=0;
				//获取本次金额
				curMoney=getSum();
				//累加
				curRushMoney+=curMoney;
				//获取数据库的当前日期
				String curDBDay=dmDao.getDBDate();
				//创建实体对象
				DayMoney dm= new DayMoney();
				dm.setDay(curDBDay);
				dm.setMoney(curMoney);
				//判断其是否存在
				if (dmDao.ifDayMoneyexists(curDBDay)) {
					//更新
					dmDao.updateDyMny(dm);
				} else {
					//插入
					dmDao.insrtDyMny(dm);
				}
				//减少库存
				updateStorage();
				//打印本次金额
				lblSum.setText(String.valueOf(curMoney));
				//创建日期
				Date date= new Date();
				//创建
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy年MM月dd日 E");
				//格式化
				String strDate=sdf.format(date);
				//打印
				lblInfo.setText("日期:"+strDate+"   操作员:"+LoginInfo.empID+"   当前已收金额:"+curRushMoney);
				//设置"结账"按钮不可用
				btnAccount.setEnabled(false);
			}
		});
		btnAccount.setFocusable(false);
		btnAccount.setIcon(new ImageIcon(Rush.class.getResource("/image/money.png")));
		btnAccount.setBounds(171, 6, 151, 30);
		contentPane.add(btnAccount);
		
		btnCancel = new JButton("\u590D\u4F4D[F3]");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取table的modle
				DefaultTableModel dtm= (DefaultTableModel)table.getModel(); 
				//清空所有的行
				dtm.setRowCount(0);
				//设置"结账"按钮可用
				btnAccount.setEnabled(true);
			}
		});
		btnCancel.setFocusable(false);
		btnCancel.setIcon(new ImageIcon(Rush.class.getResource("/image/cancel.png")));
		btnCancel.setBounds(334, 6, 151, 30);
		contentPane.add(btnCancel);
		
		btnExit = new JButton("\u9000\u51FA\u7CFB\u7EDF[F4]");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//弹出确认对话框
				int rtn= JOptionPane.showConfirmDialog(Rush.this, "你确认要退出吗?", "提示信息",  JOptionPane.YES_NO_OPTION);
				//如果单击是"yes"
				if (rtn==JOptionPane.YES_OPTION) {
					//在线->离线
					empDao.setLoginOff(LoginInfo.empID);
					//退出
					System.exit(0);
				}
			}
		});
		btnExit.setFocusable(false);
		btnExit.setIcon(new ImageIcon(Rush.class.getResource("/image/exit.png")));
		btnExit.setBounds(492, 6, 151, 30);
		contentPane.add(btnExit);
		
		btnDel = new JButton("\u5220\u9664\u5546\u54C1[F5]");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取选中的行号
				int selRow=table.getSelectedRow();
				//获取table的modle
				DefaultTableModel dtm= (DefaultTableModel)table.getModel();
				//删除指定行
				dtm.removeRow(selRow);
			}
		});
		btnDel.setFocusable(false);
		btnDel.setIcon(new ImageIcon(Rush.class.getResource("/image/data1.png")));
		btnDel.setBounds(502, 43, 135, 40);
		contentPane.add(btnDel);
		
		btnPlus = new JButton("\u6570\u91CF\u52A0\u4E00[F6]");
		btnPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取选中的行号
				int selRow=table.getSelectedRow();
				//如果没有选中的时候
				if(selRow==-1){
					JOptionPane.showMessageDialog(Rush.this, "请选中某商品", "提示信息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//获取当前行的数量
				int num= Integer.parseInt(String.valueOf(table.getValueAt(selRow, 3)));
				//获取当前行的单价
				double price=Double.parseDouble(String.valueOf(table.getValueAt(selRow, 2))); 
				//获取该商品的库存
				int intStor=Integer.parseInt(String.valueOf(table.getValueAt(selRow, 5)));
				//判断购买数量大于库存数量时候
				if (num+1>intStor) {
					JOptionPane.showMessageDialog(null, "库存数量不足,数量为"+intStor, "提示信息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//设置数量
				table.setValueAt(num+1, selRow, 3);
				//设置金额
				table.setValueAt(df.format((num+1)*price), selRow, 4);
			}
		});
		btnPlus.setFocusable(false);
		btnPlus.setIcon(new ImageIcon(Rush.class.getResource("/image/chart1.png")));
		btnPlus.setBounds(502, 103, 135, 40);
		contentPane.add(btnPlus);
		
		btnReduce = new JButton("\u6570\u91CF\u51CF\u4E00[F7]");
		btnReduce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取选中的行号
				int selRow=table.getSelectedRow();
				//如果没有选中的时候
				if(selRow==-1){
					JOptionPane.showMessageDialog(Rush.this, "请选中某商品", "提示信息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//获取当前行的数量
				int num= Integer.parseInt(String.valueOf(table.getValueAt(selRow, 3)));
				//获取当前行的单价
				//获取单价
				double price=Double.parseDouble(String.valueOf(table.getValueAt(selRow, 2))); 
				//判断
				if(num-1<0){
					JOptionPane.showMessageDialog(Rush.this, "购买数量不能为负数", "提示信息", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//设置数量
				table.setValueAt(num-1, selRow, 3);
				//设置金额
				table.setValueAt(df.format((num-1)*price), selRow, 4);
			}
		});
		btnReduce.setFocusable(false);
		btnReduce.setIcon(new ImageIcon(Rush.class.getResource("/image/chart2.png")));
		btnReduce.setBounds(502, 163, 135, 40);
		contentPane.add(btnReduce);
		
		btnUpdateNum = new JButton("\u4FEE\u6539\u6570\u91CF[F8]");
		btnUpdateNum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取选中的行号
				int selRow=table.getSelectedRow();
				//如果没有选中的时候
				if(selRow==-1){
					JOptionPane.showMessageDialog(Rush.this, "请选中某商品", "提示信息", JOptionPane.QUESTION_MESSAGE);
					return;
				}
				//弹出输入对话框,准备接收购买数量
				String num=JOptionPane.showInputDialog(null, "请输入购买数量","输入", JOptionPane.INFORMATION_MESSAGE);
				//本次的购买数量
				int intNum=Integer.parseInt(num);
				//获取该商品的库存
				int intStor=Integer.parseInt(String.valueOf(table.getValueAt(selRow, 5)));
				//判断购买数量大于库存数量时候
				if (intNum>intStor) {
					JOptionPane.showMessageDialog(null, "库存数量不足,数量为"+intStor, "提示信息", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//设置购买数量
				table.setValueAt(num, selRow, 3);
				//获取单价
				double price=Double.parseDouble(String.valueOf(table.getValueAt(selRow, 2)));
				//设置金额
				table.setValueAt(df.format(price*Integer.parseInt(num)), selRow, 4);
			}
		});
		btnUpdateNum.setFocusable(false);
		btnUpdateNum.setIcon(new ImageIcon(Rush.class.getResource("/image/chart3.png")));
		btnUpdateNum.setBounds(502, 229, 135, 40);
		contentPane.add(btnUpdateNum);
		
		JLabel lblNewLabel = new JLabel("\u91D1\u989D\u603B\u8BA1\uFF1A");
		lblNewLabel.setBounds(134, 279, 79, 15);
		contentPane.add(lblNewLabel);
		
		lblSum = new JLabel("New label");
		lblSum.setBounds(223, 279, 54, 15);
		contentPane.add(lblSum);
		
		lblInfo = new JLabel("New label");
		lblInfo.setBounds(20, 314, 445, 15);
		contentPane.add(lblInfo);
		
		table = new JTable();
		table.setFocusable(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u6761\u7801", "\u540D\u79F0", "\u5355\u4EF7", "\u6570\u91CF", "\u91D1\u989D", "num"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(5).setPreferredWidth(0);
		table.getColumnModel().getColumn(5).setMinWidth(0);
		table.getColumnModel().getColumn(5).setMaxWidth(0);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 43, 475, 226);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(table);
	}
	//grid长出一行,根据条码获取单价,名称;
	public void gridAddRow(String shpID){
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//根据条码获取单价,名称
		Shop shp=shpDao.getShpNmPrc(shpID);
		Vector<Object> v= new Vector<Object>();
		//条码
		v.add(shpID);
		//名称
		v.add(shp.getShopName());
		//售价
		v.add(shp.getSellPrice());
		//数量
		v.add(0);
		//金额
		v.add(0);
		//数量
		v.add(shp.getNum());
		//添加该行
		dtm.addRow(v);
	}
	//遍历table,获取本次的金额
	public double getSum(){
		//声明返回变量
		double rtn=0;
		//获取table的modle属性
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//设置总共的行数
		int rows=dtm.getRowCount();

		//遍历
		for (int i = 0; i < rows; i++) {
			//获取该商品的金额
			rtn+=Double.parseDouble(String.valueOf(dtm.getValueAt(i, 4)));
		}
		//返回 
		return rtn;
	}
	//遍历table,更新每种物品的库存
	public void updateStorage(){
		//获取table的modle属性
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//设置总共的行数
		int rows=dtm.getRowCount();
		//遍历
		for (int i = 0; i < rows; i++) {
			//获取当前的条码
			String shpID=String.valueOf(dtm.getValueAt(i, 0));
			//获取本次的购买数量
			int num=Integer.parseInt(String.valueOf(dtm.getValueAt(i, 3)));
			//更新该商品的库存
			shpDao.updateNum(shpID, num);
		}
	}
	//该商品是否被添加过
	public boolean ifAddInGrid(String shpID){
		//声明返回变量
		boolean flag=false;//false:没有添加过;true:添加过
		//获取table的modle属性
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//设置总共的行数
		int rows=dtm.getRowCount();
		//遍历
		for (int i = 0; i < rows; i++) {
			//获取当前的条码
			String strID=String.valueOf(dtm.getValueAt(i, 0));
			//判断
			if (strID.equals(shpID)) {
				flag=true;
				break;
			}
		}
		//返回
		return flag;
	}
}
