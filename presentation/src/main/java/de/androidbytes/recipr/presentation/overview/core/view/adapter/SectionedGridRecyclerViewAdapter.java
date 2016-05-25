package de.androidbytes.recipr.presentation.overview.core.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.overview.categories.view.OnExpandSectionClickListener;
import de.androidbytes.recipr.presentation.overview.categories.view.adapter.CategoriesOverviewAdapter;
import de.androidbytes.recipr.presentation.overview.core.view.AdapterPositionMapper;
import de.androidbytes.recipr.presentation.overview.core.view.OnRecipeClickListener;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SectionedGridRecyclerViewAdapter<A extends ManagedBaseAdapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterPositionMapper {

    private final Context mContext;
    private static final int SECTION_HEADER_TYPE = 0;
    private static final int SECTION_FOOTER_TYPE = 1;

    private boolean mValid = true;
    private int mSectionHeaderResourceId;
    private int mHeaderTextResourceId;
    private int mSectionFooterResourceId;
    private int mFooterTextViewResourceId;
    private int mFooterTextResourceId;
    private A mBaseAdapter;
    private SparseArray<Section> mSections = new SparseArray<>();
    private OnExpandSectionClickListener onExpandSectionClickListener;
    
    
    public SectionedGridRecyclerViewAdapter(
            Context context,
            int sectionHeaderResourceId,
            int headerTextResourceId,
            int sectionFooterResourceId,
            int footerTextViewResourceId,
            int footerTextResourceId,
            A baseAdapter
    ) {
        mSectionHeaderResourceId = sectionHeaderResourceId;
        mHeaderTextResourceId = headerTextResourceId;
        mSectionFooterResourceId = sectionFooterResourceId;
        mFooterTextViewResourceId = footerTextViewResourceId;
        mFooterTextResourceId = footerTextResourceId;
        mBaseAdapter = baseAdapter;
        mContext = context;

        mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                mValid = mBaseAdapter.getItemCount() > 0;
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    public void setOnExpandSectionClickListener(OnExpandSectionClickListener onExpandSectionClickListener) {
        this.onExpandSectionClickListener = onExpandSectionClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        adjustLayoutManagerForSections(recyclerView);
    }

    private void adjustLayoutManagerForSections(RecyclerView recyclerView) {
        final GridLayoutManager layoutManager = (GridLayoutManager) (recyclerView.getLayoutManager());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (isSectionHeaderPosition(position) || isSectionFooterPosition(position)) ? layoutManager.getSpanCount() : 1;
            }
        });
    }

    public void swapData(List data) {
        //noinspection unchecked
        mBaseAdapter.swapData(data);
    }

    @Override
    public int getCorrespondingPosition(int offsetPosition) {
        return sectionedPositionToPosition(offsetPosition);
    }

    private static class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView title;

        SectionHeaderViewHolder(View view, int mTextResourceid) {
            super(view);
            title = (TextView) view.findViewById(mTextResourceid);
        }
    }


    private static class SectionFooterViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView title;
        public String sectionName;

        SectionFooterViewHolder(View view, int mTextViewResourceid) {
            super(view);
            rootView = view;
            title = (TextView) view.findViewById(mTextViewResourceid);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {
        if (typeView == SECTION_HEADER_TYPE) {
            final View view = LayoutInflater.from(mContext).inflate(mSectionHeaderResourceId, parent, false);
            return new SectionHeaderViewHolder(view, mHeaderTextResourceId);
        } else if (typeView == SECTION_FOOTER_TYPE) {
            final View view = LayoutInflater.from(mContext).inflate(mSectionFooterResourceId, parent, false);
            final SectionFooterViewHolder viewHolder = new SectionFooterViewHolder(view, mFooterTextViewResourceId);

            viewHolder.rootView.findViewById(R.id.tv_section_footer_title).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onExpandSectionClickListener != null) {
                        onExpandSectionClickListener.onExpandSectionClicked(viewHolder.sectionName);
                    }
                }
            });

            return viewHolder;
        } else {
            return mBaseAdapter.onCreateViewHolder(parent, typeView - 1);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {
        if (isSectionHeaderPosition(position)) {
            ((SectionHeaderViewHolder) sectionViewHolder).title.setText(mSections.get(position).title);
        } else if (isSectionFooterPosition(position)) {
            ((SectionFooterViewHolder) sectionViewHolder).title.setText(mContext.getResources().getString(mFooterTextResourceId));
            ((SectionFooterViewHolder) sectionViewHolder).sectionName = getSectionOnPosition(position).title.toString();
        } else {
            //noinspection unchecked
            mBaseAdapter.onBindViewHolder(sectionViewHolder, sectionedPositionToPosition(position));
        }

    }


    
    @Override
    public int getItemViewType(int position) {

        int itemViewType;

        if (isSectionHeaderPosition(position)) {
            itemViewType = SECTION_HEADER_TYPE;
        } else if (isSectionFooterPosition(position)) {
            itemViewType = SECTION_FOOTER_TYPE;
        } else {
            itemViewType = mBaseAdapter.getItemViewType(sectionedPositionToPosition(position)) + 2;
        }

        return itemViewType;
    }


    public static class Section {
        int sectionedPosition;
        int firstPosition;
        int itemCount;
        CharSequence title;
        SectionType sectionType;

        enum SectionType {
            HEADER, FOOTER
        }

        public Section(CharSequence title, int itemCount) {
            this(title, itemCount, SectionType.HEADER);
        }

        Section(CharSequence title, int itemCount, SectionType sectionType) {
            this.title = title;
            this.itemCount = itemCount;
            this.sectionType = sectionType;
        }

        public CharSequence getTitle() {
            return title;
        }
    }


    public void setSections(Section[] sections) {
        mSections.clear();

        Arrays.sort(sections, new Comparator<Section>() {
            @Override
            public int compare(Section o, Section o1) {
                return o.title.toString().compareToIgnoreCase(o1.title.toString());
            }
        });

        int offset = 2; // offset positions for the header and footer we're adding
        int currentSectionStartPosition = 0;
        int unsectionedPosition = 0;
        for (Section section : sections) {
            section.sectionedPosition = currentSectionStartPosition;
            section.firstPosition = unsectionedPosition;

            Section sectionFooter = new Section(section.getTitle(), 0, Section.SectionType.FOOTER);
            sectionFooter.sectionedPosition = section.sectionedPosition + section.itemCount + 1;

            mSections.append(section.sectionedPosition, section);
            mSections.append(sectionFooter.sectionedPosition, sectionFooter);

            currentSectionStartPosition += section.itemCount + offset;
            unsectionedPosition += section.itemCount;
        }
        notifyDataSetChanged();
    }

    private Section getSectionOnPosition(int position) {
        return mSections.get(position);
    }

    public int positionToSectionedPosition(int position) {
        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            Section section = mSections.valueAt(i);
            if (section.sectionType == Section.SectionType.HEADER && section.firstPosition > position)
                break;
            offset += 2;
        }
        return position + offset;
    }

    private int sectionedPositionToPosition(int sectionedPosition) {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION;
        } else if (isSectionFooterPosition(sectionedPosition)) {
            return RecyclerView.NO_POSITION;
        }

        int offset = 0;
        for (int i = 0; i < mSections.size(); i++) {
            Section section = mSections.valueAt(i);
                if (section.sectionedPosition > sectionedPosition) {
                    break;
                }
                offset--;
        }
        return sectionedPosition + offset;
    }

    private boolean isSectionHeaderPosition(int position) {
        Section section = mSections.get(position);
        return section != null && section.sectionType == Section.SectionType.HEADER;
    }

    private boolean isSectionFooterPosition(int position) {
        Section section = mSections.get(position);
        return section != null && section.sectionType == Section.SectionType.FOOTER;
    }


    @Override
    public long getItemId(int position) {

        long itemId;

        if (isSectionHeaderPosition(position) || isSectionFooterPosition(position)) {
            itemId = Integer.MAX_VALUE - mSections.indexOfKey(position);
        } else {
            itemId = mBaseAdapter.getItemId(sectionedPositionToPosition(position));
        }

        return itemId;
    }

    @Override
    public int getItemCount() {
        return (mValid ? mBaseAdapter.getItemCount() + mSections.size() : 0);
    }

    public void setOnRecipeClickedListener(OnRecipeClickListener listener) {
        mBaseAdapter.registerAdapterPositionMapper(this);
        ((CategoriesOverviewAdapter) mBaseAdapter).setOnRecipeClickListener(listener);
    }

}