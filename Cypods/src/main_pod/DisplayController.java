package main_pod;

import java.io.InputStream;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DisplayController extends Window{
	
	 GET getItemSprite =  new GET(); 
	 Tab tab1 ;
	 Tab tab2 ;
	 Tab tab3 ;
	 Tab tab4 ;
	 Tab tab5 ;
	 Tab tab6 ;
	 Tab tab7 ;
	 Tab tab8 ;
	 Tab tab9 ;
	 Tab tab10;
	 


	public void getTab() {
		try {
			
			//tab10.setVisibleInterface(false);
		   System.out.println("Called tab1");
		    tab1 = new Tab("Tab1"); 

		   System.out.println("Called tab2");
		    tab1 = new Tab("Tab2"); 

		   tab1.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26382"));
		   System.out.println("Called tab2");
	
		   System.out.println("Called tab3");
		   Tab tab3 = new Tab("Tab3"); 

		   System.out.println("Called tab4");		   
		   Tab tab4 = new Tab("Tab4"); 

		   System.out.println("Called tab5");
		   Tab tab5 = new Tab("Tab5"); 

		   System.out.println("Called tab6");		   
		   Tab tab6 = new Tab("Tab6"); 

		   System.out.println("Called tab7");		   
		   Tab tab7 = new Tab("Tab7"); 
		   tab7.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26386"));

		   System.out.println("Called tab8");		   
		   Tab tab8 = new Tab("Tab8"); 

		   System.out.println("Called tab9");		   
		   Tab tab9 = new Tab("Tab9"); 

		   System.out.println("Called tab10");		   
		   Tab tab10 = new Tab("Tab10"); 
		   tab10.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26382"));

		   
		}
		catch(Exception e) {
			System.out.println("Display Controller: Error in calling one of the tabs");
		}
		
	}
	
}
