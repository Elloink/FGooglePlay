package com.example.gooleplay.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gooleplay.gloable.MyApplication;
import com.example.gooleplay.utils.UiUtils;

/**
 * 用于展示排名页面的ViewGroup
 * 
 * @author admin
 *
 */
public class TopViewGroup extends ViewGroup {
	private String[] mDatas;
	private GradientDrawable pressedSharp; // TextView按下的背景
	private int horizontalPadding = UiUtils.dp2px(15); // ViewGroup中横向的padding
	private int verticalPadding = UiUtils.dp2px(13); // ViewGroup中底部和顶部的padding
	private int horizontalSpace = UiUtils.dp2px(15); // 每个Item之间的间距
	private List<Line> totalLines = new ArrayList<TopViewGroup.Line>();
	private String DEBUG_TAG = "TopViewGroup";

	public TopViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TopViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TopViewGroup(Context context) {
		super(context);
	}

	public TopViewGroup(Context context, String[] data) {
		super(context);
		Log.d(DEBUG_TAG, "进入构造函数TopViewGroup");
		mDatas = data;
		init();
	}

	/**
	 * 设置传入的数据
	 * 
	 * @param datas
	 */
	public void setDatas(String[] datas) {
		if (datas == null) {
			throw new RuntimeException("当前传入的数据为null");
		}
		mDatas = datas;
	}

	/**
	 * 初始化函数，在其中完成初始化任务 1.生成TExtView按下的背景 2.根据Text的数量添加等量的TextView
	 */
	private void init() {

		setPadding(horizontalPadding, verticalPadding, horizontalPadding,
				verticalPadding);

		// 初始化TextView按下的背景
		pressedSharp = new GradientDrawable();
		pressedSharp.setShape(GradientDrawable.RECTANGLE);
		pressedSharp.setCornerRadius(5);
		pressedSharp.setColor(0xffcecece);

		// 将TextView添加进布局中
		for (int i = 0; i < mDatas.length; i++) {
			this.addView(getTextView(mDatas[i]));
		}
		Log.d(DEBUG_TAG, "已经添加了孩子");

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		totalLines.clear();
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		Log.d(DEBUG_TAG, "在onMeasure中的size为 ： " + widthSize);

		// 进行排列
		int hAvailableWidth = widthSize - getPaddingLeft() - getPaddingRight(); // 横向减去padding之后可用的空间

		int makeMeasureSpec = MeasureSpec.makeMeasureSpec(hAvailableWidth,
				MeasureSpec.EXACTLY);
		// 测量孩子
		measureChildren(makeMeasureSpec, heightMeasureSpec);

		int avbWidth = widthSize - getPaddingLeft() - getPaddingRight();

		Line line = new Line();
		for (int count = 0; count < getChildCount(); count++) {
			View currentChild = getChildAt(count);
			int childWidth = currentChild.getMeasuredWidth();
			// 这个child大到超过一行，也加入,在onLayout中处理
			// 判断是否还能放下一个child
			if (avbWidth < childWidth || childWidth > hAvailableWidth) {
				/*
				 * 判断是否为
				 */
				if(avbWidth < childWidth) {
					// 保存剩下的空间
					line.setSurplusSpace(avbWidth + horizontalSpace);
				}

				// 放不下一个View了
				// 保存上一行
				totalLines.add(line);
				// 新建一行,并重置可用空间
				line = new Line();
				avbWidth = hAvailableWidth;
			}
			// 将item加入行中
			line.addMember(currentChild);
			avbWidth = avbWidth - childWidth - horizontalSpace;
		}
		// 将最后一行也加入集合中
		totalLines.add(line);
		line.setSurplusSpace(avbWidth + horizontalSpace);
//		checkLines();
		// 计算已经使用了的高度
		int useHeight = getChildAt(0).getMeasuredHeight() * totalLines.size()
				+ getPaddingBottom() + getPaddingTop()
				+ (totalLines.size() - 1) * verticalPadding;
		// 设置宽度和高度
		setMeasuredDimension(widthSize,
				resolveSize(useHeight, heightMeasureSpec));

	}

