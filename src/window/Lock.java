package window;

import java.awt.Color;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;



import com.LoginInfo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Lock extends JDialog {
	private JPasswordField txtPWD;



	



	/**
	 * Create the dialog.
	 */
	public Lock(Frame owner, boolean modal) {
		super(owner, modal);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("\u89E3\u9501");
		setBounds(100, 100, 277, 160);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html><p>\u7A97\u53E3\u5DF2\u9501\u5B9A\uFF0C\u5728\u6B64\u8F93\u5165\u5BC6\u7801\u89E3\u9501\u3002</p>\r\n<p>\u89E3\u9501\u5BC6\u7801\u8DDF\u767B\u5F55\u5BC6\u7801\u4E00\u81F4\u3002</p></html>");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(24, 10, 218, 42);
		getContentPane().add(lblNewLabel);
		
		txtPWD = new JPasswordField();
		txtPWD.setBounds(24, 62, 218, 21);
		getContentPane().add(txtPWD);
		
		JButton btnUnlock = new JButton("\u89E3\u9501");
		btnUnlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取解锁密码
				String keyPWD=new String(txtPWD.getPassword());
				//比较密码
				if (keyPWD.equals(LoginInfo.empPWD)) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(Lock.this, "解锁密码有误！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnUnlock.setBounds(64, 93, 93, 23);
		getContentPane().add(btnUnlock);
	}
}
