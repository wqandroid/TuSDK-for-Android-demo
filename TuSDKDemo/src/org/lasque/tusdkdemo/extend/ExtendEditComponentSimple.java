/** 
 * TuSdkDemo
 * EditComponentSimple.java
 *
 * @author 		Clear
 * @Date 		2015-4-21 下午1:33:49 
 * @Copyright 	(c) 2015 Lasque. All rights reserved.
 * 
 */
package org.lasque.tusdkdemo.extend;

import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.core.utils.image.BitmapHelper;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.base.TuSdkHelperComponent;
import org.lasque.tusdk.impl.components.edit.TuEditTurnAndCutFragment;
import org.lasque.tusdk.impl.components.edit.TuEditTurnAndCutFragment.TuEditTurnAndCutFragmentDelegate;
import org.lasque.tusdk.impl.components.edit.TuEditTurnAndCutOption;
import org.lasque.tusdkdemo.R;
import org.lasque.tusdkdemo.simple.SimpleBase;

import android.app.Activity;

/**
 * 图片编辑组件范例
 * 
 * @author Clear
 */
public class ExtendEditComponentSimple extends SimpleBase implements
		TuEditTurnAndCutFragmentDelegate
{
	/**
	 * 图片编辑组件范例
	 */
	public ExtendEditComponentSimple()
	{
		super(3, R.string.extend_EditComponent);
	}

	/**
	 * 显示范例
	 * 
	 * @param activity
	 */
	@Override
	public void showSimple(Activity activity)
	{
		if (activity == null) return;

		// 组件选项配置
		// @see-http://www.tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditTurnAndCutOption.html
		TuEditTurnAndCutOption option = new TuEditTurnAndCutOption();
		// 控制器类型
		// option.setComponentClazz(TuEditTurnAndCutFragment.class);

		// 设置根视图布局资源ID
		// option.setRootViewLayoutId(TuEditTurnAndCutFragment.getLayoutId());

		// 保存到临时文件 (默认不保存, 当设置为true时, TuSdkResult.imageFile, 处理完成后将自动清理原始图片)
		// option.setSaveToTemp(false);

		// 保存到系统相册 (默认不保存, 当设置为true时, TuSdkResult.sqlInfo, 处理完成后将自动清理原始图片)
		// option.setSaveToAlbum(true);

		// 保存到系统相册的相册名称
		// option.setSaveToAlbumName("TuSdk");

		// 照片输出压缩率 (默认:90，0-100 如果设置为0 将保存为PNG格式)
		// option.setOutputCompress(90);

		// 是否开启滤镜支持 (默认: 关闭)
		option.setEnableFilters(true);

		// 需要显示的滤镜名称列表 (如果为空将显示所有自定义滤镜)
		// option.setFilterGroup(new ArrayList<String>());

		// 需要裁剪的长宽
		// option.setCutSize(new TuSdkSize(640, 640));

		// 是否显示处理结果预览图 (默认：关闭，调试时可以开启)
		// option.setShowResultPreview(false);

		// 滤镜组行视图宽度 (单位:DP)
		// option.setGroupFilterCellWidthDP(75);

		// 滤镜组选择栏高度 (单位:DP)
		// option.setFilterBarHeightDP(100);

		// 滤镜分组列表行视图布局资源ID (默认:
		// tusdk_impl_component_widget_group_filter_group_view，如需自定义请继承自
		// GroupFilterGroupView)
		// option.setGroupTableCellLayoutId(GroupFilterGroupView.getLayoutId());

		// 滤镜列表行视图布局资源ID (默认:
		// tusdk_impl_component_widget_group_filter_item_view，如需自定义请继承自
		// GroupFilterItemView)
		// option.setFilterTableCellLayoutId(GroupFilterItemView.getLayoutId());

		// 是否在控制器结束后自动删除临时文件
		option.setAutoRemoveTemp(true);

		TuEditTurnAndCutFragment fragment = option.fragment();

		// 输入的图片对象 (处理优先级: Image > TempFilePath > ImageSqlInfo)
		fragment.setImage(BitmapHelper.getRawBitmap(activity,
				R.raw.sample_photo));

		// 输入的临时文件目录 (处理优先级: Image > TempFilePath > ImageSqlInfo)
		// editFragment.setTempFilePath(result.imageFile);

		// 输入的相册图片对象 (处理优先级: Image > TempFilePath > ImageSqlInfo)
		// editFragment.setImageSqlInfo(result.imageSqlInfo);

		fragment.setDelegate(this);

		// see-http://www.tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/base/TuSdkHelperComponent.html
		this.componentHelper = new TuSdkHelperComponent(activity);
		// 开启相机
		this.componentHelper.presentModalNavigationActivity(fragment);
	}

	/**
	 * 图片编辑完成
	 * 
	 * @param fragment
	 *            旋转和裁剪视图控制器
	 * @param result
	 *            旋转和裁剪视图控制器处理结果
	 */
	@Override
	public void onTuEditTurnAndCutFragmentEdited(
			TuEditTurnAndCutFragment fragment, TuSdkResult result)
	{
		if (!fragment.isShowResultPreview())
		{
			fragment.hubDismissRightNow();
			fragment.dismissActivityWithAnim();
		}
		TLog.d("onTuEditTurnAndCutFragmentEdited: %s", result);
	}

	/**
	 * 图片编辑完成 (异步方法)
	 * 
	 * @param fragment
	 *            旋转和裁剪视图控制器
	 * @param result
	 *            旋转和裁剪视图控制器处理结果
	 * @return 是否截断默认处理逻辑 (默认: false, 设置为True时使用自定义处理逻辑)
	 */
	@Override
	public boolean onTuEditTurnAndCutFragmentEditedAsync(
			TuEditTurnAndCutFragment fragment, TuSdkResult result)
	{
		TLog.d("onTuEditTurnAndCutFragmentEditedAsync: %s", result);
		return false;
	}

	@Override
	public void onComponentError(TuFragment fragment, TuSdkResult result,
			Error error)
	{
		TLog.d("onComponentError: fragment - %s, result - %s, error - %s",
				fragment, result, error);
	}
}
