package main_pod;

import java.io.InputStream;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DisplayController extends Window{
	
	 GET getItemSprite =  new GET(); 
	
	public void getTab() {
		try {
			// paneInterface pi = new paneInterface();
		   Tab tab1 = new Tab(); 
		   tab1.tabSettings("Tab1");
		   //pi.activateInterface();

           
		   Tab tab2 = new Tab(); 
		   tab2.tabSettings("Tab2");
		   tab2.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detai.json?item=26382"));
		   
		   Tab tab3 = new Tab(); 
		   tab3.tabSettings("Tab3");
		   
		   Tab tab4 = new Tab(); 
		   tab4.tabSettings("Tab4");

		   Tab tab5 = new Tab(); 
		   tab5.tabSettings("Tab5");
		   
		   Tab tab6 = new Tab(); 
		   tab6.tabSettings("Tab6");
		   
		   Tab tab7 = new Tab(); 
		   tab7.tabSettings("Tab7");
		   tab7.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26386"));
		   
		   Tab tab8 = new Tab(); 
		   tab8.tabSettings("Tab8");
		   
		   Tab tab9 = new Tab(); 
		   tab9.tabSettings("Tab9");
		   
		   Tab tab10 = new Tab(); 
		   tab10.tabSettings("Tab10");
		   tab10.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26382"));
		   
		}
		catch(Exception e) {
			System.out.println("Error in calling tab1");
		}
		
	}
	
}
