/**
 *
 *  @author Kowalski Robert S18290
 *
 */

package zad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



public class Main {
	 static int[] s = {0};
  public static void main(String[] args) throws IOException {
	  String u = "http://wiki.puzzlers.org/pub/wordlists/unixdict.txt";
	  URL w = new URL(u);
	  InputStream z = w.openStream();
	  InputStreamReader r = new InputStreamReader(z);
	  BufferedReader b = new BufferedReader(r);
	  List<List<String>> e = new ArrayList<List<String>>(); 
	 Map<String,List<String>> k = b.lines().sorted().collect(Collectors.groupingBy(new Function<String,String>(){
		public String apply(String s){
			char[] a = s.toCharArray();
			Arrays.sort(a);
			return new String(a);}}));
	   k.forEach(new BiConsumer<String,List<String>>(){
		public void accept(String i, List<String> l){
			if (s[0]<l.size())
				s[0] =l.size();}});
	   k.entrySet().stream().filter(new Predicate<Map.Entry<String,List<String>>>(){
		public boolean test(Entry<String, List<String>> t){
		if (s[0] == t.getValue().size()) 
			return true;
		return false;
		}}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).forEach(new BiConsumer<String,List<String>>(){
			public void accept(String i, List<String> l){
				e.add(l);}});
			 e.sort(new Comparator<List<String>>() {
					public int compare(List<String> a, List<String> b){
					return a.get(0).compareTo(b.get(0));	
				}});
			  for(List<String> s : e) {
		     for (int i = 0; i < s.size(); i++) {
			    	 System.out.print(s.get(i)+" ");}System.out.println();}}}
	  
	  
	/*  
	  List<List<String>> e = new ArrayList<List<String>>(); 
	   p.forEach(new BiConsumer<String,List<String>>(){
				public void accept(String i, List<String> l) {
					e.add(l);
				}});
	  e.sort(new Comparator<List<String>>() {
		public int compare(List<String> a, List<String> b) {
			return a.get(0).compareTo(b.get(0));	
		}});
	  for(List<String> s : e) {
	     for (int i = 0; i < s.size(); i++) {
	    	 System.out.print(s.get(i)+" ");
	     }
	    	System.out.println();
	  }
	*/  
	//  List<String> e = pr.entrySet().stream().sorted(new Comparator<Map.Entry<String,List<String>>>(){
	//	  	public int compare(Entry<String, List<String>> a, Entry<String, List<String>> b) {
//				
	//			
//	  }}).map().collect(Collectors.toList());
	// pr.forEach(new BiConsumer<String,List<String>>(){
	//	public void accept(String i, List<String> l) {
	//		System.out.println(l);
	//	}});}
	 
  //}

