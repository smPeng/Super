package window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.LoginInfo;
import dao.EmpDao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PWDUpdate extends JDialog {
	private JTextField txtOld;
	private JTextField txtNew;
	private JTextField txtConfirm;
	private EmpDao empDao = new EmpDao();
	private static PWDUpdate up= new PWDUpdate();
	public static PWDUpdate getInstance(){
		return up;
	}
	/**
	 * Create the dialog.
	 */
	private PWDUpdate() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("\u4FEE\u6539\u5BC6\u7801");
		setBounds(100, 100, 307, 207);
		getContentPane().setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("\u539F\u5BC6\u7801\uFF1A");
			lblNewLabel.setBounds(34, 29, 54, 15);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
			lblNewLabel_1.setBounds(34, 58, 54, 15);
			getContentPane().add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
			lblNewLabel_2.setBounds(34, 91, 69, 15);
			getContentPane().add(lblNewLabel_2);
		}
		{
			txtOld = new JTextField();
			txtOld.setBounds(109, 26, 150, 21);
			getContentPane().add(txtOld);
			txtOld.setColumns(10);
		}
		{
			txtNew = new JTextField();
			txtNew.setBounds(109, 55, 150, 21);
			getContentPane().add(txtNew);
			txtNew.setColumns(10);
		}
		{
			txtConfirm = new JTextField();
			txtConfirm.setBounds(109, 88, 150, 21);
			getContentPane().add(txtConfirm);
			txtConfirm.setColumns(10);
		}
		{
			JButton btnUpdate = new JButton("\u4FEE\u6539");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//��ȡԭ����
					String strOld =txtOld.getText().trim();
					//��ȡ������
					String strNew =txtNew.getText().trim();
					//��ȡȷ������
					String strConfirm =txtConfirm.getText().trim();
					//ԭ���logininfo��һ��
					//������ĳ��ȱ���Ϊ6
					//ȷ�������������һ��
					if (!strOld.equals(LoginInfo.empPWD)) {
						JOptionPane.showMessageDialog(PWDUpdate.this, "ԭ�������!", "����", JOptionPane.ERROR_MESSAGE);
						return;
					} else{
						if(strNew.length()!=6){
							JOptionPane.showMessageDialog(PWDUpdate.this, "���볤�ȱ���Ϊ6λ!", "����", JOptionPane.ERROR_MESSAGE);
							return;
						}
						else{
							//�ж�ȷ�������������һ��
							if (!strNew.equals(strConfirm)) {
								JOptionPane.showMessageDialog(null, "ȷ������������벻һ��!", "����", JOptionPane.ERROR_MESSAGE);
								return;
							}
							else{
								//�������
								if(empDao.updatePWD(strConfirm)>0){
									JOptionPane.showMessageDialog(null, "�����޸ĳɹ�!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
									//����->����
									empDao.setLoginOff(LoginInfo.empID);
									System.exit(0);
								}
								
							}
						}
					}
					
				}
			});
			btnUpdate.setBounds(34, 128, 93, 23);
			getContentPane().add(btnUpdate);
		}
		{
			JButton btnCancel = new JButton("\u53D6\u6D88");
			btnCancel.setBounds(159, 128, 93, 23);
			getContentPane().add(btnCancel);
		}
	}

}
