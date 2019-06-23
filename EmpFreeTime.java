/*
We are given a list schedule of employees, which represents the working time for each employee.

Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.

Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.

Example 1:

Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
Output: [[3,4]]
Explanation:
There are a total of three employees, and all common
free time intervals would be [-inf, 1], [3, 4], [10, inf].
We discard any intervals that contain inf as they aren't finite.

*/

import java.util.*;

class EmpFreeTime {

   /**
    * Definition for an interval.
    */
    static class Interval {
       int start;
       int end;
       Interval() { start = 0; end = 0; }
       Interval(int s, int e) { start = s; end = e; }
    }

   /**
    * employeeFreeTime
    */
    private List<Interval> employeeFreeTime(List<List<Interval>> schedule) { 
        List<Interval> allEmp = new ArrayList<>();
        // convert individual employee intervals into one interval
        for(List<Interval> emp: schedule) {
            allEmp.addAll(emp);
        }
        
        // sort all intervals
        Collections.sort(allEmp, new Comparator<Interval>(){
            public int compare(Interval obj1, Interval obj2) {
                return obj1.start - obj2.start;
            }
        });
        
        // merge intervals
        Interval prev = allEmp.get(0);        
        List<Interval> merged = new ArrayList<>();
        for(int i = 1; i < allEmp.size(); i++) {
            Interval curr = allEmp.get(i);
            
            if(curr.start > prev.end) {
                merged.add(prev);
                prev = curr;
            } else {
                int end = Math.max(prev.end, curr.end);
                prev.end = end;
            }
        }
        merged.add(prev);
        
        // find the gap between the merged interval
        List<Interval> freeTime = new ArrayList<>();
        prev = merged.get(0);
        for(int i = 1; i < merged.size(); i++) {
            Interval curr = merged.get(i);
            Interval free = new Interval(prev.end, curr.start);
            freeTime.add(free);
            prev = curr;
        }
        
        return freeTime;
    }

    private void print(List<Interval> list) {
	for(Interval intv: list) {
		System.out.println(intv.start + ", " + intv.end);
	}
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
	// test1 [ [[1,2],[5,6]] , [[1,3]] ,[[4,10]] ]
	List<List<Interval>> master = new ArrayList<>();
        List<Interval> list = new ArrayList<>();
        EmpFreeTime thisObj = new EmpFreeTime();
	List<Interval> result = null;

	/**
	* Test1
	*/
        System.out.println("Test1: [ [[1,2],[5,6]] , [[1,3]] ,[[4,10]] ]");

	list.add(new Interval(1, 3)); 
	list.add(new Interval(5, 6)); 
        master.add(new ArrayList(list));

        list.clear();	
	list.add(new Interval(1, 3)); 
        master.add(new ArrayList(list));

        list.clear();	
	list.add(new Interval(4, 10)); 
        master.add(new ArrayList(list));

	result = thisObj.employeeFreeTime(master);
	thisObj.print(result);

	/**
	* Test2
	*/
        System.out.println("\nTest2: [ [[1,3],[6,7]] ,[[2,4]], [[2,5],[9,12]] ]");
        master.clear();
        list.clear();

	list.add(new Interval(1, 3)); 
	list.add(new Interval(6, 7)); 
        master.add(new ArrayList(list));

        list.clear();	
	list.add(new Interval(2, 4)); 
        master.add(new ArrayList(list));

        list.clear();	
	list.add(new Interval(2, 5)); 
	list.add(new Interval(9, 12)); 
        master.add(new ArrayList(list));

	result = thisObj.employeeFreeTime(master);
	thisObj.print(result);
    }
    
}
