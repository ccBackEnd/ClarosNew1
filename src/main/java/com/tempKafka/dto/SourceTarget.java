package com.tempKafka.dto;

public class SourceTarget {

	public String source;
	public String target;

	public SourceTarget(String source, String target) {
		super();
		this.source = source;
		this.target = target;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "SourceTarget [source=" + source + ", target=" + target + "]";
	}

}
