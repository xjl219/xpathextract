package com.xujl.etl.xpath.schema;
public abstract class UrlMatchRule {
	
	protected boolean isNegativeEnabled;
	public UrlMatchRule setNegativeEnabled(boolean isNegativeEnabled) {
		this.isNegativeEnabled = isNegativeEnabled;
		return this;
	}
	
	protected abstract boolean doMatches(Object request);
	
	public boolean matches(Object request) {
		boolean r = this.doMatches(request);
		return this.isNegativeEnabled ? !r : r;
	}

	@Override
	public String toString() {
		return "UrlMatchRule [isNegativeEnabled=" + isNegativeEnabled + "]";
	}
	
}