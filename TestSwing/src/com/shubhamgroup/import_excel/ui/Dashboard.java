package com.shubhamgroup.import_excel.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class Dashboard extends JFrame 
{
	public SubInputPanel2 subInputPanel2;
	public SubInputPanel1 subInputPanel1;

	/**
	 * Launch the application.
	 */
	
	
	private static void redirectOut()
	{
		try
		{
			File file = new File("C:\\Users\\NIKHIL\\Desktop\\Excels\\logs.txt");
 
			if( file.exists()) file.delete();

			file.createNewFile();

			PrintStream os = new PrintStream(file);
			
			System.setOut(os);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) 
	{
		//redirectOut();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard frame = new Dashboard();
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
	public Dashboard() 
	{
		try 
		{
			//Nimbus
		/*	for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
		*/	
			//Code For Liquid Look & Feel Added By Nikhil 14--2-2016
		//	javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 388);
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[]{0.5,0.5};
		gbl_panel.rowWeights = new double[]{1.0};
		panel.setLayout(gbl_panel);
		
		InputPanel inputPanel = new InputPanel();
		inputPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_inputPanel = new GridBagConstraints();
		gbc_inputPanel.fill = GridBagConstraints.BOTH;
		gbc_inputPanel.gridx = 0;
		gbc_inputPanel.gridy = 0;
		panel.add(inputPanel, gbc_inputPanel);
		
		
		
		
		OutputPanel outputPanel = new OutputPanel();
		outputPanel.setBorder(new TitledBorder(null, "Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_outputPanel = new GridBagConstraints();
		gbc_outputPanel.fill = GridBagConstraints.BOTH;
		gbc_outputPanel.gridx = 1;
		gbc_outputPanel.gridy = 0;
		panel.add(outputPanel, gbc_outputPanel);
		
		inputPanel.setOutputPanel(outputPanel);
		
	}
	
	
	
	

}
