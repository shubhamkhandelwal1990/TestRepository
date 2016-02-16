package com.shubhamgroup.import_excel.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import com.shubhamgroup.import_excel.core.SynonymsInfo;
import com.shubhamgroup.import_excel.core.TableUnitData;

public class InputPanel extends JPanel 
{
	public SubInputPanel1 subInputPanel1;
	public SubInputPanel2 subInputPanel2;
	public OutputPanel outputPanel;
	
	public InputPanel()
	{
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWeights = new double[]{1.0};
		gbl_inputPanel.rowWeights = new double[]{0.0, 1.0};
		this.setLayout(gbl_inputPanel);
		
		subInputPanel1 = new SubInputPanel1();
		
		GridBagConstraints gbc_subInputPanel1 = new GridBagConstraints();
		gbc_subInputPanel1.anchor = GridBagConstraints.NORTHWEST;
		gbc_subInputPanel1.gridx = 0;
		gbc_subInputPanel1.gridy = 0;
		this.add(subInputPanel1, gbc_subInputPanel1);
		
		
		subInputPanel2 = new SubInputPanel2();
		
		GridBagConstraints gbc_subInputPanel2 = new GridBagConstraints();
		gbc_subInputPanel2.fill = GridBagConstraints.BOTH;
		gbc_subInputPanel2.gridx = 0;
		gbc_subInputPanel2.gridy = 1;
		this.add(subInputPanel2, gbc_subInputPanel2);		
		
		ComputeModeChange computeModeChange = new ComputeModeChange(this);
		subInputPanel1.trafficIn.addActionListener(computeModeChange);
		subInputPanel1.trafficOut.addActionListener(computeModeChange);
		
		subInputPanel1.computeBtn.addActionListener(new ComputeActionListener(this));
	}
	
	public void setOutputPanel(OutputPanel outputPanel)
	{
		this.outputPanel = outputPanel;
	}
	
}

class ComputeActionListener implements ActionListener
{
	private InputPanel inputPanel; 
	
	public ComputeActionListener(InputPanel inputPanel)
	{
		this.inputPanel = inputPanel;		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(inputPanel.outputPanel == null || (!inputPanel.subInputPanel1.trafficIn.isSelected() && !inputPanel.subInputPanel1.trafficOut.isSelected()))
		{
			return;
		}
		
		Entry<String, JTable> refTable = null;
		List<Entry<String, JTable>> otherTables = null;
		String refProductName = null;
		String otherProductName = null;
		boolean trafficOut = false;
		
		if(inputPanel.subInputPanel1.trafficOut.isSelected())
		{
			trafficOut = true;
			
			List<Entry<String, JTable>> tables = inputPanel.subInputPanel2.expectedBuyPanel.getAllTables();
			
			if(tables != null && tables.size() > 0)
			{
				refProductName  = SynonymsInfo.getProductNameFromFilePath(inputPanel.subInputPanel2.expectedBuyPanel.uploadBox.getText());
				
				refTable = tables.get(0);
			}
			
			tables = inputPanel.subInputPanel2.vendorBuyPanel.getAllTables();
			
			if(tables != null && tables.size() > 0)
			{
				otherProductName  = SynonymsInfo.getProductNameFromFilePath(inputPanel.subInputPanel2.vendorBuyPanel.uploadBox.getText());
				
				otherTables = tables;
			}
		}
		else
		{			
			List<Entry<String, JTable>> tables = inputPanel.subInputPanel2.expectedSalePanel.getAllTables();
			
			System.out.println("tables1: "+tables.size());
			
			if(tables != null && tables.size() > 0)
			{
				refProductName  = SynonymsInfo.getProductNameFromFilePath(inputPanel.subInputPanel2.expectedSalePanel.uploadBox.getText());
				refTable = tables.get(0);
			}
			
			tables = inputPanel.subInputPanel2.vendorSalePanel.getAllTables();
			
			System.out.println("tables2: "+tables.size());
			
			if(tables != null && tables.size() > 0)
			{
				otherProductName  = SynonymsInfo.getProductNameFromFilePath(inputPanel.subInputPanel2.vendorSalePanel.uploadBox.getText());
				otherTables = tables;
			}
		}
		
		if(refTable != null && otherTables != null)
		{		
			System.out.println("refProductName: "+refProductName);
			System.out.println("otherProductName: "+otherProductName);
			
			List<TableUnitData> outUnitDataList = getOutputData(refTable, otherTables, refProductName, otherProductName, 2, trafficOut);
			
			inputPanel.outputPanel.setOutput(outUnitDataList, trafficOut);
			
			System.out.println("==============Start Data=====================");
			for(TableUnitData outUnitData : outUnitDataList)
			{
				System.out.println(outUnitData);
			}
			System.out.println("===============End Data======================");
		}
		else
		{
			inputPanel.outputPanel.clearOutput();
			System.out.println("No output");
		}
		
	}
	
