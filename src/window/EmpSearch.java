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
	//����Ψһʵ��
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
				//�������ݸ�ԭ
				fillTable("");
				//��ղ�ѯ����
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
				//sql��where����
				String sql=" WHERE 1=1 ";
				//��ȡ����
				String strID=txtID.getText().trim();
				//�ж��Ƿ�����
				if (strID.length()>0) {
					sql+=" AND empID='"+strID+"' ";
				}
				//����
				//��ȡ����
				String strName=txtName.getText().trim();
				if (strName.length()>0) {
					sql+=" AND empName LIKE'%"+strName+"%' ";
				}
				//�Ա�
				if (rdoMale.isSelected()||rdoFemale.isSelected()) {
					if (rdoMale.isSelected()) {
						sql+=" AND empSex=1 ";
					} else {
						sql+=" AND empSex=0 ";
					}
				}
				//Ȩ��
				if (rdoManager.isSelected()||rdoRush.isSelected()) {
					if (rdoManager.isSelected()) {
						sql+=" AND empPower=1 ";
					} else {
						sql+=" AND empPower=2 ";
					}
				}
				//�绰
				String strPhone=txtPhone.getText().trim();
				if(strPhone.length()>0){
					sql+=" AND empPhone LIKE '%"+strPhone+"%' ";
				}
				//��ѯ
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
		//����table������
		fillTable("");
	}
	//��table��������
	public void fillTable(String where){
		//��ȡtable��modle
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//���������
		dtm.setRowCount(0);
		//��ȡ���е�Ա����Ϣ
		Vector<Vector<Object>> vV=empDao.getEmpList(where);
		//���,����,ת�����Ա�,�ֻ���,��ַ,ת����Ȩ��,ת����״̬ 
		//����, getint
		for (int i = 0; i < vV.size(); i++) {
			//���
			dtm.addRow(vV.get(i));
		}
	}
	//��ղ�ѯ����
	public void clearSearchArea(){
		txtID.setText("");
		txtName.setText("");
		txtPhone.setText("");
		//��յ�ѡŤ
		bgSex.clearSelection();
		bgPower.clearSelection();
	}
	public static void main(String[] args) {
		new EmpSearch().setVisible(true);
	}
}
