package com;

import java.io.Serializable;

import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModele extends DefaultTableModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1218810638177359397L;

	//沿用父类的构造方法
	public MyDefaultTableModele(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		// TODO Auto-generated constructor stub
	}
	
	//可设置该单元格是否编辑
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}
}