	private TableUnitData getOldTableUnitData(TableUnitData tableUnitData, List<TableUnitData> outDataList)
	{
		for(TableUnitData unitData : outDataList)
		{
			if(unitData.equalsWithVendorOrCustomer(tableUnitData))
			{
				return unitData;
			}
		}
		
		return null;
	}
	
	private List<TableUnitData> getOutputData(Entry<String, JTable> refTable, List<Entry<String, JTable>> otherTables, String refProductName, String otherProductName, double percentDiff, boolean trafficOut)
	{
		List<TableUnitData> refDataList = TableUnitData.fromTable(refTable.getValue(), "BBTVoice", refProductName);
		
		System.out.println("refDataList: "+refDataList);
		
		List<TableUnitData> outDataList = new ArrayList<TableUnitData>();
		
		
		for(Entry<String, JTable> otherTable : otherTables)
		{
			List<TableUnitData> otherDataList = TableUnitData.fromTable(otherTable.getValue(),otherTable.getKey(), otherProductName);
			
			System.out.println("otherDataList: "+otherDataList);
			
			for(TableUnitData otherTableUnitData : otherDataList)
			{
				for(TableUnitData refTableUnitData : refDataList)
				{
					if(otherTableUnitData.equals(refTableUnitData))
					{
						TableUnitData oldUnitData = getOldTableUnitData(otherTableUnitData, outDataList);
						
						if(oldUnitData != null)
						{
							if(oldUnitData.price > otherTableUnitData.price )
							{
								outDataList.remove(oldUnitData);
							}
							else
							{
								continue;
							}
						}
						
						
						int result = refTableUnitData.compare(otherTableUnitData, percentDiff, trafficOut);
						
						if(result == 0)
						{
							otherTableUnitData.type = TableUnitData.Type.Allowed;
							outDataList.add(otherTableUnitData);
						}
						else if(result == 1)
						{
							otherTableUnitData.type = TableUnitData.Type.Negosiable;
							outDataList.add(otherTableUnitData);
						}
						
					}
				}
			}
			
		}
		
		return outDataList;
	}
	
	
	
	
	
	
	
	
	
	
}

class ComputeModeChange implements ActionListener
{
	private InputPanel inputPanel; 
	
	public ComputeModeChange(InputPanel inputPanel)
	{
		this.inputPanel = inputPanel;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (inputPanel.subInputPanel2.tabbedPane == null || inputPanel.subInputPanel2.tabbedPane.getTabCount() < 4)
		{
			return;
		}
		
		String text = ((JRadioButton) e.getSource()).getText();
		
		System.out.println("text-"+text);
		
		if(text != null && text.equalsIgnoreCase("traffic in"))
		{
			System.out.println("Traffice in ");
			inputPanel.subInputPanel2.tabbedPane.setEnabledAt(0, false);
			inputPanel.subInputPanel2.tabbedPane.setEnabledAt(1, false);
			inputPanel.subInputPanel2.tabbedPane.setEnabledAt(2, true);
			inputPanel.subInputPanel2.tabbedPane.setEnabledAt(3, true);
			
			inputPanel.subInputPanel2.tabbedPane.setSelectedIndex(2);
		}
		else if(text != null && text.equalsIgnoreCase("traffic out"))
		{
			System.out.println("Traffice out ");
			inputPanel.subInputPanel2.tabbedPane.setEnabledAt(0, true);
			inputPanel.subInputPanel2.tabbedPane.setEnabledAt(1, true);
			inputPanel.subInputPanel2.tabbedPane.setEnabledAt(2, false);
			inputPanel.subInputPanel2.tabbedPane.setEnabledAt(3, false);			
			
			inputPanel.subInputPanel2.tabbedPane.setSelectedIndex(0);
		}
		
	}
	
}


