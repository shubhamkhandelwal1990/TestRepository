package com.shubhamgroup.import_excel.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class TableUnitData 
{
	public String vendorOrCustomer;
	public String destination;
	public String product;
	public double price;
	
	public Type type;
	
	public enum Type {Negosiable, Allowed, Not_Allowed};
	
	public TableUnitData()
	{
		
	}
	
	private static Pattern pricePattern = Pattern.compile("[0-9]+(\\.[0-9]+)?");
	
	public static List<TableUnitData> fromTable(JTable table,  String vendorOrCustomer, String productName)
	{
		List<TableUnitData> unitDataList = new ArrayList<TableUnitData>();
		
		int destColIdx = -1;
		int priceColIdx = -1;
		int prodColIdx = -1;
		
		TableModel model = table.getModel();
		int columnCount = model.getColumnCount();
		
		for(int i=0; i < columnCount; i++)
		{
			String columnName = model.getColumnName(i);
			
			if( destColIdx == -1 && SynonymsInfo.isEqualsDestColumn(columnName))
			{
				destColIdx = i;
			}
			else if( priceColIdx == -1 && SynonymsInfo.isEqualsPriceColumn(columnName))
			{
				priceColIdx = i;
			}
			else if( productName == null && prodColIdx == -1 && SynonymsInfo.isEqualsProdColumn(columnName))
			{
				prodColIdx = i;
			}
		}
		
		System.out.println("destColIdx: "+destColIdx+" priceColIdx: "+priceColIdx+" prodColIdx: "+prodColIdx + " productName: "+productName);
		
		if(destColIdx == -1 || priceColIdx == -1 || (productName == null && prodColIdx == -1))
		{
			return unitDataList;
		}
		
		
		
		int rowCount = model.getRowCount();
		
		for(int i=0; i < rowCount; i++)
		{
			String dest = model.getValueAt(i, destColIdx).toString();
			String priceStr = model.getValueAt(i, priceColIdx).toString();
			String prod = productName == null ? model.getValueAt(i, prodColIdx).toString() : productName;
			
			Matcher matcher = null;
			
			if(priceStr != null && (matcher = pricePattern.matcher(priceStr)).find())
			{
				double price = Double.parseDouble(matcher.group());
				
				unitDataList.add(new TableUnitData(vendorOrCustomer, dest, prod, price));
			}
		}
		
		return unitDataList;
		
	}
	
	
	public TableUnitData(String vendorOrCustomer, String destination, String product, double price)
	{
		this.vendorOrCustomer = vendorOrCustomer;
		this.destination = destination;
		this.product = product;
		this.price = price;
	}
	
	public int compare(TableUnitData obj, double percentDiff, boolean trafficOut)
	{		
		if( compare(this.price, obj.price, trafficOut))
		{
			return 0;
		}
		else if(  compare( this.price * (1 +  ((trafficOut ? 1 : -1)*(percentDiff/100.0))), obj.price, trafficOut))
		{
			return 1;
		}
		else 
		{
			return -1;
		}
	}
	
	private boolean compare(double price1, double price2, boolean trafficOut)
	{
		return trafficOut ? price1 >= price2 : price1 <= price2;
	}


	public boolean equalsWithVendorOrCustomer(TableUnitData obj) 
	{
		if(obj == null ) return false;
		
		return isEquals(destination, obj.destination) && isEquals(vendorOrCustomer, obj.vendorOrCustomer) && SynonymsInfo.isProductEquals(product, obj.product);
		
	}
	
	public boolean equals(TableUnitData obj) 
	{
		if(obj == null ) return false;
		
		return isEquals(destination, obj.destination) && SynonymsInfo.isProductEquals(product, obj.product);
		
	}
	
	private static Pattern destTokenPattern = Pattern.compile("[a-zA-Z0-9]+");
	
	private boolean isEquals(String str1, String str2)
	{
		if((str1 == null && str2 != null ) || (str2 == null && str1 != null ))
		{
			return false;
		}
		
		if(str1 == null && str2 == null)
		{
			return true;
		}
		
		Matcher matcher1 = destTokenPattern.matcher(str1);
		Matcher matcher2 = destTokenPattern.matcher(str2);
		
		String tempStr1 = "";
		while(matcher1.find())
		{
			tempStr1 += matcher1.group();
		}
		
		String tempStr2 = "";
		while(matcher2.find())
		{
			tempStr2 += matcher2.group();
		}
		
		return tempStr1.equalsIgnoreCase(tempStr2);
	}
	
	@Override
	public String toString() 
	{
		return "{destination : "+destination+", product: "+product+", price: "+price+", type: "+type+"}";
	}
	
}
