package window;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



import com.LoginInfo;

import dao.EmpDao;

public class Login extends JFrame {

	private JPanel contentPane;
	//编号
	private JTextField txtID;
	//密码
	private JPasswordField txtPWD;
	//收银员
	private JRadioButton rdoRush;
	//管理者
	private JRadioButton rdoManager;
	
	private EmpDao empDao= new EmpDao();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("\u7528\u6237\u767B\u5F55");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/image/login_client_icon.png")));
		lblNewLabel.setBounds(20, 24, 135, 149);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u7F16\u53F7\uFF1A");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(199, 24, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u5BC6\u7801\uFF1A");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(199, 67, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		txtID = new JTextField();
		txtID.setBounds(260, 21, 117, 30);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtPWD = new JPasswordField();
		txtPWD.setBounds(260, 60, 117, 30);
		contentPane.add(txtPWD);
		
		rdoRush = new JRadioButton("\u6536\u94F6\u5458");
		rdoRush.setSelected(true);
		rdoRush.setBounds(194, 102, 77, 23);
		contentPane.add(rdoRush);
		
		rdoManager = new JRadioButton("\u7BA1\u7406\u5458");
		rdoManager.setBounds(290, 102, 108, 23);
		contentPane.add(rdoManager);
		ButtonGroup bgPower = new ButtonGroup();
		bgPower.add(rdoRush);
		bgPower.add(rdoManager);
		
		JButton btnLogin = new JButton("\u767B\u5F55");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//验证当前用户跟密码是否OK
				//当前用户ID
				String  strID=txtID.getText().trim();
				//当前用户的密码
				String strPWD=new String(txtPWD.getPassword());
				//验证收银员
				if (rdoRush.isSelected()) {
					//验证收银员
					if (empDao.ifRushExistLoginOn(strID, strPWD)) {
						JOptionPane.showMessageDialog(Login.this, "账号或者密码有误/初次登陆，未开通服务", "登陆", JOptionPane.ERROR_MESSAGE);
					} else {
						//打开收银画面
/*						Rush r= new Rush();
						r.setVisible(true);*/
						Rush.geInstance().setVisible(true);
					}
				 //验证管理员	
				} else {
					//验证管理员
					if (empDao.ifManagerExistLoginOn(strID, strPWD)) {
						JOptionPane.showMessageDialog(null, "账号或者密码有误", "登陆失败", JOptionPane.ERROR_MESSAGE);
					} else {
						//打开main画面
/*						Main main= new Main();
						main.setVisible(true);*/
						Main.getInstance().setVisible(true);
					}
				}
				//存储当前用户信息
				LoginInfo.empID=strID;
				LoginInfo.empPWD=strPWD;
				//离线->在线
				empDao.setLoginOn(strID);
			}
		});
		btnLogin.setBounds(165, 150, 93, 23);
		contentPane.add(btnLogin);
		
		JButton btnExit = new JButton("\u9000\u51FA");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//退出系统
				System.exit(0);
			}
		});
		btnExit.setBounds(292, 150, 93, 23);
		contentPane.add(btnExit);
	}
}
