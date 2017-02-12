package biz.stratadigm.tpi.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import biz.stratadigm.tpi.R;
import biz.stratadigm.tpi.entity.vo.ThaliVO;
import biz.stratadigm.tpi.ui.adapter.viewholder.BaseViewHolder;
import biz.stratadigm.tpi.ui.adapter.viewholder.SplashProgressViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ThaliAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int THALI_VIEW_TYPE = 0;
    private static final int SPLASH_VIEW_TYPE = 1;
    // TODO: 1/21/17 create empty view holder

    private List<ThaliVO> thalis = new ArrayList<>();
    private boolean isSplashShown = false;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case THALI_VIEW_TYPE:
                return new ThaliViewHolder(parent);
            case SPLASH_VIEW_TYPE:
                return new SplashProgressViewHolder(parent);
        }
        throw new IllegalStateException("Unknown viewType=" + viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof ThaliViewHolder) {
            ((ThaliViewHolder) holder).bind(thalis.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (isSplashShown) {
            return 1;
        }
        return thalis.size();
    }

    public void addThalis(List<ThaliVO> newThalis) {
        this.thalis.addAll(newThalis);
    }

    public void setThalis(List<ThaliVO> newThalis) {
        this.thalis = new ArrayList<>(newThalis);
        notifyDataSetChanged();
    }

    public void showThalis(List<ThaliVO> newThalis) {
        this.thalis = new ArrayList<>(newThalis);
        notifyDataSetChanged();
    }

    public void showSplashLoader(boolean show) {
        isSplashShown = show;
        notifyDataSetChanged();
    }

    class ThaliViewHolder extends BaseViewHolder {
        @BindView(R.id.photo)
        ImageView photoImageView;

        @BindView(R.id.name)
        TextView nameTextView;

        @BindView(R.id.region)
        TextView regionTextView;

        @BindView(R.id.price)
        TextView priceTextView;

        public ThaliViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_thali);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ThaliVO thali) {
            nameTextView.setText(thali.getName());
            regionTextView.setText(thali.getRegion());
            priceTextView.setText(" Rs. " + thali.getPrice());
        }
    }
}
