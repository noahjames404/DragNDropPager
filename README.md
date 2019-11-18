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


