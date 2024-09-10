package org.kstoi.ws;

import lombok.ToString;
import org.kstoi.entities.Page;

import java.util.ArrayList;
import java.util.List;
@ToString
public class WSClock {
    private List<Page> clock;
    private int clockPointer;
    private long tau;
    private int size;

    public WSClock(int size,long tau) {
        this.tau = tau;
        this.clock=new ArrayList<>(size);
        this.size=size;
    }
    public void addPage(Page page){
        page.setLastUsedTime(System.currentTimeMillis());
        if(clock.size() < size){
            clock.add(page);
            return;
        }
        while(true){
            Page currentPage = clock.get(clockPointer);
            if (currentPage.isReferenceBit()){
                currentPage.setReferenceBit(false);
                moveClockPointer();
                continue;
            }
            long currentTime = System.currentTimeMillis();
            if(currentPage.isDirtyBit()){
                if(currentTime - currentPage.getLastUsedTime() > tau){
                    System.out.println("writing page to disk and replacing "+currentPage.getId());
                    clock.set(clockPointer,page);
                    moveClockPointer();
                    return;
                }else{
                    moveClockPointer();
                    continue;
                }
            }
            if(currentTime - currentPage.getLastUsedTime() > tau){
                System.out.println("replacing page "+currentPage.getId());
                clock.set(clockPointer,page);
                moveClockPointer();
                return;
            }

            moveClockPointer();
        }

    }
    private void moveClockPointer(){
        clockPointer = (clockPointer + 1) % clock.size();
    }
}
