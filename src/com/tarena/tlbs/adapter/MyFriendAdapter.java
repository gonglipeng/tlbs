package com.tarena.tlbs.adapter;

import java.io.File;
import java.util.ArrayList;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;

import com.tarena.tlbs.R;
import com.tarena.tlbs.util.Const;
import com.tarena.tlbs.util.ImageLoaderUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyFriendAdapter extends BaseExpandableListAdapter {
	Context context;
	ArrayList<RosterGroup> listGroup;

	public MyFriendAdapter(Context context,ArrayList<RosterGroup> listGroup) {
		// TODO Auto-generated constructor stub
		this.context=context;
		if(listGroup==null){
			this.listGroup=new ArrayList<RosterGroup>();
		}else{
			this.listGroup=listGroup;
		}
	}
	
	@Override
	public int getGroupCount() {
		return listGroup.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		RosterGroup rosterGroup=listGroup.get(groupPosition);
		return rosterGroup.getEntryCount();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return listGroup.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		//获得每个组对象
		RosterGroup rosterGroup=(RosterGroup) getGroup(groupPosition);
		//获得组中的人对象
		// 得到分组下的所有好友
		ArrayList<RosterEntry> rosterEntries=new ArrayList<RosterEntry>(rosterGroup.getEntries());
		return rosterEntries.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		//有固定的Id返回true
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupViewHolder groupViewHolder=null;
		if(convertView==null){
			groupViewHolder=new GroupViewHolder();
			convertView=View.inflate(context, R.layout.my_friend_group , null);
			groupViewHolder.ivIcon=(ImageView) convertView.findViewById(R.id.iv_my_friend_group_icon);
			groupViewHolder.tvGroupName=(TextView)convertView.findViewById(R.id.tv_my_friend_group_groupName);
			convertView.setTag(groupViewHolder);
		}else{
			groupViewHolder=(GroupViewHolder) convertView.getTag();
		}
		RosterGroup rosterGroup=(RosterGroup) getGroup(groupPosition);
		String imageUrl = "http://124.207.192.18:8080/tlbsServer/tlbsMyfriendIcon/myfriend_group_icon.png";
		String fileName=imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		File file=new File(Const.IMAGE_PATH,fileName);
		if(file.exists()){
			ImageLoaderUtil.displaySdcardImage(context, file.getPath(), groupViewHolder.ivIcon);
		}else{
		    ImageLoaderUtil.displayNetImage(context, imageUrl, groupViewHolder.ivIcon);
		}
		if(groupViewHolder.ivIcon.getDrawable()==null){
			groupViewHolder.ivIcon.setImageResource(R.drawable.myfriend_group_icon);
	    }
		groupViewHolder.tvGroupName.setText(rosterGroup.getName());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		ChildViewHolder childViewHolder = null;
		if (convertView == null) {
			childViewHolder = new ChildViewHolder();
			convertView = View
					.inflate(context, R.layout.my_friend_friend, null);

			childViewHolder.ivIcon = (ImageView) convertView
					.findViewById(R.id.iv_my_friend_friend_icon);
			childViewHolder.tvFriendName = (TextView) convertView
					.findViewById(R.id.tv_my_friend_friend_friendName);
			convertView.setTag(childViewHolder);
		} else {
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}
		RosterEntry rosterEntry=(RosterEntry) getChild(groupPosition, childPosition);
		String imageUrl="http://124.207.192.18:8080/tlbsServer/tlbsMyfriendIcon/myfriend_friend_icon.png";
		String fileName=imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		File file=new File(Const.IMAGE_PATH,fileName);
		if(file.exists()){
			ImageLoaderUtil.displaySdcardImage(context, file.getPath(), childViewHolder.ivIcon);
		}else{
		    ImageLoaderUtil.displayNetImage(context, imageUrl, childViewHolder.ivIcon);
		}
		if(childViewHolder.ivIcon.getDrawable()==null){
			childViewHolder.ivIcon.setImageResource(R.drawable.myfriend_friend_icon);
		}
		childViewHolder.tvFriendName.setText(rosterEntry.getName());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		//此处为true点击事件才能响应
		return true;
	}

	class GroupViewHolder {
		ImageView ivIcon;
		TextView tvGroupName;
	}

	class ChildViewHolder {
		ImageView ivIcon;
		TextView tvFriendName;
	}

}
