package org.qenherkhopeshef.svgnormalize;

import java.io.File;
import java.util.Comparator;

import jsesh.hieroglyphs.data.GardinerCode;


/**
 * Compare file names as if they were Gardiner codes.
 */
class GardinerFileOrderComparator implements Comparator<File>{
    public int compare(File f1, File f2) {                    
        String s1 = GardinerCode.getCodeForFileName(f1.getName());
        if (s1 == null)
            s1 = f1.getName();
        String s2 = GardinerCode.getCodeForFileName(f2.getName());
        if (s2 == null)
            s2 = f2.getName();
        return GardinerCode.compareCodes(s1, s2);
    }

}
