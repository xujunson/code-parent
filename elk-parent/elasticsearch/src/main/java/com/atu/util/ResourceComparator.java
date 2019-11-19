package com.atu.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;

import com.atu.entity.sys.Resource;
public class ResourceComparator implements Comparator<Resource> {

	@Override
	public int compare(Resource o1, Resource o2) {
		if (o1.getSeq() > o2.getSeq()) {
			return 1;
		}else if (o1.getSeq() == o2.getSeq()) {
				return 0;
		} else {
			return -1;
		}
	}
	@SuppressWarnings("unchecked")
	public static List sortList(List list, String propertyName, boolean isAsc) { 
        //借助commons-collections包的ComparatorUtils    
        //BeanComparator，ComparableComparator和ComparatorChain都是实现了Comparator这个接口    
        Comparator mycmp = ComparableComparator.getInstance();       
        mycmp = ComparatorUtils.nullLowComparator(mycmp);  //允许null 
        if(isAsc){ 
        mycmp = ComparatorUtils.reversedComparator(mycmp); //逆序       
        } 
        Comparator cmp = new BeanComparator(propertyName, mycmp);    
        Collections.sort(list, cmp);   
        return list; 
} 

}
