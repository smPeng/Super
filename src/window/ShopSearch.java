package window;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.MyDefaultTableModele;

import dao.ShopDao;

public class ShopSearch extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtNum;
	private JTable table;
	private ShopDao shpDao=new ShopDao();

	private JComboBox cboSign;
	private JComboBox cboCategory ;
	private static ShopSearch ss= new ShopSearch();
	public static ShopSearch getInstance(){
		return ss;
	}

	/**
	 * Create the frame.
	 */
	private ShopSearch() {
		setTitle("\u67E5\u770B\u5546\u54C1\u4FE1\u606F");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u6761\u7801\uFF1A");
		lblNewLabel.setBounds(10, 10, 44, 15);
		contentPane.add(lblNewLabel);
		
		txtID = new JTextField();
		txtID.setBounds(64, 7, 120, 21);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u540D\u79F0\uFF1A");
		lblNewLabel_1.setBounds(226, 10, 36, 15);
		contentPane.add(lblNewLabel_1);
		
		txtName = new JTextField();
		txtName.setBounds(272, 7, 142, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u5E93\u5B58\u91CF\uFF1A");
		lblNewLabel_2.setBounds(10, 40, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		txtNum = new JTextField();
		txtNum.setBounds(118, 37, 66, 21);
		contentPane.add(txtNum);
		txtNum.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("\u5206\u7C7B\uFF1A");
		lblNewLabel_3.setBounds(226, 40, 54, 15);
		contentPane.add(lblNewLabel_3);
		
		cboCategory = new JComboBox();
		cboCategory.setModel(new DefaultComboBoxModel(new String[] {"\u5168\u90E8", "\u5305\u88C5\u98DF\u54C1 ", "\u996E\u6599\u70DF\u9152 ", "\u852C\u83DC\u6C34\u679C ", "\u7CAE\u6CB9 ", "\u8089\u7C7B ", "\u65E5\u5E38\u7528\u54C1 ", "\u529E\u516C\u7528\u54C1 ", "\u6D17\u6DA4\u7528\u54C1 ", "\u6563\u88C5\u52A0\u5DE5"}));
		cboCategory.setBounds(272, 38, 102, 21);
		contentPane.add(cboCategory);
		
		JButton btnReset = new JButton("\u590D\u4F4D");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillTbl("");
				clearTxt();
			}
		});
		btnReset.setBounds(220, 65, 70, 23);
		contentPane.add(btnReset);
		
		JButton btnSearch = new JButton("\u67E5\u8BE2");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//拼接Where部分的sql
				String where =" WHERE 1=1 ";
				//条码
				String strID= txtID.getText().trim();
				if(strID.length()>0){
					where+=" AND shopID="+strID;
				}
				//名称
				String strName=txtName.getText().trim();
				if(strName.length()>0){
					where+=" AND shopName like '%"+strName+"%'";
				}
				//取库存量符号位
				String strSign = String.valueOf(cboSign.getItemAt(cboSign.getSelectedIndex()));
				//库存量
				String strNum = txtNum.getText().trim();
				if(strNum.length()>0){
					where+=" AND num "+strSign+strNum;
				}
				//分类
				if(cboCategory.getSelectedIndex()>0){
					String strCategory= String.valueOf(cboCategory.getSelectedIndex());
					where+=" AND category= "+strCategory;
				}
				//查询
				fillTbl(where);
			}
		});
		btnSearch.setBounds(297, 65, 70, 23);
		contentPane.add(btnSearch);
		
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(371, 65, 70, 23);
		contentPane.add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		scrollPane.setBounds(10, 98, 431, 196);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new MyDefaultTableModele(
				new Object[][] {
					{null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null},
				},
				new String[] {
					"\u6761\u7801", "\u540D\u79F0", "\u8FDB\u4EF7", "\u552E\u4EF7", "\u5E93\u5B58\u91CF", "\u5206\u7C7B", "\u5382\u5BB6"
				}
			));
			table.setBounds(23, 150, 391, 170);
		scrollPane.setViewportView(table);
		
		cboSign = new JComboBox();
		cboSign.setModel(new DefaultComboBoxModel(new String[] {">", ">=", "<", "<="}));
		cboSign.setBounds(64, 37, 54, 21);
		contentPane.add(cboSign);
		fillTbl("");
	}
	public void fillTbl(String where){
		DefaultTableModel dtm= (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		Vector<Vector<Object>> vV= shpDao.getShpLstSrch(where);
		//遍历vV
		for (int i = 0; i < vV.size(); i++) {
			//添加
			dtm.addRow(vV.get(i));
		}
	}
	public void clearTxt(){
		txtID.setText("");
		txtName.setText("");
		txtNum.setText("");
		cboSign.setSelectedIndex(0);
		cboCategory.setSelectedIndex(0);
	}
}