	private void checkLines() {
		for (Line line : totalLines) {
			System.out.print("行: ");
			for (View tv : line.members) {
				CharSequence text = ((TextView) tv).getText();
				System.out.print(text + "  ");
			}
			System.out.println(" ");
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int startLeft = l + horizontalPadding;
		int startTop = t + verticalPadding;
		int startRight = getMeasuredWidth() - horizontalPadding;
		int startBottom = verticalPadding + getChildAt(0).getMeasuredHeight();
		for (int i = 0; i < totalLines.size(); i++) {
			int left = startLeft;
			int top = startTop
					+ (getChildAt(0).getMeasuredHeight() + verticalPadding) * i;
			int bottom = startBottom
					+ (getChildAt(0).getMeasuredHeight() + verticalPadding) * i;
			int right = startRight;
			totalLines.get(i).layout(left, top, right, bottom);
		}

	}

	/**
	 * 一个对象代表一行 一行的事物交给一个Line对象自行处理
	 * 
	 * @author admin
	 *
	 */
	class Line {
		public ArrayList<View> members = null;
		// 排列后还剩余的空间
		private int surplusSpace = 0;

		/**
		 * 添加一个View进ArrayList中
		 * 
		 * @param tv
		 */
		public void addMember(View tv) {
			if (members == null) {
				members = new ArrayList<View>();
			}
			members.add(tv);
		}

		/**
		 * 设置剩余的空间
		 * 
		 * @param space
		 */
		public void setSurplusSpace(int space) {
			surplusSpace = space;
		}

		public int getMemberCount() {
			return members.size();
		}

		// 处理layout
		public void layout(int l, int t, int r, int b) {

			// 根据剩余空间，算出每个item可以多得到的空间数
			int sp = surplusSpace / getMemberCount();

			int left = l ;
			int right = members.get(0).getMeasuredWidth() + left  + sp;
			for (int i = 0; i < members.size(); i++) {
				View child = members.get(i);
				//设置padding , 设置了然后当textView在绘制的时候就会对应的将padding部分绘制进去
				child.setPadding(child.getPaddingLeft() + sp / 2,
						child.getPaddingTop(),
						child.getPaddingRight() + sp / 2,
						child.getPaddingBottom());
				child.layout(left, t, right , b);

				if (i == (members.size() - 1)) {
					break;
				}

				left = right + horizontalSpace;
				right = left + members.get(i + 1).getMeasuredWidth()  + sp;
			}
		}
	}

	/*
	 * 获得一个已经设置好属性的TextView
	 */
	private TextView getTextView(final String str) {
		// 初始化一个TextView
		TextView tv = new TextView(MyApplication.getContext());

		// 设置布局属性
		ViewGroup.LayoutParams layoutParams = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(layoutParams);

		tv.setText(str);
		tv.setTextSize(UiUtils.dp2px(10));
		tv.setPadding(UiUtils.dp2px(3), UiUtils.dp2px(3), UiUtils.dp2px(3),
				UiUtils.dp2px(3));
		tv.setTextColor(Color.WHITE);
		tv.setClickable(true); // 让textView可以被单击

		// 生成默认的背景
		GradientDrawable backSharp = new GradientDrawable();
		backSharp.setShape(GradientDrawable.RECTANGLE);
		backSharp.setCornerRadius(5);
		backSharp.setColor(getRadomColor());

		// 生成状态选择器
		StateListDrawable selectorDrawable = new StateListDrawable();
		selectorDrawable.addState(new int[] { android.R.attr.state_pressed },
				pressedSharp);
		selectorDrawable.addState(new int[] {}, backSharp);

		// 设置背景
		tv.setBackgroundDrawable(selectorDrawable);

		// 给TextView添加单击事件
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MyApplication.getContext(), str,
						Toast.LENGTH_SHORT).show();
			}
		});

		return tv;
	}

	/*
	 * 获得一个随机生成的Color
	 */
	private int getRadomColor() {
		Random random = new Random();
		int r = random.nextInt(200) + 22;
		int g = random.nextInt(200) + 22;
		int b = random.nextInt(200) + 22;
		int rgb = Color.rgb(r, g, b);
		return rgb;
	}
}
