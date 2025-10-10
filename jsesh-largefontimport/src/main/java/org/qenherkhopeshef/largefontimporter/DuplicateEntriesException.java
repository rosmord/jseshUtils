package org.qenherkhopeshef.largefontimporter;

import java.util.ArrayList;
import java.util.List;

public class DuplicateEntriesException extends Exception {

	List<String> entries;
	public DuplicateEntriesException(ArrayList<String> entries) {
		this.entries= List.copyOf(entries);
	}

	public List<String> getEntries() {
		return entries;
	}
}
