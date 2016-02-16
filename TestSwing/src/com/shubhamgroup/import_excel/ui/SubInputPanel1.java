package com.shubhamgroup.import_excel.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SubInputPanel1 extends JPanel 
{
	public JButton computeBtn;
	public JRadioButton trafficIn;
	public JRadioButton trafficOut;
	
	public SubInputPanel1()
	{
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_inputPanel.rowWeights = new double[]{0.0};
		this.setLayout(gbl_inputPanel);
		
		
		
		trafficOut = new JRadioButton("Traffic Out");
		GridBagConstraints gbc_trafficOut = new GridBagConstraints();
		gbc_trafficOut.anchor = GridBagConstraints.NORTHWEST;
		gbc_trafficOut.insets = new Insets(5, 5, 5, 5);
		gbc_trafficOut.gridx = 0;
		gbc_trafficOut.gridy = 0;
		this.add(trafficOut, gbc_trafficOut);
		
		
		trafficIn = new JRadioButton("Traffic In");
		GridBagConstraints gbc_trafficIn = new GridBagConstraints();
		gbc_trafficIn.anchor = GridBagConstraints.NORTHWEST;
		gbc_trafficIn.insets = new Insets(5, 5, 5, 5);
		gbc_trafficIn.gridx = 1;
		gbc_trafficIn.gridy = 0;
		this.add(trafficIn, gbc_trafficIn);
		
		
		ButtonGroup group = new ButtonGroup();
		group.add(trafficIn);
		group.add(trafficOut);
		
		computeBtn = new JButton("Compute");
		GridBagConstraints gbc_computBtn = new GridBagConstraints();
		gbc_computBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_computBtn.insets = new Insets(5, 5, 5, 5);
		gbc_computBtn.gridx = 2;
		gbc_computBtn.gridy = 0;
		this.add(computeBtn, gbc_computBtn);
	}
}
