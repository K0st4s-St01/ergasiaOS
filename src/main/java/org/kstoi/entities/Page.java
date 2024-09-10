package org.kstoi.entities;

import lombok.*;


@NoArgsConstructor
@Setter
@Getter
@ToString
public class Page {
    int id;
    boolean referenceBit;
    boolean dirtyBit;
    long lastUsedTime;
    public Page(int id,boolean dirtyBit,long lastUsedTime){
        this.id=id;
        this.referenceBit=true;
        this.dirtyBit=dirtyBit;
        this.lastUsedTime=lastUsedTime;
    }
}
