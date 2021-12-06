package com.company.Lab3;/* It is in this file, specifically the replacePage function that will
   be called by MemoryManagement when there is a page fault.  The 
   users of this program should rewrite PageFault to implement the 
   page replacement algorithm.
*/

  // This PageFault file is an example of the FIFO Page Replacement 
  // Algorithm as described in the Memory Management section.

import java.util.*;

public class PageFault {

  /**
   * The page replacement algorithm for the memory management sumulator.
   * This method gets called whenever a page needs to be replaced.
   * <p>
   * The page replacement algorithm included with the simulator is 
   * FIFO (first-in first-out).  A while or for loop should be used 
   * to search through the current memory contents for a canidate 
   * replacement page.  In the case of FIFO the while loop is used 
   * to find the proper page while making sure that virtPageNum is 
   * not exceeded.
   * <pre>
   *   Page page = ( Page ) mem.elementAt( oldestPage )
   * </pre>
   * This line brings the contents of the Page at oldestPage (a 
   * specified integer) from the mem vector into the page object.  
   * Next recall the contents of the target page, replacePageNum.  
   * Set the physical memory address of the page to be added equal 
   * to the page to be removed.
   * <pre>
   *   controlPanel.removePhysicalPage( oldestPage )
   * </pre>
   * Once a page is removed from memory it must also be reflected 
   * graphically.  This line does so by removing the physical page 
   * at the oldestPage value.  The page which will be added into 
   * memory must also be displayed through the addPhysicalPage 
   * function call.  One must also remember to reset the values of 
   * the page which has just been removed from memory.
   *
   * @param mem is the vector which contains the contents of the pages 
   *   in memory being simulated.  mem should be searched to find the 
   *   proper page to remove, and modified to reflect any changes.  
   * @param virtPageNum is the number of virtual pages in the 
   *   simulator (set in Kernel.java).  
   * @param replacePageNum is the requested page which caused the 
   *   page fault.  
   * @param controlPanel represents the graphical element of the 
   *   simulator, and allows one to modify the current display.
   */
  public static void replacePage ( Vector mem , Vector<Integer> workingSet , Vector<Integer> physicalUnmapped ,
                                   Vector<Integer> notInWorkingSet,
                                   int tau , int currentTime , int virtPageNum ,
                                   int replacePageNum , ControlPanel controlPanel , boolean modified, String RorW)
  {
    int firstPage = -1;
    int firstCleanPage = -1;
    int greatestAge = -1;
    boolean isRZeroFound = false;

    Vector<Integer> toDelete = new Vector<>();
    int finalVictim = -1;


    for (int i = 0; i < workingSet.size(); i++){
      int pageId = workingSet.elementAt(i);
      Page page = (Page) mem.elementAt(pageId);
      if (page.R == 1){
        if (!isRZeroFound){
          if (firstCleanPage == -1 && page.M == 0){
            firstCleanPage = i;
            firstPage = i;
          }
          else if(firstPage == -1){
            firstPage = i;
          }
        }
      }
      else{
        isRZeroFound = true;
        int age = currentTime - page.lastTouchTime;
        if (age > tau){
          if (finalVictim == -1){
            finalVictim = i;
          }
          else{
            toDelete.addElement(i);
          }
        }
        else{
          if (greatestAge == -1){
            greatestAge = i;
          }
          else{
            Page tempPage = (Page) mem.elementAt(workingSet.elementAt(greatestAge));
            if ((currentTime - tempPage.lastTouchTime) < age){
              greatestAge = i;
            }
          }
        }
      }
    }

    if (finalVictim != -1){
      for(int i = toDelete.size() - 1; i >= 0; i--){
        Page page = (Page) mem.elementAt(workingSet.elementAt(toDelete.elementAt(i)));

        if(modified){
          notInWorkingSet.addElement(workingSet.elementAt(toDelete.elementAt(i)));
        }
        else {
          physicalUnmapped.addElement(page.physical);
          controlPanel.removePhysicalPage(page.id);
          page.physical = -1;
        }

        page.M = 0;
        page.R = 0;
        workingSet.removeElementAt(toDelete.elementAt(i));
      }
    }
    else if(greatestAge != -1){
      finalVictim = greatestAge;
    }
    else if(firstCleanPage != -1){
      finalVictim = firstCleanPage;
    }
    else{
      finalVictim = firstPage;
    }

    int pageId = workingSet.elementAt(finalVictim);
    Page page = ( Page ) mem.elementAt( pageId );
    Page nextPage = ( Page ) mem.elementAt( replacePageNum );
    controlPanel.removePhysicalPage( pageId );
    nextPage.physical = page.physical;
    controlPanel.addPhysicalPage( nextPage.physical , replacePageNum );
    page.R = 0;
    page.M = 0;
    page.physical = -1;
    workingSet.removeElementAt(finalVictim);
    nextPage.R = 1;
    if (RorW == "W"){
      nextPage.M = 1;
    }
    nextPage.lastTouchTime = currentTime;
    workingSet.addElement(replacePageNum);

  }
}
