package com.exercises.scott.minesweeper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by scott on 9/11/16.
 */
public class Cell implements Parcelable {
    private boolean isRevealed;
    private boolean isMine;
    private int numAdjacent;

    public Cell(boolean isRevealed, boolean isMine, int numAdjacent) {
        this.isRevealed = isRevealed;
        this.isMine = isMine;
        this.numAdjacent = numAdjacent;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getNumAdjacent() {
        return numAdjacent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(isRevealed ? 1 : 0);
        parcel.writeInt(isMine ? 1 : 0);
        parcel.writeInt(numAdjacent);
    }

    public static final Parcelable.Creator<Cell> CREATOR
            = new Parcelable.Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel parcel) {
            int revealed = parcel.readInt();
            int mine = parcel.readInt();
            int adjacent = parcel.readInt();

            return new Cell(revealed == 1, mine == 1, adjacent);
        }

        @Override
        public Cell[] newArray(int i) {
            return new Cell[i];
        }
    };
}
