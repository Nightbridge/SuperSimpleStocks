
public class PreferredStock extends Stock{

	private float fixedDividend;
	
	public PreferredStock(String symbol) {
		super(symbol);
		// TODO Auto-generated constructor stub
	}

	public float getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(float fixedDividend) {
		if(fixedDividend > 0 && fixedDividend <= 100)
			this.fixedDividend = fixedDividend;
		else
			System.out.println("The value MUST be between 0 and 100");
	}

}
