package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.LoginInfo;
import com.MyDefaultTableModele;

import dao.EmpDao;
import entity.Emp;

import java.awt.Color;

public class EmpUpdate extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtPhone;
	private JTextField txtAddresss;
	private JTextField txtName;
	private JTextField txtAge;
	private JRadioButton rdoMale;
	private JRadioButton rdoFemale;
	private JRadioButton rdoRush;
	private JRadioButton rdoManager;
	private JTable table;
	private EmpDao empDao= new EmpDao();
	private static EmpUpdate eu= new EmpUpdate();
	//����Ψһʵ��
	public static EmpUpdate getInstance(){
		return eu;
	}

	/**
	 * Create the frame.
	 */
	private EmpUpdate() {
		setTitle("\u5458\u5DE5\u7EF4\u62A4\u4FE1\u606F");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 465, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u7F16\u53F7\uFF1A");
		lblNewLabel.setBounds(24, 10, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u6027\u522B\uFF1A");
		lblNewLabel_1.setBounds(24, 43, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u7535\u8BDD\uFF1A");
		lblNewLabel_2.setBounds(24, 71, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u5730\u5740\uFF1A");
		lblNewLabel_3.setBounds(24, 98, 54, 15);
		contentPane.add(lblNewLabel_3);
		
		txtID = new JTextField();
		txtID.setBackground(Color.PINK);
		txtID.setEnabled(false);
		txtID.setBounds(72, 7, 66, 21);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		rdoMale = new JRadioButton("\u7537");
		rdoMale.setBounds(72, 39, 47, 23);
		contentPane.add(rdoMale);
		
		rdoFemale = new JRadioButton("\u5973");
		rdoFemale.setBounds(121, 39, 66, 23);
		contentPane.add(rdoFemale);
		ButtonGroup bgSex = new ButtonGroup();
		bgSex.add(rdoMale);
		bgSex.add(rdoFemale);
		
		txtPhone = new JTextField();
		txtPhone.setBackground(Color.PINK);
		txtPhone.setBounds(72, 68, 130, 21);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);
		
		txtAddresss = new JTextField();
		txtAddresss.setBounds(72, 96, 322, 21);
		contentPane.add(txtAddresss);
		txtAddresss.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("\u59D3\u540D\uFF1A");
		lblNewLabel_4.setBounds(234, 10, 54, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\u5E74\u9F84\uFF1A");
		lblNewLabel_5.setBounds(234, 43, 54, 15);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("\u6743\u9650\uFF1A");
		lblNewLabel_6.setBounds(234, 71, 54, 15);
		contentPane.add(lblNewLabel_6);
		
		txtName = new JTextField();
		txtName.setBackground(Color.PINK);
		txtName.setBounds(289, 7, 105, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtAge = new JTextField();
		txtAge.setBounds(289, 40, 66, 21);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		rdoRush = new JRadioButton("\u6536\u94F6\u5458");
		rdoRush.setBounds(285, 67, 61, 23);
		contentPane.add(rdoRush);
		
		rdoManager = new JRadioButton("\u7BA1\u7406\u5458");
		rdoManager.setBounds(348, 67, 88, 23);
		contentPane.add(rdoManager);
		ButtonGroup bgPower = new ButtonGroup();
		bgPower.add(rdoManager);
		bgPower.add(rdoRush);
		
		JButton btnPWDReset = new JButton("\u5BC6\u7801\u590D\u4F4D");
		btnPWDReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��ȡ��ǰ�Ĺ���
				String strID=txtID.getText().trim();
				//�����Ի���
				int rtn=JOptionPane.showConfirmDialog(EmpUpdate.this, "��ȷ��Ҫ����������?", "��ʾ��Ϣ",  JOptionPane.YES_NO_OPTION);
				if (rtn==JOptionPane.YES_OPTION) {
					if (empDao.empPWDReset(strID)>0) {
						JOptionPane.showMessageDialog(EmpUpdate.this, "�������óɹ�", "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		btnPWDReset.setBounds(24, 123, 85, 23);
		contentPane.add(btnPWDReset);
		
		JButton btnUpdate = new JButton("\u4FEE\u6539");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//����һ��ʵ�������
				Emp emp= new Emp();
				//��ȡ����
				String strID=txtID.getText();
				//���ù���
				emp.setEmpID(strID);
				//��������Ϊ��
				String strName=txtName.getText().trim();
				if (strName.length()>0) {
					emp.setEmpName(strName);
				} else {
					JOptionPane.showMessageDialog(EmpUpdate.this, "��������Ϊ��!", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//�Ա�
				if (rdoMale.isSelected()) {
					emp.setEmpSex(1);
				} else {
					emp.setEmpSex(0);
				}
				//����
				if (txtAge.getText().trim().length()>0) {
					emp.setEmpAge(Integer.parseInt(txtAge.getText().trim()));
				}
				//�ֻ���
				//��ȡ
				String strPhone=txtPhone.getText().trim();
				if (strPhone.length()>0) {
					if (strPhone.length()==11) {
						emp.setEmpPhone(strPhone);
					} else {
						JOptionPane.showMessageDialog(null, "�ֻ��ű���Ϊ11λ!", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
						return;	
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "�ֻ��Ų���Ϊ��!", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
					return;		
				}
				//��ַ
				emp.setEmpAddress(txtAddresss.getText().trim());
				//Ȩ��
				if (rdoManager.isSelected()) {
					emp.setEmpPower(1);
				} else {
					emp.setEmpPower(2);
				}
				//�޸�
				if (empDao.empUpdate(emp)>0) {
					JOptionPane.showMessageDialog(null, "�޸ĳɹ�!", "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				}
				//ˢ�±��
				fillTbl("");
			}
		});
		btnUpdate.setBounds(129, 123, 85, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDel = new JButton("\u5220\u9664");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��ȡҪɾ�Ĺ���
				String strEmpID =txtID.getText().trim();
				//��ǰ�û�������ɾ��
				if(strEmpID.equals(LoginInfo.empID)){
					JOptionPane.showMessageDialog(EmpUpdate.this, "��ǰ�û�������ɾ��!", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//�����Ի�����ɾ��
				int rtn=JOptionPane.showConfirmDialog(EmpUpdate.this, "��ȷ��Ҫɾ����?", "��ʾ��Ϣ",  JOptionPane.YES_NO_OPTION);
				//�ж��Ƿ񵥻�"yes"
				if (rtn==JOptionPane.YES_OPTION) {
					if(empDao.empDel(strEmpID)>0){
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�!", "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					}
					//ˢ��table
					fillTbl("");
					//Ĭ��ѡ��table��һ��
					table.setRowSelectionInterval(0, 0);
					//����һ�е�ֵ����ȥ
					setValue();
				}
			}
		});
		btnDel.setBounds(234, 123, 85, 23);
		contentPane.add(btnDel);
		
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(338, 123, 85, 23);
		contentPane.add(btnCancel);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setValue();
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 156, 452, 402);
		contentPane.add(scrollPane);
		table.setModel(new MyDefaultTableModele(
				new Object[][] {
						{null, null, null, null, null, null, null, null,null},
						{null, null, null, null, null, null, null, null,null},
						{null, null, null, null, null, null, null, null,null},
					},
				new String[] {
					"\u7F16\u53F7", "\u59D3\u540D", "\u6027\u522B", "\u5E74\u9F84", "\u624B\u673A\u53F7", "\u5730\u5740", "\u6743\u9650", "sex", "power"
				}
			));
		table.getColumnModel().getColumn(7).setPreferredWidth(0);
		table.getColumnModel().getColumn(7).setMinWidth(0);
		table.getColumnModel().getColumn(7).setMaxWidth(0);
		table.getColumnModel().getColumn(8).setPreferredWidth(0);
		table.getColumnModel().getColumn(8).setMinWidth(0);
		table.getColumnModel().getColumn(8).setMaxWidth(0);
		table.setBounds(0, 0, 1, 1);
		//����fillTbl,��table�ṩ����
		fillTbl("");
	}
	//���table��modle
	public void fillTbl(String where){
		//��ȡtable��modle
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//������е���
		dtm.setRowCount(0);
		//��ȡ���е�Ա����Ϣ
		Vector<Vector<Object>> vV=empDao.getEmpList(where);
		//���,����,ת�����Ա�,�ֻ���,��ַ,ת����Ȩ�� getString
		//����,�Ա�,Ȩ��
		//����vV
		for (int i = 0; i < vV.size(); i++) {
			//���
			dtm.addRow(vV.get(i));
		}
	}
	//�༭��������
	public void setValue(){
		//��ȡ��ǰ�е��к�
		int curRow=table.getSelectedRow();
		//����
		txtID.setText(String.valueOf(table.getValueAt(curRow, 0)));
		//����
		txtName.setText(String.valueOf(table.getValueAt(curRow, 1)));
		//�Ա�
		String sex=String.valueOf(table.getValueAt(curRow, 7));
		if (sex.equals("true")) {
			rdoMale.setSelected(true);
		} else {
			rdoFemale.setSelected(true);
		}
		//����
		txtAge.setText(String.valueOf(table.getValueAt(curRow, 3)));
		//�绰
		txtPhone.setText(String.valueOf(table.getValueAt(curRow, 4)));
		//��ַ
		txtAddresss.setText(String.valueOf(table.getValueAt(curRow, 5)));
		//Ȩ��
		String power =String.valueOf(table.getValueAt(curRow, 8));
		if (power.equals("1")) {
			rdoManager.setSelected(true);
		} else {
			rdoRush.setSelected(true);
		}
	}
	public static void main(String[] args) {
		new EmpUpdate().setVisible(true);
	}
}
