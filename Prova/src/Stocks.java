import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Stocks {

	public static void main(String[] args) {
		
		ArrayList<Stock> stocksList = new ArrayList<Stock>();
		ArrayList<Trade> tradesList = new ArrayList<Trade>();
		HashMap<Stock,Float> stocksPrices = new HashMap<Stock, Float>();
		Trade t = null;
		Random random = new Random();
		
		CommonStock tea = new CommonStock("TEA");
		tea.setLastDividend(0);
		tea.setParValue(100);
		
		CommonStock pop = new CommonStock("POP");
		pop.setLastDividend(8);
		pop.setParValue(100);
		
		CommonStock ale = new CommonStock("ALE");
		ale.setLastDividend(23);
		ale.setParValue(60);
		
		PreferredStock gin = new PreferredStock("GIN");
		gin.setLastDividend(8);
		gin.setFixedDividend(2);
		gin.setParValue(100);
		
		CommonStock joe = new CommonStock("JOE");
		joe.setLastDividend(13);
		joe.setParValue(250);
		
		stocksList.add(tea);
		stocksList.add(pop);
		stocksList.add(ale);
		stocksList.add(gin);
		stocksList.add(joe);
		
		for(int i = 0; i < stocksList.size(); i++){
			stocksPrices.put(stocksList.get(i), random.nextFloat());
		}
		
		Boolean cycle = true;
		Scanner scanner = new Scanner(System.in);
		
		while(cycle){
			
			int c = 0;
			int s = 0;
			float m = 0;
			Float result;
			String str = null;
			
			System.out.println("1] Calculate Dividend Yield.\n"
					+ "2] Calculate P/E Ratio.\n"
					+ "3] Record a Trade.\n"
					+ "4] Calculate Volume Weighted Stock Price.\n"
					+ "5] Calculate the GBCE.\n"
					+ "6] Exit.\n");
			
			c = scanner.nextInt();
			
			switch(c){
				
				case 1:
					System.out.println("Choose the stock\n");
					for(int i = 0; i < stocksList.size(); i++){
						System.out.println(i+1+"] "+stocksList.get(i).getSymbol());
					}
					System.out.println("\n");
					s = scanner.nextInt();
					System.out.println("Market price: ");
					m = scanner.nextFloat();
					System.out.println("Dividend Yeld of the stock "+stocksList.get(s-1).getSymbol()+": "+calculateDividendYield(stocksList.get(s-1), m)+"\n\n");
					
					break;
				case 2:
					System.out.println("Choose the stock\n");
					for(int i = 0; i < stocksList.size(); i++){
						System.out.println(i+1+"] "+stocksList.get(i).getSymbol());
					}
					System.out.println("\n");
					s = scanner.nextInt();
					System.out.println("Market price: ");
					m = scanner.nextFloat();
					System.out.println("P/E Ratio of the stock "+stocksList.get(s-1).getSymbol()+": "+calculatePERatio(stocksList.get(s-1), m)+"\n\n");
					
					break;
				case 3:
					System.out.println("Choose the stock\n");
					for(int i = 0; i < stocksList.size(); i++){
						System.out.println(i+1+"] "+stocksList.get(i).getSymbol());
					}
					System.out.println("\n");
					s = scanner.nextInt();
					t = new Trade();
					Date date = new Date();
					t.setDate(date);
					t.setStock(stocksList.get(s-1));
					System.out.println("Stock "+t.getStock().getSymbol()+" Time Stamp of the trade: "+t.getDate()+"\n\nQuantity of shares: ");
					s = scanner.nextInt();
					t.setShares(s);
					Boolean ok = true;
					while (ok){
						System.out.println("Buy or sell (b/s)");
						str = scanner.next();
						if(str.equals("b")){
							t.setBuy(true);
							t.setSale(false);
							ok = false;
						}
						else if(str.equals("s")){
							t.setSale(true);
							t.setBuy(false);
							ok = false;
						}
						else{
							System.out.println("Wrong char, type again\n");
						}
					}
					System.out.println("Trade price: ");
					m = scanner.nextFloat();
					t.setTradePrice(m);
					tradesList.add(t);
					System.out.println(t.toString()+ "\n\n");
					break;
				case 4:
					System.out.println("Choose the stock\n");
					for(int i = 0; i < stocksList.size(); i++){
						System.out.println(i+1+"] "+stocksList.get(i).getSymbol());
					}
					System.out.println("\n");
					s = scanner.nextInt();
					result = calculateVWSP(tradesList, stocksList.get(s-1));
					if (!result.isNaN())
						System.out.println("Volume Weighted Stock Price in last 15 minutes: "+result+"\n\n");
					else
						System.out.println("No trades in last 15 minutes\n\n");
					break;
				case 5:
					System.out.println("This is an example of Geometric Mean whit random Prices. "
							+ "This Function can be invoced normally whit calculated numbers.\nValue: "+calculateGeometricMean(stocksPrices)+"\n\n");
					
					break;
				case 6:
					System.out.println("Goodbye");
					scanner.close();
					cycle = false;
				
			}
			
		}
		
	}
	
	public static float calculateDividendYield(Stock stock, float marketPrice){
		
		float result = 0;
		
		if(stock instanceof CommonStock){
			result = stock.getLastDividend()/marketPrice;
		}
		else if(stock instanceof PreferredStock){
			PreferredStock ps = (PreferredStock)stock;
			result = ((ps.getFixedDividend()/100)*(ps.getParValue()))/marketPrice;
		}
		
		return result;
	}
	
	public static float calculatePERatio(Stock stock, float marketPrice){
		
		if (stock.getLastDividend() != 0)
			return marketPrice/stock.getLastDividend();
		
		return 0;
	}
	
	public static float calculateVWSP(ArrayList<Trade> tradeList, Stock stock){
		
		float vwsp = 0;
		float numerator = 0;
		float denominator = 0;
		
		for (int i = 0; i < tradeList.size(); i++){
			Date now = new Date();
			if(tradeList.get(i).getStock().getSymbol().equals(stock.getSymbol()) && (now.getTime() - tradeList.get(i).getDate().getTime()) < 900000){
				numerator += (tradeList.get(i).getTradePrice() * tradeList.get(i).getShares());
				denominator += tradeList.get(i).getShares();
			}
		}

		vwsp = numerator/denominator;
		
		return vwsp;
	}
	
	public static float calculateGeometricMean(HashMap<Stock,Float> stocksPrices){
		
		float geometricMean = 0;
		float product = 1;
		
		for (Stock stock: stocksPrices.keySet()){
			product *= stocksPrices.get(stock);
			System.out.println(product);
		}
		
		geometricMean = (float) Math.pow(product, (1/stocksPrices.size()));
		
		return geometricMean;
	}

}
