package com.shubhamgroup.import_excel.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shubhamgroup.import_excel.ui.SubInputPanel2.BrowseListner;

public class SubInputPanel2 extends JPanel 
{
	public InputTabbedPanel tabPanel;
	
	public SubInputPanel2_1 expectedBuyPanel;
	public SubInputPanel2_1 vendorBuyPanel;
	public SubInputPanel2_1 expectedSalePanel;
	public SubInputPanel2_1 vendorSalePanel;
	
	public JTabbedPane tabbedPane;
	
	public SubInputPanel2()
	{
		this.setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane();
		
		expectedBuyPanel = new SubInputPanel2_1(SubInputPanel2_1.Type.ExpectedBuy);
		vendorBuyPanel = new SubInputPanel2_1(SubInputPanel2_1.Type.VendorBuy);
		expectedSalePanel = new SubInputPanel2_1(SubInputPanel2_1.Type.ExpectedSale);
		vendorSalePanel = new SubInputPanel2_1(SubInputPanel2_1.Type.VendorSale);
		
		tabbedPane.addTab("BBTVoice Target", expectedBuyPanel);
		tabbedPane.addTab("Vendor Top Rutes", vendorBuyPanel);
		tabbedPane.addTab("BBTVoice Best Offer", expectedSalePanel);
		tabbedPane.addTab("Customer Target", vendorSalePanel);
		
		this.add(tabbedPane, BorderLayout.CENTER);
		
		
		
	}
	
	public SubInputPanel2(String name)
	{
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWeights = new double[]{1.0};
		gbl_inputPanel.rowWeights = new double[]{0.0, 1.0};
		this.setLayout(gbl_inputPanel);
		
		JPanel subInputPanel = new JPanel();
		GridBagConstraints gbc_subInputPanel = new GridBagConstraints();
		gbc_subInputPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_subInputPanel.insets = new Insets(5, 5, 5, 5);
		gbc_subInputPanel.gridx = 0;
		gbc_subInputPanel.gridy = 0;
		this.add(subInputPanel, gbc_subInputPanel);
		
		GridBagLayout gbl_subInputPanel = new GridBagLayout();
		gbl_subInputPanel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_subInputPanel.rowWeights = new double[]{0.0 , 0.0};
		subInputPanel.setLayout(gbl_subInputPanel);
		
		JLabel selflabel = new JLabel("SelfFile: ");
		GridBagConstraints gbc_selflabel = new GridBagConstraints();
		gbc_selflabel.anchor = GridBagConstraints.WEST;
		gbc_selflabel.gridx = 0;
		gbc_selflabel.gridy = 0;
		subInputPanel.add(selflabel, gbc_selflabel);
		
		
		JTextField selfTextField = new JTextField();
		selfTextField.setPreferredSize(new Dimension(200, 20));
		
		GridBagConstraints gbc_selfTextField = new GridBagConstraints();
		//gbc_selfTextField.fill = GridBagConstraints.BOTH;
		//gbc_selfTextField.weightx = 0;
		gbc_selfTextField.gridx = 1;
		gbc_selfTextField.gridy = 0;
		subInputPanel.add(selfTextField, gbc_selfTextField);
		
		JButton selfBrowseBtn = new JButton("Browse");
		
		GridBagConstraints gbc_selfBrowseBtn = new GridBagConstraints();
		//gbc_selfBrowseBtn.fill = GridBagConstraints.BOTH;
		gbc_selfBrowseBtn.gridx = 2;
		gbc_selfBrowseBtn.gridy = 0;
		subInputPanel.add(selfBrowseBtn, gbc_selfBrowseBtn);
		
		
		JLabel otherlabel = new JLabel("OtherFile: ");
		GridBagConstraints gbc_otherlabel = new GridBagConstraints();
		gbc_otherlabel.anchor = GridBagConstraints.WEST;
		gbc_otherlabel.fill = GridBagConstraints.VERTICAL;
		gbc_otherlabel.gridx = 0;
		gbc_otherlabel.gridy = 1;
		subInputPanel.add(otherlabel, gbc_otherlabel);
		
		
		JTextField otherTextField = new JTextField();
		otherTextField.setPreferredSize(new Dimension(200, 20));
		
		GridBagConstraints gbc_otherTextField = new GridBagConstraints();
		//gbc_otherTextField.fill = GridBagConstraints.BOTH;
		//gbc_otherTextField.weightx = 0;
		gbc_otherTextField.gridx = 1;
		gbc_otherTextField.gridy = 1;
		subInputPanel.add(otherTextField, gbc_otherTextField);
		
		JButton otherBrowseBtn = new JButton("Browse");
		
		GridBagConstraints gbc_otherBrowseBtn = new GridBagConstraints();
		//gbc_otherBrowseBtn.fill = GridBagConstraints.BOTH;
		gbc_otherBrowseBtn.gridx = 2;
		gbc_otherBrowseBtn.gridy = 1;
		subInputPanel.add(otherBrowseBtn, gbc_otherBrowseBtn);
		
		
		tabPanel = new InputTabbedPanel();
		
		
		GridBagConstraints gbc_subPanel2 = new GridBagConstraints();
		gbc_subPanel2.fill = GridBagConstraints.BOTH;
		gbc_subPanel2.insets = new Insets(5, 5, 5, 5);
		//gbc_otherBrowseBtn.fill = GridBagConstraints.BOTH;
		gbc_subPanel2.gridx = 0;
		gbc_subPanel2.gridx = 0;
		gbc_subPanel2.gridy = 1;
		this.add(tabPanel, gbc_subPanel2);
		
		BrowseListner browseListner1 = new BrowseListner(selfTextField, tabPanel, BrowseListner.Type.SELF);
		browseListner1.start();
		
		BrowseListner browseListner2 = new BrowseListner(otherTextField, tabPanel, BrowseListner.Type.OTHER);
		browseListner2.start();
		
		
	}
	
	
	
	
	public static class BrowseListner extends Thread
	{
		private JTextField textField;
		private InputTabbedPanel inputTabbedPanel;
		private Type type ;
		
