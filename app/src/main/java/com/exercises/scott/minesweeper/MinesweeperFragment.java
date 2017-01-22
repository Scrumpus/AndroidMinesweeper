package com.exercises.scott.minesweeper;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exercises.scott.minesweeper.util.DimensionUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scott on 9/3/16.
 */
public class MinesweeperFragment extends Fragment implements MinesweeperContract.View {

    public static MinesweeperFragment newInstance() {
        return new MinesweeperFragment();
    }

    private MinesweeperContract.Presenter mPresenter;
    private MineAdapter mAdapter;
    private List<Cell> mCells;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.minesweeper_frag, container, false);
        RecyclerView rView = (RecyclerView) root.findViewById(R.id.minesweeper_recyclerview);



        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = DimensionUtils.getCellDimen(displaymetrics.widthPixels);
        int height = DimensionUtils.getCellDimen(displaymetrics.heightPixels);

        mPresenter.createNewGame(height, width);
        mCells = mPresenter.getCells();
        rView.setLayoutManager(new GridLayoutManager(getContext(), width));
        mAdapter = new MineAdapter();
        rView.setAdapter(mAdapter);
        return root;

    }

    private class MineAdapter extends RecyclerView.Adapter<MineHolder> {
        public MineAdapter() {

        }

        @Override
        public MineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_layout, null);
            MineHolder holder = new MineHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MineHolder holder, final int position) {
            if (mCells == null) return;

            Cell cell = mCells.get(position);

            if (cell.isRevealed()) {
                if (cell.isMine()) {
                    holder.setMine();
                } else {
                    holder.setClear(cell.getNumAdjacent());
                }
            }

            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.clickMine(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCells.size();
        }
    }

    private class MineHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public MineHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.mine_text);
        }

        public void setMine() {
            mTextView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorMineRevealed));
            mTextView.setText("X");
        }

        public void setClear(int adjacent) {
            mTextView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorMineRevealed));
            mTextView.setText("" + adjacent);
        }
    }

    @Override
    public void setPresenter(MinesweeperContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void updateBoard() {
        mAdapter.notifyDataSetChanged();
    }
}
