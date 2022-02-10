package main_pod;

import java.io.InputStream;
import java.net.URL;

import org.w3c.dom.html.HTMLTableCaptionElement;

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
		    tab2 = new Tab("Tab2"); 

		    tab2.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26382"));
		    System.out.println("Called tab2");
	
		   System.out.println("Called tab3");
		   tab3 = new Tab("Tab3"); 

		   System.out.println("Called tab4");		   
		   tab4 = new Tab("Tab4"); 

		   System.out.println("Called tab5");
		   tab5 = new Tab("Tab5"); 

		   System.out.println("Called tab6");		   
		   tab6 = new Tab("Tab6"); 

		   System.out.println("Called tab7");		   
		   tab7 = new Tab("Tab7"); 
		   tab7.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26386"));

		   System.out.println("Called tab8");		   
		   tab8 = new Tab("Tab8"); 

		   System.out.println("Called tab9");		   
		   tab9 = new Tab("Tab9"); 

		   System.out.println("Called tab10");		   
		   tab10 = new Tab("Tab10"); 
		   tab10.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26382"));

		   
		}
		catch(Exception e) {
			System.out.println("Display Controller: Error in calling one of the tabs");
		}
		
	}
	public void setListners()
	{
		tab1.lTab.setOnMouseClicked((mouseEvent) ->  tab1.setInterfaceVisible(true));
		tab2.lTab.setOnMouseClicked((mouseEvent) ->  tab2.setInterfaceVisible(true));
		tab3.lTab.setOnMouseClicked((mouseEvent) ->  tab3.setInterfaceVisible(true));
		tab4.lTab.setOnMouseClicked((mouseEvent) ->  tab4.setInterfaceVisible(true));
		tab5.lTab.setOnMouseClicked((mouseEvent) ->  tab5.setInterfaceVisible(true));
		tab6.lTab.setOnMouseClicked((mouseEvent) ->  tab6.setInterfaceVisible(true));
		tab7.lTab.setOnMouseClicked((mouseEvent) ->  tab7.setInterfaceVisible(true));
		tab8.lTab.setOnMouseClicked((mouseEvent) ->  tab8.setInterfaceVisible(true));
		tab9.lTab.setOnMouseClicked((mouseEvent) ->  tab9.setInterfaceVisible(true));
		tab10.lTab.setOnMouseClicked((mouseEvent) ->  tab10.setInterfaceVisible(true));
		
	}
	
	private void setInterfaceInvisible()
	{
		
		tab1.setInterfaceVisible(false);
		tab2.setInterfaceVisible(false);
		tab3.setInterfaceVisible(false);
		tab4.setInterfaceVisible(false);
		tab5.setInterfaceVisible(false);
		tab6.setInterfaceVisible(false);
		tab7.setInterfaceVisible(false);
		tab8.setInterfaceVisible(false);
		tab9.setInterfaceVisible(false);
		tab10.setInterfaceVisible(false);
	}
	
}
