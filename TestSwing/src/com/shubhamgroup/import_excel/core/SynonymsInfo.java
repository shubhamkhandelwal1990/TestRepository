package com.shubhamgroup.import_excel.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SynonymsInfo 
{
	private static Map<String, String[]> products = new HashMap<String, String[]>();
	
	private static Pattern prodNamePattern1;
	private static Pattern prodNamePattern2;
	private static Pattern prodNamePattern3;
	
	private static List<Pattern> destColPatterns;
	private static List<Pattern> prodColPatterns;
	private static List<Pattern> priceColPatterns;
	
	
	static
	{
		products.put("CLI", new String[]{"Premium", "Gold", "White", "Direct", "CLI"});
		products.put("NCLI", new String[]{"NCLI", "WHOLESALE", "SILVER", "GREY", "BLEND", "Standard"});
		products.put("TDM", new String[]{"TDM", "DIGITAL"});
		products.put("CC", new String[]{"Call Centre", "CC"});
		
		String regex = "";
		
		for(String[] prods : products.values())
		{
			for(String prod : prods)
			{
				regex += (regex.isEmpty() ? "": "|") + "("+prod.trim().replaceAll("(\\s)+", "(\\\\s)+")+")";
			}
		}
		
		regex = "(^|[^a-zA-Z0-9])(" + regex + ")([^a-zA-Z0-9]|$)";
		
		//System.out.println(regex);
		
		prodNamePattern1 = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		prodNamePattern2 = Pattern.compile("((tagret)|(list)|(all)|(compan(y|(ies)))|(\\s))+", Pattern.CASE_INSENSITIVE);
		prodNamePattern3 = Pattern.compile("((tagret)|(list)|([a-zA-Z0-9]+)|(\\s))+", Pattern.CASE_INSENSITIVE);
		
		destColPatterns = getColumnPatterns("DestinationColumnSynonyms.txt");
		prodColPatterns = getColumnPatterns("ProductColumnSynonyms.txt");
		priceColPatterns = getColumnPatterns("PriceColumnSynonyms.txt");
		
		
	}
	
	public static boolean isProductEquals(String prod1, String prod2)
	{
		if((prod1 == null && prod1 != null ) || (prod1 == null && prod1 != null ))
		{
			return false;
		}
		
		if(prod1 == null && prod1 == null)
		{
			return true;
		}
		
		String tempStr1 = prod1.trim().replaceAll("(\\s)+", " ");
		String tempStr2 = prod1.trim().replaceAll("(\\s)+", " ");
		
		if(tempStr1.equalsIgnoreCase(tempStr2)) return true;
		
		for(String[] prods : products.values())
		{
			boolean foundStr1 = false;
			boolean foundStr2 = false;
			
			for(String prod : prods)
			{
				if(prod.equalsIgnoreCase(tempStr1))
				{
					foundStr1 = true;
				}
				else if(prod.equalsIgnoreCase(tempStr2))
				{
					foundStr2 = true;
				}
			}
			
			if(foundStr1 && foundStr2)
			{
				return true;
			}
			else if((foundStr1 && !foundStr2) || (!foundStr1 && foundStr2))
			{
				return false;
			}
		}
		
		return false;
	}
	
	
	
	
	public static List<Pattern> getColumnPatterns(String fileName)
	{
		try
		{
			InputStream patternStream = SynonymsInfo.class.getResourceAsStream("/com/shubhamgroup/import_excel/resources/"+fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(patternStream));
			
			String line = null;
			
			List<Pattern> patterns = new ArrayList<Pattern>();
			
			while((line = reader.readLine()) != null)
			{
				line = line.trim();
				if(line.isEmpty()) continue;
				
				Pattern pattern = Pattern.compile("^("+line+")$", Pattern.CASE_INSENSITIVE);
				patterns.add(pattern);
			}
			
			reader.close();
			patternStream.close();
			
			return patterns;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	public static String getProductNameFromFilePath(String filePath)
	{
		String productName = null;
		
		String fileName = new File(filePath).getName();
		
		String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.')).trim();
		
		Matcher matcher = prodNamePattern1.matcher(fileNameWithoutExt);
		
		if(matcher.find())
		{
			productName = matcher.group(2).replaceAll("(\\s)+", " ").toUpperCase();
		}
		else if((matcher = prodNamePattern2.matcher(fileNameWithoutExt)).find())
		{
			return null;
		}
		else if((matcher = prodNamePattern3.matcher(fileNameWithoutExt)).find())
		{
			productName = matcher.group().toLowerCase().replaceAll("(tagret)|(list)", "").trim().replaceAll("(\\s)+", " ");
		}
		
		return productName;
		
	}
	
	public static boolean isEqualsDestColumn(String val)
	{
		return isEqualsColumn(val, destColPatterns);
	}
	
	public static boolean isEqualsProdColumn(String val)
	{
		return isEqualsColumn(val, prodColPatterns);
	}
	
	public static boolean isEqualsPriceColumn(String val)
	{
		return isEqualsColumn(val, priceColPatterns);
	}	
	
	private static boolean isEqualsColumn(String val, List<Pattern> patternList)
	{
		try
		{
			if(val == null)
			{
				return false;
			}

			val = val.trim();

			if(val.isEmpty() || patternList == null || patternList.size() == 0)
			{
				return false;
			}
			
			

			for(Pattern pattern : patternList)
			{
				if(pattern.matcher(val).find())
				{
					return true;
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return false;
	}
	
	
	
	public static void main(String[] args) 
	{
		System.out.println(getProductNameFromFilePath("Call     Centre taregt list.xlsx"));
	}
	
}
