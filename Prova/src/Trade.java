import java.util.Date;


public class Trade {

	private Stock stock;
	private Date date;
	private int shares;
	private Boolean sale;
	private Boolean buy;
	private float tradePrice;
	
	public Trade(){
		
	}
	
	public Trade(Stock stock, int shares, Boolean sale, Boolean buy, float tp){
		this.stock = stock;
		this.date = new Date();
		this.shares = shares;
		if(sale == true){
			this.sale = true;
			this.buy = false;
		}
		else if(sale == true){
			this.buy = true;
			this.sale = false;
		}
		this.tradePrice = tp;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public Boolean getSale() {
		return sale;
	}

	public void setSale(Boolean sale) {
		this.sale = sale;
		if(this.buy == sale)
			this.buy = false;
	}

	public Boolean getBuy() {
		return buy;
	}

	public void setBuy(Boolean buy) {
		this.buy = buy;
		if(this.sale == buy)
			this.sale = false;
	}

	public float getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(float tradePrice) {
		this.tradePrice = tradePrice;
	}
	
	public String toString(){
		
		String buySale = "";
		
		if(buy == true)
			buySale = "buy";
		else if(sale == true)
			buySale = "sale";
		
		return "Trade \nStock: "+this.stock.getSymbol()+"\nTimestamp: "+this.getDate()+"\nQuantity of shares: "+this.getShares()+"\nBuy/Sale: "+buySale+"\nTrade price: "+this.tradePrice;
		
	}
	
}
