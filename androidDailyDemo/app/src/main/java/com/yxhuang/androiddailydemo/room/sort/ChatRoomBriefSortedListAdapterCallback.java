package com.yxhuang.androiddailydemo.room.sort;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedListAdapterCallback;

import com.yxhuang.androiddailydemo.room.ChatRoomBrief;

/**
 * Created by yxhuang
 * Date: 2021/2/25
 * Description:
 */
public class ChatRoomBriefSortedListAdapterCallback extends SortedListAdapterCallback<ChatRoomBrief> {

    public ChatRoomBriefSortedListAdapterCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public int compare(ChatRoomBrief o1, ChatRoomBrief o2) {
        long time1 = o1.getMsgTime();
        long time2 = o2.getMsgTime();
        if (time1 > time2) {
            return 1;
        } else if (time1 < time2) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean areContentsTheSame(ChatRoomBrief oldItem, ChatRoomBrief newItem) {
        return oldItem.getChannelId() == newItem.getChannelId();
    }

    @Override
    public boolean areItemsTheSame(ChatRoomBrief item1, ChatRoomBrief item2) {
        return item1.equals(item2);
    }
}
