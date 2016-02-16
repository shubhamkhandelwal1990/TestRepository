package com.shubhamgroup.import_excel.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.shubhamgroup.import_excel.core.TableUnitData;

public class OutputPanel extends JPanel 
{
	
	private JTextField searchBox;
	
	public JPanel subOutPanel ;
	
	public OutputPanel()
	{
		GridBagLayout gbl_outputPanel = new GridBagLayout();
		gbl_outputPanel.columnWeights = new double[]{1.0};
		gbl_outputPanel.rowWeights = new double[]{0.0, 1.0};
		this.setLayout(gbl_outputPanel);
		
		
		JPanel searchPanel = new JPanel();
		
		
		GridBagConstraints gbc_searchPanel = new GridBagConstraints();
		gbc_searchPanel.anchor = GridBagConstraints.WEST;
		gbc_searchPanel.insets = new Insets(5, 5, 5, 5);
		gbc_searchPanel.gridx = 0;
		gbc_searchPanel.gridy = 0;
		
		this.add(searchPanel, gbc_searchPanel);
		
		GridBagLayout gbl_searchPanel = new GridBagLayout();
		gbl_searchPanel.columnWeights = new double[]{0.0, 1.0};
		gbl_searchPanel.rowWeights = new double[]{1.0};
		searchPanel.setLayout(gbl_searchPanel);
		
		
		
		JLabel searchLabel = new JLabel("Search:");
		GridBagConstraints gbc_searchLabel = new GridBagConstraints();
		gbc_searchLabel.insets = new Insets(5, 5, 5, 5);
		gbc_searchLabel.anchor = GridBagConstraints.WEST;
		gbc_searchLabel.gridx = 0;
		gbc_searchLabel.gridy = 0;
		searchPanel.add(searchLabel, gbc_searchLabel);
		
		searchBox = new JTextField();
		searchBox.setPreferredSize(new Dimension(200, 20));
		GridBagConstraints gbc_searchBox = new GridBagConstraints();
		gbc_searchBox.anchor = GridBagConstraints.WEST;
		gbc_searchBox.gridx = 1;
		gbc_searchBox.gridy = 0;
		searchPanel.add(searchBox, gbc_searchBox);
		
		subOutPanel = new JPanel();
		
		GridBagConstraints gbc_subOutPanel = new GridBagConstraints();
		gbc_subOutPanel.fill = GridBagConstraints.BOTH;
		gbc_subOutPanel.insets = new Insets(5, 5, 5, 5);
		gbc_subOutPanel.gridx = 0;
		gbc_subOutPanel.gridy = 1;
		this.add(subOutPanel, gbc_subOutPanel);
		
		subOutPanel.setLayout(new BorderLayout());
		
		
		
		String COMMIT_ACTION = "commit";

		searchBox.setFocusTraversalKeysEnabled(false);
		// Our words to complete
		List<String> keywords = new ArrayList<String>(5);
		        keywords.add("example");
		        keywords.add("autocomplete");
		        keywords.add("stackabuse");
		        keywords.add("java");
		Autocomplete autoComplete = new Autocomplete(searchBox, keywords);
		searchBox.getDocument().addDocumentListener(autoComplete);

		// Maps the tab key to the commit action, which finishes the autocomplete
		// when given a suggestion
		searchBox.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		searchBox.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());
		
		
	}
	
	public void clearOutput()
	{
		subOutPanel.removeAll();
	}
	
	public void setOutput(List<TableUnitData> list, boolean trafficOut)
	{
		if(list == null || list.size() == 0)
		{
			clearOutput();
			
			return;
		}
		
		Object[] headers = { trafficOut ? "Vendor": "Customer", "Destination", "Product", "Price"};
		
		Object[][] data = new Object[list.size()][4];
		
		final List<Integer> negosiableList = new ArrayList<Integer>();
		
		for(int i=0; i < list.size(); i++)
		{
			TableUnitData unitData = list.get(i);
			data[i][0] = unitData.vendorOrCustomer;
			data[i][1] = unitData.destination;
			data[i][2] = unitData.product; 
			data[i][3] = unitData.price; 
			
			if(unitData.type.equals(TableUnitData.Type.Negosiable))
			negosiableList.add(i);
		}

		System.out.println("negosiableList: "+negosiableList);

		JTable table = new OutTable(negosiableList);
		table.setDefaultRenderer(Object.class, new OutRenderer(negosiableList));
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		DefaultTableModel dtm = new DefaultTableModel(data, headers);
		table.setModel(dtm);
		
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		subOutPanel.removeAll();
		subOutPanel.add(scrollPane, BorderLayout.CENTER);
		subOutPanel.revalidate();
	}
	
	class OutRenderer extends DefaultTableCellRenderer
	{
		private List<Integer> negosiableList;
		
		public OutRenderer(List<Integer> negosiableList)
		{
			this.negosiableList = negosiableList;
			
		}
		
		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
	        final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        
	        if(isSelected)
	        {
	        	return component;
	        }
	        
	        if(negosiableList.contains(row))
			{
				component.setBackground(Color.yellow);
				component.setForeground(Color.black);
			}
			else
			{
				component.setBackground(Color.white);
				component.setForeground(Color.black);
			}

	        
	        return component;
	    }
	}
	
	class OutTable extends JTable
	{
		private List<Integer> negosiableList;
		
		public OutTable(List<Integer> negosiableList)
		{
			this.negosiableList = negosiableList;
			
			
		}

		@Override
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			Component component = super.prepareRenderer(renderer, row, column);
			//component.setBackground(Color.red);
			int rendererWidth = component.getPreferredSize().width;
			TableColumn tableColumn = getColumnModel().getColumn(column);
			tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
			return component;
		}
	}
	
}