		private String filePath;
		
		public enum Type {SELF, OTHER};
		
		public BrowseListner(JTextField textField, InputTabbedPanel inputTabbedPanel, Type type)
		{
			this.textField = textField;
			this.inputTabbedPanel = inputTabbedPanel;
			this.type = type;
		}
		
		
		@Override
		public void run() 
		{
			while(true)
			{
				String text = textField.getText();
				
				if(text == null || !text.matches(".*\\.xls(x)?"))
				{
					sleepThread(100);
					continue;
				}
				
				File file = new File(text.trim());
				
				if(!file.exists())
				{
					sleepThread(100);
					continue;
				}
				
				if(filePath != null && filePath.equals(text.trim()))
				{
					sleepThread(100);
					continue;
				}
				
				filePath = text.trim();
				
				inputTabbedPanel.importFile(filePath, type);
				
			}

		}
		
		private void sleepThread(long val)
		{
			try 
			{
				Thread.sleep(val);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
//	public static class BrowseActionListner extends
}


class InputTabbedPanel extends JTabbedPane
{
	private JScrollPane selfScrollPane;
	
	private List<JScrollPane> otherScrollPane = new ArrayList<JScrollPane>();
	
	public InputTabbedPanel()
	{
		
	}
	
	public void importFile(String filePath, BrowseListner.Type type)
	{
		try
		{
			Workbook workbook = null;

			InputStream iStream = new FileInputStream(filePath);

			if(filePath.endsWith(".xls"))
			{
				workbook = new HSSFWorkbook(iStream);				
			}
			else
			{
				workbook = new XSSFWorkbook(iStream);
			}
			
			List<Entry<String, JTable>> list = new ArrayList<Entry<String, JTable>>();
			
			for(Sheet sheet : workbook)
			{
				Entry<String, JTable> entry = new AbstractMap.SimpleEntry<String, JTable>(sheet.getSheetName(), getTable(sheet));
				list.add(entry);
			}
			
			if(type.equals(BrowseListner.Type.SELF))
			{
				if(selfScrollPane != null)
				{
					this.remove(selfScrollPane);
					selfScrollPane = null;
				}
				
				if(list.size() == 0)
				{
					return;
				}
				else
				{
					JScrollPane scrollPane = new JScrollPane(list.get(0).getValue(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					

			        ImageIcon bbtIcon = new ImageIcon(this.getClass().getResource("/com/shubhamgroup/import_excel/resources/icon.png"));
					//scrollPane.set
					
					//Icon icon = new I
					
					insertTab(list.get(0).getKey(), bbtIcon, scrollPane, null, 0);
					//setBackgroundAt(0, Color.blue);
					//setBackgroundAt(0, Color.red);
					
					selfScrollPane = scrollPane;
				}
			}
			else
			{
				if(otherScrollPane != null && otherScrollPane.size() > 0)
				{
					for(JScrollPane sPane : otherScrollPane)
					{
						this.remove(sPane);
					}
				}
				
				otherScrollPane.clear();
				
				for(Entry<String, JTable> entry : list)
				{
					JScrollPane scrollPane = new JScrollPane(entry.getValue(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					addTab(entry.getKey(), scrollPane);
					otherScrollPane.add(scrollPane);
				}
			}
			
			workbook.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	public JTable getTable(Sheet sheet)
	{
		//new String[][]{{"xyz","pqr"},{"xyz","pqr"}}, new String[]{"abc", "pqr"});
		//System.out.println("SheetName: "+sheet.getSheetName());
		
		int rowCount = -1;
		
		List<Object[]> listOfList = new ArrayList<Object[]>();
		
		Map<Integer, String> headers = null;
		

		for(Row row : sheet)
		{
			
			List<String> list = new ArrayList<String>();
			
			boolean hasVal = false;
			
			int cellCount = -1;
			
			for(Cell cell : row)
			{
				cellCount++;
				
				if(headers != null && !headers.containsKey(cellCount))
				{
					continue;
				}
				//cell.getCellStyle().get
				String val = getValue(cell);
				
				if(!hasVal && !val.trim().isEmpty()) hasVal = true;
				
				list.add(val);				
				
			}
			
			if(hasVal)
			{
				rowCount++;
				
				if(headers == null)
				{					
					Map<Integer, String> tempHeaders = new HashMap<Integer, String>();
					
					for(int i=0; i < list.size() ; i++)
					{
						String str = list.get(i).toString();
						
						if(!str.trim().isEmpty())
						{
							tempHeaders.put(i, str);
						}
						else if(tempHeaders.size() > 0)
						{
							break;
						}
					}
					
					if(tempHeaders.size() > 2)
					{
						headers = tempHeaders;
					}
				}
				else
				{
					listOfList.add(list.toArray());
				}
				
			}
		}
		
		
		Object[][] arrayList = listOfList.toArray(new Object[][]{});
		
		
		
		JTable table = new JTable(){
			@Override
		       public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		           Component component = super.prepareRenderer(renderer, row, column);
		           //component.setBackground(Color.red);
		           int rendererWidth = component.getPreferredSize().width;
		           TableColumn tableColumn = getColumnModel().getColumn(column);
		           tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
		           return component;
		        }
		};
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		DefaultTableModel dtm = new DefaultTableModel(arrayList, headers.values().toArray());
		table.setModel(dtm);
		
		
		
		return table;
	}
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	private String getValue(Cell cell)
	{
		//System.out.println("ref: "+Cell.CELL_TYPE_BOOLEAN+ " "+Cell.CELL_TYPE_FORMULA+" "+Cell.CELL_TYPE_NUMERIC+" "+Cell.CELL_TYPE_STRING);
		
		int cellType = cell.getCellType();
		
		//System.out.println("act: "+cellType);
		
		
		switch (cellType)
		{
			case Cell.CELL_TYPE_BOOLEAN : return cell.getBooleanCellValue() + "";
			case Cell.CELL_TYPE_FORMULA : return cell.getCellFormula();
			case Cell.CELL_TYPE_NUMERIC :
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					Date date = cell.getDateCellValue();
					return dateFormat.format(date);
				}
				else
				{
					return cell.getNumericCellValue() + "";
				
				}
			case Cell.CELL_TYPE_STRING : return cell.getStringCellValue();
		}
		
		return "";
	}
	
	
}
