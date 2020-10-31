package window;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.MyDefaultTableModele;

import dao.EmpDao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmpSearch extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTable table;
	private EmpDao empDao= new EmpDao();
	private JRadioButton rdoMale;
	private JRadioButton rdoFemale;
	private JRadioButton rdoRush;
	private JRadioButton rdoManager;
	private ButtonGroup bgSex;
	private ButtonGroup bgPower;
	private static EmpSearch es= new EmpSearch();
	//返回唯一实例
	public static EmpSearch getInstance(){
		return es;
	}
	/**
	 * Create the frame.
	 */
	private EmpSearch() {
		setTitle("\u67E5\u770B\u5458\u5DE5\u4FE1\u606F");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 483, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u7F16\u53F7\uFF1A");
		lblNewLabel.setBounds(22, 10, 41, 15);
		contentPane.add(lblNewLabel);
		
		txtID = new JTextField();
		txtID.setBounds(63, 7, 88, 21);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u59D3\u540D\uFF1A");
		lblNewLabel_1.setBounds(217, 10, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		txtName = new JTextField();
		txtName.setBounds(266, 7, 112, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u6027\u522B\uFF1A");
		lblNewLabel_2.setBounds(22, 35, 41, 15);
		contentPane.add(lblNewLabel_2);
		
		JButton btnReset = new JButton("\u590D\u4F4D");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//网格数据复原
				fillTable("");
				//清空查询区域
				clearSearchArea();
			}
		});
		btnReset.setBounds(217, 60, 69, 23);
		contentPane.add(btnReset);
		
		rdoMale = new JRadioButton("\u7537");
		rdoMale.setBounds(63, 31, 41, 23);
		contentPane.add(rdoMale);
		
		rdoFemale = new JRadioButton("\u5973");
		rdoFemale.setBounds(106, 31, 45, 23);
		contentPane.add(rdoFemale);
		bgSex = new ButtonGroup();
		bgSex.add(rdoMale);
		bgSex.add(rdoFemale);
		
		JLabel lblNewLabel_3 = new JLabel("\u6743\u9650\uFF1A");
		lblNewLabel_3.setBounds(217, 35, 54, 15);
		contentPane.add(lblNewLabel_3);
		
		rdoRush = new JRadioButton("\u6536\u94F6\u5458");
		rdoRush.setBounds(266, 31, 69, 23);
		contentPane.add(rdoRush);
		
		rdoManager = new JRadioButton("\u7BA1\u7406\u5458");
		rdoManager.setBounds(337, 31, 75, 23);
		contentPane.add(rdoManager);
		bgPower = new ButtonGroup();
		bgPower.add(rdoManager);
		bgPower.add(rdoRush);
		
		JLabel lblNewLabel_4 = new JLabel("\u7535\u8BDD\uFF1A");
		lblNewLabel_4.setBounds(22, 59, 54, 15);
		contentPane.add(lblNewLabel_4);
		
		txtPhone = new JTextField();
		txtPhone.setBounds(63, 60, 88, 21);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);
		
		JButton btnSearch = new JButton("\u67E5\u8BE2");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//sql的where部分
				String sql=" WHERE 1=1 ";
				//获取工号
				String strID=txtID.getText().trim();
				//判断是否输入
				if (strID.length()>0) {
					sql+=" AND empID='"+strID+"' ";
				}
				//姓名
				//获取姓名
				String strName=txtName.getText().trim();
				if (strName.length()>0) {
					sql+=" AND empName LIKE'%"+strName+"%' ";
				}
				//性别
				if (rdoMale.isSelected()||rdoFemale.isSelected()) {
					if (rdoMale.isSelected()) {
						sql+=" AND empSex=1 ";
					} else {
						sql+=" AND empSex=0 ";
					}
				}
				//权限
				if (rdoManager.isSelected()||rdoRush.isSelected()) {
					if (rdoManager.isSelected()) {
						sql+=" AND empPower=1 ";
					} else {
						sql+=" AND empPower=2 ";
					}
				}
				//电话
				String strPhone=txtPhone.getText().trim();
				if(strPhone.length()>0){
					sql+=" AND empPhone LIKE '%"+strPhone+"%' ";
				}
				//查询
				fillTable(sql);
			}
		});
		btnSearch.setBounds(287, 60, 69, 23);
		contentPane.add(btnSearch);
		
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(363, 60, 69, 23);
		contentPane.add(btnCancel);
		
		table = new JTable();
		table.setModel(new MyDefaultTableModele(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
	 		},
			new String[] {
				"\u7F16\u53F7", "\u59D3\u540D", "\u6027\u522B", "\u5E74\u9F84", "\u624B\u673A\u53F7", "\u5730\u5740", "\u6743\u9650", "\u72B6\u6001"
			}
		));
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 93, 455, 402);
		contentPane.add(scrollPane);
		//加载table的数据
		fillTable("");
	}
	//给table加载数据
	public void fillTable(String where){
		//获取table的modle
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//清空所有行
		dtm.setRowCount(0);
		//获取所有的员工信息
		Vector<Vector<Object>> vV=empDao.getEmpList(where);
		//编号,姓名,转换后性别,手机号,地址,转换后权限,转换后状态 
		//年龄, getint
		for (int i = 0; i < vV.size(); i++) {
			//添加
			dtm.addRow(vV.get(i));
		}
	}
	//清空查询区域
	public void clearSearchArea(){
		txtID.setText("");
		txtName.setText("");
		txtPhone.setText("");
		//清空单选扭
		bgSex.clearSelection();
		bgPower.clearSelection();
	}
	public static void main(String[] args) {
		new EmpSearch().setVisible(true);
	}
}
