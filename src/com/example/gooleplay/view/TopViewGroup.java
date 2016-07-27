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
 * ����չʾ����ҳ���ViewGroup
 * 
 * @author admin
 *
 */
public class TopViewGroup extends ViewGroup {
	private String[] mDatas;
	private GradientDrawable pressedSharp; // TextView���µı���
	private int horizontalPadding = UiUtils.dp2px(15); // ViewGroup�к����padding
	private int verticalPadding = UiUtils.dp2px(13); // ViewGroup�еײ��Ͷ�����padding
	private int horizontalSpace = UiUtils.dp2px(15); // ÿ��Item֮��ļ��
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
		Log.d(DEBUG_TAG, "���빹�캯��TopViewGroup");
		mDatas = data;
		init();
	}

	/**
	 * ���ô��������
	 * 
	 * @param datas
	 */
	public void setDatas(String[] datas) {
		if (datas == null) {
			throw new RuntimeException("��ǰ���������Ϊnull");
		}
		mDatas = datas;
	}

	/**
	 * ��ʼ����������������ɳ�ʼ������ 1.����TExtView���µı��� 2.����Text��������ӵ�����TextView
	 */
	private void init() {

		setPadding(horizontalPadding, verticalPadding, horizontalPadding,
				verticalPadding);

		// ��ʼ��TextView���µı���
		pressedSharp = new GradientDrawable();
		pressedSharp.setShape(GradientDrawable.RECTANGLE);
		pressedSharp.setCornerRadius(5);
		pressedSharp.setColor(0xffcecece);

		// ��TextView��ӽ�������
		for (int i = 0; i < mDatas.length; i++) {
			this.addView(getTextView(mDatas[i]));
		}
		Log.d(DEBUG_TAG, "�Ѿ�����˺���");

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		totalLines.clear();
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		Log.d(DEBUG_TAG, "��onMeasure�е�sizeΪ �� " + widthSize);

		// ��������
		int hAvailableWidth = widthSize - getPaddingLeft() - getPaddingRight(); // �����ȥpadding֮����õĿռ�

		int makeMeasureSpec = MeasureSpec.makeMeasureSpec(hAvailableWidth,
				MeasureSpec.EXACTLY);
		// ��������
		measureChildren(makeMeasureSpec, heightMeasureSpec);

		int avbWidth = widthSize - getPaddingLeft() - getPaddingRight();

		Line line = new Line();
		for (int count = 0; count < getChildCount(); count++) {
			View currentChild = getChildAt(count);
			int childWidth = currentChild.getMeasuredWidth();
			// ���child�󵽳���һ�У�Ҳ����,��onLayout�д���
			// �ж��Ƿ��ܷ���һ��child
			if (avbWidth < childWidth || childWidth > hAvailableWidth) {
				/*
				 * �ж��Ƿ�Ϊ
				 */
				if(avbWidth < childWidth) {
					// ����ʣ�µĿռ�
					line.setSurplusSpace(avbWidth + horizontalSpace);
				}

				// �Ų���һ��View��
				// ������һ��
				totalLines.add(line);
				// �½�һ��,�����ÿ��ÿռ�
				line = new Line();
				avbWidth = hAvailableWidth;
			}
			// ��item��������
			line.addMember(currentChild);
			avbWidth = avbWidth - childWidth - horizontalSpace;
		}
		// �����һ��Ҳ���뼯����
		totalLines.add(line);
		line.setSurplusSpace(avbWidth + horizontalSpace);
//		checkLines();
		// �����Ѿ�ʹ���˵ĸ߶�
		int useHeight = getChildAt(0).getMeasuredHeight() * totalLines.size()
				+ getPaddingBottom() + getPaddingTop()
				+ (totalLines.size() - 1) * verticalPadding;
		// ���ÿ�Ⱥ͸߶�
		setMeasuredDimension(widthSize,
				resolveSize(useHeight, heightMeasureSpec));

	}

	private void checkLines() {
		for (Line line : totalLines) {
			System.out.print("��: ");
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
	 * һ���������һ�� һ�е����ｻ��һ��Line�������д���
	 * 
	 * @author admin
	 *
	 */
	class Line {
		public ArrayList<View> members = null;
		// ���к�ʣ��Ŀռ�
		private int surplusSpace = 0;

		/**
		 * ���һ��View��ArrayList��
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
		 * ����ʣ��Ŀռ�
		 * 
		 * @param space
		 */
		public void setSurplusSpace(int space) {
			surplusSpace = space;
		}

		public int getMemberCount() {
			return members.size();
		}

		// ����layout
		public void layout(int l, int t, int r, int b) {

			// ����ʣ��ռ䣬���ÿ��item���Զ�õ��Ŀռ���
			int sp = surplusSpace / getMemberCount();

			int left = l ;
			int right = members.get(0).getMeasuredWidth() + left  + sp;
			for (int i = 0; i < members.size(); i++) {
				View child = members.get(i);
				//����padding , ������Ȼ��textView�ڻ��Ƶ�ʱ��ͻ��Ӧ�Ľ�padding���ֻ��ƽ�ȥ
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
	 * ���һ���Ѿ����ú����Ե�TextView
	 */
	private TextView getTextView(final String str) {
		// ��ʼ��һ��TextView
		TextView tv = new TextView(MyApplication.getContext());

		// ���ò�������
		ViewGroup.LayoutParams layoutParams = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(layoutParams);

		tv.setText(str);
		tv.setTextSize(UiUtils.dp2px(10));
		tv.setPadding(UiUtils.dp2px(3), UiUtils.dp2px(3), UiUtils.dp2px(3),
				UiUtils.dp2px(3));
		tv.setTextColor(Color.WHITE);
		tv.setClickable(true); // ��textView���Ա�����

		// ����Ĭ�ϵı���
		GradientDrawable backSharp = new GradientDrawable();
		backSharp.setShape(GradientDrawable.RECTANGLE);
		backSharp.setCornerRadius(5);
		backSharp.setColor(getRadomColor());

		// ����״̬ѡ����
		StateListDrawable selectorDrawable = new StateListDrawable();
		selectorDrawable.addState(new int[] { android.R.attr.state_pressed },
				pressedSharp);
		selectorDrawable.addState(new int[] {}, backSharp);

		// ���ñ���
		tv.setBackgroundDrawable(selectorDrawable);

		// ��TextView��ӵ����¼�
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
	 * ���һ��������ɵ�Color
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
