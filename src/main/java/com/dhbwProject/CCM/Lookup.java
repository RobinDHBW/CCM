package com.dhbwProject.CCM;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

public abstract class Lookup extends Window{
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout vlFields;
	private VerticalLayout vlLayout;
	
	private Button btnOk;
	private Grid grdData;
	
	public Lookup(){
		this.initVlFields();
		this.initVlLayout();
		this.initBtnOk();
		this.initGrdData();
		this.setCaptionAsHtml(true);
		this.setSizeFull();
		this.setModal(true);
		this.setDraggable(false);
		this.setClosable(true);
		this.setContent(this.vlLayout);
		this.center();	
	}
	
	private void initVlFields(){
		this.vlFields = new VerticalLayout();
		this.vlFields.setSizeUndefined();
		this.vlFields.setSpacing(true);
		this.vlFields.setMargin(new MarginInfo(true, false, true, false));
	}
	
	private void initVlLayout(){
		this.vlLayout = new VerticalLayout();
		this.vlLayout.setSizeFull();
		this.vlLayout.addComponent(this.vlFields);
		this.vlLayout.setComponentAlignment(this.vlFields, Alignment.TOP_CENTER);
	}
	
	private void initBtnOk(){
		this.btnOk = new Button();
		this.btnOk.setIcon(FontAwesome.UPLOAD);
//		this.btnOk.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		this.vlFields.addComponent(this.btnOk);
	}
	
	private void initGrdData(){
		this.grdData = new Grid();
		this.grdData.setWidth("300px");
//		this.grdData.setStyleName(ValoTheme.TABLE_BORDERLESS);
		this.vlFields.addComponent(this.grdData);
		
	}

	protected VerticalLayout getLayoutFields(){
		return this.vlFields;
	}
	
	protected Button getOkButton(){
		return this.btnOk;
	}
	
	protected Grid getGrid(){
		return this.grdData;
	}
	
	protected void initGridMatchingFilter(){
		IndexedContainer container = (IndexedContainer) this.grdData.getContainerDataSource();
		HeaderRow filterRow = this.grdData.appendHeaderRow();
		for (Object propId: container.getContainerPropertyIds()) {
		    HeaderCell cell = filterRow.getCell(propId);
		    TextField tfFilter = new TextField();
//		    tfFilter.setStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		    tfFilter.setInputPrompt("Filter...");

		    tfFilter.addTextChangeListener(change -> {
		    	container.removeContainerFilters(propId);
		        if (! change.getText().isEmpty())
		        	container.addContainerFilter(
		                new SimpleStringFilter(propId,
		                    change.getText(), true, false));
		    });
		    cell.setComponent(tfFilter);
		}
	}
	
	protected abstract Container loadData();
}
