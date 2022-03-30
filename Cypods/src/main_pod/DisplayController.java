package main_pod;

import java.io.InputStream;
import java.net.URL;

import org.w3c.dom.html.HTMLTableCaptionElement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DisplayController extends Window implements ChangeListener{
	
	 
	TabController tab1  ;
	TabController tab2 ;
	TabController tab3 ;
	TabController tab4 ;
	TabController tab5 ;
	TabController tab6 ;
	TabController tab7 ;
	TabController tab8 ;
	TabController tab9 ;
	TabController tab10;
	 String tabNo;

	 
	 DisplayController (){
		
	 }

	public void getTab() {
		try {
			

		   System.out.println("Called tab1");
		   tab1 = new TabController("Tab1"); 
		    
		   System.out.println("Called tab2");
		   tab2 = new TabController("Tab2"); 

		    System.out.println("Called tab2");
	
		   System.out.println("Called tab3");
		   tab3 = new TabController("Tab3"); 
		  
		   System.out.println("Called tab4");		   
		   tab4 = new TabController("Tab4"); 
		   
		   System.out.println("Called tab5");
		   tab5 = new TabController("Tab5"); 
		   
		   System.out.println("Called tab6");		   
		   tab6 = new TabController("Tab6"); 
		   
		   System.out.println("Called tab7");		   
		   tab7 = new TabController("Tab7"); 
		  
		   System.out.println("Called tab8");		   
		   tab8 = new TabController("Tab8"); 
		  
		   System.out.println("Called tab9");		   
		   tab9 = new TabController("Tab9"); 
		   
		   System.out.println("Called tab10");		   
		   tab10 = new TabController("Tab10"); 
		   setTabActiveOnStart();
   
		}
		catch(Exception e) {
			System.out.println("Display Controller: Error in calling one of the tabs");
		}
		
	}
	protected void initListners() {
		tab1.lTab.setOnMousePressed((mouseEvent) -> tab1.setActive());
	}
	protected void setTabActiveOnStart() {
		tab1.setActive();
		setInterfaceInvisible(tabNo = "Tab1");
	}
	protected void setListners()
	{
		tab1.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab1"));
		tab2.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab2"));
		tab3.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab3"));
		tab4.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab4"));
		tab5.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab5"));
		tab6.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab6"));
		tab7.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab7"));
		tab8.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab8"));
		tab9.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab9"));
	   tab10.lTab.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab10"));
	    
	    tab1.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab1"));
	    tab2.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab2"));
	    tab3.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab3"));
	    tab4.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab4"));
	    tab5.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab5"));
	    tab6.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab6"));
	    tab7.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab7"));
	    tab8.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab8"));
	    tab9.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab9"));
	   tab10.imageView.setOnMousePressed((mouseEvent) ->  setInterfaceInvisible(tabNo = "Tab10"));
	   
	   
	    
		
		
	}
	//addChangelistener to the 1Tab label
	private void setInterfaceInvisible(String s)
	{
		double opacity = .4;
		 
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
		tab1.imageView.setOpacity(opacity);
		tab2.imageView.setOpacity(opacity);
		tab3.imageView.setOpacity(opacity);
		tab4.imageView.setOpacity(opacity);
		tab5.imageView.setOpacity(opacity);
		tab6.imageView.setOpacity(opacity);
		tab7.imageView.setOpacity(opacity);
		tab8.imageView.setOpacity(opacity);
		tab9.imageView.setOpacity(opacity);
		tab10.imageView.setOpacity(opacity);
		dc_enableActiveInterface();
		
	}


	private void dc_enableActiveInterface() {
		
		System.out.println("dc_enableActiveInterface, tab no: " + tabNo);
	    switch(tabNo)
		{
			case "Tab1": tab1.setActive(); break;
			case "Tab2": tab2.setActive(); break;
			case "Tab3": tab3.setActive(); break;
			case "Tab4": tab4.setActive(); break;
			case "Tab5": tab5.setActive(); break;
			case "Tab6": tab6.setActive(); break;
			case "Tab7": tab7.setActive(); break;
			case "Tab8": tab8.setActive(); break;
			case "Tab9": tab9.setActive(); break;
			case "Tab10": tab10.setActive(); break;
		}
		
	}
	
	
	
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {}
		// TODO Auto-generated method stub
		
	
	
}
