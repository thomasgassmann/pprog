package ethz.ch.pp.seq;

public class Sequence {
	public final int startIndex;
	public final int endIndex;
	
	public Sequence(int start, int end) {
		startIndex = start;
		endIndex = end;
	}
	
	int lenght() {
		return endIndex - startIndex + 1;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endIndex;
		result = prime * result + startIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Sequence))
			return false;
		Sequence other = (Sequence) obj;
		if (endIndex != other.endIndex)
			return false;
		if (startIndex != other.startIndex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sequence [start=" + startIndex + ", end=" + endIndex + "]";
	}
	
	boolean isGreaterThan(Sequence other) {
	    return (lenght() > other.lenght()) || (lenght() == other.lenght() && startIndex < other.startIndex);
	}
	
	
}
