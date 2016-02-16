package com.shubhamgroup.import_excel.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
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


public class SubInputPanel2_1 extends JPanel 
{
	public JTextField uploadBox;
	
	public JPanel diplayPanel;
	
	public Type type ;
	
	public enum Type {ExpectedBuy, ExpectedSale, VendorSale, VendorBuy};
	
	private List<Entry<String, JTable>> tables;
	
	public List<Entry<String, JTable>>  getAllTables()
	{
		return tables;
	}
	
	public List<JTable> getAllTables(Component component)
	{
		if(component == null) component = this;
		
		List<JTable> tables = new ArrayList<JTable>();
		
		Component[] components =  null;
		
		if(component instanceof Container)
		{
			components = ((Container)component).getComponents();
		}		
		
		if(components == null || components.length == 0)
		{
			return tables;
		}
		
		for(Component comp : components)
		{
			System.out.println("componentName: "+comp.getClass().getName());
			
			if(comp != null && comp.getClass().getName().trim().equals("javax.swing.JTable"))
			{
				tables.add((JTable) comp);
			}
			else if(comp != null)
			{
				List<JTable> tempTables = getAllTables(comp);
				tables.addAll(tempTables);
			}
		}
		
		return tables;
		
	}
	
	
	public SubInputPanel2_1(Type type)
	{
		this.type = type;
		
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
		
		JLabel selflabel = new JLabel("UploadFile: ");
		GridBagConstraints gbc_selflabel = new GridBagConstraints();
		gbc_selflabel.anchor = GridBagConstraints.WEST;
		gbc_selflabel.gridx = 0;
		gbc_selflabel.gridy = 0;
		subInputPanel.add(selflabel, gbc_selflabel);
		
		
		uploadBox = new JTextField();
		uploadBox.setPreferredSize(new Dimension(200, 20));
		GridBagConstraints gbc_uploadBox = new GridBagConstraints();
		gbc_uploadBox.gridx = 1;
		gbc_uploadBox.gridy = 0;
		subInputPanel.add(uploadBox, gbc_uploadBox);
		
		JButton browseBtn = new JButton("Browse");
		GridBagConstraints gbc_browseBtn = new GridBagConstraints();
		gbc_browseBtn.gridx = 2;
		gbc_browseBtn.gridy = 0;
		subInputPanel.add(browseBtn, gbc_browseBtn);
		
		diplayPanel = new JPanel();
		GridBagConstraints gbc_diplayPanel = new GridBagConstraints();
		gbc_diplayPanel.fill = GridBagConstraints.BOTH;
		gbc_diplayPanel.insets = new Insets(5, 5, 5, 5);
		gbc_diplayPanel.gridx = 0;
		gbc_diplayPanel.gridy = 1;
		this.add(diplayPanel, gbc_diplayPanel);
		
		BorderLayout diplayPanel_layout = new BorderLayout();
		diplayPanel.setLayout(diplayPanel_layout);
		
		browseBtn.addActionListener(new BrowseActionListener(this));
		
	}
	
	class BrowseActionListener implements ActionListener
	{
		JFileChooser fileChooser;
		
		private SubInputPanel2_1 panel;
		
		public BrowseActionListener(SubInputPanel2_1 panel)
		{
			this.panel = panel;
			fileChooser = new JFileChooser();
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			
			FileFilter filter = new FileFilter() 
			{
				 
			    public String getDescription() 
			    {
			        return "Excel Document (*.xls or xlsx)";
			    }
			 
			    public boolean accept(File file) 
			    {
			        if (file.isDirectory()) 
			        {
			            return true;
			        }
			        else
			        {
			        	String fileName = file.getName().toLowerCase();
			            return fileName.endsWith(".xls") || fileName.endsWith(".xlsx");
			        }
			    }
			};
			
			
		    fileChooser.setFileFilter(filter);
			
			
			int result = fileChooser.showOpenDialog(panel);
			if (result == JFileChooser.APPROVE_OPTION) 
			{
				File file = fileChooser.getSelectedFile();
				fileChooser.setCurrentDirectory(file.getParentFile());
				panel.uploadBox.setText(file.getAbsolutePath());
				
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				try
				{
					JComponent component = getComponent(file.getAbsolutePath());
					panel.diplayPanel.removeAll();
					panel.diplayPanel.add(component, BorderLayout.CENTER);
					
					panel.diplayPanel.revalidate();
					
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(panel, ex, "Error", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}

				panel.setCursor(Cursor.getDefaultCursor());
				
			}
		}
		
	}
	
	
	private JComponent getComponent(String filePath)
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
			
			tables = list;
			
			for(Sheet sheet : workbook)
			{
				JTable table = getTable(sheet);
				if(table!= null)
				{
					Entry<String, JTable> entry = new AbstractMap.SimpleEntry<String, JTable>(sheet.getSheetName(), getTable(sheet));
					list.add(entry);
				}
			}
			
			workbook.close();
			
			if(list.size() == 0)
			{
				return null;
			}
			
			if(list.size() == 1)
			{
				//tables.clear();
				//tables.add(list.get(0).getValue());
				JScrollPane scrollPane = new JScrollPane(list.get(0).getValue(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				return scrollPane;
			}
			
			JTabbedPane tabPane = new JTabbedPane();
			
			//tables.clear();
			
			for(Entry<String, JTable> entry : list)
			{
				//tables.add(entry.getValue());
				JScrollPane scrollPane = new JScrollPane(entry.getValue(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				tabPane.addTab(entry.getKey(), scrollPane);
			}
			
			return tabPane;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public JTable getTable(Sheet sheet)
	{
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
		
		if(headers == null || headers.size() == 0)
		{
			return null;
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
		int cellType = cell.getCellType();
		
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

