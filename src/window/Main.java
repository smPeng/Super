package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.LoginInfo;

import dao.DayMoneyDao;
import dao.EmpDao;

public class Main extends JFrame {

	private JPanel contentPane;
	private EmpDao empDAO=new EmpDao();
	private JLabel lblRushNum;
	private JLabel lblDayMoney;
	private JLabel lblSrvrStrt;
	private DayMoneyDao dmDao= new DayMoneyDao();
	private static Main m= new Main();
	public static Main getInstance(){
		return m;
	}
	/**
	 * Create the frame.
	 */
	private Main() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//判断是否为"F2"按下
				if (e.getKeyCode()==KeyEvent.VK_F2) {
					//弹出锁屏画面
					Lock l= new Lock(Main.this, true);
					//可视
					l.setVisible(true);
				}
			}
		});
		setTitle("\u540E\u53F0\u7BA1\u7406");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 583, 434);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuService = new JMenu("\u670D\u52A1");
		menuBar.add(menuService);
		
		JMenuItem itemStart = new JMenuItem("\u542F\u52A8\u670D\u52A1");
		itemStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//对所有的收银员开启服务
				empDAO.setRushLoginOn();
				lblSrvrStrt.setText("已启动");
			}
		});
		itemStart.setIcon(new ImageIcon(Main.class.getResource("/image/start.png")));
		menuService.add(itemStart);
		
		JMenuItem itemClose = new JMenuItem("\u5173\u95ED\u670D\u52A1");
		itemClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//对所有的收银员关闭服务
				empDAO.setRushLoginOff();
				lblSrvrStrt.setText("未启动");
			}
		});
		itemClose.setIcon(new ImageIcon(Main.class.getResource("/image/stop.png")));
		menuService.add(itemClose);
		
		JMenuItem itemExit = new JMenuItem("\u9000\u51FA\u7CFB\u7EDF");
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//弹出一个确认对话框
				int rtn=JOptionPane.showConfirmDialog(Main.this, "你确实要退出吗?", "提示信息", JOptionPane.YES_NO_OPTION );
				if(rtn==JOptionPane.YES_OPTION){
					//在线->离线
					empDAO.setLoginOff(LoginInfo.empID);
					System.exit(0);
				}
			}
		});
		itemExit.setIcon(new ImageIcon(Main.class.getResource("/image/exit.png")));
		menuService.add(itemExit);
		
		JMenu menuEmp = new JMenu("\u5458\u5DE5\u7BA1\u7406");
		menuBar.add(menuEmp);
		
		JMenuItem itemAdd = new JMenuItem("\u65B0\u589E\u5458\u5DE5");
		itemAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开员工插入
				new EmpInsert().setVisible(true);
				//EmpInsert.getInstance().setVisible(true);
			}
		});
		itemAdd.setIcon(new ImageIcon(Main.class.getResource("/image/add.png")));
		menuEmp.add(itemAdd);
		
		JMenuItem itemUpdate = new JMenuItem("\u5458\u5DE5\u7EF4\u62A4");
		itemUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开员工维护画面
				//new EmpUpdate().setVisible(true);
				EmpUpdate.getInstance().setVisible(true);
			}
		});
		itemUpdate.setIcon(new ImageIcon(Main.class.getResource("/image/chart3.png")));
		menuEmp.add(itemUpdate);
		
		JMenuItem itemList = new JMenuItem("\u5458\u5DE5\u5217\u8868");
		itemList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开查询画面
				//new EmpSearch().setVisible(true);
				EmpSearch.getInstance().setVisible(true);
			}
		});
		itemList.setIcon(new ImageIcon(Main.class.getResource("/image/employee.png")));
		menuEmp.add(itemList);
		
		JMenu menuShop = new JMenu("\u5546\u54C1\u7BA1\u7406");
		menuBar.add(menuShop);
		
		JMenuItem itemShpAdd = new JMenuItem("\u65B0\u589E\u5546\u54C1");
		itemShpAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开商品插入画面
				//new InsertShop().setVisible(true);
				ShopInsert.getInstance().setVisible(true);
			}
		});
		itemShpAdd.setIcon(new ImageIcon(Main.class.getResource("/image/chart2.png")));
		menuShop.add(itemShpAdd);
		
		JMenuItem itemStock = new JMenuItem("\u7EF4\u62A4\u5E93\u5B58");
		itemStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开维护库存画面
				//new UpdateShop().setVisible(true);
				ShopUpdate.getInstance().setVisible(true);
			}
		});
		itemStock.setIcon(new ImageIcon(Main.class.getResource("/image/data1.png")));
		menuShop.add(itemStock);
		
		JMenuItem itemShpLst = new JMenuItem("\u5546\u54C1\u5217\u8868");
		itemShpLst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开商品列表画面
				//new ShopSelect().setVisible(true);
				ShopSearch.getInstance().setVisible(true);
			}
		});
		itemShpLst.setIcon(new ImageIcon(Main.class.getResource("/image/goods.png")));
		menuShop.add(itemShpLst);
		
		JMenu menuHelp = new JMenu("\u5E2E\u52A9");
		menuBar.add(menuHelp);
		
		JMenuItem itemChgPWD = new JMenuItem("\u4FEE\u6539\u5BC6\u7801");
		itemChgPWD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开密码修改画面
				//new UpdatePWD().setVisible(true);
				PWDUpdate.getInstance().setVisible(true);
			}
		});
		itemChgPWD.setIcon(new ImageIcon(Main.class.getResource("/image/password.png")));
		menuHelp.add(itemChgPWD);
		
		JMenuItem itemSoftInfo = new JMenuItem("\u8F6F\u4EF6\u4FE1\u606F");
		itemSoftInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开帮助画面
