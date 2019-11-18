# DragNDropPager
Drag button elements between fragments & cutomize it. This buttons are aligned in a grid layout pattern, but it's parent layout is relative layout rather than gridlayout.  

## Preview
**Relative Layout** - Pass views from one layout to another.
<img src='https://github.com/noahjames404/mema-activities/blob/master/images/DragNDropPager/rrelative_layout_preview.gif?raw=true'>

**View Pager** - Pass views between fragments.
<img src='https://github.com/noahjames404/mema-activities/blob/master/images/DragNDropPager/rview_pager_preview.gif.gif?raw=true'>

## Installation
[![](https://jitpack.io/v/noahjames404/dragndroppager.svg)](https://jitpack.io/#noahjames404/dragndroppager)

## Compatibility
* Minimum SDK Version: 16
* Target SDK Version: 29

## Documentation
The **DNDPager** class is the core of the library, this is responsbile for managing the views. 

### Constructor
 Data Type | Name | Description
 ----------|------|-----------
 Relative Layout | layout | target layout to populate.
 int | row_num | number of rows to fit inside the layout
 int | col_num | number of columns to fit inside the layout
 String | group_id | when migrating a view to another layout, group_ids must be equivalent.   
 Context | context | used for creating view elements.

### Methods
 **void render()** - generates view listeners inside the layout.
 
 **void updateLayoutSize()**

  Data Type | Name | Description
 -----------|------|------------
 Relative Layout | layout | triggers a callback when layout's dimension updates.
 IDNDPager | callback | triggers on event.
 
 **public DNDButton generateButton()**
 
 Data Type | Name | Description
 ----------|------|------------
 int | width_ratio | specifies the width of a button, the ratio is base on the number of columns in a layout.
 int | height_ratio | specifies the height of a button, the ratio is base on the number of rows in a layout.
 int | x | x coordinate in layout
 int | y | y coordinate in layout
 String | btn_text | specify the preview text
 Drawable | btnbg_image | nullable, specify the background image of the view
 int | btnbg_color | specify the background color of the view, this variable is ignored if btnbg_image is not null
 **DNDButton** | **RETURN** | **the generated view as a DNDButton**
 
 **public void setOnClickBtnListener()**
 
 Data Type | Name | Description
 ----------|------|------------
 View.OnClickListener | btn_listener | set a callback, triggers when a button is click (only triggers when editable is false).
 
 **RelativeLayout.LayoutParams setGridPosition()** - generates the position & size of a view base on layout's preferences.
 
 
  Data Type | Name | Description
 ----------|------|------------
 double | width_ratio | the width of button
 double | height_ratio | the height of button
 int | x | x coordinates in layout
 int | y | y coordinates in layout
 **RelativeLayout.LayoutParams** | **RETURN** | **this results are base on layout's width,height,row_num,col_num**
 
 
 **void setBackgroundColor(int color)** - must be applied before ```render()```
 
 Data Type | Name | Description
 ----------|------|------------
 int | color | modifies the layout's background color
 
 **List<DNDButton> getButtons()** - takes all buttons inside the layout
 
 Data Type | Name | Description
 ----------|------|------------
 **List<DNDButton>** | **RETURNS** | **a list of buttons from the layout**
 
 **List<DNDSnapView> getSnapViews()** - takes all SnapView inside the layout, SnapViews are listeners that specifies where to designate a dragable view, forming a grid like formation.
 
 Data Type | Name | Description
 ----------|------|------------
 **List<DNDSnapView>** | **RETURNS** | **a list of SnapViews from the layout**
 
 
 **DNDButton getButtonByCoordinates()** - get a button base on coordinates given.
 
  Data Type | Name | Description
 ----------|------|------------
 int | x | x coordinate of a button
 int | y | y coordinate of a button
 **DNDButton** | **RETURNS** | **null if no button exist base on coordinates**
 
 **int getInvalidColor()** - specify the invalid color shadow of a view, if view is being obstructed.
 
  Data Type | Name | Description
 ----------|------|------------
 **int** | **RETURNS** | **color of obstruction**
 
  **void setInvalidColor()** - set the default obstruction color of a view
  
  Data Type | Name | Description
 ----------|------|------------
 int | invalid_color | specify the obstructed color of view.
 
  **void setOnCustomize()** - double tap a button to trigger (works only if the view is editable)
  
  Data Type | Name | Description
 ----------|------|------------
 IDNDPager.ItemView | view | callback method
 
  **MESSAGE checkItem()** - validate view coordinates
  
  Data Type | Name | Description
 ----------|------|------------
 ViewGroup.MarginLayoutParams | params | parameters to validate
 DNDButton | btn | view to validate
  **DNDPager.MESSAGE** | **RETURNS** | **validation result**
  
   **boolean addButtonToLayout()** - dynamically add a button inside the layout.
  
  Data Type | Name | Description
 ----------|------|------------
 DNDItem | item | holds the properties of the button to be added, this does not include its definite size as it will always base on the parent layout's preferences.
int | page_num | specify which page the view is located other than x & y coordinates, set value to -1 default (no definite page, ignoring the DNDPager's assign page number).  
**boolean** | **RETURNS** | **true if view is already added in the layout**

 **int getPageNum()** - take the page number of the DNDPager layout, this is mostly used for view pagers.
  
  Data Type | Name | Description
 ----------|------|------------
**int** | **RETURNS** | **the page number of DNDPager**

**void setPageNum()** - set the page number of DNDPager
  
  Data Type | Name | Description
 ----------|------|------------
 int | page_num | the page number of the DNDPager
 
 
 
 
 
 
 
 
 
 
 
