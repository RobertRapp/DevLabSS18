package hdm.developmentlab.ebi.eve_implementation.events;

public enum TimeReference {
	INSTANCE;
	
	private String future;
	private String past;
	private String present;

 private TimeReference(){
	 
	 setFuture("2 weeks");
	 setPresent("today");
	 setPast("2weeks left");
 }

public String getFuture() {
	return future;
}

public void setFuture(String future) {
	this.future = future;
}

public String getPresent() {
	return present;
}

public void setPresent(String present) {
	this.present = present;
}

public String getPast() {
	return past;
}

public void setPast(String past) {
	this.past = past;
}
	
}
