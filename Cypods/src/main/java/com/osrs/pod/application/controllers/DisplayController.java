package com.osrs.pod.application.controllers;


import com.osrs.pod.application.Window;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.osrs.pod.application.ApplicationConstant.DEBUG;

public class DisplayController implements ChangeListener{

	Window window;
	List<TabController> tabControllerList = new ArrayList<>();
	String tabNo;


	 public DisplayController(){
		 window = new Window();
	 }

	public void getTab() {
		try {
					tabControllerList.add(new TabController("Tab1"));
					tabControllerList.add(new TabController("Tab2"));
					tabControllerList.add(new TabController("Tab3"));
					tabControllerList.add(new TabController("Tab4"));
					tabControllerList.add(new TabController("Tab5"));
					tabControllerList.add(new TabController("Tab6"));
					tabControllerList.add(new TabController("Tab7"));
					tabControllerList.add(new TabController("Tab8"));
					tabControllerList.add(new TabController("Tab9"));
					tabControllerList.add(new TabController("Tab10"));
			   setTabActiveOnStart();
			   setListeners();
		}
		catch(Exception e) {
			System.out.println("Display Controller: Error in calling one of the tabs");
			e.printStackTrace();
		}
		
	}

	protected void setTabActiveOnStart() {
		 for(TabController tab : tabControllerList){
			 if(tab.getInstanceTabName().equals("Tab1")){
				 tab.setActive();
				 tabNo = tab.getInstanceTabName();
			 }
		 }
		setInterfaceInvisible();
	}
	public void setListeners()
	{
		try {
			for(TabController tab : tabControllerList){

				tab.getTabSelectionLabel().setOnMousePressed((mouseEvent) ->  {
					tabNo = tab.getInstanceTabName();
					setInterfaceInvisible();
				});

				tab.imageView.setOnMousePressed((mouseEvent) ->  {
					tabNo = tab.getInstanceTabName();
					setInterfaceInvisible();
				});
			}
		}
		catch(Exception e) {
			System.out.println("Unable to add listners on the method DisplayController.setListners -> " );
			e.printStackTrace();
		}
		
	}
	/**
	 * Making all interfaces invisible and reducing opacity
	 * */
	private void setInterfaceInvisible()
	{
		double opacity = .4;

		for(TabController tab : tabControllerList){
			tab.setInterfaceVisible(false);
			tab.imageView.setOpacity(opacity);
		}
		dc_enableActiveInterface();
	}

/**
 * Selecting which tab is active based on user selection (tabNo)
 * */
	private void dc_enableActiveInterface() {
		if(DEBUG == true) {
		System.out.println("dc_enableActiveInterface, tab no: " + tabNo);}

		for(TabController tab : tabControllerList){
			if(tabNo.equals(tab.getInstanceTabName())){
				tab.setActive();
				break;
			}
		}
	}
	
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {}
		
	
	
}