/*				Help h= new Help();
				h.setVisible(true);*/
				Help.getInstance().setVisible(true);
			}
		});
		itemSoftInfo.setIcon(new ImageIcon(Main.class.getResource("/image/server_state.png")));
		menuHelp.add(itemSoftInfo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDayMoney = new JLabel("\u00A50\u5143");
		lblDayMoney.setForeground(Color.GREEN);
		lblDayMoney.setBounds(409, 134, 80, 15);
		contentPane.add(lblDayMoney);
		//实时更新当天的营业额
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					lblDayMoney.setText(String.valueOf(dmDao.getCurDayMoney(dmDao.getDBDate())));
					//休眠
					try {
						sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		lblRushNum = new JLabel("0\u4EBA");
		lblRushNum.setForeground(Color.GREEN);
		lblRushNum.setBounds(409, 98, 67, 15);
		contentPane.add(lblRushNum);
		//创建一个线程,实时跟新在线收银员人数
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					int rushNum=empDAO.getRushNum();
					//跟新
					lblRushNum.setText(String.valueOf(rushNum));
					//线程休眠5s
					try {
						sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		lblSrvrStrt = new JLabel("\u672A\u542F\u52A8");
		lblSrvrStrt.setForeground(Color.GREEN);
		lblSrvrStrt.setBounds(409, 59, 67, 15);
		contentPane.add(lblSrvrStrt);
		
		JLabel lblNewLabel_3 = new JLabel("\u5F53\u5929\u8425\u4E1A\u989D\uFF1A");
		lblNewLabel_3.setIcon(new ImageIcon(Main.class.getResource("/image/money.png")));
		lblNewLabel_3.setForeground(Color.YELLOW);
		lblNewLabel_3.setBounds(296, 130, 103, 22);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("\u6536\u94F6\u5458\u4EBA\u6570\uFF1A");
		lblNewLabel_2.setIcon(new ImageIcon(Main.class.getResource("/image/employee.png")));
		lblNewLabel_2.setForeground(Color.YELLOW);
		lblNewLabel_2.setBounds(296, 91, 103, 29);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("\u670D\u52A1\u5668\u72B6\u6001\uFF1A");
		lblNewLabel_1.setIcon(new ImageIcon(Main.class.getResource("/image/server_state.png")));
		lblNewLabel_1.setForeground(Color.YELLOW);
		lblNewLabel_1.setBounds(296, 52, 103, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Main.class.getResource("/image/back.jpg")));
		lblNewLabel.setBounds(0, 10, 577, 362);
		contentPane.add(lblNewLabel);
		//判断如果有一个收银员的权限开通的话,服务器的状态为"已启动"
		if(empDAO.getRshLgnOnNm()>0){
			lblSrvrStrt.setText("已启动");
		}
	}
}
