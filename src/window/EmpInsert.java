package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.EmpDao;
import entity.Emp;


public class EmpInsert extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JPasswordField txtPWD;
	private JTextField txtAge;
	private JTextField txtPhone;
	private JTextField txtAddress;
	private JRadioButton rdoRush;
	private JRadioButton rdoManager;
	private JRadioButton rdoMale;
	private JRadioButton rdoFemale;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JTextField txtID;
    private EmpDao empDao= new EmpDao();
    private static EmpInsert ei=new EmpInsert();	
/*    //����һ��ʵ��
    public static EmpInsert getInstance(){
    	return ei;
    }*/
	/**
	 * Create the frame.
	 */
	public EmpInsert() {
		setTitle("\u65B0\u589E\u5458\u5DE5");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(20, 34, 352, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u59D3\u540D\uFF1A");
		lblNewLabel.setBounds(52, 49, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
		lblNewLabel_1.setBounds(52, 85, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u6027\u522B\uFF1A");
		lblNewLabel_2.setBounds(52, 119, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u5E74\u9F84\uFF1A");
		lblNewLabel_3.setBounds(52, 157, 54, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u7535\u8BDD\uFF1A");
		lblNewLabel_4.setBounds(52, 191, 54, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\u5730\u5740\uFF1A");
		lblNewLabel_5.setBounds(52, 229, 54, 15);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("\u6743\u9650\uFF1A");
		lblNewLabel_6.setBounds(52, 265, 54, 15);
		contentPane.add(lblNewLabel_6);
		
		txtName = new JTextField();
		txtName.setBackground(Color.PINK);
		txtName.setBounds(113, 46, 121, 21);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtPWD = new JPasswordField();
		txtPWD.setBounds(113, 82, 121, 21);
		contentPane.add(txtPWD);
		
		rdoMale = new JRadioButton("\u7537");
		rdoMale.setSelected(true);
		rdoMale.setBounds(112, 115, 62, 23);
		contentPane.add(rdoMale);
		
		rdoFemale = new JRadioButton("\u5973");
		rdoFemale.setBounds(191, 115, 54, 23);
		contentPane.add(rdoFemale);
		ButtonGroup bgsex =new ButtonGroup();
		bgsex.add(rdoMale);
		bgsex.add(rdoFemale);
		
		txtAge = new JTextField();
		txtAge.setBounds(116, 157, 118, 21);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setBackground(Color.PINK);
		txtPhone.setBounds(116, 188, 118, 21);
		contentPane.add(txtPhone);
		txtPhone.setColumns(10);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(116, 226, 218, 18);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		rdoRush = new JRadioButton("\u6536\u94F6\u5458");
		rdoRush.setSelected(true);
		rdoRush.setBounds(112, 261, 62, 23);
		contentPane.add(rdoRush);
		
		rdoManager = new JRadioButton("\u7BA1\u7406\u5458");
		rdoManager.setBounds(191, 261, 121, 23);
		contentPane.add(rdoManager);
		ButtonGroup bgPower =new ButtonGroup();
		bgPower.add(rdoRush);
		bgPower.add(rdoManager);
		
		JButton btnInsert = new JButton("\u5F55\u5165");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��Ҫ����check����:
				//����Ƿ��ظ�
				//��ų��ȱ���Ϊ5λ
				//��Ų���Ϊ�գ�
				//��������Ϊ�գ�
				//���볤�ȱ���Ϊ6λ
				//�ֻ��ű���Ϊ11λ
				//�ֻ��Ų���Ϊ��
				//����Emp����
				Emp emp= new Emp();
				//��ȡ����
				String strID=txtID.getText();
				if (strID.length()>0) {
					//�жϹ����Ƿ��ظ�
					if (empDao.ifEmpIDRepeat(strID)) {
						JOptionPane.showMessageDialog(EmpInsert.this, "�����ظ���!", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
					} else {
						emp.setEmpID(strID);
					}
				} else {
					JOptionPane.showMessageDialog(EmpInsert.this, "���Ų���Ϊ��!", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//��������Ϊ��
				String strName=txtName.getText().trim();
				if (strName.length()>0) {
					emp.setEmpName(strName);
				} else {
					JOptionPane.showMessageDialog(null, "��������Ϊ��!", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//����
				//��ȡ����
				String strPWD=new String(txtPWD.getPassword());
				if (strPWD.length()>0) {
					if (strPWD.length()==6) {
						emp.setEmpPWD(strPWD);
					}else{
						JOptionPane.showMessageDialog(null, "�������Ϊ6λ!", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
						return;						
					}
				} else {
					emp.setEmpPWD("000000");
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
				emp.setEmpAddress(txtAddress.getText().trim());
				//Ȩ��
				if (rdoManager.isSelected()) {
					emp.setEmpPower(1);
				} else {
					emp.setEmpPower(2);
				}
				//����
				if (empDao.empInsert(emp)>0) {
					JOptionPane.showMessageDialog(null, "����ɹ�!", "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnInsert.setBounds(52, 294, 93, 23);
		contentPane.add(btnInsert);
		
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�رյ�ǰ����,���ͷ���Դ
				dispose();
			}
		});
		btnCancel.setBounds(177, 294, 93, 23);
		contentPane.add(btnCancel);
		
		lblNewLabel_7 = new JLabel("\u9ED8\u8BA4\"000000\"");
		lblNewLabel_7.setForeground(Color.RED);
		lblNewLabel_7.setBounds(258, 85, 76, 15);
		contentPane.add(lblNewLabel_7);
		
		lblNewLabel_8 = new JLabel("\u7F16\u53F7\uFF1A");
		lblNewLabel_8.setBounds(52, 10, 54, 15);
		contentPane.add(lblNewLabel_8);
		
		txtID = new JTextField();
		txtID.setBackground(Color.PINK);
		txtID.setBounds(113, 7, 121, 21);
		contentPane.add(txtID);
		txtID.setColumns(10);
	}
	public static void main(String[] args) {
		new EmpInsert().setVisible(true);;
	}
}