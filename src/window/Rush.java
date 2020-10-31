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
	//������Ա�յ�Ǯ
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
	//����
	DecimalFormat df= new DecimalFormat("#.00");
	/**
	 * Create the frame.
	 */
	private Rush() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				//�������Ʒ
				case KeyEvent.VK_F1:
					btnAdd.doClick();
					break;
			    //����
				case KeyEvent.VK_F2:
					btnAccount.doClick();
					break;
				//��λ
				case KeyEvent.VK_F3:
					btnCancel.doClick();
					break;
				//�˳�ϵͳ	
				case KeyEvent.VK_F4:
					btnExit.doClick();
					break;
				//���	
				case KeyEvent.VK_F5:
					btnDel.doClick();
					break;
				//��һ	
				case KeyEvent.VK_F6:
					btnPlus.doClick();
					break;
				//��һ	
				case KeyEvent.VK_F7:
					btnReduce.doClick();
					break;
				//�޸�����	
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
				//��������Ի���
				String strShpID=JOptionPane.showInputDialog(Rush.this, "����������:", "����",JOptionPane.QUESTION_MESSAGE);
				if(strShpID!=null&&strShpID.length()>0){
					//�ж������Ƿ����
					if (shpDao.ifShpIDExists(strShpID)) {
						//�Ƿ���������ӹ�
						if(ifAddInGrid(strShpID)){
							JOptionPane.showMessageDialog(null, "����Ʒ�Ѿ�����ӹ�", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
							return ;							
						}
						gridAddRow(strShpID);
						//ѡ�и���ӵ���Ʒ
						table.setRowSelectionInterval(0, 0);
					} else {
						JOptionPane.showMessageDialog(null, "����Ʒ������", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
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
		 *  //1.���ҵ��Ľ��
			//2.��ȡ��ǰ���ݿ�����
			//3.����ǰ�����Ƿ����
			//3.1 ���û�еĻ�����Insert
			//3.2 ����еĻ�����update
			//4.���ٿ��
			//5.��ӡ"����ܼ�"
			//6.��ȡ��ǰ���ս��
			//7.��ӡʱ���
			//7.1��ӡʱ��
			//7.2��ӡ����Ա
			//7.3��ӡ���ս��
		 */
		btnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�洢����ҵ��Ľ��
				double curMoney=0;
				//��ȡ���ν��
				curMoney=getSum();
				//�ۼ�
				curRushMoney+=curMoney;
				//��ȡ���ݿ�ĵ�ǰ����
				String curDBDay=dmDao.getDBDate();
				//����ʵ�����
				DayMoney dm= new DayMoney();
				dm.setDay(curDBDay);
				dm.setMoney(curMoney);
				//�ж����Ƿ����
				if (dmDao.ifDayMoneyexists(curDBDay)) {
					//����
					dmDao.updateDyMny(dm);
				} else {
					//����
					dmDao.insrtDyMny(dm);
				}
				//���ٿ��
				updateStorage();
				//��ӡ���ν��
				lblSum.setText(String.valueOf(curMoney));
				//��������
				Date date= new Date();
				//����
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy��MM��dd�� E");
				//��ʽ��
				String strDate=sdf.format(date);
				//��ӡ
				lblInfo.setText("����:"+strDate+"   ����Ա:"+LoginInfo.empID+"   ��ǰ���ս��:"+curRushMoney);
				//����"����"��ť������
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
				//��ȡtable��modle
				DefaultTableModel dtm= (DefaultTableModel)table.getModel(); 
				//������е���
				dtm.setRowCount(0);
				//����"����"��ť����
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
				//����ȷ�϶Ի���
				int rtn= JOptionPane.showConfirmDialog(Rush.this, "��ȷ��Ҫ�˳���?", "��ʾ��Ϣ",  JOptionPane.YES_NO_OPTION);
				//���������"yes"
				if (rtn==JOptionPane.YES_OPTION) {
					//����->����
					empDao.setLoginOff(LoginInfo.empID);
					//�˳�
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
				//��ȡѡ�е��к�
				int selRow=table.getSelectedRow();
				//��ȡtable��modle
				DefaultTableModel dtm= (DefaultTableModel)table.getModel();
				//ɾ��ָ����
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
				//��ȡѡ�е��к�
				int selRow=table.getSelectedRow();
				//���û��ѡ�е�ʱ��
				if(selRow==-1){
					JOptionPane.showMessageDialog(Rush.this, "��ѡ��ĳ��Ʒ", "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//��ȡ��ǰ�е�����
				int num= Integer.parseInt(String.valueOf(table.getValueAt(selRow, 3)));
				//��ȡ��ǰ�еĵ���
				double price=Double.parseDouble(String.valueOf(table.getValueAt(selRow, 2))); 
				//��ȡ����Ʒ�Ŀ��
				int intStor=Integer.parseInt(String.valueOf(table.getValueAt(selRow, 5)));
				//�жϹ����������ڿ������ʱ��
				if (num+1>intStor) {
					JOptionPane.showMessageDialog(null, "�����������,����Ϊ"+intStor, "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//��������
				table.setValueAt(num+1, selRow, 3);
				//���ý��
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
				//��ȡѡ�е��к�
				int selRow=table.getSelectedRow();
				//���û��ѡ�е�ʱ��
				if(selRow==-1){
					JOptionPane.showMessageDialog(Rush.this, "��ѡ��ĳ��Ʒ", "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//��ȡ��ǰ�е�����
				int num= Integer.parseInt(String.valueOf(table.getValueAt(selRow, 3)));
				//��ȡ��ǰ�еĵ���
				//��ȡ����
				double price=Double.parseDouble(String.valueOf(table.getValueAt(selRow, 2))); 
				//�ж�
				if(num-1<0){
					JOptionPane.showMessageDialog(Rush.this, "������������Ϊ����", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//��������
				table.setValueAt(num-1, selRow, 3);
				//���ý��
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
				//��ȡѡ�е��к�
				int selRow=table.getSelectedRow();
				//���û��ѡ�е�ʱ��
				if(selRow==-1){
					JOptionPane.showMessageDialog(Rush.this, "��ѡ��ĳ��Ʒ", "��ʾ��Ϣ", JOptionPane.QUESTION_MESSAGE);
					return;
				}
				//��������Ի���,׼�����չ�������
				String num=JOptionPane.showInputDialog(null, "�����빺������","����", JOptionPane.INFORMATION_MESSAGE);
				//���εĹ�������
				int intNum=Integer.parseInt(num);
				//��ȡ����Ʒ�Ŀ��
				int intStor=Integer.parseInt(String.valueOf(table.getValueAt(selRow, 5)));
				//�жϹ����������ڿ������ʱ��
				if (intNum>intStor) {
					JOptionPane.showMessageDialog(null, "�����������,����Ϊ"+intStor, "��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//���ù�������
				table.setValueAt(num, selRow, 3);
				//��ȡ����
				double price=Double.parseDouble(String.valueOf(table.getValueAt(selRow, 2)));
				//���ý��
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
	//grid����һ��,���������ȡ����,����;
	public void gridAddRow(String shpID){
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//���������ȡ����,����
		Shop shp=shpDao.getShpNmPrc(shpID);
		Vector<Object> v= new Vector<Object>();
		//����
		v.add(shpID);
		//����
		v.add(shp.getShopName());
		//�ۼ�
		v.add(shp.getSellPrice());
		//����
		v.add(0);
		//���
		v.add(0);
		//����
		v.add(shp.getNum());
		//��Ӹ���
		dtm.addRow(v);
	}
	//����table,��ȡ���εĽ��
	public double getSum(){
		//�������ر���
		double rtn=0;
		//��ȡtable��modle����
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//�����ܹ�������
		int rows=dtm.getRowCount();

		//����
		for (int i = 0; i < rows; i++) {
			//��ȡ����Ʒ�Ľ��
			rtn+=Double.parseDouble(String.valueOf(dtm.getValueAt(i, 4)));
		}
		//���� 
		return rtn;
	}
	//����table,����ÿ����Ʒ�Ŀ��
	public void updateStorage(){
		//��ȡtable��modle����
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//�����ܹ�������
		int rows=dtm.getRowCount();
		//����
		for (int i = 0; i < rows; i++) {
			//��ȡ��ǰ������
			String shpID=String.valueOf(dtm.getValueAt(i, 0));
			//��ȡ���εĹ�������
			int num=Integer.parseInt(String.valueOf(dtm.getValueAt(i, 3)));
			//���¸���Ʒ�Ŀ��
			shpDao.updateNum(shpID, num);
		}
	}
	//����Ʒ�Ƿ���ӹ�
	public boolean ifAddInGrid(String shpID){
		//�������ر���
		boolean flag=false;//false:û����ӹ�;true:��ӹ�
		//��ȡtable��modle����
		DefaultTableModel dtm= (DefaultTableModel)table.getModel();
		//�����ܹ�������
		int rows=dtm.getRowCount();
		//����
		for (int i = 0; i < rows; i++) {
			//��ȡ��ǰ������
			String strID=String.valueOf(dtm.getValueAt(i, 0));
			//�ж�
			if (strID.equals(shpID)) {
				flag=true;
				break;
			}
		}
		//����
		return flag;
	}
}
